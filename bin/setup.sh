!#/bin/bash
BIN_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
BUILD_DIR="$(dirname "$BIN_DIR")"
command -v wget  >/dev/null 2>&1 || { echo >&2 "It requires wget but it's not installed.  Aborting."; exit 1; }
command -v unzip  >/dev/null 2>&1 || { echo >&2 "It requires unzip but it's not installed.  Aborting."; exit 1; }
command -v unzip  >/dev/null 2>&1 || { echo >&2 "It requires java version 8 but it's not installed.  Aborting."; exit 1; }

wget -N https://services.gradle.org/distributions/gradle-2.9-all.zip

unzip  gradle-2.9-all.zip

( cd $BUILD_DIR && $BIN_DIR/gradle-2.9/bin/gradle clean build )

mv $BUILD_DIR/build/libs/parking-lot.jar $BIN_DIR




