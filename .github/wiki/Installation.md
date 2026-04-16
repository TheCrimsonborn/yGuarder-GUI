# Installation

This page covers all methods for installing and running yGuarder GUI on a Windows system.

---

## Option 1: Portable Bundle (Recommended)

The portable bundle is the simplest way to get started. It requires no system-wide Java installation.

### Step 1: Download

Download the latest `yGuarder-vX.X.X-portable-windows.zip` from the [Releases](../../releases) page and extract it to any directory.

```
yGuarder-v1.0.0-portable-windows\
  run.bat
  install_runtime.ps1
  yguarder-gui.jar
  lib\
    atlantafx-base-2.0.1.jar
    yguard-5.0.0.jar
    asm-9.6.jar
    ... (other dependencies)
```

### Step 2: Install the Bundled Java Runtime (First Time Only)

```powershell
.\install_runtime.ps1
```

This script downloads **Azul Zulu JRE FX 21** (~100 MB) from the official Azul repository and extracts it into the `runtime\` folder. Internet access is required for this step only.

After this step, your directory will contain a `runtime\` folder:

```
runtime\
  bin\
    java.exe   ← used by run.bat
  lib\
  ...
```

### Step 3: Launch the Application

```
run.bat
```

---

## Option 2: Manual Runtime Setup (Offline Environments)

If the machine has no internet access, manually place a compatible Java 21 runtime into the `runtime\` folder.

1. Download **Azul Zulu JRE FX 21** (`zulu21.*-ca-fx-jre21.*-win_x64.zip`) from [azul.com/downloads](https://www.azul.com/downloads/).
2. Extract the ZIP.
3. Rename the extracted folder to `runtime` and place it next to `run.bat`.

The launcher (`run.bat`) expects `runtime\bin\java.exe` to exist.

---

## Option 3: Use System Java

If you already have Java 21 installed system-wide, you can skip the runtime installation. The launcher automatically falls back to the system `java` command if `runtime\` is not found.

> **Note:** The system Java must be version 21 or higher. The application is compiled with `--release 21`. Running it on Java 8 or Java 11 will result in an `UnsupportedClassVersionError`.

---

## Building from Source

See [[Architecture]] for project structure details.

```powershell
# 1. Clone the repository
git clone https://github.com/TheCrimsonborn/yGuarder-GUI.git
cd yGuarder-GUI

# 2. Download dependencies
.\download_deps.ps1

# 3. Compile and package
$cp = (Get-ChildItem lib/*.jar | % { $_.FullName }) -join ';'
New-Item -ItemType Directory -Force -Path target\classes\com\example
javac -cp $cp src\main\java\com\example\*.java `
              src\main\java\com\example\core\*.java `
              src\main\java\com\example\util\*.java `
              -d target\classes
jar cfe target\yguarder-gui.jar com.example.Launcher -C target\classes .
```
