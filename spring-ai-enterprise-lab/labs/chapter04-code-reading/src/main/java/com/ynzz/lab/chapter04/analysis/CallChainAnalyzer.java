package com.ynzz.lab.chapter04.analysis;

import com.ynzz.lab.chapter04.common.CallChain;
import com.ynzz.lab.chapter04.common.JavaProject;
import com.ynzz.lab.chapter04.common.JavaSourceFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallChainAnalyzer {
    public CallChain analyze(JavaProject project, String entrypoint) {
        CallChain chain = new CallChain();
        chain.add(entrypoint);

        String[] parts = entrypoint.split("#");
        if (parts.length != 2) {
            return chain;
        }

        String currentClass = parts[0];
        String currentMethod = parts[1];
        int guard = 0;

        while (guard < 10) {
            guard++;
            JavaSourceFile file = project.findByClassName(currentClass);
            if (file == null) {
                break;
            }

            NextCall next = findNextCall(project, file.getContent(), currentMethod);
            if (next == null) {
                break;
            }

            String step = next.className + "#" + next.methodName;
            if (chain.getSteps().contains(step)) {
                break;
            }
            chain.add(step);
            currentClass = next.className;
            currentMethod = next.methodName;
        }

        return chain;
    }

    private NextCall findNextCall(JavaProject project, String content, String methodName) {
        for (JavaSourceFile target : project.getFiles()) {
            String variableName = lowerFirst(target.getClassName());
            Pattern pattern = Pattern.compile(variableName + "\\.([A-Za-z0-9_]+)\\(");
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                return new NextCall(target.getClassName(), matcher.group(1));
            }
        }

        Pattern direct = Pattern.compile("new\\s+([A-Za-z0-9_]+)\\s*\\(");
        Matcher directMatcher = direct.matcher(content);
        while (directMatcher.find()) {
            String className = directMatcher.group(1);
            if (!className.equals(methodName) && project.findByClassName(className) != null) {
                return new NextCall(className, "unknown");
            }
        }
        return null;
    }

    private String lowerFirst(String value) {
        if (value.length() == 0) {
            return value;
        }
        return Character.toLowerCase(value.charAt(0)) + value.substring(1);
    }

    private static class NextCall {
        private final String className;
        private final String methodName;

        private NextCall(String className, String methodName) {
            this.className = className;
            this.methodName = methodName;
        }
    }
}

