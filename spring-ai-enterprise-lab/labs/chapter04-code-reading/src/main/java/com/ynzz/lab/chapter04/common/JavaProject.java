package com.ynzz.lab.chapter04.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaProject {
    private final String projectName;
    private final List<JavaSourceFile> files = new ArrayList<JavaSourceFile>();

    public JavaProject(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void addFile(JavaSourceFile file) {
        files.add(file);
    }

    public List<JavaSourceFile> getFiles() {
        return Collections.unmodifiableList(files);
    }

    public JavaSourceFile findByClassName(String className) {
        for (JavaSourceFile file : files) {
            if (file.getClassName().equals(className)) {
                return file;
            }
        }
        return null;
    }
}

