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

$jdkCandidates = @()

if ($env:JAVA8_HOME) {
    $jdkCandidates += $env:JAVA8_HOME
}

$jdkCandidates += "D:\workspace\jdk8.0.472"

$selectedJdk = $null
foreach ($candidate in $jdkCandidates) {
    if ($candidate -and (Test-Path (Join-Path $candidate "bin\java.exe"))) {
        $selectedJdk = $candidate
        break
    }
}

if ($selectedJdk) {
    $env:JAVA_HOME = $selectedJdk
    $env:PATH = (Join-Path $selectedJdk "bin") + ";" + $env:PATH
    Write-Host ">>> JAVA_HOME=$env:JAVA_HOME" -ForegroundColor Cyan
}

$mavenCandidates = @()

if ($env:MAVEN_HOME) {
    $mavenCandidates += Join-Path $env:MAVEN_HOME "bin\mvn.cmd"
}

$mavenCandidates += "D:\workspace\apache-maven-3.9.12\bin\mvn.cmd"
$mavenCandidates += "mvn"

$mvn = $null
foreach ($candidate in $mavenCandidates) {
    if ($candidate -eq "mvn") {
        $mvn = $candidate
        break
    }
    if (Test-Path $candidate) {
        $mvn = $candidate
        break
    }
}

Write-Host ">>> Maven compile + Spring Boot run ..." -ForegroundColor Cyan
& $mvn spring-boot:run

if ($LASTEXITCODE -ne 0) {
    Write-Host ">>> Run failed, exit code: $LASTEXITCODE" -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host ">>> Done" -ForegroundColor Green
