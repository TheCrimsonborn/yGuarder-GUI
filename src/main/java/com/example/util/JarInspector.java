package com.example.util;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarInspector {

    public static class ClassInfo {
        public String name;
        public List<String> methods = new ArrayList<>();
        public List<String> fields = new ArrayList<>();

        public ClassInfo(String name) {
            this.name = name;
        }
    }

    public static Map<String, List<ClassInfo>> inspectJar(String jarPath) throws Exception {
        Map<String, List<ClassInfo>> packageToClasses = new TreeMap<>();
        
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    try (InputStream is = jarFile.getInputStream(entry)) {
                        ClassReader reader = new ClassReader(is);
                        String className = reader.getClassName().replace('/', '.');
                        int lastDot = className.lastIndexOf('.');
                        String pkg = (lastDot == -1) ? "(default package)" : className.substring(0, lastDot);

                        ClassInfo classInfo = new ClassInfo(className);
                        reader.accept(new ClassVisitor(Opcodes.ASM9) {
                            @Override
                            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                                classInfo.fields.add(name);
                                return null;
                            }

                            @Override
                            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                                if (!name.equals("<init>") && !name.equals("<clinit>")) {
                                    classInfo.methods.add(name);
                                }
                                return null;
                            }
                        }, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

                        packageToClasses.computeIfAbsent(pkg, k -> new ArrayList<>()).add(classInfo);
                    }
                }
            }
        }
        return packageToClasses;
    }

    // Keep legacy support for initial scan if needed
    public static Set<String> listPackages(String jarPath) throws Exception {
        return inspectJar(jarPath).keySet();
    }
}
