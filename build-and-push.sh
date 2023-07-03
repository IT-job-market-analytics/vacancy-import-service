#!/bin/bash

IMAGE_NAME="vacancy-import-service"
TAG="dev"
DOCKERHUB_NAME="vacusertest"

docker build -t "${IMAGE_NAME}" .

docker tag "${IMAGE_NAME}:latest" "${DOCKERHUB_NAME}/${IMAGE_NAME}:${TAG}"

docker push "${DOCKERHUB_NAME}/${IMAGE_NAME}:${TAG}"
