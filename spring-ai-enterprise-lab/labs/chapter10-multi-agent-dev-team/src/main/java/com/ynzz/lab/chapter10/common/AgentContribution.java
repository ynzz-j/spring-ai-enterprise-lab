package com.ynzz.lab.chapter10.common;

public class AgentContribution {
    private final String agentName;
    private final String outputJson;
    private final String note;

    public AgentContribution(String agentName, String outputJson, String note) {
        this.agentName = agentName;
        this.outputJson = outputJson;
        this.note = note;
    }

    public String getAgentName() {
        return agentName;
    }

    public String getOutput() {
        return outputJson;
    }

    public String getNote() {
        return note;
    }

    public String toJson() {
        return "{"
                + "\"agentName\": \"" + agentName + "\", "
                + "\"output\": " + outputJson + ", "
                + "\"note\": \"" + escape(note) + "\""
                + "}";
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
