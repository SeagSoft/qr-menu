@echo off
echo Starting GourmetQR Next.js Frontend (Port 3000)...
cd /d "%~dp0frontend"
if not exist "node_modules" (
    echo Installing npm dependencies...
    cmd /c npm install
)
cmd /c npm run dev
pause
