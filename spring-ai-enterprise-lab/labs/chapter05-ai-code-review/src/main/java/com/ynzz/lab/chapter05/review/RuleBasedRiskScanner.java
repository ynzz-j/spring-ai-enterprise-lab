package com.ynzz.lab.chapter05.review;

import com.ynzz.lab.chapter05.diff.ChangedLine;
import com.ynzz.lab.chapter05.diff.DiffModel;

import java.util.ArrayList;
import java.util.List;

public class RuleBasedRiskScanner {
    public List<ReviewFinding> scan(DiffModel diff) {
        List<ReviewFinding> findings = new ArrayList<>();

        boolean usesRequestGetter = false;
        int requestGetterLine = -1;
        boolean hasRequestNullCheck = false;

        boolean hasUserIdParam = false;
        int userIdLine = -1;
        int productIdLine = -1;
        boolean hasParamCheck = false;
        ReviewFinding stockFinding = null;

        for (ChangedLine line : diff.getAddedLines()) {
            String content = line.getContent();

            // 空指针风险扫描
            if (content.contains("request.get")) {
                usesRequestGetter = true;
                if (requestGetterLine < 0) {
                    requestGetterLine = line.getLineNumber();
                }
            }
            if (content.contains("request == null") || content.contains("request != null")) {
                hasRequestNullCheck = true;
            }

            // 参数校验扫描
            if (content.contains("userId") || content.contains("productId")) {
                hasUserIdParam = true;
                if (userIdLine < 0) {
                    userIdLine = line.getLineNumber();
                }
                if (content.contains("productId") && productIdLine < 0) {
                    productIdLine = line.getLineNumber();
                }
            }
            if (content.contains("null") || content.contains("isEmpty")
                    || content.contains("isBlank") || content.contains("StringUtils")) {
                hasParamCheck = true;
            }

            // 库存校验缺失
            if (content.contains("TODO") && content.toLowerCase().contains("stock")) {
                stockFinding = new ReviewFinding(
                        "missing-stock-check",
                        "MEDIUM",
                        diff.getFileName(),
                        line.getLineNumber(),
                        "库存校验缺失",
                        "新增代码留下库存校验 TODO，创建订单前可能没有真实校验库存。",
                        "在 Service 层增加库存检查，Controller 不承载业务规则。");
            }
        }

        if (usesRequestGetter && !hasRequestNullCheck) {
            findings.add(0, new ReviewFinding(
                    "null-safety",
                    "MEDIUM",
                    diff.getFileName(),
                    requestGetterLine,
                    "可能的空指针风险",
                    "新增代码直接读取 request 字段，但没有判断 request 是否为空。",
                    "在进入业务逻辑前增加请求对象校验。"));
        }

        if (hasUserIdParam && !hasParamCheck) {
            findings.add(new ReviewFinding(
                    "missing-param-check",
                    "MEDIUM",
                    diff.getFileName(),
                    productIdLine > 0 ? productIdLine : userIdLine,
                    "参数校验缺失",
                    "新增代码提取了 userId、productId 业务参数，但没有做非空或格式校验。",
                    "在 Service 入口或 Controller 层增加必填参数校验。"));
        }

        if (stockFinding != null) {
            findings.add(stockFinding);
        }

        return findings;
    }
}
