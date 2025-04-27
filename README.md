docker commands to run psql and redis:
docker run -d \
  --name my-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=password \
  -e POSTGRES_DB=test \
  -p 5432:5432 \
  postgres

docker run -d \
  --name my-redis \
  -p 9000:6379 \
  redis
