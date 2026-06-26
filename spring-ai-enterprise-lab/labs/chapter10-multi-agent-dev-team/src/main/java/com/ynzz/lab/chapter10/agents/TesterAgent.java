package com.ynzz.lab.chapter10.agents;

import com.ynzz.lab.chapter10.common.AgentContribution;
import com.ynzz.lab.chapter10.common.DevTaskRequest;

public class TesterAgent implements DevAgent {
    public AgentContribution contribute(DevTaskRequest request) {
        return new AgentContribution(
                "Tester",
                "{"
                        + "\"testCases\": ["
                        + "{\"name\": \"正常场景：订单超过 48 小时未发货，触发预警\", \"type\": \"正常\"}, "
                        + "{\"name\": \"边界场景：订单刚好 48 小时未发货，触发预警\", \"type\": \"边界\"}, "
                        + "{\"name\": \"边界场景：订单 47.9 小时未发货，不触发预警\", \"type\": \"边界\"}, "
                        + "{\"name\": \"异常场景：订单已发货，不触发预警\", \"type\": \"异常\"}, "
                        + "{\"name\": \"幂等场景：同一订单在扫描窗口内不被重复提醒\", \"type\": \"幂等\"}, "
                        + "{\"name\": \"失败场景：通知渠道不可用，预警记录标记待重试\", \"type\": \"异常\"}"
                        + "], "
                        + "\"testDataHints\": [\"使用 H2 内存数据库，预置订单数据（不同创建时间、不同状态）\"], "
                        + "\"edgeCaseChecklist\": [\"多租户：不同租户的阈值可能不同\", \"时钟回拨：服务器时间异常时的处理\", \"AI Gateway 超时：降级为默认文案\"]"
                        + "}",
                "requiresTestDataSetup=true");
    }
}
