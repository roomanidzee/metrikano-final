from sklearn.ensemble import RandomForestClassifier

import pickle
import csv
import datetime
import os
import logging

from app import app, base_path
from app.utils.classification_utils import ClassificationUtils


class VKUserDataClassifier:
    """
    Формирование модели обучения по пользователям ВКонтакте
    """

    def __init__(self, directory_path: str, model_store_path: str):
        self.directory_path = directory_path
        self.model_store_path = model_store_path
        self.classifier = RandomForestClassifier(n_estimators=1000, random_state=0)
        self.logger = logging.getLogger(__name__)

    def prepare_model(self):
        final_data_path = os.path.join(base_path, self.directory_path)
        final_model_path = os.path.join(base_path, self.model_store_path, "vk_user_model_data.dump")
        current_date = datetime.datetime.today().strftime("%d-%m-%Y")

        file_name = os.path.join(final_data_path, f"data_{current_date}/{current_date}.csv")

        with open(file_name, 'r') as f:
            reader = csv.reader(f)

            x_array = [item for item in reader]

            for index, item in enumerate(x_array):

                for convert_index, convert_item in enumerate(item):

                    if isinstance(convert_item, str):
                        item[convert_index] = ClassificationUtils.string_to_number(convert_item)

                x_array[index] = item

            y_array = [item[1] for item in x_array]

            del x_array[0]
            del y_array[0]

            self.classifier.fit(x_array, y_array)

        with open(final_model_path, 'wb') as f:
            pickle.dump(self.classifier, f)
