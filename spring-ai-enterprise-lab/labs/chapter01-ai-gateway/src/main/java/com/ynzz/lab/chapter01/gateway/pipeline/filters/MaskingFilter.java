package com.ynzz.lab.chapter01.gateway.pipeline.filters;

import com.ynzz.lab.chapter01.gateway.MaskingService;
import com.ynzz.lab.chapter01.gateway.MaskingService.MaskingResult;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayContext;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayFilter;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayChain;

/**
 * 脱敏 Filter（执行顺序：400）。
 *
 * <p>调用 {@link MaskingService#mask(String)} 对订单文本进行脱敏处理。
 *
 * <p>脱敏规则：
 * <ul>
 *   <li>手机号（1开头11位）：替换为 {@code 1**********}</li>
 *   <li>身份证（18位或15位）：替换为 {@code ******************}</li>
 *   <li>邮箱（含@符）：替换为 {@code ***@***}</li>
 * </ul>
 *
 * <p>脱敏后的文本和字段列表写入 {@link GatewayContext}，
 * 后续 Filter（如 ModelCallFilter）从 Context 中读取脱敏后的文本。
 */
public class MaskingFilter implements GatewayFilter {

    private final MaskingService maskingService;

    public MaskingFilter(MaskingService maskingService) {
        this.maskingService = maskingService;
    }

    @Override
    public void doFilter(GatewayContext context, GatewayChain chain) {
        String orderText = context.getRequest().getOrderText();

        // 调用脱敏服务
        MaskingResult result = maskingService.mask(orderText);

        // 将脱敏结果写入 Context
        context.setMaskedText(result.getText());
        context.setMaskedFields(result.getMaskedFields());

        // 继续执行下一个 Filter
        chain.doFilter(context);
    }
}
