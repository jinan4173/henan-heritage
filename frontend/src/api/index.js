import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
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
    return Promise.reject(error)
  }
)

// 响应拦截器
api.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    console.error('API Error:', error)
    return Promise.reject(error)
  }
)

export default api