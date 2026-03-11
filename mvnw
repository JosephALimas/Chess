#!/usr/bin/env sh
set -eu

WRAPPER_DIR="$(CDPATH= cd -- "$(dirname "$0")/.mvn/wrapper" && pwd)"
PROPS_FILE="$WRAPPER_DIR/maven-wrapper.properties"

if [ ! -f "$PROPS_FILE" ]; then
  echo "Missing $PROPS_FILE"
  exit 1
fi

DISTRIBUTION_URL="$(grep '^distributionUrl=' "$PROPS_FILE" | cut -d'=' -f2-)"
if [ -z "$DISTRIBUTION_URL" ]; then
  echo "distributionUrl is missing in $PROPS_FILE"
  exit 1
fi

DISTRIBUTION_FILE="$(basename "$DISTRIBUTION_URL")"
DISTRIBUTION_BASENAME="${DISTRIBUTION_FILE%-bin.zip}"
MAVEN_HOME="$WRAPPER_DIR/$DISTRIBUTION_BASENAME"
MAVEN_BIN="$MAVEN_HOME/bin/mvn"

if [ ! -x "$MAVEN_BIN" ]; then
  echo "Downloading Maven distribution..."
  ZIP_PATH="$WRAPPER_DIR/$DISTRIBUTION_FILE"
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$DISTRIBUTION_URL" -o "$ZIP_PATH"
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$DISTRIBUTION_URL" -O "$ZIP_PATH"
  else
    echo "Neither curl nor wget is available to download Maven."
    exit 1
  fi
  unzip -oq "$ZIP_PATH" -d "$WRAPPER_DIR"
fi

exec "$MAVEN_BIN" "$@"
