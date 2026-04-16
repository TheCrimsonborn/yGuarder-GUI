# CI/CD Integration

yGuarder GUI is designed with DevSecOps workflows in mind. This page covers how to integrate it into automated pipelines.

---

## GitHub Actions

The repository ships with two pre-configured workflows.

### Release Workflow

**File:** `.github/workflows/release.yml`

Triggered by pushing a semantic version tag. Builds the application, packages it as a Windows-portable ZIP, and publishes a GitHub Release.

```bash
git tag v1.0.0
git push origin v1.0.0
```

The workflow can also be triggered manually from the **Actions** tab in GitHub using the **"Run workflow"** button.

**Output:** A GitHub Release with a downloadable `yGuarder-vX.X.X-portable-windows.zip` asset.

---

### Docker Publish Workflow

**File:** `.github/workflows/docker-publish.yml`

Triggered on every push to `main` and on every version tag. Builds the Docker image and pushes it to **GitHub Container Registry** (`ghcr.io`).

**Image:** `ghcr.io/thecrimsonborn/yguarder-gui`

| Tag | When Created |
|---|---|
| `edge` | Every push to `main` |
| `sha-<commit>` | Every push to `main` |
| `1.0.0` | On tag `v1.0.0` |
| `1.0` | On tag `v1.0.0` |

---

## Docker

### Build Locally

```bash
docker-compose up --build
```

### Pull from Registry

```bash
docker pull ghcr.io/thecrimsonborn/yguarder-gui:edge
```

### Run as a CLI Tool

The Docker image contains the application JAR and all dependencies. It can be used as a headless build-time step in a pipeline:

```bash
docker run --rm \
  -v /path/to/your.jar:/app/input.jar \
  -v /path/to/output:/app/output \
  ghcr.io/thecrimsonborn/yguarder-gui:edge \
  java -cp "yguarder-gui.jar:lib/*" com.example.Launcher
```

---

## Integrating into a Jenkins Pipeline

```groovy
pipeline {
    agent any
    stages {
        stage('Obfuscate') {
            steps {
                sh '''
                  docker run --rm \
                    -v ${WORKSPACE}/target/myapp.jar:/app/input.jar \
                    -v ${WORKSPACE}/target/obf:/app/output \
                    ghcr.io/thecrimsonborn/yguarder-gui:edge \
                    java -cp "yguarder-gui.jar:lib/*" com.example.Launcher
                '''
            }
        }
    }
}
```

---

## Security Considerations for CI/CD

- The `GITHUB_TOKEN` used in the workflows is automatically provisioned by GitHub Actions and scoped to the minimum required permissions (`contents: write` for releases, `packages: write` for Docker).
- No external secrets are required.
- Dependencies are fetched from **Maven Central** at build time over HTTPS; no third-party registries are used.
- The Docker image is based on `eclipse-temurin:21-jre-alpine` — a minimal, frequently patched base image.
