# Chapter 10 - 多 Agent 软件开发团队

## 本讲目标

用多个 Agent 模拟企业研发流程，围绕 Java 8 老系统需求输出分析、方案、Patch 建议、测试用例和 Review 报告。

## 模块结构

```text
chapter10-multi-agent-dev-team
├── agent-orchestrator
│   └── 多 Agent 编排、上下文传递、冲突处理
├── dev-team-agents
│   └── Planner / Architect / Coder / Tester / Reviewer
├── final-report
│   └── 最终开发报告和训练路径建议
├── sample-data
│   └── 输入需求和输出报告样例
└── api.http
```

## 演示流程

```text
1. 用户提交老系统改造需求
2. Planner Agent 拆解任务
3. Architect Agent 输出技术方案
4. Coder Agent 生成 Patch 建议
5. Tester Agent 生成测试用例
6. Reviewer Agent 检查风险
7. Orchestrator 汇总冲突和不确定项
8. 输出最终报告
```

## 本地运行

当前目录提供 Java 8 纯 Stub 版本，演示多 Agent 研发协作的结构化输出。

```powershell
.\compile-and-run.ps1
```

可观察结果：

- Planner / Architect / Coder / Tester / Reviewer 都会输出各自贡献。
- Coder 只输出 Patch 建议，`patchSuggestionOnly=true`。
- Orchestrator 会列出冲突和不确定项。
- 最终报告要求人工确认，`requiresHumanConfirmation=true`。
- 报告会输出 CodeStep 训练路径建议。

## API

```http
POST /ai/dev-team/tasks
```

## 企业边界

- 多 Agent 只输出建议和 Patch，不直接提交代码。
- 冲突必须显式列出。
- 不确定项必须显式列出。
- 最终结果需要人工确认。
- 可以把薄弱点导出为 CodeStep 训练路径。

## CodeStep 连接点

多 Agent 发现的知识缺口可以沉淀为训练路径：

```text
需求理解薄弱
接口设计薄弱
事务边界薄弱
测试用例薄弱
```

## 文章反写角度

多 Agent 不要写成炫技，要落到企业研发流程：需求、设计、编码、测试、评审，每一步都有边界。
