package com.ynzz.lab.chapter06.ingestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentChunk {
    private final String chunkId;
    private final String source;
    private final String content;
    private final List<String> allowedRoles;

    public DocumentChunk(String chunkId, String source, String content, List<String> allowedRoles) {
        this.chunkId = chunkId;
        this.source = source;
        this.content = content;
        this.allowedRoles = new ArrayList<String>(allowedRoles);
    }

    public String getChunkId() {
        return chunkId;
    }

    public String getSource() {
        return source;
    }

    public String getContent() {
        return content;
    }

    public List<String> getAllowedRoles() {
        return Collections.unmodifiableList(allowedRoles);
    }
}

