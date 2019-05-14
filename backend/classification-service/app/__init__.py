from flask import Flask
from flask_cors import CORS
from flask_restplus import Api, fields
from flask_consulate import Consul

from dynaconf import FlaskDynaconf

from kafka import KafkaProducer

import logging
from logging.handlers import TimedRotatingFileHandler

import datetime

import atexit
import json
import os

import vk

app = Flask(__name__)
CORS(app, supports_credentials=True, resources=r'/api/*')

FlaskDynaconf(app)

consul = Consul(app=app)

# конфигурация для Consul
consul.register_service(
    name=app.config.consul.name,
    interval=app.config.consul.interval,
    tags=app.config.consul.tags,
    port=app.config.consul.port,
    httpcheck=app.config.consul.httpcheck
)

base_path = os.path.dirname(os.path.abspath(__file__))

api = Api(app, version='0.0.1', title='Metrikano Classification API',
          description='Классификация данных в сервисе Metrikano',
          doc=app.config.docs, prefix=app.config.prefix)

# тип ответа для результата классификации
classification_response = api.model('ClassificationResponseDTO', {
    'result': fields.String(required=True, description="Результат классификации")
})

consul_namespace = api.namespace('consul', description='Эндпоинты для Consul')
classification_namespace = api.namespace('classification', description='Эндпоинты для моделей классификации')


# конфигурация логирования выполнения сервиса
log_path = os.path.join(base_path, app.config.logs.directory)
current_time = datetime.datetime.now().strftime("%d.%m.%Y %H:%M:%S")

logging.basicConfig(
    level=logging.INFO,
    format=app.config.logs.format,
    handlers=[
        TimedRotatingFileHandler(filename=f"{log_path}/classification-service.({current_time}).log", encoding='utf-8',
                                 when="d", backupCount=5, interval=1),
        logging.StreamHandler()
    ]
)

# обработка ошибок
@api.errorhandler
def default_handler(error):
    return {
        "error": {
            "service_name": "classification-service",
            "status_code": getattr(error, 'code', 500),
            "time": datetime.datetime.now().strftime("%d.%m.%Y %H:%M:%S"),
            "message": str(error),
            "debug_message": type(error).__name__
        }
    }, getattr(error, 'code', 500)


# конфигурация для клиента API ВК
access_key = app.config.vk.access_key
vk_api_version = app.config.vk.api_version
vk_api_lang = app.config.vk.lang
vk_session = vk.Session(access_token=access_key)
vk_api = vk.API(session=vk_session, v=vk_api_version, lang=vk_api_lang)

# конфиграция отправителя сообщений в Apache Kafka и периодических заданий
kafka_producer = KafkaProducer(bootstrap_servers=f"{app.config.kafka.host}:{app.config.kafka.port}",
                               value_serializer=lambda v: json.dumps(v).encode('utf-8'),
                               linger_ms=app.config.kafka.linger_ms)

if app.config.schedulers.produce_data:
 
    from app.schedulers.influx.group_transfer import InfluxGroupCollectScheduler
    from app.schedulers.clickhouse.user_transfer import ClickHouseUserCollectScheduler

    influx_task = InfluxGroupCollectScheduler().get_scheduler()
    influx_task.start()

    clickhouse_task = ClickHouseUserCollectScheduler().get_scheduler()
    clickhouse_task.start()

    atexit.register(lambda: influx_task.shutdown())
    atexit.register(lambda: clickhouse_task.shutdown())

# импортирование ресурсов для их инициализации
from app.routes import consul_routes, classification_routes, group_routes, user_routes    
