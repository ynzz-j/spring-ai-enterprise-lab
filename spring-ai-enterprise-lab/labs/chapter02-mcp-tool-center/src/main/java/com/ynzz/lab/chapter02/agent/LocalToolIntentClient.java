package com.ynzz.lab.chapter02.agent;

import com.ynzz.lab.chapter02.common.ToolAskRequest;
import com.ynzz.lab.chapter02.mcp.ToolPermissionPolicy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalToolIntentClient implements ToolIntentClient {
    private final ToolPermissionPolicy permissionPolicy;

    public LocalToolIntentClient(ToolPermissionPolicy permissionPolicy) {
        this.permissionPolicy = permissionPolicy;
    }

    @Override
    public ToolIntent inspect(ToolAskRequest request) {
        String intentType = permissionPolicy.isWriteIntent(request.getQuestion()) ? "WRITE" : "READ";
        return new ToolIntent(intentType, extractOrderId(request.getQuestion()), "local intent by deterministic rules");
    }

    private String extractOrderId(String question) {
        Matcher matcher = Pattern.compile("O[0-9]{12,}").matcher(question);
        if (matcher.find()) {
            return matcher.group();
        }
        return "UNKNOWN";
    }
}
