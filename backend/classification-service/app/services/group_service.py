from typing import Dict, List

from sklearn.ensemble import RandomForestClassifier
from gensim.sklearn_api import D2VTransformer

from app import app, base_path, vk_api, kafka_producer

import datetime
import logging
import pickle
import os

from app.utils.classification_utils import ClassificationUtils
from app.managers.influx.prepare_text import TextPrettifier


class GroupService:
    """
    Класс для работы с данными групп ВК
    """

    def __init__(self):

        self.logger = logging.getLogger(__name__)
        self.categories = ["aggregate", "games", "local"]
        self.classification_topic = app.config.kafka.influx_topics[1]

    def get_vk_data(self) -> List[Dict]:
        """
        Метод для получения данных, необходимых для формирования модели обучения
        """

        with app.open_resource(app.config.schedulers.influx.file_path) as f:
            ids = [ClassificationUtils.clear_file_string(string_to_clean=str(line)) for line in f]

        result = []

        for item in ids:

            item_data = item.split(" ")

            vk_resp = vk_api.wall.get(owner_id=int(item_data[0]), count=app.config.schedulers.influx.records_count)

            for new_item in vk_resp["items"]:

                current_time = datetime.datetime.now()

                dict_to_send = {

                    "group_id": str(item_data[0]),
                    "creation_time": current_time.strftime("%d.%m.%Y %H:%M:%S"),
                    "post_text": ClassificationUtils.clear_post_text(new_item["text"]),
                    "post_type": str(item_data[1]),
                    "post_id": str(new_item["id"])

                }

                self.logger.info(f"Полученные данные: {dict_to_send}")
                result.append(dict_to_send)

        return result

    def get_vk_group_prediction(self, payload: dict):
        """
        Метод для опредления типа группы ВК
        :param payload: тело запроса
        """

        username = payload["username"]
        link = payload["link"]

        data = {
           "username": username,
           "link": link,
           "link_type": "VK_GROUP"
        }

        link_topic = app.config.kafka.postgres_topics[0]

        kafka_producer.send(topic=link_topic, value=data)
        kafka_producer.flush()

        model_path = \
            os.path.join(base_path, f"{app.config.management.influx.model_store_path}", "group_model_data.dump")

        classifier: RandomForestClassifier = pickle.load(open(model_path, 'rb'))
        vectorizer = D2VTransformer(min_count=1, size=5, dm=1, dm_concat=1)

        link = link.replace("https://vk.com/public", "-")
        self.logger.info(f"LINK: {link}")

        vk_resp = vk_api.wall.get(owner_id=int(link), count=1)

        self.logger.info(f"Получен ответ от ВК: {vk_resp}")

        item = vk_resp["items"][0]

        text_to_classify = vectorizer.fit_transform([TextPrettifier.get_prepared_text(item["text"])])

        result_index = classifier.predict(text_to_classify)[0]

        current_time = datetime.datetime.now()

        dict_to_send = {

            "group_id": link,
            "creation_time": current_time.strftime("%d.%m.%Y %H:%M:%S"),
            "post_text": ClassificationUtils.clear_post_text(item["text"]),
            "post_type": self.categories[result_index],
            "post_id": str(item["id"]),
            "username": username

        }

        kafka_producer.send(topic=self.classification_topic, value=dict_to_send)
        kafka_producer.flush()

        return self.categories[result_index]
