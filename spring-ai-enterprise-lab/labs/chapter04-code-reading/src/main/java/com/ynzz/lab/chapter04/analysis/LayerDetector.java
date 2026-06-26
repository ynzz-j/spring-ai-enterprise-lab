package com.ynzz.lab.chapter04.analysis;

import com.ynzz.lab.chapter04.common.JavaProject;
import com.ynzz.lab.chapter04.common.JavaSourceFile;

import java.util.ArrayList;
import java.util.List;

public class LayerDetector {
    public List<String> detect(JavaProject project) {
        List<String> layers = new ArrayList<String>();
        for (JavaSourceFile file : project.getFiles()) {
            String className = file.getClassName();
            addIfMissing(layers, layerOf(className));
        }
        return ordered(layers);
    }

    private String layerOf(String className) {
        if (className.endsWith("Controller")) {
            return "controller";
        }
        if (className.endsWith("Service")) {
            return "service";
        }
        if (className.endsWith("Mapper")) {
            return "mapper";
        }
        return "domain";
    }

    private void addIfMissing(List<String> values, String value) {
        if (!values.contains(value)) {
            values.add(value);
        }
    }

    private List<String> ordered(List<String> layers) {
        List<String> ordered = new ArrayList<String>();
        addIfPresent(ordered, layers, "controller");
        addIfPresent(ordered, layers, "service");
        addIfPresent(ordered, layers, "mapper");
        addIfPresent(ordered, layers, "domain");
        return ordered;
    }

    private void addIfPresent(List<String> ordered, List<String> layers, String value) {
        if (layers.contains(value)) {
            ordered.add(value);
        }
    }
}
