FROM nginx:1.15.9-alpine
MAINTAINER Andrey Romanov <steampart@gmail.com>

COPY ./docker/nginx.conf /etc/nginx/nginx.conf

WORKDIR /usr/share/nginx/html
COPY dist/ .