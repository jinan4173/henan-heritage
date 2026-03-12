<template>
  <div class="login-view">
    <div class="login-container">
      <h2>用户登录</h2>
      <el-form :model="loginForm" :rules="loginRules" ref="loginForm" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" style="width: 100%;">登录</el-button>
        </el-form-item>
        <el-form-item>
          <div class="login-footer">
            <span>还没有账号？</span>
            <el-button type="text" @click="$router.push('/register')">立即注册</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import api from '../api/index'

export default {
  name: 'LoginView',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
      handleLogin() {
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            // 调用后端登录API
            api.post('/auth/login', {
              username: this.loginForm.username,
              password: this.loginForm.password
            })
            .then(response => {
              console.log('登录响应:', response);
              // 统一 Result 返回：code + success + data
              if (response.code === 200 && response.data) {
                // 登录成功，保存用户信息到本地存储
                localStorage.setItem('user', JSON.stringify(response.data.user));
                localStorage.setItem('token', response.data.token);
                console.log('保存的用户信息:', response.data.user);
                this.$message.success('登录成功');
                // 根据用户名决定跳转路径
                if (response.data.user.username === 'admin') {
                  this.$router.push('/admin');
                } else {
                  this.$router.push('/');
                }
              } else {
                // 登录失败
                console.error('登录失败:', response.message);
                this.$message.error('登录失败: ' + (response.message || '请检查用户名或密码'));
              }
            })
            .catch(error => {
              console.error('登录失败:', error);
              this.$message.error('登录失败，请检查网络连接');
            });
          } else {
            return false;
          }
        });
      }
    }
}
</script>

<style>
.login-view {
  min-height: 100vh !important;
  display: flex !important;
  justify-content: center !important;
  align-items: center !important;
  padding: 0 !important;
  background: #f8f3e7 !important;
  margin: 0 !important;
  overflow: hidden !important;
  position: relative !important;
  z-index: 1 !important;
}

.login-container {
  width: 100% !important;
  max-width: 400px !important;
  background: #fff !important;
  padding: 40px !important;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1) !important;
  border-radius: 0 !important;
  min-height: 400px !important;
}

.login-container h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.login-footer {
  text-align: center;
  margin-top: 10px;
}
</style>