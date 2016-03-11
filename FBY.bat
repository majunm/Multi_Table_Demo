@echo off
cls
title 反编译
color 0a
setlocal enabledelayedexpansion
set winrarPath=%WINRAR_HOME%
set dexPath=%DEX_HOME%
:: rd /s/q temp
:: md temp
::%~1 取第一个参数,删掉 ""号(删掉双引号)
if "!%~1!"=="" echo 请将apk直接拖放至bat文件！
echo 开始反编译资源 !%~1!
echo !%~1!>tamc.txt
echo|findstr  /M *.apk tamc.txt
REM pause
cmd /c apktool d -f !%~1!
echo 反编译资源完成
REM pause
echo 开始反编译源码
rem 从apk中解压出classes.dex文件
"%winrarPath%"\WinRAR x !%~1! classes.dex 
rem 调用dex2jar 把dex 转换成jar
cmd /c %dexPath%\dex2jar.bat classes.dex
echo 反编译源码完成
echo.
pause

