<#
  Compile and run the Spring AI MVP sidecar.

  Default mode:
    .\compile-and-run.ps1

  Real model mode:
    $env:SPRING_AI_OPENAI_API_KEY="sk-..."
    $env:SPRING_PROFILES_ACTIVE="real"
    .\compile-and-run.ps1
#>

$ErrorActionPreference = "Stop"

$jdkCandidates = @()

if ($env:JAVA17_HOME) {
    $jdkCandidates += $env:JAVA17_HOME
}

if ($env:JAVA_HOME) {
    $jdkCandidates += $env:JAVA_HOME
}

$jdkCandidates += "D:\workspace\jdk21.0.10"

$selectedJdk = $null
foreach ($candidate in $jdkCandidates) {
    if ($candidate -and (Test-Path (Join-Path $candidate "bin\java.exe"))) {
        $selectedJdk = $candidate
        break
    }
}

if (-not $selectedJdk) {
    Write-Host ">>> JDK 17+ not found. Set JAVA17_HOME or JAVA_HOME first." -ForegroundColor Red
    exit 1
}

$env:JAVA_HOME = $selectedJdk
$env:PATH = (Join-Path $selectedJdk "bin") + ";" + $env:PATH

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

Write-Host ">>> JAVA_HOME=$env:JAVA_HOME" -ForegroundColor Cyan
Write-Host ">>> Maven compile + Spring Boot run ai-center-mvp ..." -ForegroundColor Cyan
& $mvn spring-boot:run

if ($LASTEXITCODE -ne 0) {
    Write-Host ">>> Run failed, exit code: $LASTEXITCODE" -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host ">>> Done" -ForegroundColor Green
