@echo off
echo Starting GourmetQR Spring Boot Backend (Port 8080)...
cd /d "%~dp0backend"
cmd /c mvnw spring-boot:run
pause
