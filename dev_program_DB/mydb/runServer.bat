@echo off
chcp 65001 >nul
echo ------------------------------------------------------------
echo HSQLDB サーバー起動スクリプト
echo ------------------------------------------------------------

REM この .bat ファイルがあるディレクトリに移動
cd /d "%~dp0dev_program_DB\mydb"

REM サーバー起動
java -classpath "../lib/hsqldb.jar" org.hsqldb.Server -database mydb

pause
