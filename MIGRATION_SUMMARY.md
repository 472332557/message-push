# 环境变量配置迁移总结

## ✅ 已完成的改进

### 1. 代码结构优化

#### 新增文件：
- **`CozeApiConfig.java`** - Coze API 配置类
  - 使用 `@Value` 注解从配置文件读取 Token 和 Workflow ID
  - 提供统一的配置管理入口
  - 添加了完整的 JavaDoc 注释

- **`.env.example`** - 环境变量示例文件
  - 包含所有必需的环境变量模板
  - 提供了默认值参考

- **`ENV_CONFIG_GUIDE.md`** - 详细的配置指南
  - Windows/Linux/Mac 各平台的环境变量设置方法
  - 故障排查指南
  - 安全提示

- **`start.bat`** - Windows 快速启动脚本
  - 自动读取 .env 文件并设置环境变量
  - 提供交互式启动选项

#### 修改文件：
- **`application.yml`** - 添加 Coze API 配置项
- **`CozeApiService.java`** - 重构为使用配置类
  - 移除了硬编码的常量
  - 添加了参数校验逻辑
  - 改进了日志输出
  - 添加了类注释和方法注释

- **`.gitignore`** - 添加敏感配置文件忽略规则

### 2. 安全性提升

✅ **移除硬编码敏感信息**：
- Token 不再直接写在代码中
- Workflow ID 可灵活配置
- 支持通过环境变量覆盖

✅ **防止泄露措施**：
- `.env` 文件已加入 `.gitignore`
- 提供 `.env.example` 模板
- 代码审查中标记为严重问题已修复

### 3. 可维护性提升

✅ **配置集中管理**：
- 所有外部 API 配置集中在 `application.yml`
- 支持多环境配置（开发/生产）
- 便于 CI/CD 集成

✅ **代码质量改进**：
- 符合项目规范要求
- 添加了参数校验
- 改进了异常处理
- 完善的文档注释

---

## 📋 使用指南

### 快速开始（推荐方式）

#### 步骤 1：复制环境变量模板
```bash
cp .env.example .env
```

#### 步骤 2：编辑 .env 文件
```
COZE_TOKEN=pat_o2zmctmRKqtxOMBlVacJF07TMvvqGfzb90zt2JEfvVGxE7j3nChDHRJQxOVU2f71
WEATHER_WORKFLOW_ID=7602641055988891686
NEWS_WORKFLOW_ID=7603669686416441384
SERVER_PORT=8088
```

#### 步骤 3：运行启动脚本（Windows）
```cmd
start.bat
```

或者手动启动：

**方式 A - 使用 Maven：**
```bash
mvn spring-boot:run
```

**方式 B - 打包后运行：**
```bash
mvn clean package
java -jar target/message-push-1.0.0.jar
```

### 验证配置

启动后查看日志，应该看到：
```
INFO  Calling Coze API with city: 深圳，workflow_id: 7602641055988891686
INFO  Calling Coze API for news workflow, workflow_id: 7603669686416441384
```

---

## 🔧 配置优先级

Spring Boot 会按以下优先级加载配置：

1. **命令行参数**（最高优先级）
   ```bash
   java -jar app.jar --coze.api.token=xxx
   ```

2. **系统环境变量**
   ```bash
   export COZE_TOKEN=xxx
   ```

3. **application.yml 中的默认值**（最低优先级）

---

## 🎯 后续建议

### 立即可做：
1. ✅ ~~更换新的 Token~~（如果担心已泄露）
2. 测试新配置是否正确加载
3. 验证定时任务是否正常运行

### 短期优化：
1. 考虑引入配置中心（如 Nacos、Apollo）
2. 添加配置刷新功能（@RefreshScope）
3. 实现 Token 自动轮换机制

### 长期规划：
1. 建立完善的密钥管理体系
2. 实施配置审计日志
3. 定期进行安全演练

---

## ⚠️ 注意事项

### 安全警告：
- ❌ 不要将 `.env` 文件提交到 Git
- ❌ 不要在公开场合分享 Token
- ✅ 定期更换 Token
- ✅ 使用最小权限原则

### 开发环境 vs 生产环境：

| 环境 | 推荐配置方式 |
|------|-------------|
| 本地开发 | .env 文件或 IDE 环境变量 |
| 测试环境 | 系统环境变量 |
| 生产环境 | 配置中心或容器环境变量 |

---

## 🐛 常见问题

### Q1: 环境变量不生效？
**A**: 检查以下几点：
1. 确认环境变量名称正确（区分大小写）
2. 重启终端或 IDE
3. 使用 `echo $COZE_TOKEN` 或 `echo %COZE_TOKEN%` 验证

### Q2: 应用启动失败？
**A**: 查看错误日志：
1. 如果是配置缺失，检查是否正确设置环境变量
2. 如果是格式错误，检查 YAML 缩进

### Q3: 如何验证配置已加载？
**A**: 启用 DEBUG 日志：
```yaml
logging:
  level:
    com.example.weatherpush.config: debug
```

---

## 📚 相关文档

- [Spring Boot 配置管理](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config)
- [环境变量最佳实践](https://12factor.net/config)
- [Coze API 文档](https://www.coze.cn/docs)

---

**作者**: liangzc  
**更新日期**: 2026 年 03 月 12 日  
**版本**: v1.0.0
