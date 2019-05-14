from app import app, base_path

import requests
import os
import csv
import datetime
import logging


class VKUserDataUploader:
    """
    Получение данных по пользователям ВК
    """

    def __init__(self, retrieve_url: str, directory_path: str):
        self.retrieve_url = retrieve_url
        self.directory_path = directory_path
        self.logger = logging.getLogger(__name__)

    def __get_record_value(self, item: dict):

        if "title" in item:
            item_id = item["id"]
            title = item["title"]
            return f"{item_id}_{title}"
        elif "time" in item:
            time = item["time"]
            platform = item["platform"]
            return f"{time}_{platform}"
        elif "photos" in item:
            photos = item["photos"]
            friends = item["friends"]
            return f"{photos}_{friends}"

    def retrieve_data(self):

        self.logger.info("Начинаем получение данных по группам ВК...")

        resp = requests.get(self.retrieve_url)

        if resp.status_code == 200:
            data = resp.json()
        else:
            raise ValueError(f"Невозможно получить данные с сервиса по пользователям ВК, причина: {resp.text}")

        headers = list(data[0].keys())
        users_data = []

        for i in range(len(data)):

            temp_data = []

            for item in data[i].values():

                if not isinstance(item, dict):
                    temp_data.append(item)
                else:
                    temp_data.append(self.__get_record_value(item))

                users_data.append(temp_data)

        current_date = datetime.datetime.today().strftime("%d-%m-%Y")

        directory_for_data = os.path.join(base_path, self.directory_path, f"data_{current_date}")

        if not os.path.isdir(directory_for_data):
            os.mkdir(directory_for_data) 

        file_for_data = os.path.join(directory_for_data, "{0}.csv".format(current_date))

        with open(file_for_data, 'a+', encoding='utf-8') as f:
            csvwriter = csv.writer(f, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL)
            csvwriter.writerow(headers)

            for item in users_data:

                csvwriter.writerow(item)

            self.logger.info("Загрузка данных завершена")
