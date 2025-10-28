#!/bin/sh

UID=$(id -u)
GID=$(id -g)

# bundle the OpenAPI spec into a single file
docker run --rm \
  -u "${UID}":"${GID}" \
  -v "${PWD}":/local openapitools/openapi-generator-cli:v7.13.0 generate \
  -i /local/src/openapi.yaml \
  -g openapi-yaml \
  -o /local/target/bundle

cp target/bundle/openapi/openapi.yaml ./openapi.yaml
