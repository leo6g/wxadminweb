@echo off  
title 生产环境自动打包
color 02 
echo 开始打包Maven工程 =================================  
REM call mvn clean package  -P prod
call mvn clean package  -P prod
echo Maven工程打包完毕 =================================
pause