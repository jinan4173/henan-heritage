import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import axios from 'axios'
import './style.css'

const app = createApp(App)

// 挂载axios到Vue实例
app.config.globalProperties.$axios = axios

// 注册全局属性
app.config.globalProperties.$api = {
  // 可以在这里添加全局API方法
  getBaseUrl: () => import.meta.env.BASE_URL
}

// 注册全局错误处理
app.config.errorHandler = (err, instance, info) => {
  console.error('Vue应用错误:', err)
  console.error('错误信息:', info)
  // 可以在这里添加错误上报逻辑
}

// 配置Element Plus
app.use(ElementPlus, {
  locale: zhCn,
  // 配置大小
  size: 'default'
})

// 注册路由
app.use(router)

// 挂载应用
app.mount('#app')