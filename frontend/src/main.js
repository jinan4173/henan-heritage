import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from 'axios'

const app = createApp(App)

// 配置axios
axios.defaults.baseURL = '/api'
axios.defaults.timeout = 10000

// 响应拦截器
axios.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    console.error('API Error:', error)
    return Promise.reject(error)
  }
)

// 挂载axios到Vue实例
app.config.globalProperties.$axios = axios

app.use(router)
app.use(ElementPlus)
app.mount('#app')