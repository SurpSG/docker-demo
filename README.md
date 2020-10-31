# docker-demo
Client-server app sending a text message via TCP connection

## Run app in Docker
Create a netowrk and volume
```
docker network create cs-net
docker volume create cs-vol
```

### Build Docker images
```
# server image
docker build -t server-image -f ./Dockerfile.server .

# client image
docker build -t client-image -f ./Dockerfile.client .
```

### Run containers
```
docker run --rm -d --name server-container server-image
docker run --rm client-image
```

## Run app using docker-compose
```
docker-compose up
```
 
