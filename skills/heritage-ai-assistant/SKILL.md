---
name: heritage-ai-assistant
description: 用于管理和调试河南非遗数字展示系统中的AI助手功能，包括API配置检查、网络连接测试、错误诊断和功能优化。当用户遇到AI助手页面的网络连接失败、API调用失败等问题时，使用此技能进行故障排除和修复。
compatibility:
  - tools: [Read, Edit, RunCommand, GetDiagnostics]
---

# 河南非遗AI助手管理与调试技能

## 功能概述

此技能用于管理和调试河南非遗数字展示系统中的AI助手功能，帮助用户解决以下问题：

- AI助手页面显示"网络连接失败"错误
- AI调用失败或无响应
- 前端axios请求接口地址配置问题
- Vite代理配置问题
- 后端Spring Boot服务状态问题
- AI API密钥配置问题

## 工作流程

### 1. 检查系统状态

1. **检查后端服务状态**：
   - 检查端口8083是否有进程在监听
   - 测试后端API接口是否正常响应
   - 验证AI API密钥配置是否正确

2. **检查前端服务状态**：
   - 检查前端开发服务器是否运行
   - 测试前端到后端的网络连接
   - 验证Vite代理配置是否正确

### 2. 诊断问题

1. **前端配置检查**：
   - 检查前端API配置文件 (`src/api/index.js`)
   - 检查Vite代理配置 (`vite.config.js`)
   - 检查AI助手页面代码 (`src/views/AiAssistantView.vue`)

2. **后端配置检查**：
   - 检查后端AI配置 (`application.yml`)
   - 检查AI控制器代码 (`AiController.java`)
   - 检查AI服务实现 (`AiServiceImpl.java`)

### 3. 修复问题

1. **网络连接问题**：
   - 修复Vite代理配置
   - 调整前端API请求路径
   - 确保后端服务正常运行

2. **AI调用问题**：
   - 检查AI API密钥配置
   - 验证AI模型配置
   - 修复API参数格式

### 4. 验证修复

1. **测试API接口**：
   - 测试后端AI聊天接口
   - 测试前端到后端的API调用
   - 验证AI助手功能是否正常工作

2. **性能优化**：
   - 提供AI助手功能的优化建议
   - 检查网络延迟和响应时间
   - 确保系统稳定性

## 操作指南

### 检查后端服务状态

```bash
# 检查后端服务端口
netstat -ano | findstr :8083

# 测试后端API接口
Invoke-WebRequest -Uri "http://localhost:8083/api/ai/chat" -Method POST -ContentType "application/json" -Body '{"messages":[{"role":"user","content":"你好"}]}'
```

### 检查前端服务状态

```bash
# 检查前端服务端口
netstat -ano | findstr :5173

# 测试前端到后端的API调用
Invoke-WebRequest -Uri "http://localhost:5173/api/ai/chat" -Method POST -ContentType "application/json" -Body '{"messages":[{"role":"user","content":"你好"}]}'
```

### 修复Vite代理配置

在 `vite.config.js` 中确保以下配置：

```javascript
'/api': {
  target: 'http://localhost:8083',
  changeOrigin: true,
  secure: false
}
```

### 修复前端API配置

在 `src/api/index.js` 中确保baseURL配置正确：

```javascript
const api = axios.create({
  baseURL: import.meta.env.DEV ? '/' : '/',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json; charset=UTF-8',
    'Accept': 'application/json; charset=UTF-8'
  }
})
```

### 修复AI助手页面代码

在 `src/views/AiAssistantView.vue` 中确保API调用路径正确：

```javascript
const res = await api.post('/api/ai/chat', payload)
```

## 常见问题解决方案

1. **网络连接失败**：
   - 检查后端服务是否运行
   - 检查Vite代理配置是否正确
   - 检查前端API请求路径是否正确

2. **AI调用失败**：
   - 检查AI API密钥是否配置正确
   - 检查后端AI服务是否正常工作
   - 检查前端请求参数格式是否正确

3. **404错误**：
   - 检查后端API接口路径是否正确
   - 检查Vite代理配置是否正确
   - 检查前端请求路径是否正确

4. **500错误**：
   - 检查后端AI服务配置
   - 检查AI API密钥是否有效
   - 检查后端服务日志

## 测试用例

### 测试用例1：检查后端服务状态

**输入**：检查后端服务是否运行
**输出**：后端服务状态报告，包括端口监听情况和API响应状态

### 测试用例2：检查前端到后端的连接

**输入**：测试前端到后端的API调用
**输出**：连接测试结果，包括响应状态和数据

### 测试用例3：修复网络连接问题

**输入**：修复AI助手页面的网络连接失败问题
**输出**：修复步骤和验证结果

### 测试用例4：修复AI调用问题

**输入**：修复AI助手的调用失败问题
**输出**：修复步骤和验证结果

## 最佳实践

1. **定期检查**：定期检查后端服务状态和API可用性
2. **错误监控**：在前端添加详细的错误日志和监控
3. **配置管理**：使用环境变量管理敏感配置，如AI API密钥
4. **性能优化**：优化API响应时间和网络请求
5. **安全措施**：确保AI API密钥的安全存储和使用

## 工具依赖

- **Read**：读取配置文件和代码文件
- **Edit**：修改配置文件和代码文件
- **RunCommand**：执行命令行命令，检查服务状态和测试API
- **GetDiagnostics**：获取代码诊断信息，检查语法错误和类型问题