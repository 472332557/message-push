# Weather Push Project

基于Spring Boot 2.7.9的天气推送项目，使用内置定时任务功能。

## 项目结构

```
weather-push/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/weatherpush/
│   │   │       ├── WeatherPushApplication.java      # 主启动类
│   │   │       ├── controller/
│   │   │       │   └── TestController.java         # 测试控制器
│   │   │       ├── scheduler/
│   │   │       │   └── WeatherScheduler.java       # 定时任务调度器
│   │   │       └── service/
│   │   │           └── CozeApiService.java         # Coze API服务类
│   │   └── resources/
│   │       └── application.yml                     # 配置文件
├── pom.xml                                         # Maven配置文件
└── README.md                                       # 说明文档
```

## 功能说明

1. **定时任务**: 使用Spring @Scheduled注解，每分钟自动执行天气推送任务
2. **API调用**: 调用Coze工作流API获取深圳天气信息
3. **测试接口**: 提供REST API用于手动测试
4. **自动运行**: 启动应用后定时任务自动执行，无需额外配置

## 配置说明

在 `application.yml` 中配置服务器端口：

```yaml
server:
  port: 8080
```

## 启动步骤

1. **启动项目**:
   ```bash
   mvn spring-boot:run
   ```

2. **自动执行**: 应用启动后，定时任务会自动每分钟执行一次

3. **访问测试接口**:
   - 健康检查: `http://localhost:8080/api/health`
   - 测试Coze API: `http://localhost:8080/api/test-coze`

4. **查看日志**: 在控制台查看任务执行日志

## 定时任务详情

定时任务在 `WeatherScheduler.java` 中配置：

```java
@Scheduled(cron = "0 * * * * ?")
public void scheduledWeatherPush() {
    // 每分钟执行一次
}
```

### Cron表达式说明：
- `0 * * * * ?` - 每分钟执行
- `0 0/5 * * * ?` - 每5分钟执行  
- `0 0 * * * ?` - 每小时执行
- `0 0 9 * * ?` - 每天上午9点执行

### Coze工作流API
- **URL**: `https://api.coze.cn/v1/workflow/stream_run`
- **Method**: POST
- **Headers**: 
  - `Content-Type: application/json`
  - `Authorization: Bearer pat_o2zmctmRKqtxOMBlVacJF07TMvvqGfzb90zt2JEfvVGxE7j3nChDHRJQxOVU2f71`
- **Request Body**:
  ```json
  {
    "workflow_id": "7602641055988891686",
    "parameters": {
      "city": "深圳"
    }
  }
  ```

## API详情