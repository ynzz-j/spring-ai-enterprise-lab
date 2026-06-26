package com.ynzz.lab.chapter04.analysis;

import com.ynzz.lab.chapter04.common.CallChain;
import com.ynzz.lab.chapter04.common.ReadingPathResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadingPathGenerator {
    public ReadingPathResult generate(String projectName, String entrypoint, List<String> layers, CallChain chain) {
        List<String> readingPath = new ArrayList<String>();
        List<String> steps = chain.getSteps();

        for (int i = 0; i < steps.size(); i++) {
            String step = steps.get(i);
            if (step.contains("Controller")) {
                readingPath.add("先读 " + step + "，理解请求入口和参数。");
            } else if (step.contains("Service")) {
                readingPath.add("再读 " + step + "，理解核心业务规则和异常分支。");
            } else if (step.contains("Mapper")) {
                readingPath.add("最后读 " + step + "，理解数据落库和持久化边界。");
            } else {
                readingPath.add("阅读 " + step + "，补齐调用链上下文。");
            }
        }

        return new ReadingPathResult(
                projectName,
                Arrays.asList(entrypoint),
                layers,
                steps,
                readingPath);
    }
}

