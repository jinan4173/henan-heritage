<template>
  <div class="register-view">
    <div class="register-container">
      <h2>用户注册</h2>
      <el-form :model="registerForm" :rules="registerRules" ref="registerForm" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请确认密码" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister" style="width: 100%;">注册</el-button>
        </el-form-item>
        <el-form-item>
          <div class="register-footer">
            <span>已有账号？</span>
            <el-button type="text" @click="$router.push('/login')">立即登录</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RegisterView',
  data() {
    return {
      registerForm: {
        username: '',
        email: '',
        password: '',
        confirmPassword: ''
      },
      registerRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        email: [
          { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value !== this.registerForm.password) {
                callback(new Error('两次输入密码不一致'));
              } else {
                callback();
              }
            },
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
      handleRegister() {
        this.$refs.registerForm.validate((valid) => {
          if (valid) {
            // 调用后端注册API（统一 Result 返回）
            this.$axios.post('/auth/register', {
              username: this.registerForm.username,
              password: this.registerForm.password,
              email: this.registerForm.email
            })
            .then(response => {
              if (response.code === 200) {
                // 注册成功，跳转到登录页
                this.$message.success('注册成功，请登录');
                this.$router.push('/login');
              } else {
                // 注册失败，显示错误信息
                this.$message.error(response.message || '注册失败');
              }
            })
            .catch(error => {
              console.error('注册失败:', error);
              this.$message.error('注册失败，请检查网络连接');
            });
          } else {
            return false;
          }
        });
      }
    }
}
</script>

<style scoped>
.register-view {
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

.register-container {
  width: 100% !important;
  max-width: 400px !important;
  background: #fff !important;
  padding: 40px !important;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1) !important;
  border-radius: 0 !important;
  min-height: 450px !important;
}

.register-container h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.register-footer {
  text-align: center;
  margin-top: 10px;
}
</style>