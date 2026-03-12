# 环境变量配置说明

## 如何从环境变量读取 Token

本项目支持三种方式配置 Coze API 的 Token 和工作流 ID：

### 方式一：使用环境变量（推荐 - 最安全）

#### Windows 系统（PowerShell）

```powershell
# 临时设置（当前终端会话有效）
$env:COZE_TOKEN="pat_o2zmctmRKqtxOMBlVacJF07TMvvqGfzb90zt2JEfvVGxE7j3nChDHRJQxOVU2f71"
$env:WEATHER_WORKFLOW_ID="7602641055988891686"
$env:NEWS_WORKFLOW_ID="7603669686416441384"

# 永久设置（系统环境变量）
[System.Environment]::SetEnvironmentVariable("COZE_TOKEN", "your_token_here", "User")
[System.Environment]::SetEnvironmentVariable("WEATHER_WORKFLOW_ID", "7602641055988891686", "User")
[System.Environment]::SetEnvironmentVariable("NEWS_WORKFLOW_ID", "7603669686416441384", "User")
```

#### Windows 系统（CMD）

```cmd
REM 临时设置
set COZE_TOKEN=pat_o2zmctmRKqtxOMBlVacJF07TMvvqGfzb90zt2JEfvVGxE7j3nChDHRJQxOVU2f71
set WEATHER_WORKFLOW_ID=7602641055988891686
set NEWS_WORKFLOW_ID=7603669686416441384

REM 永久设置（需要管理员权限）
setx COZE_TOKEN "your_token_here"
setx WEATHER_WORKFLOW_ID "7602641055988891686"
setx NEWS_WORKFLOW_ID "7603669686416441384"
```

#### Linux/Mac 系统

```bash
# 临时设置
export COZE_TOKEN="pat_o2zmctmRKqtxOMBlVacJF07TMvvqGfzb90zt2JEfvVGxE7j3nChDHRJQxOVU2f71"
export WEATHER_WORKFLOW_ID="7602641055988891686"
export NEWS_WORKFLOW_ID="7603669686416441384"

# 永久设置（添加到 ~/.bashrc 或 ~/.zshrc）
echo 'export COZE_TOKEN="your_token_here"' >> ~/.bashrc
echo 'export WEATHER_WORKFLOW_ID="7602641055988891686"' >> ~/.bashrc
echo 'export NEWS_WORKFLOW_ID="7603669686416441384"' >> ~/.bashrc
source ~/.bashrc
```

### 方式二：使用 .env 文件（本地开发方便）

1. 复制环境变量示例文件：
```bash
cp .env.example .env
```

2. 编辑 `.env` 文件，填入真实值：
```
COZE_TOKEN=your_real_token_here
WEATHER_WORKFLOW_ID=7602641055988891686
NEWS_WORKFLOW_ID=7603669686416441384
```

3. 启动应用前加载环境变量：

**Windows PowerShell:**
```powershell
Get-Content .env | ForEach-Object {
    if ($_ -match '^\s*([^#][^=]+)\s*=\s*(.+)\s*$') {
        $key = $matches[1].Trim()
        $value = $matches[2].Trim()
        Set-Item -Path "Env:$key" -Value $value
    }
}
java -jar target/message-push-1.0.0.jar
```

**Linux/Mac:**
```bash
set -a
source .env
set +a
java -jar target/message-push-1.0.0.jar
```

### 方式三：直接修改 application.yml（不推荐用于生产环境）

编辑 `src/main/resources/application.yml`：

```yaml
coze:
  api:
    url: https://api.coze.cn/v1/workflow/stream_run
    token: pat_o2zmctmRKqtxOMBlVacJF07TMvvqGfzb90zt2JEfvVGxE7j3nChDHRJQxOVU2f71
    workflow-id:
      weather: 7602641055988891686
      news: 7603669686416441384
```

## 验证配置是否生效

启动应用后，查看日志输出：

```
INFO  Calling Coze API with city: 深圳，workflow_id: 7602641055988891686
INFO  Calling Coze API for news workflow, workflow_id: 7603669686416441384
```

如果看到 workflow_id 正确显示，说明配置已生效。

## 安全提示

⚠️ **重要安全警告**：

1. **永远不要**将包含真实 Token 的 `.env` 文件提交到 Git
2. **永远不要**将真实的 Token 硬编码到代码中
3. 生产环境务必使用环境变量或配置中心
4. 定期更换 Token，避免泄露

项目已配置 `.gitignore` 忽略以下文件：
- `.env`
- `.env.local`
- `.env.production`

## 故障排查

### 问题 1：启动时报配置缺失错误

**原因**：环境变量未设置且 application.yml 中无默认值

**解决**：检查是否正确设置了环境变量，或确认 application.yml 中有默认值

### 问题 2：Token 无效或过期

**原因**：Token 配置错误或已失效

**解决**：
1. 检查环境变量是否正确设置
2. 确认 Token 是否在 Coze 平台有效
3. 联系管理员获取新的 Token

### 问题 3：读取不到环境变量

**原因**：环境变量作用域问题

**解决**：
- Windows: 确认是用户变量还是系统变量
- Linux/Mac: 确认是否在正确的 shell 配置文件（.bashrc/.zshrc）中设置
