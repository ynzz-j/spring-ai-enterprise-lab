package com.ynzz.lab.chapter01.gateway.pipeline;

import com.ynzz.lab.chapter01.common.OrderSummaryRequest;
import com.ynzz.lab.chapter01.common.OrderSummaryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 贯穿整条责任链的上下文对象。
 *
 * <p>每个 Filter 通过 GatewayContext 读写中间状态，
 * 避免用方法参数传递大量中间变量。
 *
 * <p>架构决策：为什么不用方法参数传递中间状态？
 * <ul>
 *   <li>Filter 接口签名稳定：doFilter(context, chain)</li>
 *   <li>新增中间状态 = 给 Context 加字段，不用改所有 Filter 的方法签名</li>
 *   <li>责任链中途的 Filter 可以读取前面 Filter 设置的状态</li>
 * </ul>
 */
public class GatewayContext {

    // ---- 请求相关 ----
    private final OrderSummaryRequest request;
    private final String traceId;  // 链路追踪 ID

    // ---- 中间状态（由各 Filter 设置）----
    private String maskedText;       // 脱敏后的文本
    private List<String> maskedFields = new ArrayList<>();  // 被脱敏的字段名列表
    private Object modelResponse;    // 模型原始响应（可能是 String 或 JSON）
    private OrderSummaryResult parsedResult;  // 解析后的结构化结果

    // ---- 最终结果 ----
    private OrderSummaryResult result;

    // ---- 异常信息 ----
    private Exception exception;

    public GatewayContext(OrderSummaryRequest request) {
        this.request = request;
        this.traceId = UUID.randomUUID().toString().substring(0, 8);
    }

    // ---- Getter / Setter ----

    public OrderSummaryRequest getRequest() {
        return request;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getMaskedText() {
        return maskedText;
    }

    public void setMaskedText(String maskedText) {
        this.maskedText = maskedText;
    }

    public List<String> getMaskedFields() {
        return maskedFields;
    }

    public void setMaskedFields(List<String> maskedFields) {
        this.maskedFields = maskedFields != null ? maskedFields : new ArrayList<>();
    }

    public Object getModelResponse() {
        return modelResponse;
    }

    public void setModelResponse(Object modelResponse) {
        this.modelResponse = modelResponse;
    }

    public OrderSummaryResult getParsedResult() {
        return parsedResult;
    }

    public void setParsedResult(OrderSummaryResult parsedResult) {
        this.parsedResult = parsedResult;
    }

    public OrderSummaryResult getResult() {
        return result;
    }

    public void setResult(OrderSummaryResult result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    // ---- 便捷方法 ----

    /**
     * 获取租户 ID（从 Request 中取）。
     */
    public String getTenantId() {
        return request != null ? request.getTenantId() : "UNKNOWN";
    }

    /**
     * 获取操作人 ID（从 Request 中取）。
     */
    public String getOperatorId() {
        return request != null ? request.getOperatorId() : "UNKNOWN";
    }

    /**
     * 获取订单 ID（从 Request 中取）。
     */
    public String getOrderId() {
        return request != null ? request.getOrderId() : "UNKNOWN";
    }
}
