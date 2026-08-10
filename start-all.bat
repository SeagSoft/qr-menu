@echo off
echo Starting GourmetQR Full Stack Application...
start "GourmetQR Backend (8080)" cmd /c "%~dp0start-backend.bat"
timeout /t 3 >nul
start "GourmetQR Frontend (3000)" cmd /c "%~dp0start-frontend.bat"
echo Applications are starting up!
echo Backend: http://localhost:8080
echo Frontend: http://localhost:3000
