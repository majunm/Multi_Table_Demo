@echo off
cls
title ��װapk
color 0A
set p=%ANDROID_HOME%\platform-tools
if exist %p%\adb.exe (goto:handle) else (echo. adb.exe·������ȷ,������! &goto:end)
:handle
setlocal enabledelayedexpansion
::%~1 ȡ��һ������,ɾ�� ""��(ɾ��˫����)
if "!%~1!"=="" echo �뽫apkֱ���Ϸ���bat�ļ���&goto:handleN
echo ��ʼ��װ
%p%\adb.exe install -r !%~1!
echo ��װ���
:end
echo.
pause
:handleN
set /p path=
echo ��ʼ��װ%path%
%p%\adb.exe install -r %path:"=%
echo ��װ���
pause
