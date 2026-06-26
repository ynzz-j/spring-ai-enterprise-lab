package com.ynzz.lab.chapter07.agent;

import com.ynzz.lab.chapter07.common.TicketAnalysisResult;
import com.ynzz.lab.chapter07.common.TicketEvent;
import com.ynzz.lab.chapter07.policy.AssigneeRecommendation;
import com.ynzz.lab.chapter07.policy.AssignmentPolicy;

public class TicketAgentService {
    private final TicketMaskingService maskingService;
    private final TicketClassifier classifier;
    private final TicketPriorityPolicy priorityPolicy;
    private final AssignmentPolicy assignmentPolicy;
    private final TicketAuditService auditService;

    public TicketAgentService(TicketMaskingService maskingService,
                              TicketClassifier classifier,
                              TicketPriorityPolicy priorityPolicy,
                              AssignmentPolicy assignmentPolicy,
                              TicketAuditService auditService) {
        this.maskingService = maskingService;
        this.classifier = classifier;
        this.priorityPolicy = priorityPolicy;
        this.assignmentPolicy = assignmentPolicy;
        this.auditService = auditService;
    }

    public TicketAnalysisResult analyze(TicketEvent event) {
        String maskedContent = maskingService.mask(event.getContent());
        String category = classifier.classify(event.getTitle(), event.getContent());
        String priority = priorityPolicy.priorityOf(category, event.getContent());
        boolean slaRisk = priorityPolicy.hasSlaRisk(priority, event.getContent());
        AssigneeRecommendation recommendation = assignmentPolicy.recommend(category, priority);

        auditService.record("ANALYZE_TICKET", event.getTicketId(),
                "category=" + category + ", priority=" + priority + ", recommend=" + recommendation.getAssignee());

        return new TicketAnalysisResult(
                event.getTicketId(),
                "工单来自 " + event.getSource() + "，核心问题：" + summarize(maskedContent),
                category,
                priority,
                slaRisk,
                recommendation.getAssignee(),
                recommendation.getReason(),
                "WAITING_FOR_HUMAN_CONFIRMATION",
                maskingService.getLastMaskedFields());
    }

    private String summarize(String content) {
        if (content.length() <= 42) {
            return content;
        }
        return content.substring(0, 42) + "...";
    }
}

