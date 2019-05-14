docker-compose -f scripts/docker-compose.support.yml build --no-cache
docker-compose -f scripts/docker-compose.support.yml up -d

echo ""
echo "Адрес Consul: " "$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' scripts_consul-service_1)"
echo "Адрес InfluxDB: " "$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' scripts_influx-service_1)"
echo "Адрес Kafka: " "$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' scripts_kafka-service_1)"
echo "Адрес ClickHouse: " "$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' scripts_clickhouse-service_1)"
echo "Адрес PostgreSQL: " "$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' scripts_postgresql-db_1)"
echo ""