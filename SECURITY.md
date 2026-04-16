# Security Policy

## Supported Versions

Only the latest stable release receives security fixes. Older versions are not actively maintained.

| Version | Supported |
|---------|-----------|
| Latest stable (`v*.*.*`) | Yes |
| Older releases | No |

---

## Reporting a Vulnerability

**Do not report security vulnerabilities through public GitHub Issues.**

If you discover a security vulnerability in yGuarder GUI, please report it privately via one of the following channels:

- **GitHub Private Vulnerability Reporting:** Use the [Security Advisory](../../security/advisories/new) feature in this repository *(preferred)*.
- **Email:** If you cannot use GitHub's advisory system, contact the maintainers directly through the contact information listed in the repository profile.

Please include the following information in your report:

- A clear description of the vulnerability and its potential impact.
- Steps to reproduce the issue (proof-of-concept code or a detailed walkthrough).
- The version of yGuarder GUI you are using.
- Your operating system and Java runtime version.
- Any suggested mitigations, if available.

---

## Response Timeline

| Stage | Target Timeframe |
|---|---|
| Initial acknowledgment | Within 72 hours |
| Triage and severity assessment | Within 7 days |
| Patch or mitigation released | Within 30 days (critical), 90 days (moderate/low) |
| Public disclosure | After a patch is available and coordinated with the reporter |

We follow a **responsible disclosure** model. We ask that you allow us adequate time to investigate and release a fix before making any details public.

---

## Scope

**In scope:**

- Vulnerabilities in the yGuarder GUI application code (`src/`).
- Issues in the obfuscation engine integration (`YGuardEngine`) that could lead to unintended data exposure or code execution.
- Dependency vulnerabilities that directly affect the security posture of yGuarder GUI.
- Vulnerabilities in the GitHub Actions CI/CD workflows (e.g., secret leakage, supply chain risks).

**Out of scope:**

- Vulnerabilities in upstream dependencies (yGuard, ASM, AtlantaFX). Please report those to their respective maintainers.
- Security issues in the user's own Java application that is being obfuscated.
- Social engineering attacks against maintainers.
- Denial-of-service attacks against the GitHub repository itself.

---

## Security Best Practices for Users

As a tool designed for the DevSecOps community, we recommend the following when using yGuarder GUI:

- Always download releases from the **official GitHub Releases page** of this repository.
- Verify the integrity of downloaded artifacts by checking the SHA hash if provided.
- Run yGuarder GUI with the **least privilege** necessary; do not run it as a system administrator unless required.
- Keep your Java runtime environment (Azul Zulu JRE FX) up to date.

---

## Preferred Languages

We accept vulnerability reports in **English** and **Turkish**.
