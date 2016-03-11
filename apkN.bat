@echo off
cls
title 安装apk
color 0A
set p=%ANDROID_HOME%\platform-tools
if exist %p%\adb.exe (goto:handle) else (echo. adb.exe路径不正确,请修正! &goto:end)
:handle
setlocal enabledelayedexpansion
::%~1 取第一个参数,删掉 ""号(删掉双引号)
if "!%~1!"=="" echo 请将apk直接拖放至bat文件！&goto:handleN
echo 开始安装
%p%\adb.exe install -r !%~1!
echo 安装完成
:end
echo.
pause
:handleN
set /p path=
echo 开始安装%path%
%p%\adb.exe install -r %path:"=%
echo 安装完成
pause
