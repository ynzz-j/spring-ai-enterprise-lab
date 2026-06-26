<#
  编译并运行本章 Demo（基于 Maven + Spring Boot）。

  用法：
    .\compile-and-run.ps1

  等效于：
    mvn spring-boot:run

  要求：
    - JDK 1.8+
    - Maven 3.6+（PATH 中可用）
#>

$ErrorActionPreference = "Stop"

Write-Host ">>> Maven 编译 + Spring Boot 运行 ..." -ForegroundColor Cyan
mvn spring-boot:run

if ($LASTEXITCODE -ne 0) {
    Write-Host ">>> 运行失败，退出码：$LASTEXITCODE" -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host ">>> 完成" -ForegroundColor Green
