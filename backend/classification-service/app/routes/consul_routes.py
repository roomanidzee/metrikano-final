from flask_restplus import Resource, fields

from app import api, consul_namespace

consul_dto = api.model('ConsulDTO', {
    'status': fields.String(required=True, description='Информация о статусе подключения к Consul')
})


@consul_namespace.route('/health_check')
class ConsulHealthCheck(Resource):
    """
    Ресурс для регистрации микросервиса в Consul
    """

    @consul_namespace.doc('Отправление информации о здоровье сервиса')
    @consul_namespace.marshal_with(consul_dto)
    def get(self):
        return {'status': 'UP'}
