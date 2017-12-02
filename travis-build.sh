#!/usr/bin/env bash

set -e

echo "Assembling....."
./gradlew assemble --stacktrace

EXIT_STATUS = 0


exit $EXIT_STATUS