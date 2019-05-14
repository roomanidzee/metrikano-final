from flask_restplus import Resource, fields

from app import app, api, classification_namespace

from app.managers.clickhouse.load_data import VKUserDataUploader
from app.managers.influx.load_data import GroupDataUploader

from app.managers.clickhouse.prepare_model import VKUserDataClassifier
from app.managers.influx.prepare_model import GroupDataClassifier

classification_simple_dto = api.model('ClassificationSimpleDTO', {
    'message': fields.String(required=True, description='Информация о процессе создания модели')
})


@classification_namespace.route('/groups-vk/create_ml')
class VKGroupModelCreation(Resource):
    """
    Ресурсы для создания моделей машинного обучения
    """

    @classification_namespace.doc('Создание модели распознавания групп ВК')
    @classification_namespace.marshal_with(classification_simple_dto)
    def post(self):

        groups_info = GroupDataUploader(retrieve_url=app.config.management.influx.load_url,
                                        directory_path=app.config.management.influx.directory_path)
        groups_info.retrieve_data()

        group_data_classifier = GroupDataClassifier(directory_path=app.config.management.influx.directory_path,
                                                    model_store_path=app.config.management.influx.model_store_path)
        group_data_classifier.prepare_model()

        return {'message': 'Модель обучения по группам ВК создана'}


@classification_namespace.route('/users-vk/create_ml')
class VKUserModelCreation(Resource):

    @classification_namespace.doc('Создание модели распознавания пользователей ВК')
    @classification_namespace.marshal_with(classification_simple_dto)
    def post(self):

        users_info = VKUserDataUploader(retrieve_url=app.config.management.clickhouse.load_url,
                                        directory_path=app.config.management.clickhouse.directory_path)

        users_info.retrieve_data()

        users_data_classifier = VKUserDataClassifier(directory_path=app.config.management.clickhouse.directory_path,
                                                     model_store_path=app.config.management.clickhouse.model_store_path)

        users_data_classifier.prepare_model()

        return {'message': 'Модель обучения по пользователям ВК создана'}
