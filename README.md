# docker-demo
Client-server app sending a text message via TCP connection

## Run app in Docker
Create a netowrk and volume
```
docker network create cs-net
docker volume create cs-vol
```

### Build Docker image
```
docker build -t client-server-image .
```

### Run containers
```
# server
docker run --rm -d --name server-container -e mode=server -e port=8888 --net cs-net -v cs-vol client-server-image

# client
docker run --rm -e mode=client -e addr=server-container -e port=8888  --net cs-net client-server-image
```

## Run app using docker-compose
```
docker-compose up
```
 
