package com.example.core;

import com.example.exception.ObfuscationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class YGuardEngine {
    
    private YGuardEngine() {
        throw new IllegalStateException("Utility class");
    }

    public static void run(String inJar, String outJar, 
                          Collection<String> keepRules, 
                          List<String> attributesToKeep,
                          List<String> externalLibs,
                          boolean replaceClassStrings,
                          String namingScheme) throws ObfuscationException, IOException {
        
        File configFile = File.createTempFile("yguard-config", ".xml");
        generateAntXml(configFile, inJar, outJar, keepRules, attributesToKeep, externalLibs, replaceClassStrings, namingScheme);

        Project project = new Project();
        project.init();
        ProjectHelper.configureProject(project, configFile);
        
        try {
            project.executeTarget(project.getDefaultTarget());
        } catch (BuildException e) {
            throw new ObfuscationException("Obfuscation failed: " + e.getMessage(), e);
        } finally {
            configFile.delete();
        }
    }

    private static void generateAntXml(File file, String inJar, String outJar, 
                                     Collection<String> keepRules, 
                                     List<String> attributesToKeep,
                                     List<String> externalLibs,
                                     boolean replaceClassStrings,
                                     String namingScheme) throws IOException {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<project name=\"yGuardTask\" default=\"obfuscate\">");
            writer.println("  <taskdef name=\"yguard\" classname=\"com.yworks.yguard.YGuardTask\"/>");
            writer.println("  <target name=\"obfuscate\">");
            writer.println("    <yguard>");
            writer.println("      <inoutpair in=\"" + inJar + "\" out=\"" + outJar + "\"/>");
            
            if (!externalLibs.isEmpty()) {
                writer.println("      <externalclasses>");
                writer.println("        <fileset dir=\".\">");
                for (String lib : externalLibs) {
                    writer.println("          <include name=\"" + lib + "\"/>");
                }
                writer.println("        </fileset>");
                writer.println("      </externalclasses>");
            }

            writer.println("      <rename mainclass=\"com.example.Main\" logfile=\"yguard-mapping.xml\" replaceClassNameStrings=\"" + replaceClassStrings + "\">");
            writer.println("        <property name=\"naming-scheme\" value=\"" + namingScheme + "\"/>");
            writer.print("        <keep>");
            writeKeepRules(writer, keepRules);
            writer.println("        </keep>");
            
            for (String attr : attributesToKeep) {
                writer.println("        <attribute name=\"" + attr + "\"/>");
            }
            
            writer.println("      </rename>");
            writer.println("    </yguard>");
            writer.println("  </target>");
            writer.println("</project>");
        }
    }

    private static void writeKeepRules(PrintWriter writer, Collection<String> rules) {
        writer.println("          <method name=\"void main(java.lang.String[])\" class=\"com.example.Main\"/>");
        
        for (String rule : rules) {
            if (rule.startsWith("pkg:")) {
                String name = rule.substring(4);
                writer.println("          <class classes=\"protected\" methods=\"protected\" fields=\"protected\">");
                writer.println("            <patternset><include name=\"" + name + ".**\"/></patternset>");
                writer.println("          </class>");
            } else if (rule.startsWith("class:")) {
                String name = rule.substring(6);
                writer.println("          <class name=\"" + name + "\"/>");
            } else if (rule.startsWith("method:")) {
                String val = rule.substring(7);
                String[] parts = val.split("#");
                writer.println("          <method class=\"" + parts[0] + "\" name=\"" + parts[1] + "\"/>");
            } else if (rule.startsWith("field:")) {
                String val = rule.substring(6);
                String[] parts = val.split("#");
                writer.println("          <field class=\"" + parts[0] + "\" name=\"" + parts[1] + "\"/>");
            }
        }
    }
}
