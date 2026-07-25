@echo off
REM Minimal gradlew.bat stub — delegates to system 'gradle' command. Replace with real Gradle Wrapper if needed.
where gradle >nul 2>&1
if %ERRORLEVEL%==0 (
  gradle %*
) else (
  echo Gradle is not installed. Please install Gradle or add a proper Gradle wrapper to this project.
  exit /b 1
)

