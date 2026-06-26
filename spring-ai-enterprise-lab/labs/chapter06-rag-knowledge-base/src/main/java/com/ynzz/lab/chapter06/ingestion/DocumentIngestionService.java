package com.ynzz.lab.chapter06.ingestion;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DocumentIngestionService {
    public List<DocumentChunk> load(Path docsDirectory) throws IOException {
        List<DocumentChunk> chunks = new ArrayList<DocumentChunk>();
        List<Path> files = new ArrayList<Path>();

        Files.walk(docsDirectory).forEach(path -> {
            if (Files.isRegularFile(path) && path.getFileName().toString().endsWith(".md")) {
                files.add(path);
            }
        });

        Collections.sort(files);
        for (Path file : files) {
            chunks.addAll(toChunks(file));
        }
        return chunks;
    }

    private List<DocumentChunk> toChunks(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        List<DocumentChunk> chunks = new ArrayList<DocumentChunk>();
        List<String> roles = rolesFor(file);
        String source = file.getFileName().toString();
        int index = 1;
        for (String line : lines) {
            String normalized = line.trim();
            if (normalized.length() == 0 || normalized.startsWith("#")) {
                continue;
            }
            String sanitized = maskSensitiveText(normalized);
            chunks.add(new DocumentChunk(source + "#" + index, source, sanitized, roles));
            index++;
        }
        return chunks;
    }

    private List<String> rolesFor(Path file) {
        String fileName = file.getFileName().toString();
        if ("pricing-policy.md".equals(fileName)) {
            return Arrays.asList("finance");
        }
        return Arrays.asList("developer", "support", "ops");
    }

    private String maskSensitiveText(String content) {
        return content.replaceAll("1[3-9][0-9]{9}", "1**********");
    }
}

