package com.ynzz.lab.chapter05.diff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitDiffParser {
    public DiffModel parse(String diffText) {
        DiffModel model = new DiffModel("OrderController.java");
        int currentLine = 1;

        try {
            BufferedReader reader = new BufferedReader(new StringReader(diffText));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("@@")) {
                    currentLine = parseStartLine(line);
                    continue;
                }
                if (line.startsWith("+") && !line.startsWith("+++")) {
                    model.addLine(new ChangedLine(currentLine, line.substring(1)));
                    currentLine++;
                } else if (!line.startsWith("-")) {
                    currentLine++;
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException("failed to parse diff", ex);
        }

        return model;
    }

    private int parseStartLine(String hunkHeader) {
        Matcher matcher = Pattern.compile("\\+(\\d+)").matcher(hunkHeader);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 1;
    }
}

