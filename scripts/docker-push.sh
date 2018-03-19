#!/bin/bash

set -ex

IMAGE_NAME="akeboshiwind/alton-towers-ride-times"
TAG="${1}"

docker build -t ${IMAGE_NAME}:${TAG} -t ${IMAGE_NAME}:latest .
docker push ${IMAGE_NAME}
