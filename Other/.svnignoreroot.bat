@echo off
for /r %%i in (*) do (
    if %%~ni == .svnignore (
        echo Run bat file : %%i
        call %%i 
    )
)
echo finish

