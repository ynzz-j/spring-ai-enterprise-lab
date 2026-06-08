# ai-center-mvp

Spring AI 1.1.7 最小旁路服务。

它不替代 Java 8 老系统，也不要求第 1-3 章 Lab 升级到 Java 17。老系统侧继续保持 Java 8，通过 HTTP 调用这个旁路 AI 能力中心。

## 运行

默认无 Key 模式：

```powershell
.\compile-and-run.ps1
```

真实模型模式：

```powershell
$env:SPRING_AI_OPENAI_API_KEY="sk-..."
$env:SPRING_PROFILES_ACTIVE="real"
.\compile-and-run.ps1
```

默认端口：`18080`。

## 接口

```text
POST /api/mvp/order-summary
POST /api/mvp/tool-intent
POST /api/mvp/sql-candidate
```

第 1-3 章设置 `AI_CENTER_BASE_URL=http://localhost:18080` 后会调用这些接口；不设置时继续走本地 Stub。
