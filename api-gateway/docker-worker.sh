#!/bin/bash
#prima fare il login in locale di docker
#build e push dell'immagine
#myspringapp
SERVICE_NAME=api-gateway
#username
DOCKER_USERNAME=mattialadisa

DOCKER_TAG=1.0.0  # or specify a version/tag as needed

echo "Building the Docker image for $SERVICE_NAME..."

docker build -t $DOCKER_USERNAME/$SERVICE_NAME:$DOCKER_TAG .


echo "Pushing the Docker image to Docker Hub..."
docker push $DOCKER_USERNAME/$SERVICE_NAME:$DOCKER_TAG

echo "Docker build and push process completed for $SERVICE_NAME."