package com.ynzz.lab.chapter01;

import com.ynzz.lab.chapter01.common.OrderSummaryResult;
import com.ynzz.lab.chapter01.gateway.*;
import com.ynzz.lab.chapter01.gateway.model.HttpAiCenterModelClient;
import com.ynzz.lab.chapter01.gateway.model.ModelClient;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayFilter;
import com.ynzz.lab.chapter01.gateway.pipeline.filters.*;
import com.ynzz.lab.chapter01.legacy.AiGatewayClient;
import com.ynzz.lab.chapter01.legacy.LegacyOrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

/**
 * 第 1 讲免费版 Demo：Java 8 老系统通过旁路 AI Gateway 生成订单处理建议。
 *
 * <p>当前 Demo 默认使用本地 Stub，也可通过 AI_CENTER_BASE_URL 调用旁路 AI Center。
 * 代码重点展示免费版最该讲清楚的企业边界：
 * 老系统少改、AI 能力旁路、敏感信息脱敏、审计留痕、幂等防重复调用、熔断降级、
 * 以及 AI 只给建议不修改老系统订单状态。
 *
 * <p>运行方式：
 * <pre>
 *   mvn spring-boot:run
 * </pre>
 */
@SpringBootApplication
public class Chapter01Demo implements CommandLineRunner {

    /**
     * Spring Boot 启动入口。
     * 启动后自动调用 {@link #run(String...)}。
     */
    public static void main(String[] args) {
        SpringApplication.run(Chapter01Demo.class, args);
    }

    /**
     * {@link CommandLineRunner#run(String...)} 实现。
     * Spring Boot 启动完成后自动执行此方法。
     */
    @Override
    public void run(String... args) {
        ModelClient modelClient = createModelClient();
        MaskingService maskingService = new MaskingService();
        AuditLogService auditLogService = new AuditLogService();
        RateLimitService rateLimitService = new RateLimitService(10);
        FallbackAnswerFactory fallbackFactory = new FallbackAnswerFactory();
        boolean httpModelMode = modelClient instanceof HttpAiCenterModelClient;

        List<GatewayFilter> filters = Arrays.asList(
                new ValidationFilter(),
                new IdempotencyFilter(),
                new RateLimitFilter(rateLimitService),
                new MaskingFilter(maskingService),
                new AuditRequestFilter(auditLogService),
                new CircuitBreakerFilter(modelClient),
                httpModelMode ? new ModelCallFilter(modelClient, 1, 200L) : new ModelCallFilter(modelClient),
                new ParseResponseFilter(),
                new AuditResponseFilter(auditLogService)
        );

        OrderSummaryService gatewayService = new OrderSummaryService(filters, fallbackFactory);
        LegacyOrderService legacyOrderService = new LegacyOrderService(
                new AiGatewayClient(gatewayService));

        runNormalRequest(legacyOrderService);
        runIdempotentRequest(legacyOrderService);
        runFallbackRequest(legacyOrderService, modelClient);
        runValidationRequest(legacyOrderService);

        System.out.println("\n========== 第 1 讲免费版 Demo 执行完毕 ==========");
    }

    private void runNormalRequest(LegacyOrderService legacyOrderService) {
        String orderId = "O202606050001";
        String statusBefore = legacyOrderService.getOrderStatus(orderId);

        System.out.println("========== 场景 1：老系统旁路调用 AI Gateway ==========");
        OrderSummaryResult result = legacyOrderService.assistOrder(orderId,
                "客户反馈订单延迟发货，希望今天给出处理方案。手机号 13800000000，身份证 110101199001011234，邮箱 vip@example.com。");
        String statusAfter = legacyOrderService.getOrderStatus(orderId);

        System.out.println(result.toJson());
        System.out.println("legacyOrderStatusBefore=" + statusBefore + ", legacyOrderStatusAfter=" + statusAfter);
        System.out.println("boundary=AI 只生成处理建议，不自动修改老系统订单状态。");
    }

    private void runIdempotentRequest(LegacyOrderService legacyOrderService) {
        System.out.println("\n========== 场景 2：重复请求命中幂等缓存 ==========");
        OrderSummaryResult result = legacyOrderService.assistOrder(
                "O202606050001",
                "客户反馈订单延迟发货，希望今天给出处理方案。手机号 13800000000，身份证 110101199001011234，邮箱 vip@example.com。");
        System.out.println(result.toJson());
        System.out.println("boundary=相同订单重复请求不会再次进入模型调用链路。");
    }

    private ModelClient createModelClient() {
        String baseUrl = System.getenv("AI_CENTER_BASE_URL");
        if (baseUrl != null && baseUrl.trim().length() > 0) {
            System.out.println("modelClient=HttpAiCenterModelClient, baseUrl=" + baseUrl);
            return new HttpAiCenterModelClient(baseUrl.trim());
        }
        System.out.println("modelClient=StubModelClient, reason=AI_CENTER_BASE_URL not set");
        return new StubModelClient();
    }

    private void runFallbackRequest(LegacyOrderService legacyOrderService, ModelClient modelClient) {
        System.out.println("\n========== 场景 3：模型不可用时熔断降级 ==========");
        if (modelClient instanceof StubModelClient) {
            ((StubModelClient) modelClient).setAvailable(false);
        }
        OrderSummaryResult result = legacyOrderService.assistOrder(
                "O202606050002",
                "客户要求退款，原因是商品到货时间超过预期。手机号 13900000000。");
        System.out.println(result.toJson());
        if (modelClient instanceof StubModelClient) {
            System.out.println("boundary=fallback=true 表示 AI 能力不可用时老系统仍有固定处理建议。");
            ((StubModelClient) modelClient).setAvailable(true);
        } else {
            System.out.println("boundary=外部 AI Center 模式下，停止 AI Center 可观察 HTTP 失败后的 fallback。");
        }
    }

    private void runValidationRequest(LegacyOrderService legacyOrderService) {
        System.out.println("\n========== 场景 4：参数校验失败短路 ==========");
        OrderSummaryResult result = legacyOrderService.assistOrder(
                "",
                "客户反馈订单延迟发货。");
        System.out.println(result.toJson());
        System.out.println("boundary=非法请求不会进入模型调用。");
    }
}
