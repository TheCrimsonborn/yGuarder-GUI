package com.example.core;

import java.util.Collection;
import java.util.List;

/**
 * Parameter object for obfuscation tasks.
 */
public class ObfuscationTask {
    private final String inJar;
    private final String outJar;
    private final Collection<String> keepRules;
    private final List<String> attributesToKeep;
    private final List<String> externalLibs;
    private final boolean replaceClassNameStrings;
    private final String namingScheme;

    public ObfuscationTask(String inJar, String outJar, 
                           Collection<String> keepRules, 
                           List<String> attributesToKeep, 
                           List<String> externalLibs, 
                           boolean replaceClassNameStrings, 
                           String namingScheme) {
        this.inJar = inJar;
        this.outJar = outJar;
        this.keepRules = keepRules;
        this.attributesToKeep = attributesToKeep;
        this.externalLibs = externalLibs;
        this.replaceClassNameStrings = replaceClassNameStrings;
        this.namingScheme = namingScheme;
    }

    public String getInJar() { return inJar; }
    public String getOutJar() { return outJar; }
    public Collection<String> getKeepRules() { return keepRules; }
    public List<String> getAttributesToKeep() { return attributesToKeep; }
    public List<String> getExternalLibs() { return externalLibs; }
    public boolean isReplaceClassNameStrings() { return replaceClassNameStrings; }
    public String getNamingScheme() { return namingScheme; }
}
