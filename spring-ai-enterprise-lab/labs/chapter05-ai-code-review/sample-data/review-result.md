### AI Review

风险等级：MEDIUM

#### 1. 可能的空指针风险

- 文件：`OrderController.java`
- 位置：`request.getUserId()` / `request.getProductId()`
- 原因：新增代码直接读取 `request` 字段，但没有判断 `request` 是否为空。
- 建议：在进入业务逻辑前增加请求对象校验。

#### 2. 参数校验缺失

- 文件：`OrderController.java`
- 位置：`String userId = request.getUserId()`
- 原因：新增代码提取了 userId、productId 业务参数，但没有做非空或格式校验。
- 建议：在 Service 入口或 Controller 层增加必填参数校验。

#### 3. 库存校验缺失

- 文件：`OrderController.java`
- 位置：`TODO check product stock later`
- 原因：创建订单前没有校验库存，可能导致超卖。
- 建议：在 `OrderService` 层增加库存检查，Controller 不承载业务规则。
