import axios from 'axios'
import { ElMessage } from 'element-plus'

const api = axios.create({
  baseURL: import.meta.env.DEV ? '/' : '/',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json; charset=UTF-8',
    'Accept': 'application/json; charset=UTF-8'
  }
})

// 请求拦截器
api.interceptors.request.use(
  config => {
    try {
      const token = localStorage.getItem('token')
      if (token) {
        config.headers.Authorization = `Bearer ${token}`
      }
    } catch (e) {
      console.error('localStorage 访问失败:', e)
    }
    return config
  },
  error => {
    // 静默处理请求拦截器错误，不显示错误消息
    console.error('请求拦截器错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
api.interceptors.response.use(
  response => {
    // 只在开发环境打印响应日志
    if (import.meta.env.DEV) {
      console.log('API Response:', response)
    }
    return response.data
  },
  error => {
    // 只在开发环境打印错误日志
    if (import.meta.env.DEV) {
      console.error('API Error:', error)
    }
    
    // 检查是否是AI助手相关的API调用
    const isAiApi = error.config && error.config.url && error.config.url.includes('/ai/chat')
    
    // 统一错误处理
    if (error.response) {
      switch (error.response.status) {
        case 401:
          if (!isAiApi) {
            ElMessage.error('未登录或登录已过期，请重新登录')
            // 跳转到登录页面
            window.location.href = '/login'
          }
          break
        case 403:
          if (!isAiApi) {
            ElMessage.error('没有权限执行此操作')
          }
          break
        case 404:
          if (isAiApi) {
            ElMessage.error('AI接口不存在，请检查后端配置')
          } else {
            // 静默处理其他404错误，避免影响页面显示
          }
          break
        case 500:
          if (isAiApi) {
            ElMessage.error('AI调用失败，请稍后重试（或检查后端AI配置）')
          } else {
            // 静默处理其他500错误，避免影响页面显示
          }
          break
        default:
          if (isAiApi) {
            ElMessage.error(`AI请求失败: ${error.response.data.message || '未知错误'}`)
          }
      }
    } else if (error.request) {
      if (isAiApi) {
        ElMessage.error('网络连接失败，请检查网络设置')
      }
    } else {
      if (isAiApi) {
        ElMessage.error('请求配置错误')
      }
    }
    return Promise.reject(error)
  }
)

export default api