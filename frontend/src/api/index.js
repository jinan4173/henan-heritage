import axios from 'axios'
import { ElMessage } from 'element-plus'

const api = axios.create({
  baseURL: import.meta.env.DEV ? 'http://localhost:8083/api' : 'http://localhost:8083/api',
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
    ElMessage.error('请求发送失败，请稍后重试')
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
    
    // 统一错误处理
    if (error.response) {
      switch (error.response.status) {
        case 401:
          ElMessage.error('未登录或登录已过期，请重新登录')
          // 跳转到登录页面
          window.location.href = '/login'
          break
        case 403:
          ElMessage.error('没有权限执行此操作')
          break
        case 404:
          ElMessage.error('请求的资源不存在')
          break
        case 500:
          ElMessage.error('服务器内部错误，请稍后重试')
          break
        default:
          ElMessage.error(`请求失败: ${error.response.data.message || '未知错误'}`)
      }
    } else if (error.request) {
      ElMessage.error('网络连接失败，请检查网络设置')
    } else {
      ElMessage.error('请求配置错误')
    }
    return Promise.reject(error)
  }
)

export default api