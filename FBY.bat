@echo off
cls
title ������
color 0a
setlocal enabledelayedexpansion
set winrarPath=%WINRAR_HOME%
set dexPath=%DEX_HOME%
:: rd /s/q temp
:: md temp
::%~1 ȡ��һ������,ɾ�� ""��(ɾ��˫����)
if "!%~1!"=="" echo �뽫apkֱ���Ϸ���bat�ļ���
echo ��ʼ��������Դ !%~1!
echo !%~1!>tamc.txt
echo|findstr  /M *.apk tamc.txt
REM pause
cmd /c apktool d -f !%~1!
echo ��������Դ���
REM pause
echo ��ʼ������Դ��
rem ��apk�н�ѹ��classes.dex�ļ�
"%winrarPath%"\WinRAR x !%~1! classes.dex 
rem ����dex2jar ��dex ת����jar
cmd /c %dexPath%\dex2jar.bat classes.dex
echo ������Դ�����
echo.
pause

