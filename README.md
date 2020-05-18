# Book Library

Author: Brandon Autrey

# Docker

Build docker container: `docker build . --tag library:latest`

Run Docker container: `docker run -d -p 8090:8090 --name library library`

View running Docker containers: `docker ps`

View logs: `docker logs <container id> --follow`

Stop Docker app: `docker stop <container id>`

Remove Docker app: `docker rm <container id>`