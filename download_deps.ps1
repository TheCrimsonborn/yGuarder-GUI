$libDir = "lib"
if (!(Test-Path $libDir)) { New-Item -ItemType Directory -Path $libDir }

$base = "https://repo1.maven.org/maven2"
$deps = @(
    # JavaFX Modules (win)
    "$base/org/openjfx/javafx-controls/21.0.2/javafx-controls-21.0.2-win.jar",
    "$base/org/openjfx/javafx-graphics/21.0.2/javafx-graphics-21.0.2-win.jar",
    "$base/org/openjfx/javafx-base/21.0.2/javafx-base-21.0.2-win.jar",
    "$base/org/openjfx/javafx-fxml/21.0.2/javafx-fxml-21.0.2-win.jar",
    
    # JavaFX Modules (linux - for Docker container build environment)
    "$base/org/openjfx/javafx-controls/21.0.2/javafx-controls-21.0.2-linux.jar",
    "$base/org/openjfx/javafx-graphics/21.0.2/javafx-graphics-21.0.2-linux.jar",
    "$base/org/openjfx/javafx-base/21.0.2/javafx-base-21.0.2-linux.jar",
    "$base/org/openjfx/javafx-fxml/21.0.2/javafx-fxml-21.0.2-linux.jar",

    # Themes & Engine
    "$base/io/github/mkpaz/atlantafx-base/2.0.1/atlantafx-base-2.0.1.jar",
    "$base/com/yworks/yguard/5.0.0/yguard-5.0.0.jar",
    "$base/org/ow2/asm/asm/9.6/asm-9.6.jar",
    
    # Logging
    "$base/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar",
    "$base/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar",
    
    # Ant (yGuard needs Ant libraries for Task execution)
    "$base/org/apache/ant/ant/1.10.14/ant-1.10.14.jar",
    "$base/org/apache/ant/ant-launcher/1.10.14/ant-launcher-1.10.14.jar"
)

foreach ($url in $deps) {
    $filename = [System.IO.Path]::GetFileName($url)
    $dest = Join-Path $libDir $filename
    Write-Host "Downloading $filename..."
    try {
        Invoke-WebRequest -Uri $url -OutFile $dest -ErrorAction Stop
    } catch {
        Write-Warning ("Failed to download " + $filename + " : " + $_.Exception.Message)
    }
}
Write-Host "All dependencies downloaded to $libDir folder."
