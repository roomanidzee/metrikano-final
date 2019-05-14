from app import app, base_path

import requests
import os
import logging


class GroupDataUploader:
    """
    Класс для получения данных по группам из API ВКонтакте
    """

    def __init__(self, retrieve_url: str, directory_path: str):
        self.retrieve_url = retrieve_url
        self.directory_path = directory_path
        self.logger = logging.getLogger(__name__)

    def retrieve_data(self):

        self.logger.info("Начинаем получение данных по группам ВК...")

        resp = requests.get(self.retrieve_url)

        if resp.status_code == 200:
            data = resp.json()
        else:
            raise ValueError(f"Невозможно получить данные с сервиса по группам, причина: {resp.text}")

        for i in range(len(data)):

            post_type = data[i]["post_type"]
            directory_for_type = os.path.join(base_path, self.directory_path, post_type)

            if not os.path.isdir(directory_for_type):
                os.mkdir(directory_for_type)

            file_for_group = os.path.join(directory_for_type, "{0}.txt".format(data[i]["group_id"]))

            with open(file_for_group, 'a+', encoding='utf-8') as f:
                f.write(data[i]["post_text"])
                f.write(" ")

        self.logger.info("Загрузка данных завершена")
