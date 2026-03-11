<template>
  <div class="admin-login">
    <div class="login-container">
      <h1>后台管理系统</h1>
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading">登录</el-button>
        </el-form-item>
      </el-form>
      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>
      <div class="debug-info">
        <p>调试信息：</p>
        <p>用户名: {{ loginForm.username }}</p>
        <p>密码: {{ loginForm.password }}</p>
        <p>错误信息: {{ errorMessage }}</p>
        <p>调试消息: {{ debugMessage }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const loginFormRef = ref(null)
const loading = ref(false)
const errorMessage = ref('')
const debugMessage = ref('')

const loginForm = reactive({
  username: 'admin',
  password: 'admin123'
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = () => {
  debugMessage.value = '开始登录'
  console.log('开始登录')
  
  if (!loginFormRef.value) {
    debugMessage.value = 'loginFormRef is null'
    errorMessage.value = 'loginFormRef is null'
    return
  }

  loginFormRef.value.validate((valid) => {
    if (valid) {
      loading.value = true
      errorMessage.value = ''
      debugMessage.value = '表单验证通过'
      
      console.log('表单验证通过，发送登录请求')
      console.log('用户名:', loginForm.username)
      console.log('密码:', loginForm.password)
      
      // 配置axios
      axios.defaults.baseURL = 'http://localhost:8083/api'
      
      debugMessage.value = '发送登录请求'
      
      axios.post('/auth/login', {
        username: loginForm.username,
        password: loginForm.password
      })
      .then(response => {
        debugMessage.value = '收到响应'
        console.log('响应:', response)
        
        const data = response.data
        console.log('响应数据:', data)
        
        if (data.success) {
          debugMessage.value = '登录成功'
          console.log('登录成功')
          
          try {
            // 保存用户信息
            localStorage.setItem('user', JSON.stringify(data.user))
            localStorage.setItem('token', data.token)
            debugMessage.value = '保存用户信息成功'
            console.log('保存用户信息成功')
          } catch (error) {
            debugMessage.value = '保存用户信息失败: ' + error.message
            console.error('保存用户信息失败:', error)
          }
          
          debugMessage.value = '登录成功，准备跳转'
          console.log('登录成功，准备跳转')
          
          // 显示成功消息
          errorMessage.value = '登录成功，准备跳转...'
          
          // 延迟跳转，以便查看调试信息
          setTimeout(() => {
            console.log('开始跳转')
            // 跳转到后台管理首页
            router.push('/admin')
          }, 2000)
        } else {
          debugMessage.value = '登录失败: ' + data.message
          console.log('登录失败:', data.message)
          errorMessage.value = data.message || '登录失败'
        }
      })
      .catch(error => {
        debugMessage.value = '请求失败: ' + error.message
        console.log('请求失败:', error)
        errorMessage.value = '网络错误，请稍后重试'
      })
      .finally(() => {
        loading.value = false
        debugMessage.value = '登录完成'
      })
    } else {
      debugMessage.value = '表单验证失败'
      errorMessage.value = '表单验证失败'
    }
  })
}
</script>

<style scoped>
.admin-login {
  min-height: 100vh !important;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
  background: #f8f3e7 !important;
  margin: 0 !important;
  overflow: hidden !important;
  position: relative !important;
  z-index: 1 !important;
}

.login-container {
  background: white !important;
  padding: 50px !important;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2) !important;
  width: 400px !important;
  max-width: 90% !important;
  border-radius: 0 !important;
  min-height: 400px !important;
}

.login-container h1 {
  font-size: 1.8rem;
  margin-bottom: 30px;
  color: #333;
  text-align: center;
}

.error-message {
  margin-top: 20px;
  color: #f56c6c;
  text-align: center;
  font-size: 0.9rem;
}

@media (max-width: 768px) {
  .login-container {
    padding: 30px;
  }
  .login-container h1 {
    font-size: 1.5rem;
  }
}
</style>