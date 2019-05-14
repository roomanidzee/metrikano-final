from apscheduler.schedulers.background import BackgroundScheduler

from app import app, kafka_producer

import logging

from app.services.user_service import UserService


class ClickHouseUserCollectScheduler:
    """
    Периодическое задание для отправки данных в ClickHouse
    """

    def __init__(self):
        self.topic = app.config.kafka.clickhouse_topics[0]
        self.logger = logging.getLogger(__name__)
        self.user_service = UserService()

    def __produce_data(self):

        data = self.user_service.get_user_data()

        for item in data:

            self.logger.info(f"Готовимся отправить сообщение: {item}")

            kafka_producer.send(topic=self.topic, value=item)
            kafka_producer.flush()

    def get_scheduler(self):

        scheduler = BackgroundScheduler()
        scheduler.add_job(func=self.__produce_data, trigger="interval",
                          seconds=app.config.schedulers.clickhouse.interval)
        return scheduler
