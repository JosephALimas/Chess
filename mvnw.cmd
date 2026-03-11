@ECHO OFF
SETLOCAL

SET "WRAPPER_DIR=%~dp0.mvn\wrapper"
SET "PROPS_FILE=%WRAPPER_DIR%\maven-wrapper.properties"

IF NOT EXIST "%PROPS_FILE%" (
  ECHO Missing "%PROPS_FILE%".
  EXIT /B 1
)

FOR /F "tokens=1,* delims==" %%A IN (%PROPS_FILE%) DO (
  IF "%%A"=="distributionUrl" SET "DISTRIBUTION_URL=%%B"
)

IF "%DISTRIBUTION_URL%"=="" (
  ECHO distributionUrl is missing in "%PROPS_FILE%".
  EXIT /B 1
)

FOR %%A IN ("%DISTRIBUTION_URL%") DO SET "DISTRIBUTION_FILE=%%~nxA"
SET "DISTRIBUTION_BASENAME=%DISTRIBUTION_FILE:-bin.zip=%"
SET "MAVEN_HOME=%WRAPPER_DIR%\%DISTRIBUTION_BASENAME%"
SET "MAVEN_CMD=%MAVEN_HOME%\bin\mvn.cmd"

IF NOT EXIST "%MAVEN_CMD%" (
  ECHO Downloading Maven distribution...
  POWERSHELL -NoProfile -ExecutionPolicy Bypass -Command ^
    "$ErrorActionPreference='Stop';" ^
    "$wrapperDir = Resolve-Path '%WRAPPER_DIR%';" ^
    "$zip = Join-Path $wrapperDir '%DISTRIBUTION_FILE%';" ^
    "Invoke-WebRequest -Uri '%DISTRIBUTION_URL%' -OutFile $zip;" ^
    "Expand-Archive -Path $zip -DestinationPath $wrapperDir -Force;"
  IF ERRORLEVEL 1 (
    ECHO Failed to download Maven distribution.
    EXIT /B 1
  )
)

CALL "%MAVEN_CMD%" %*
EXIT /B %ERRORLEVEL%
