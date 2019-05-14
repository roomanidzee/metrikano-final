from sklearn.ensemble import RandomForestClassifier
from sklearn.datasets import load_files

from gensim.sklearn_api import D2VTransformer

import pickle
import os
import logging

from app.managers.influx.prepare_text import TextPrettifier

from app import app, base_path


class GroupDataClassifier:
    """
    Класс для формирования модели обучения по группам
    """

    def __init__(self, directory_path: str, model_store_path: str):
        self.directory_path = directory_path
        self.model_store_path = model_store_path
        self.classifier = RandomForestClassifier(n_estimators=1000, random_state=0)
        self.vectorizer = D2VTransformer(min_count=1, size=5, dm=1, dm_concat=1)
        self.logger = logging.getLogger(__name__)

    def prepare_model(self):

        final_data_path = os.path.join(base_path, self.directory_path)
        final_model_path = os.path.join(base_path, self.model_store_path, "group_model_data.dump")

        group_data = load_files(container_path=final_data_path)
        X, y = group_data.data, group_data.target

        documents = []

        for item in X:

            temp_documents = []

            for sen in range(0, len(item)):

                document = TextPrettifier.get_prepared_text(str(item[sen]))
                temp_documents.append(document)

            documents.extend(self.vectorizer.fit_transform(temp_documents))

        self.logger.info(f"X: {documents}, y: {y}")
        self.logger.info(f"X LEN: {len(documents)}, y LEN: {len(y)}")

        self.classifier.fit(documents, y)

        with open(final_model_path, 'wb') as f:
            pickle.dump(self.classifier, f)
