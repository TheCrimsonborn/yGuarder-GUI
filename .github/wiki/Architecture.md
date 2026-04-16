# Architecture

This page describes the internal design of yGuarder GUI.

---

## Overview

yGuarder GUI follows a clean separation of concerns called **The Bridge** architecture: the user interface layer and the obfuscation engine are completely decoupled and communicate only through well-defined data structures.

```
+-----------------------------+
|         JavaFX UI           |
|   (FXML + MainController)   |
+------------+----------------+
             |
             | keepRules, attributes,
             | namingScheme, externalLibs
             v
+-----------------------------+
|       YGuardEngine          |  <-- The Bridge
|  (Ant XML generation +      |
|   yGuard Task execution)    |
+------------+----------------+
             |
             | Ant XML config
             v
+-----------------------------+
|    yGuard 5.0.0 Engine      |
|    (via Ant Task API)        |
+-----------------------------+
```

---

## Component Breakdown

### MainApp.java

The JavaFX `Application` entry point. Responsibilities:
- Sets the AtlantaFX `PrimerDark` theme.
- Loads the FXML view with the active `ResourceBundle` (i18n).
- Manages the active `Locale` and provides the `setLanguage()` method for dynamic language switching.

### MainController.java

The FXML controller. Responsibilities:
- Handles all UI events (JAR selection, obfuscation trigger, library addition).
- Calls `JarInspector` in a background thread to populate the package tree without blocking the UI.
- Collects keep rules from the `CheckBoxTreeView` and passes them to `YGuardEngine`.
- Manages the log console output.

### YGuardEngine.java

The obfuscation bridge. Responsibilities:
- Accepts structured input (JAR paths, keep rules, attribute lists, naming scheme).
- Generates a temporary Ant XML configuration file.
- Executes the yGuard `YGuardTask` via the Apache Ant Task API.
- Throws exceptions on failure, which are caught by the controller and displayed in the log.

The engine does **not** call `mvn` or use any build system. It invokes yGuard directly as a Java library.

### JarInspector.java

The bytecode analysis utility. Responsibilities:
- Opens a JAR file using `java.util.jar.JarFile`.
- For each `.class` entry, uses **ASM 9.6** (`ClassReader` + `ClassVisitor`) to extract:
  - Class name
  - Method names (excluding constructors and static initializers)
  - Field names
- Returns a `Map<String, List<ClassInfo>>` (package name → list of classes).

The key advantage of using ASM is that classes are **never loaded into the JVM**. This means no `ClassNotFoundException` errors even when analyzing JARs whose dependencies are not present.

---

## Internationalization (i18n)

All UI strings are externalized into Java `ResourceBundle` property files:

| File | Locale |
|---|---|
| `messages_tr.properties` | Turkish (default) |
| `messages_en.properties` | English |

FXML files reference keys using the `%key` syntax. The controller uses `ResourceBundle.getString("key")` and `MessageFormat.format()` for dynamic log messages.

Language switching is performed at runtime by reloading the FXML scene with the new `ResourceBundle` — no application restart required.

---

## Dependency Summary

| Library | Version | Purpose |
|---|---|---|
| JavaFX | 21 | UI framework |
| AtlantaFX | 2.0.1 | Dark theme (PrimerDark) |
| yGuard | 5.0.0 | Java bytecode obfuscation engine |
| ASM | 9.6 | Bytecode analysis (JarInspector) |
| Apache Ant | 1.10.14 | Task execution API for yGuard |
| SLF4J | 2.0.9 | Logging facade |
| Azul Zulu JRE FX | 21 | Bundled portable runtime |
