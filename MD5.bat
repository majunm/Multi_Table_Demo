@echo off
setlocal enabledelayedexpansion 
certutil -hashfile C:\Users\1\Desktop\info.txt MD5|findstr /V ��ϣ|findstr /V ���  >>xx.txt
for /f "eol= delims=" %%i in (xx.txt) do (
set s=%%i
set s=!s: =!
echo !s! >>bbb.txt
)
del xx.txt
echo.
pause
