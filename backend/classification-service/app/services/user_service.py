from typing import Dict, List

from sklearn.ensemble import RandomForestClassifier

from app import app, base_path, vk_api, kafka_producer

import datetime
import pickle
import os
import logging

from app.utils.classification_utils import ClassificationUtils


class UserService:
    """
    Класс для работы с данными пользователей ВК
    """

    def __init__(self):

        self.logger = logging.getLogger(__name__)
        self.classification_topic = app.config.kafka.clickhouse_topics[1]

        self.fields_to_know = ("about activities can_post can_see_all_post career city connections counters country "
                               "has_photo last_seen online")

        self.fields_to_classify = "city country has_photo online can_post last_seen counters"
        self.additional_fields = ["id", "first_name", "last_name", "is_closed", "can_access_closed"]

        self.fields = ','.join(self.fields_to_know.split(" "))

        self.send_fields = self.fields_to_know.split(" ")
        self.send_fields.extend(self.additional_fields)

        self.compare_dict = {
            420: "fake",
            407: "real"
        }

    def get_user_data(self) -> List[Dict]:
        """
        Метод для получения данных, необходимых для формирования модели обучения
        """

        with app.open_resource(app.config.schedulers.clickhouse.file_path) as f:

            cleaned_list = [ClassificationUtils.clear_file_string(string_to_clean=str(line)) for line in f]

            temp_ids_list = []
            dict_of_ids = {}

            for item in cleaned_list:
                temp_str = item.split(" ")
                temp_ids_list.append(temp_str[0])
                dict_of_ids[temp_str[0]] = temp_str[1]

            ids = ','.join(temp_ids_list)

        result = []

        vk_resp = vk_api.users.get(user_ids=ids, fields=self.fields)

        current_time = datetime.datetime.now()

        for i in range(0, len(vk_resp)):

            dict_to_send = {item: vk_resp[i][item] if item in vk_resp[i] else '' for item in self.send_fields}
            dict_to_send["creation_time"] = current_time.strftime("%d.%m.%Y %H:%M:%S")
            dict_to_send["user_id"] = vk_resp[i]["id"]
            dict_to_send["user_type"] = dict_of_ids[str(dict_to_send["user_id"])]
            del dict_to_send["id"]

            self.logger.info(f"Полученные данные: {dict_to_send}")
            result.append(dict_to_send)

        return result

    def get_vk_user_prediction(self, payload: dict):
        """
        Метод для проверки пользователя ВК
        :param payload: тело запроса
        """

        username = payload["username"]
        link = payload["link"]

        data = {
            "username": username,
            "link": link,
            "link_type": "VK_USER"
        }

        link_topic = app.config.kafka.postgres_topics[0]

        kafka_producer.send(topic=link_topic, value=data)
        kafka_producer.flush()

        model_path = \
            os.path.join(base_path, f"{app.config.management.clickhouse.model_store_path}", "vk_user_model_data.dump")
        classifier: RandomForestClassifier = pickle.load(open(model_path, 'rb'))

        if "id" in link:
            user_id: str = link.replace("https://vk.com/id", "")
        else:
            user_id: str = link.replace("https://vk.com/", "")

        self.logger.info(f"USER ID : {user_id}")    

        vk_resp = vk_api.users.get(user_ids=user_id, fields=self.fields)

        info_to_know = []
        info_to_know.extend(self.additional_fields)
        info_to_know.extend(self.fields_to_classify.split(" "))

        current_time = datetime.datetime.now()

        prepared_dict = {}

        for item in info_to_know:

            if item in vk_resp[0]:
                prepared_dict[item] = vk_resp[0][item]
            else:
                prepared_dict[item] = ""    

        prepared_dict["user_id"] = vk_resp[0]["id"]
        prepared_dict["user_type"] = -1
        prepared_dict["creation_time"] = current_time.strftime("%d.%m.%Y %H:%M:%S")

        classify_list = []
        hard_columns = ["city", "country", "last_seen", "counters"]

        classify_list.append(prepared_dict["user_id"])
        classify_list.append(prepared_dict["user_type"])

        del prepared_dict["user_id"]
        del prepared_dict["user_type"]

        for elem in prepared_dict.keys():

            if elem in hard_columns:

                if isinstance(elem, dict):
                    elem_values = list(prepared_dict[elem].values())
                    classify_list.append(f"{elem_values[0]}_{elem_values[1]}")
                else:
                    classify_list.append(elem) 

            else:
                classify_list.append(prepared_dict[elem])

        for index, item in enumerate(classify_list):

            if isinstance(item, str):
                classify_list[index] = ClassificationUtils.string_to_number(item)

        predict_result = self.compare_dict[classifier.predict([classify_list])[0]]

        dict_to_send = {item: vk_resp[0][item] if item in vk_resp[0] else '' for item in self.send_fields}
        dict_to_send["creation_time"] = current_time.strftime("%d.%m.%Y %H:%M:%S")
        dict_to_send["user_id"] = vk_resp[0]["id"]
        dict_to_send["user_type"] = predict_result
        dict_to_send["username"] = "username"
        del dict_to_send["id"]

        kafka_producer.send(topic=self.classification_topic, value=dict_to_send)
        kafka_producer.flush()

        return predict_result
