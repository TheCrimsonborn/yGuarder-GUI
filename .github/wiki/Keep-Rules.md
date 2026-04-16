# Keep Rules

Keep rules tell yGuard which elements of your JAR should **not** be renamed during obfuscation. This page explains how they work internally and how to use them effectively.

---

## How Keep Rules Work

When you check an item in the tree panel, yGuarder GUI generates a corresponding `<keep>` entry in the Ant XML configuration passed to the yGuard engine.

**Example — Keeping a class:**
```xml
<keep>
  <class name="com.example.Main" />
</keep>
```

**Example — Keeping a specific method:**
```xml
<keep>
  <class name="com.example.Service">
    <method name="processRequest" />
  </class>
</keep>
```

yGuard renames everything that is **not** explicitly listed in a `<keep>` block.

---

## What Should Always Be Kept

### Entry Points

Always keep your application's main class:

```
com.example.Main          (contains public static void main)
```

### Reflection-Based Access

If your code uses `Class.forName("com.example.SomeClass")`, that class name must be kept, otherwise the string will no longer match after obfuscation.

### Serialized Classes

If you use Java Serialization (`Serializable`), the class and field names must be kept to ensure binary compatibility with previously serialized data.

### Public API / SPI

If your JAR is a library or plugin, keep all public-facing interfaces and their method signatures.

### Spring / Jakarta Annotations

Framework-managed classes (Spring beans, JPA entities, etc.) that are referenced by name in configuration files must be kept.

---

## Hierarchical Selection

The tree panel in yGuarder GUI supports three states for each node:

| State | Meaning |
|---|---|
| Checked (full) | This element and all its children are kept |
| Indeterminate (partial) | Some children are kept; others will be obfuscated |
| Unchecked | This element will be renamed |

When you check a **package**, all classes within it are automatically protected. You can then uncheck individual classes or members to apply finer control.

---

## Common Pitfalls

| Scenario | Problem | Solution |
|---|---|---|
| Using `Class.forName()` | Runtime `ClassNotFoundException` | Keep the referenced class |
| Serialized objects | `InvalidClassException` on deserialization | Keep all `Serializable` classes and their fields |
| XML/JSON mapping (Jackson, JAXB) | Mapping fails at runtime | Keep model classes |
| Plugin architecture | Plugin class not found | Keep plugin interface implementations |
| JNI native methods | `UnsatisfiedLinkError` | Keep classes with `native` methods |
