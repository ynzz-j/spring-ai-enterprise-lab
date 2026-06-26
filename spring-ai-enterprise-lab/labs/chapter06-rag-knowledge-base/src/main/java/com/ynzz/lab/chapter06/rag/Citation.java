package com.ynzz.lab.chapter06.rag;

public class Citation {
    private final String source;
    private final String chunkId;

    public Citation(String source, String chunkId) {
        this.source = source;
        this.chunkId = chunkId;
    }

    public String getSource() {
        return source;
    }

    public String getChunkId() {
        return chunkId;
    }
}

