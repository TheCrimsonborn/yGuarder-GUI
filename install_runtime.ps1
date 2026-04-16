$url = "https://static.azul.com/zulu/bin/zulu21.34.19-ca-fx-jre21.0.3-win_x64.zip"
$zipFile = "zulu-runtime.zip"
$extractDir = "runtime_temp"
$finalDir = "yGuarder-Portable\runtime"

if (!(Test-Path "yGuarder-Portable")) { New-Item -ItemType Directory -Path "yGuarder-Portable" }
if (Test-Path $finalDir) { Remove-Item -Recurse -Force $finalDir }

Write-Host "Downloading Azul Zulu JRE FX 21 (~100MB)..."
Invoke-WebRequest -Uri $url -OutFile $zipFile

Write-Host "Extracting runtime..."
Expand-Archive -Path $zipFile -DestinationPath $extractDir

# Move contents to final 'runtime' folder (removing the versioned root folder from zip)
$subDir = Get-ChildItem -Path $extractDir | Select-Object -First 1
Move-Item -Path $subDir.FullName -Destination $finalDir

# Cleanup
Remove-Item -Recurse -Force $extractDir
Remove-Item -Force $zipFile

Write-Host "Standalone runtime installed to $finalDir"
