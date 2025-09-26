<#
Simple helper to run tests locally on Windows PowerShell.
- Verifies Docker is available (required for Testcontainers)
- Runs Gradle test with --no-daemon to better match CI
#>

param(
    [switch]$Clean
)

function Check-Docker {
    try {
        $null = docker version --format '{{.Server.Version}}' 2>$null
        return $true
    } catch {
        return $false
    }
}

Write-Host "Checking Docker availability..."
if (-not (Check-Docker)) {
    Write-Error "Docker does not appear to be available. Start Docker Desktop and try again."
    exit 2
}

$gradleCmd = "./gradlew --no-daemon"
if ($Clean) {
    $cmd = "$gradleCmd clean test"
} else {
    $cmd = "$gradleCmd test"
}

Write-Host "Running: $cmd"
$processInfo = New-Object System.Diagnostics.ProcessStartInfo
$processInfo.FileName = "pwsh"
$processInfo.Arguments = "-NoProfile -ExecutionPolicy Bypass -Command $cmd"
$processInfo.RedirectStandardOutput = $true
$processInfo.RedirectStandardError = $true
$processInfo.UseShellExecute = $false
$processInfo.CreateNoWindow = $false

$process = [System.Diagnostics.Process]::Start($processInfo)
$stdout = $process.StandardOutput.ReadToEnd()
$stderr = $process.StandardError.ReadToEnd()
$process.WaitForExit()

Write-Host $stdout
if ($stderr) { Write-Error $stderr }
exit $process.ExitCode
