package com.ynzz.lab.chapter06.rag;

import com.ynzz.lab.chapter06.ingestion.DocumentChunk;

public class RoleDocumentPolicy {
    public boolean canRead(String role, DocumentChunk chunk) {
        return chunk.getAllowedRoles().contains(role);
    }
}

