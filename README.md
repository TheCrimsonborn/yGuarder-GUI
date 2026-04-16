# yGuarder GUI 🛡️

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![DevSecOps Ready](https://img.shields.io/badge/DevSecOps-Ready-brightgreen.svg)]()
[![JavaFX](https://img.shields.io/badge/UI-JavaFX-blue.svg)]()

**yGuarder GUI**, yWorks tarafından geliştirilen popüler **yGuard** Java obfuscator aracı için geliştirilmiş, profesyonel, modern ve tamamen bağımsız (standalone) bir grafik arayüzdür. 

Yazılım geliştirme süreçlerinize (SDLC) ek bir güvenlik katmanı eklemek ve fikri mülkiyetinizi (IP) tersine mühendislik (reverse engineering) girişimlerinden korumak için tasarlanmıştır.

---

## 🚀 Öne Çıkan Özellikler

- **Ultimate Standalone Architecture:** Sistemde Java kurulumuna ihtiyaç duymaz. Kendi içinde Azul Zulu JRE FX çalışma zamanını barındırabilir.
- **Deep Code Inspection (ASM based):** JAR dosyalarını "yüklemeden" analiz eder. Paket, Sınıf, Metod ve Alan seviyesinde ince ayarlı koruma (keep) kuralları sağlar.
- **Duyarlı (Responsive) Tasarım:** HiDPI 4K ekranlardan küçük dizüstü bilgisayarlara kadar tüm çözünürlüklerde kusursuz çalışma.
- **Çoklu Dil Desteği:** Türkçe ve İngilizce dillerinde tam yerelleştirme.
- **DevSecOps Dostu:** Docker ve PowerShell odaklı yapılandırma ile CI/CD süreçlerine kolay entegrasyon.

---

## 🛠️ DevSecOps Entegrasyonu

yGuarder GUI, sadece bir masaüstü aracı değil, aynı zamanda boru hattınızın (pipeline) bir parçasıdır.

### 🐳 Docker ile Build
Konteynerize edilmiş build süreciyle, geliştirme ortamından bağımsız olarak kararlı çıktılar alabilirsiniz:
```bash
docker-compose up --build
```

### ⚙️ CI/CD Pipeline
Pipeline aşamalarınızda `YGuardEngine` yapısını kullanarak Java çıktılarınızı otomatik olarak karıştırabilirsiniz. Standalone yapısı sayesinde Jenkins, GitLab CI veya GitHub Actions üzerinde herhangi bir ortam kurulumu gerektirmeden çalıştırılabilir.

---

## 📂 Proje Yapısı

- `src/`: JavaFX kaynak kodu (The Bridge mimarisi ile Motor ve UI ayrılmıştır).
- `Dockerfile` & `docker-compose.yml`: DevOps süreçleri için hazır konteyner yapılandırması.
- `download_deps.ps1`: Kısıtlı ağ ortamları için bağımlılık yöneticisi.
- `install_runtime.ps1`: Taşınabilir çalışma zamanı (portable runtime) kurulum scripti.

---

## 🏗️ Geliştiriciler İçin Derleme

Projeyi yerel ortamda derlemek için:

1.  Bağımlılıkları indirin:
    ```powershell
    .\download_deps.ps1
    ```
2.  Maven ile hata ayıklayın veya manuel derleyin:
    ```powershell
    mvn clean package
    ```

---

## 📄 Lisans

Bu proje **MIT Lisansı** altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakınız.

---

## 🤝 Katıda Bulunma

Hata bildirimleri, özellik önerileri ve pull request'ler her zaman bekleriz. DevSecOps araç setini birlikte güçlendirelim!

---
*Developed with ❤️ for the DevSecOps community.*
