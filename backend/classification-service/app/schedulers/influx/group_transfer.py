from apscheduler.schedulers.background import BackgroundScheduler

from app import app, kafka_producer
import logging

from app.services.group_service import GroupService


class InfluxGroupCollectScheduler:
    """
    Периодическое задание для отправки данных в InfluxDB
    """

    def __init__(self):

        self.topic = app.config.kafka.influx_topics[0]
        self.logger = logging.getLogger(__name__)
        self.group_service = GroupService()

    def __produce_data(self):

        data = self.group_service.get_vk_data()

        for item in data:
            self.logger.info(f"Готовимся отправить сообщение: {item}")

            kafka_producer.send(topic=self.topic, value=item)
            kafka_producer.flush()

    def get_scheduler(self):

        scheduler = BackgroundScheduler()
        scheduler.add_job(func=self.__produce_data, trigger="interval", 
                          seconds=app.config.schedulers.influx.interval)
        return scheduler
