package com.ynzz.lab.chapter04.common;

public class JavaSourceFile {
    private final String className;
    private final String path;
    private final String content;

    public JavaSourceFile(String className, String path, String content) {
        this.className = className;
        this.path = path;
        this.content = content;
    }

    public String getClassName() {
        return className;
    }

    public String getPath() {
        return path;
    }

    public String getContent() {
        return content;
    }
}

