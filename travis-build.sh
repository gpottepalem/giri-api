#!/bin/bash

set -e
echo "Running Gradle Task: ./gradlew -Dgrails.env=test clean assemble --stacktrace ..."

./gradlew -Dgrails.env=test clean assemble

exit $EXIT_STATUS