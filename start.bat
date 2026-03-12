@echo off
REM ===================================
REM Windows 启动脚本（加载环境变量）
REM ===================================

echo ====================================
echo  Message Push 应用启动器
echo ====================================
echo.

REM 检查 .env 文件是否存在
if not exist ".env" (
    echo [警告] .env 文件不存在！
    echo.
    echo 请按照以下步骤操作：
    echo 1. 复制 .env.example 为 .env
    echo 2. 编辑 .env 文件，填入真实的 Token 和配置
    echo.
    pause
    exit /b 1
)

echo [信息] 正在从 .env 文件加载环境变量...
echo.

REM 读取 .env 文件并设置环境变量
for /f "tokens=*" %%a in ('findstr /r /c:"^[^#]" .env') do (
    setlocal enabledelayedexpansion
    for /f "tokens=1,2 delims==" %%k in ("%%a") do (
        set "key=%%k"
        set "value=%%l"
        if defined key (
            set "key=!key: =!"
            set "value=!value: =!"
            if not "!key!"=="" (
                echo   设置 !key!=!value!
                setx !key! "!value!" >nul
            )
        )
    )
    endlocal
)

echo.
echo [信息] 环境变量已设置（仅对新终端窗口有效）
echo.
echo [提示] 当前窗口无法使用新设置的环境变量
echo.
echo 请选择启动方式：
echo   1. 在新窗口启动应用（推荐）
echo   2. 直接运行 Maven（需要手动设置环境变量）
echo   3. 退出
echo.
set /p choice="请输入选项 (1-3): "

if "%choice%"=="1" (
    echo.
    echo [信息] 正在打开新窗口启动应用...
    start cmd /k "echo 正在启动 Message Push 应用... ^&^& mvn spring-boot:run"
    echo [成功] 应用已在新窗口启动！
) else if "%choice%"=="2" (
    echo.
    echo [信息] 请使用以下命令手动启动：
    echo   mvn spring-boot:run
) else (
    echo.
    echo [信息] 已退出
)

pause
