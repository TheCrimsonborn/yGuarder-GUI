# yGuarder GUI

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![DevSecOps Ready](https://img.shields.io/badge/DevSecOps-Ready-brightgreen.svg)]()
[![JavaFX](https://img.shields.io/badge/UI-JavaFX-blue.svg)]()

**yGuarder GUI** is a professional, modern, and completely standalone graphical user interface developed for the popular **yGuard** Java obfuscator tool by yWorks.

It is designed to add an extra layer of security to your Software Development Life Cycle (SDLC) and protect your intellectual property (IP) from reverse engineering attempts.

---

## Core Features

- **Ultimate Standalone Architecture:** Does not require a Java installation on the host system. It can bundle its own Azul Zulu JRE FX runtime.
- **Deep Code Inspection (ASM based):** Analyzes JAR files without "loading" them. Provides fine-tuned protection (keep) rules at the Package, Class, Method, and Field levels.
- **Responsive Design:** Seamless operation across all resolutions, from High-DPI 4K displays to small laptops.
- **Multi-language Support:** Complete localization in Turkish and English.
- **DevSecOps Ready:** Easy integration into CI/CD processes with Docker and PowerShell-oriented configuration.

---

## DevSecOps Integration

yGuarder GUI is more than just a desktop tool; it is a component of your delivery pipeline.

### Build with Docker
You can obtain stable build outputs independent of the development environment using a containerized build process:
```bash
docker-compose up --build
```

### CI/CD Pipeline
You can automatically obfuscate your Java outputs using the `YGuardEngine` structure in your pipeline stages. Thanks to its standalone nature, it can run on Jenkins, GitLab CI, or GitHub Actions without requiring any environment setup.

---

## Project Structure

- `src/`: JavaFX source code (Engine and UI are separated via The Bridge architecture).
- `Dockerfile` & `docker-compose.yml`: Ready-to-use container configuration for DevOps processes.
- `download_deps.ps1`: Dependency manager for restricted network environments.
- `install_runtime.ps1`: Setup script for the portable runtime.

---

## Building for Developers

To build the project in a local environment:

1.  Download dependencies:
    ```powershell
    .\download_deps.ps1
    ```
2.  Debug with Maven or build manually:
    ```powershell
    mvn clean package
    ```

---

## License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---

## Contributing

Bug reports, feature suggestions, and pull requests are always welcome. Let's strengthen the DevSecOps toolset together!

---
*Developed for the DevSecOps community.*
