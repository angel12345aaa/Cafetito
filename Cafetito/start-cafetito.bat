@echo off
echo Iniciando Cafetito...
echo -------------------------------------------

REM Detener contenedores previos
docker compose down --remove-orphans

REM Reconstruir imágenes
echo Construyendo imágenes...
docker compose build

REM Levantar dependencias críticas primero
echo Levantando dependencias: Eureka y PostgreSQL...
docker compose up -d eureka-server db-cafetito

REM Esperar unos segundos para que Eureka y la base de datos estén listos
echo Esperando que Eureka y PostgreSQL estén listos...
timeout /t 15 /nobreak >nul

REM Levantar el resto de microservicios
echo Levantando el resto de los servicios...
docker compose up -d config-server api-gateway agricultor-service beneficio-service login-service

REM Confirmación final
echo -------------------------------------------
echo Todos los microservicios de Cafetito están arriba.
echo Eureka: http://localhost:8761
echo API Gateway: http://localhost:8090
echo PostgreSQL: localhost:5432
echo -------------------------------------------
pause
