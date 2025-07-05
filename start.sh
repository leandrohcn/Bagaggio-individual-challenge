docker-compose down
docker build -t individual-challenge-baggaggio:latest ./individual-challenge
docker-compose up --build --force-recreate --remove-orphans
