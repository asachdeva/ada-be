#!/usr/bin/env bash
PROJECT_DIR="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )/.." &> /dev/null && pwd )"
PORT="${PORT:-5005}"
DEFAULT_HOST=$(nslookup host.docker.internal > /dev/null && echo "host.docker.internal" || hostname)
TESTHOST="${TESTHOST:-${DEFAULT_HOST}}"

docker run --rm -it -v "$PROJECT_DIR:/app" -w /app/tests -e "PORT=$PORT" -e "TESTHOST=$TESTHOST" nyurik/alpine-python3-requests /bin/sh -c "pip install -Ur /app/tests/requirements.txt; python /app/tests/test_messages.py"
docker run --rm -it -v "$PROJECT_DIR:/app" -w /app/tests -e "PORT=$PORT" -e "TESTHOST=$TESTHOST" nyurik/alpine-python3-requests /bin/sh -c "pip install -Ur /app/tests/requirements.txt; python /app/tests/test_search.py"
