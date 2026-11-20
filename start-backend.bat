@echo off
echo Starting GourmetQR Spring Boot Backend (Port 8080)...
set "JAVA_HOME=C:\Program Files\Java\jdk-22"
set "MVN_HOME=C:\Users\rdvn_\.m2\wrapper\dists\apache-maven-3.9.16\0daed3be3ebd1c706f0e69e8b07c6b73f5cc4ea3dfce72a8d0ec2e849ca2ddb0"
cd /d "%~dp0backend"
if exist "%JAVA_HOME%\bin\java.exe" (
    if exist "%MVN_HOME%\boot\plexus-classworlds-2.11.0.jar" (
        "%JAVA_HOME%\bin\java.exe" "-Dclassworlds.conf=%MVN_HOME%\bin\m2.conf" "-Dmaven.home=%MVN_HOME%" "-Dmaven.multiModuleProjectDirectory=%~dp0backend" -classpath "%MVN_HOME%\boot\plexus-classworlds-2.11.0.jar" org.codehaus.plexus.classworlds.launcher.Launcher spring-boot:run
        goto :end
    )
)
set "PATH=%JAVA_HOME%\bin;%PATH%"
cmd /c mvnw.cmd spring-boot:run
:end
pause
