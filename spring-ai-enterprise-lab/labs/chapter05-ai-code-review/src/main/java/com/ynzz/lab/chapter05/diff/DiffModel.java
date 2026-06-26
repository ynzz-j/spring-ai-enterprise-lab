package com.ynzz.lab.chapter05.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiffModel {
    private final String fileName;
    private final List<ChangedLine> addedLines = new ArrayList<ChangedLine>();

    public DiffModel(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void addLine(ChangedLine line) {
        addedLines.add(line);
    }

    public List<ChangedLine> getAddedLines() {
        return Collections.unmodifiableList(addedLines);
    }
}

