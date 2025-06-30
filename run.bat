@echo off
echo Starting Medical Management System...
echo.

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Clean and compile
echo Compiling application...
mvn clean compile

if %errorlevel% neq 0 (
    echo Compilation failed
    pause
    exit /b 1
)

REM Run the application
echo Running Medical Management System...
mvn exec:java -Dexec.mainClass="com.medical.Main"

pause