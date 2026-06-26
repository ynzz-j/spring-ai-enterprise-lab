package com.ynzz.lab.chapter10.common;

import java.util.ArrayList;
import java.util.List;

public class DevTeamReport {
    private final DevTaskRequest request;
    private final List<AgentContribution> contributions;
    private final List<String> conflicts;
    private final List<String> uncertainties;
    private final List<String> codeStepPaths;
    private final boolean patchSuggestionOnly;
    private final boolean requiresHumanConfirmation;

    public DevTeamReport(DevTaskRequest request,
                         List<AgentContribution> contributions,
                         List<String> conflicts,
                         List<String> uncertainties,
                         List<String> codeStepPaths,
                         boolean patchSuggestionOnly,
                         boolean requiresHumanConfirmation) {
        this.request = request;
        this.contributions = new ArrayList<AgentContribution>(contributions);
        this.conflicts = new ArrayList<String>(conflicts);
        this.uncertainties = new ArrayList<String>(uncertainties);
        this.codeStepPaths = new ArrayList<String>(codeStepPaths);
        this.patchSuggestionOnly = patchSuggestionOnly;
        this.requiresHumanConfirmation = requiresHumanConfirmation;
    }

    public String toJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("  \"projectName\": \"").append(request.getProjectName()).append("\",\n");
        builder.append("  \"requirement\": \"").append(escape(request.getRequirement())).append("\",\n");
        builder.append("  \"patchSuggestionOnly\": ").append(patchSuggestionOnly).append(",\n");
        builder.append("  \"requiresHumanConfirmation\": ").append(requiresHumanConfirmation).append(",\n");
        builder.append("  \"contributions\": [");
        for (int i = 0; i < contributions.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(contributions.get(i).toJson());
        }
        builder.append("],\n");
        builder.append("  \"conflicts\": ").append(stringArray(conflicts)).append(",\n");
        builder.append("  \"uncertainties\": ").append(stringArray(uncertainties)).append(",\n");
        builder.append("  \"codeStepPaths\": ").append(stringArray(codeStepPaths)).append("\n");
        builder.append("}");
        return builder.toString();
    }

    public String toMarkdown() {
        StringBuilder builder = new StringBuilder();
        builder.append("# 多 Agent 开发报告\n\n");
        builder.append("## 需求摘要\n\n").append(request.getRequirement()).append("\n\n");
        builder.append("## Agent 输出\n\n");
        for (AgentContribution contribution : contributions) {
            builder.append("- ").append(contribution.getAgentName()).append(" Agent：")
                    .append(contribution.getOutput()).append("（").append(contribution.getNote()).append("）\n");
        }
        builder.append("\n## 冲突\n\n");
        for (String conflict : conflicts) {
            builder.append("- ").append(conflict).append("\n");
        }
        builder.append("\n## 不确定项\n\n");
        for (String uncertainty : uncertainties) {
            builder.append("- ").append(uncertainty).append("\n");
        }
        builder.append("\n## CodeStep 训练建议\n\n");
        for (String path : codeStepPaths) {
            builder.append("- ").append(path).append("\n");
        }
        return builder.toString();
    }

    private String stringArray(List<String> values) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append("\"").append(escape(values.get(i))).append("\"");
        }
        builder.append("]");
        return builder.toString();
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
