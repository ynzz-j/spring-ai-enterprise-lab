package com.ynzz.lab.chapter07;

import com.ynzz.lab.chapter07.agent.AssignmentConfirmationService;
import com.ynzz.lab.chapter07.agent.TicketAgentService;
import com.ynzz.lab.chapter07.agent.TicketAuditService;
import com.ynzz.lab.chapter07.agent.TicketClassifier;
import com.ynzz.lab.chapter07.agent.TicketMaskingService;
import com.ynzz.lab.chapter07.agent.TicketPriorityPolicy;
import com.ynzz.lab.chapter07.common.TicketAnalysisResult;
import com.ynzz.lab.chapter07.common.TicketEvent;
import com.ynzz.lab.chapter07.policy.AssignmentPolicy;

public class Chapter07Demo {
    public static void main(String[] args) {
        TicketAuditService auditService = new TicketAuditService();
        TicketAgentService ticketAgentService = new TicketAgentService(
                new TicketMaskingService(),
                new TicketClassifier(),
                new TicketPriorityPolicy(),
                new AssignmentPolicy(),
                auditService);
        AssignmentConfirmationService confirmationService = new AssignmentConfirmationService(auditService);

        TicketAnalysisResult paymentResult = ticketAgentService.analyze(new TicketEvent(
                "demo",
                "T202606050001",
                "用户支付成功但订单仍显示未支付",
                "用户反馈 10:32 已支付，订单号 O202606050001，后台仍显示 CREATED，手机号 13800000000。",
                "customer-service"));
        print(paymentResult);

        TicketAnalysisResult deliveryResult = ticketAgentService.analyze(new TicketEvent(
                "demo",
                "T202606050002",
                "用户咨询订单延迟发货",
                "用户说订单已经三天没有发货，希望确认仓库进度。",
                "customer-service"));
        print(deliveryResult);

        System.out.println("=== human confirmation ===");
        System.out.println(confirmationService.confirm("T202606050001", paymentResult.getRecommendedAssignee(), "ops-admin"));
    }

    private static void print(TicketAnalysisResult result) {
        System.out.println("=== ticket: " + result.getTicketId() + " ===");
        System.out.println(result.toJson());
        System.out.println();
    }
}

