package com.ynzz.lab.chapter01.gateway.pipeline;

/**
 * 责任链中各个 Filter 的执行顺序常量。
 *
 * <p>为什么用常量而不是硬编码数字？
 * <ul>
 *   <li>可读性强：{@code order = FilterOrderConstants.VALIDATION}</li>
 *   <li>易维护：插入新 Filter 时只需调整常量值，不用改所有 Filter 的实现</li>
 *   <li>集中管理：所有顺序都在这里，一目了然</li>
 * </ul>
 *
 * <p>执行顺序（从小到大）：
 * <ol>
 *   <li>VALIDATION (100)      - 参数校验</li>
 *   <li>IDEMPOTENCY (200)     - 幂等校验</li>
 *   <li>RATE_LIMIT (300)       - 限流</li>
 *   <li>MASKING (400)          - 脱敏</li>
 *   <li>AUDIT_REQUEST (500)    - 请求审计</li>
 *   <li>CIRCUIT_BREAKER (600) - 熔断器（在模型调用之前）</li>
 *   <li>MODEL_CALL (700)       - AI 模型调用</li>
 *   <li>PARSE_RESPONSE (800)   - 结构化输出解析</li>
 *   <li>AUDIT_RESPONSE (900)  - 响应审计</li>
 * </ol>
 */
public class FilterOrderConstants {

    public static final int VALIDATION     = 100;
    public static final int IDEMPOTENCY    = 200;
    public static final int RATE_LIMIT     = 300;
    public static final int MASKING        = 400;
    public static final int AUDIT_REQUEST  = 500;
    public static final int CIRCUIT_BREAKER = 600;
    public static final int MODEL_CALL     = 700;
    public static final int PARSE_RESPONSE = 800;
    public static final int AUDIT_RESPONSE = 900;

    private FilterOrderConstants() {}  // 工具类，不允许实例化
}
