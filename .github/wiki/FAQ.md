# FAQ

Frequently asked questions and known issues for yGuarder GUI.

---

## Installation

**Q: The application starts but immediately crashes with `UnsupportedClassVersionError`.**

The system Java on your machine is too old. yGuarder GUI requires Java 21. Run `install_runtime.ps1` to install the bundled Azul Zulu JRE FX 21, which will be used automatically by `run.bat`.

---

**Q: `run.bat` says "Bundled runtime not found" even though I ran `install_runtime.ps1`.**

Ensure `install_runtime.ps1` was run from inside the `yGuarder-Portable\` directory, and that the `runtime\bin\java.exe` file exists. If it does not, re-run the installer.

---

**Q: `install_runtime.ps1` fails with "running scripts is disabled on this system".**

PowerShell's execution policy is blocking the script. Run it with:

```powershell
powershell -ExecutionPolicy Bypass -File .\install_runtime.ps1
```

---

## Usage

**Q: After obfuscation, my application crashes at runtime.**

The most common causes are:

1. **Missing keep rules for the entry point.** Make sure your `main` class is checked in the tree.
2. **Reflection usage.** If your code calls `Class.forName("com.example.SomeName")`, that class must be kept, and "Obfuscate Class Strings" should be disabled unless you have full control of the reflection calls.
3. **Serialization.** `Serializable` classes and their fields must be kept.
4. **Framework annotations.** Spring, JPA, and similar frameworks look up classes and fields by name. Keep all framework-managed classes.

---

**Q: The package tree is empty after selecting a JAR.**

This can happen if the JAR contains no `.class` files (e.g., it is a sources JAR or a resources-only JAR). Check that the selected file is a compiled application JAR.

---

**Q: Obfuscation fails with "yguard doesn't support the nested shrink element".**

yGuard 5.0.0 removed the `<shrink>` element. yGuarder GUI does not generate this element. If you are seeing this error, ensure you are using the latest version of yGuarder GUI.

---

**Q: Can I obfuscate a Spring Boot fat JAR?**

Spring Boot fat JARs have a non-standard structure (`BOOT-INF/classes/`). Direct obfuscation is generally not recommended. The preferred approach is to obfuscate the application module JAR **before** it is packaged into the fat JAR.

---

## Build

**Q: `download_deps.ps1` fails to download some files.**

Check your internet connection and proxy settings. If you are behind a corporate proxy, configure it in PowerShell:

```powershell
$env:HTTPS_PROXY = "http://proxy.corp.example.com:8080"
.\download_deps.ps1
```

---

**Q: The IDE shows "org.objectweb cannot be resolved" errors in JarInspector.java.**

This is an IDE classpath issue, not a build error. The code compiles correctly. To fix the IDE display:

1. Open the Command Palette (`Ctrl+Shift+P`).
2. Run **Java: Clean Java Language Server Workspace**.
3. Reload the window.

The `.vscode/settings.json` in the repository includes the necessary `java.project.referencedLibraries` configuration pointing to the `lib/` folder.

---

## GitHub Actions

**Q: The Docker publish workflow fails with "Cache export is not supported for the docker driver".**

This happens when `docker/setup-buildx-action` is missing from the workflow. The current workflow includes this step. If you have customized the workflow and removed it, add it back before the build step.

---

**Q: How do I publish a new release?**

```bash
git tag v1.0.0
git push origin v1.0.0
```

The release workflow runs automatically. Alternatively, trigger it manually from the **Actions** tab using the "Run workflow" button.
