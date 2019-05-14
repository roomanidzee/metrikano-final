echo " "
echo "Собираем сервис конфигураций.."
echo " "

cd backend/config-server
gradle clean build
cd ..

echo " "
echo "Собираем сервис для InfluxDB.."
echo " "

cd influx-service
gradle clean build
cd ..

echo " "
echo "Собираем сервис для ClickHouse.."
echo " "

cd clickhouse-service
gradle clean build
cd ..

echo " "
echo "Собираем сервис для работы с БД.."
echo " "

cd postgres-service
gradle clean build
cd ..

echo " "
echo "Собираем сервис работы с пользователями.."
echo " "

cd auth-service
gradle clean build
cd ..

echo " "
echo "Собираем фронтенд для всей этой системы.."
echo " "

cd ../frontend/metrikano-frontend
yarn build:docker
cd ../../

echo " "
echo "Попытаемся взлететь.."
echo " "

docker-compose -f scripts/docker-compose.support.yml -f scripts/development/docker-compose.apps.yml build --no-cache
docker-compose -f scripts/docker-compose.support.yml -f scripts/development/docker-compose.apps.yml up -d 