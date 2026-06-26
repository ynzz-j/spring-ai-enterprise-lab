package com.ynzz.lab.chapter04.analysis;

import com.ynzz.lab.chapter04.common.JavaProject;
import com.ynzz.lab.chapter04.common.JavaSourceFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaProjectScanner {
    public JavaProject scan(String projectName, File projectPath) {
        JavaProject project = new JavaProject(projectName);
        scanDirectory(project, projectPath);
        return project;
    }

    private void scanDirectory(JavaProject project, File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                scanDirectory(project, file);
            } else if (file.getName().endsWith(".java")) {
                project.addFile(readJavaFile(file));
            }
        }
    }

    private JavaSourceFile readJavaFile(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < lines.size(); i++) {
                content.append(lines.get(i)).append("\n");
            }
            return new JavaSourceFile(extractClassName(content.toString(), file), file.getPath(), content.toString());
        } catch (IOException ex) {
            throw new IllegalStateException("failed to read " + file.getPath(), ex);
        }
    }

    private String extractClassName(String content, File file) {
        Matcher matcher = Pattern.compile("class\\s+([A-Za-z0-9_]+)").matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return file.getName().replace(".java", "");
    }
}

