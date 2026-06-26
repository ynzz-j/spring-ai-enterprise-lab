package com.ynzz.lab.chapter06;

import com.ynzz.lab.chapter06.common.KnowledgeAskRequest;
import com.ynzz.lab.chapter06.common.KnowledgeAnswer;
import com.ynzz.lab.chapter06.ingestion.DocumentIngestionService;
import com.ynzz.lab.chapter06.rag.KeywordRetriever;
import com.ynzz.lab.chapter06.rag.RagAnswerService;
import com.ynzz.lab.chapter06.rag.RoleDocumentPolicy;

import java.nio.file.Paths;

public class Chapter06Demo {
    public static void main(String[] args) throws Exception {
        DocumentIngestionService ingestionService = new DocumentIngestionService();
        KeywordRetriever retriever = new KeywordRetriever(new RoleDocumentPolicy());
        RagAnswerService ragAnswerService = new RagAnswerService(
                ingestionService.load(Paths.get("sample-data", "docs")),
                retriever);

        run(ragAnswerService, new KnowledgeAskRequest(
                "demo",
                "u1001",
                "developer",
                "订单状态 DELAYED 代表什么？"));

        run(ragAnswerService, new KnowledgeAskRequest(
                "demo",
                "u1001",
                "developer",
                "公司明年的秘密定价策略是什么？"));

        run(ragAnswerService, new KnowledgeAskRequest(
                "demo",
                "finance01",
                "finance",
                "公司明年的定价策略是什么？"));
    }

    private static void run(RagAnswerService ragAnswerService, KnowledgeAskRequest request) {
        System.out.println("=== question: " + request.getQuestion() + " role=" + request.getRole() + " ===");
        KnowledgeAnswer answer = ragAnswerService.ask(request);
        System.out.println(answer.toJson());
        System.out.println();
    }
}

