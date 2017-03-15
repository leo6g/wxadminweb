@echo off  
title 测试环境自动打包
color 02 
echo 开始打包Maven工程 =================================  
REM call mvn clean package  -P test
call mvn clean package  -P test
echo Maven工程打包完毕 =================================
pause