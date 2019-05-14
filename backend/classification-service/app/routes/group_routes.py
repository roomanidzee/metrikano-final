from flask_restplus import Resource, fields

from app import app, api, classification_namespace, classification_response

from app.services.group_service import GroupService

group_request_dto = api.model('GroupRequestDTO', {
    'username': fields.String(required=True, description='Никнейм пользователя в системе'),
    'link': fields.String(required=True, description='Ссылка на группу для классификации')
})


@classification_namespace.route('/groups-vk/classify')
class VKGroupsClassification(Resource):
    """
    Ресурс для классификации групп ВК
    """

    @classification_namespace.doc('Эндпоинт для классификации групп ВК')
    @classification_namespace.expect(group_request_dto)
    @classification_namespace.marshal_with(classification_response)
    def post(self):

        group_service = GroupService()
        result = group_service.get_vk_group_prediction(api.payload)

        return {'result': result}
