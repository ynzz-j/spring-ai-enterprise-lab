package com.ynzz.lab.chapter04.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReadingPathResult {
    private final String projectName;
    private final List<String> entrypoints;
    private final List<String> layers;
    private final List<String> callChain;
    private final List<String> readingPath;

    public ReadingPathResult(String projectName, List<String> entrypoints, List<String> layers,
                             List<String> callChain, List<String> readingPath) {
        this.projectName = projectName;
        this.entrypoints = new ArrayList<String>(entrypoints);
        this.layers = new ArrayList<String>(layers);
        this.callChain = new ArrayList<String>(callChain);
        this.readingPath = new ArrayList<String>(readingPath);
    }

    public String getProjectName() {
        return projectName;
    }

    public List<String> getEntrypoints() {
        return Collections.unmodifiableList(entrypoints);
    }

    public List<String> getLayers() {
        return Collections.unmodifiableList(layers);
    }

    public List<String> getCallChain() {
        return Collections.unmodifiableList(callChain);
    }

    public List<String> getReadingPath() {
        return Collections.unmodifiableList(readingPath);
    }

    public String toJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("  \"projectName\": \"").append(projectName).append("\",\n");
        builder.append("  \"entrypoints\": ").append(array(entrypoints)).append(",\n");
        builder.append("  \"layers\": ").append(array(layers)).append(",\n");
        builder.append("  \"callChain\": ").append(array(callChain)).append(",\n");
        builder.append("  \"readingPath\": ").append(array(readingPath)).append("\n");
        builder.append("}");
        return builder.toString();
    }

    private String array(List<String> values) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append("\"").append(values.get(i).replace("\"", "\\\"")).append("\"");
        }
        builder.append("]");
        return builder.toString();
    }
}
