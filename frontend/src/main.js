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

app.use(router)
app.use(ElementPlus, {
  locale: zhCn
})
app.mount('#app')