:: ����ɾ������Ҫsvn������ļ���
:: ��ر���Ŀ�����У����к��ٴδ���Ŀ���α���ʱ��ϳ���
:: �˲�������ʾ�����޷�����������ʹ�á�
@echo off
cd %~dp0
for /d %%i in (*) do (
    if %%i EQU .idea (
        echo rd .idea , location:[%%i]
        rd /q /s %%i
    )
    if %%i EQU .gradle (
        echo rd .gradle , location:[%%i]
        rd /q /s %%i
    )
    if %%i EQU captures (
        echo rd captures , location:[%%i]
        rd /q /s %%i
    )
)
for /r /d %%i in (*) do (
    if %%~ni EQU build (
        echo rd build , location:[%%i]
        rd /q /s %%i
    )
)


for /r %%i in (*.iml) do (
    echo del iml ,location[%%i]
    del %%i
)
echo If the AS cannot open this project normally, you can: 
echo 	step1:Reopen the project ;
echo 	step2:Reopen Android Studio ;
echo done
pause

