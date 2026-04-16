# Usage

This page walks through the complete obfuscation workflow in yGuarder GUI.

---

## 1. Select Your JAR File

Click the **Select JAR** button in the header bar and choose the `.jar` file you want to obfuscate.

Once selected, yGuarder GUI will automatically:
- Populate the output directory field with the same directory as the input JAR.
- Scan the JAR using ASM bytecode analysis and display all packages, classes, methods, and fields in the tree panel.

---

## 2. Configure the Output Directory

By default, the obfuscated JAR is written to the same directory as the input JAR, with the suffix `-obf.jar` appended to the filename (e.g., `myapp-obf.jar`).

To change the output location, click the **"..."** button next to the output path field.

---

## 3. Select Keep Rules (What NOT to Obfuscate)

The central tree panel shows the full structure of your JAR. Use the checkboxes to mark items that should **not** be renamed by the obfuscator.

| Item Type | Behaviour When Checked |
|---|---|
| Package | All classes, methods, and fields inside are kept |
| Class | The class name and all its members are kept |
| Method | Only that specific method name is kept |
| Field | Only that specific field name is kept |

Checked items are passed as `<keep>` rules to the yGuard engine. Unchecked items will be renamed.

> **Tip:** Always keep your application's entry point (`main` class) and any classes used via reflection.

For a detailed reference on keep rules, see [[Keep Rules]].

---

## 4. Configure Attribute Protection

Under the **Protect Attributes** section, choose which bytecode attributes to preserve:

| Option | Effect |
|---|---|
| Line Numbers | Keeps `LineNumberTable`; stack traces will show line numbers |
| Source File Name | Keeps `SourceFile`; stack traces will show the original filename |
| Local Variables | Keeps local variable names (useful for debugging) |
| Deprecated Info | Keeps `@Deprecated` annotations in bytecode |

Removing these attributes increases obfuscation strength but makes debugging harder.

---

## 5. Advanced Hardening

### Obfuscate Class Strings

When enabled, yGuard replaces string literals containing fully qualified class names with obfuscated versions. This hardens against reflection-based reverse engineering.

> **Important:** Enable this only if your application does **not** use `Class.forName()` with externally provided class names. Incorrect use will cause runtime `ClassNotFoundException` errors.

### Naming Scheme

Controls the algorithm used to generate new (obfuscated) names:

| Scheme | Description |
|---|---|
| `small` | Very short names (a, b, c, ...) — maximum size reduction |
| `mix-ascii` | Mix of letters and ASCII characters — good balance (default) |
| `digits` | Numeric-only names (0, 1, 2, ...) |

---

## 6. Add External Libraries (Optional)

If your JAR depends on other JARs that are **not** included in it (e.g., framework JARs), add them via the **Add Library** button. yGuard uses these for type analysis to produce a correct obfuscation mapping.

---

## 7. Run Obfuscation

Click the **Obfuscate Now** button. Progress and any errors are displayed in the **Log Console** at the bottom of the window.

On success, the obfuscated JAR is written to the configured output path.

---

## 8. Changing the Language

Click the language dropdown in the top-right corner of the window to switch between **Türkçe** and **English**.
