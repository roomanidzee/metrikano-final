from flask_restplus import Resource, fields
from app import app, api, classification_namespace, classification_response

from app.services.user_service import UserService

user_request_dto = api.model('UserRequestDTO', {
    'username': fields.String(required=True, description='Никнейм пользователя в системе'),
    'link': fields.String(required=True, description='Ссылка на профиль пользователя в ВК')
})


@classification_namespace.route('/users-vk/classify')
class VKUsersClassification(Resource):
    """
    Ресурс для классификации пользователей ВК
    """

    @classification_namespace.doc('Эндпоинт для классификации пользователей ВК')
    @classification_namespace.expect(user_request_dto)
    @classification_namespace.marshal_with(classification_response)
    def post(self):

        user_service = UserService()
        result = user_service.get_vk_user_prediction(api.payload)

        return {"result": result}
