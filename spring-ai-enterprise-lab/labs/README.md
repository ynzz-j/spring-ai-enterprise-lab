# Labs

每一讲的 Java 8 基础 Lab 放在这里。

建议每个 Lab 保持相同结构：

```text
chapterXX-name
├── README.md
├── api.http
├── docker-compose.yml
├── sample-data
└── src
```

每个 Lab 的 `README.md` 必须回答：

1. 这一讲模拟哪个企业场景。
2. Java 8 老系统侧新增了什么。
3. AI 能力中心侧新增了什么。
4. 如何本地运行。
5. 如何验证企业边界生效。
6. 如果启用 `AI_CENTER_BASE_URL`，这一讲如何调用旁路 AI 能力中心。

## Spring AI MVP 接入方式

Spring AI 1.1.7 不直接加入这些 Java 8 Lab。

```text
Java 8 Lab
        ↓ HTTP
../ai-center-mvp
        ↓
Spring AI 1.1.7
```

默认不设置 `AI_CENTER_BASE_URL` 时，Lab 继续走本地 Stub，保证公开代码无 Key 可运行。
