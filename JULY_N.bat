@echo off
cls
title 测试
color 0a
setlocal enabledelayedexpansion
mode con cols=150 lines=1000
:begin
echo.
echo -----------------------------------------------------按Q键退出---------------------------------------
echo --------------------------------------重新输入字符串按R(retry)键------------------------------------
echo 				==第一个参数为字符串,第二个为操作符,现在默认乘号(无意义),第三个参数为倍数==
echo 				例子: 参数依次为  第一个参数   abc =
echo 									 第二个参数	 	*
echo									 第三个参数		2
echo 				文件原内容是 abc = 100
echo								abcd = 22
echo								abc = 2,8,9
echo 				执行批处理后  abc = 200 (原有值*2 = 100 *2 = 200)
echo								abcd = 22
echo  				              abc = 4,16,18
echo --------------------------------------------------------------------------------------------------------
:one
echo 请输入第一个参数
set /p x=!%~1! 
if /i !x!==q (
	exit
)
if /i !x!==r (
	goto one
)
echo -------x--%x%-----------------
:two
echo 请输入第二个参数,+-*/ **代表乘于小数5个输入操作符,不要乱输入
set /p y=!%~2! 
if /i !y!==q (
	exit
)
if /i !y!==r (
	goto two
)
set /a lock=-4
if !y!==+ set /a lock=1
if !y!==- set /a lock=2
if !y!==* set /a lock=3
if !y!==/ set /a lock=4
if !y!==** set /a lock=5 && echo ------------------------**-----------------
echo -------y--%y%-----------------
if !lock!==-4 echo 输入错误 & goto two
:thr
echo 请输入第三个参数
set /p z=!%~3!
if /i !z!==q (
	exit
)
if /i !z!==r (
	goto thr
)
echo -------z--%z%-----------------
if exist 5055.txt del 5055.txt
echo !z!>5055.txt
echo|findstr /X ^[0-9]*$ 5055.txt || echo 请输入纯数字 && goto thr
echo|findstr /X ^[0-9]*$ 5055.txt && set /a vav=!z! && if !lock!==5 (
	if !vav! LSS 10 (
		echo 输入数值要大于等于10才可以 & goto thr
	)
)
if exist 5055.txt del 5055.txt
echo ====================BEGIN TIME==========================
set /a StartS=%time:~6,2% 2>nul
set /a StartM=%time:~3,2% 2>nul
set /a HourStart=%time:~0,2% 2>nul
echo 开始时间：%time%
echo ====================BEGIN TIME==========================
set mvar=""
FOR /f "delims== tokens=1,2" %%a in (ITEM.txt) do (
	echo %%a=%%b>1022.txt
	REM echo a=%a%---
	REM echo b=%b%---
	ECHO|findstr /c:"!x!" 1022.txt 2>nul || if "%%b"=="" (
		echo %%a>>90909.txt
	) else (
		echo %%a=%%b>>90909.txt
	) 
	ECHO|findstr /c:"!x!" 1022.txt 2>nul && for /f "eol= delims== tokens=1,2" %%e in (1022.txt) do (
		set tst1=%%f
		set /a va=!tst1: =!
		if !lock!==1 set /a va+=!z!
		if !lock!==2 set /a va-=!z!
		if !lock!==3 set /a va*=!z!
		if !lock!==4 set /a va/=!z!
		if !lock!==5 set /a va*=!z!
		REM echo !va!----------SUCCESSFULLY---------------
		echo !tst1: =!>1033.txt
		if !lock! LSS 5 (
			echo|findstr /X ^[0-9]*$ 1033.txt 2>nul && echo %%e= !va!>>90909.txt
		)
		if !lock!==5 (
			REM set /a va=!va:~0,-1!
			if !va! LSS 10 echo|findstr /X ^[0-9]*$ 1033.txt 2>nul && echo %%e= !va!>>90909.txt
			REM echo !va:~0,-1!
			REM echo !va!-------------lock 5----------------
			if !va! GEQ 10 echo|findstr /X ^[0-9]*$ 1033.txt 2>nul && echo %%e= !va:~0,-1!>>90909.txt
		)
		set /a ww=0
		set /a xx=0
		set /a yy=0
		set /a count=0
		echo|findstr /X ^[0-9]*$ 1033.txt 2>nul || for /f " tokens=1,2-3 eol= delims=," %%h in (1033.txt) do (
			set /a ww=%%h
			set /a xx=%%i
			set /a yy=%%j 2>nul || set /a count=1
			REM set /a count=1
			if !lock!==1 (
				set /a ww+=!z!
				set /a xx+=!z!
				if !count!==0 set /a yy+=!z!
			)
			if !lock!==2 (
				set /a ww-=!z!
				set /a xx-=!z!
				if !count!==0 set /a yy-=!z!
			)
			if !lock!==3 (
				set /a ww*=!z!
				set /a xx*=!z!
				if !count!==0 set /a yy*=!z!
			)
			if !lock!==4 (
				set /a ww/=!z!
				set /a xx/=!z!
				if !count!==0 set /a yy/=!z!
			)
			if !lock!==5 (
				set /a ww*=!z!
				set /a xx*=!z!
				if !count!==0 set /a yy*=!z!
			)
			if !lock! LSS 5 (
				if !count!==1 echo %%e= !ww!,!xx!>>90909.txt
				if !count!==0 echo %%e= !ww!,!xx!,!yy!>>90909.txt
			) else (
				if !count!==0 echo %%e= !ww:~0,-1!,!xx:~0,-1!,!yy:~0,-1!>>90909.txt
				if !count!==1 echo %%e= !ww:~0,-1!,!xx:~0,-1!>>90909.txt
			)
		) 
		REM if !count!==1 (
		REM	echo ==============================================
		REM )
	)
	rem echo %%a=%%b>1011.txt
)
if exist 1022.txt del 1022.txt
if exist 1033.txt del 1033.txt
echo ====================END TIME==========================
echo 结束时间：%time%
set /a EndS=%time:~6,2% 2>nul
set /a EndM=%time:~3,2% 2>nul
set /a HourEnd=%time:~0,2% 2>nul
set /a diffS_=%EndS%-%StartS% 2>nul
set /a diffM_=%EndM%-%StartM% 2>nul
set /a diffH_=%HourEnd%-%HourStart% 2>nul
echo ====================END TIME==========================
echo 程序运行时间： %diffH_%小时%diffM_%分钟%diffS_:-=%秒
REM goto begin
REM echo.
del  /Q 101.txt
ren 90909.txt 101.txt
pause