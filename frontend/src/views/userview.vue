<template>
  <div class="user-view">
    <div class="user-container">
      <h2>个人中心</h2>
      
      <!-- 未登录状态 -->
      <div v-if="!isLoggedIn" class="not-logged-in">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>登录提示</span>
            </div>
          </template>
          <div class="login-prompt">
            <el-empty description="请先登录" />
            <p class="prompt-text">登录后可以查看个人信息、浏览历史和收藏的非遗项目</p>
            <div class="login-buttons">
              <el-button type="primary" @click="$router.push('/login')">立即登录</el-button>
            </div>
          </div>
        </el-card>
      </div>
      
      <!-- 登录状态 -->
      <div v-else class="logged-in">
        <el-card class="user-card">
          <template #header>
            <div class="card-header">
              <span>用户信息</span>
            </div>
          </template>
          <div class="info-item">
            <span class="label">用户名：</span>
            <span class="value">{{ userInfo.username }}</span>
          </div>
          <div class="info-item">
            <span class="label">邮箱：</span>
            <span class="value">{{ userInfo.email || '未设置' }}</span>
          </div>
          <div class="info-item">
            <span class="label">登录时间：</span>
            <span class="value">{{ userInfo.loginTime }}</span>
          </div>
          <div class="action-buttons">
            <el-button type="primary" @click="editProfile">编辑资料</el-button>
            <el-button type="danger" @click="handleLogout">退出登录</el-button>
          </div>
        </el-card>
        
        <el-card class="user-card">
          <template #header>
            <div class="card-header">
              <span>浏览历史</span>
            </div>
          </template>
          <div class="history-list">
            <el-empty v-if="historyList.length === 0" description="暂无浏览历史" />
            <div v-else v-for="item in historyList" :key="item.id" class="history-item">
              <span class="history-title">{{ item.title }}</span>
              <span class="history-date">{{ item.date }}</span>
            </div>
          </div>
        </el-card>
        
        <el-card class="user-card">
          <template #header>
            <div class="card-header">
              <span>我的收藏</span>
            </div>
          </template>
          <div class="favorites-list">
            <el-empty v-if="favoritesList.length === 0" description="暂无收藏项目" />
            <div v-else v-for="item in favoritesList" :key="item.id" class="favorite-item">
              <span class="favorite-title">{{ item.title }}</span>
              <span class="favorite-category">{{ item.category }}</span>
            </div>
          </div>
        </el-card>
        
        <el-card class="user-card">
          <template #header>
            <div class="card-header">
              <span>我的评论</span>
            </div>
          </template>
          <div class="comments-list">
            <el-empty v-if="commentsList.length === 0" description="暂无评论" />
            <div v-else v-for="comment in commentsList" :key="comment.id" class="comment-item">
              <div class="comment-content">
                <div class="comment-header">
                  <span class="comment-title">{{ comment.title }}</span>
                  <span class="comment-time">{{ comment.time }}</span>
                </div>
                <p class="comment-text">{{ comment.content }}</p>
              </div>
            </div>
          </div>
        </el-card>
      </div>
    </div>

    <!-- 编辑资料对话框 -->
    <el-dialog v-model="dialogVisible" title="编辑资料" width="500px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="editForm.password" type="password" placeholder="请输入新密码（可选）" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveProfile">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'UserView',
  data() {
    return {
      userInfo: {
        username: '',
        email: '',
        loginTime: ''
      },
      historyList: [
        {
          id: 1,
          title: '豫剧',
          date: '2024-01-29 10:00'
        },
        {
          id: 2,
          title: '少林功夫',
          date: '2024-01-29 09:30'
        }
      ],
      favoritesList: [
        {
          id: 1,
          title: '豫剧',
          category: '传统戏剧'
        },
        {
          id: 2,
          title: '少林功夫',
          category: '传统体育'
        },
        {
          id: 3,
          title: '非遗技艺展示',
          category: '媒体收藏'
        },
        {
          id: 4,
          title: '河南非遗传承之路',
          category: '媒体收藏'
        }
      ],
      commentsList: [
        {
          id: 1,
          title: '非遗技艺展示',
          content: '这张图片展示的非遗技艺非常精彩，希望能看到更多这样的内容！',
          time: '2026-03-01 10:00'
        },
        {
          id: 2,
          title: '河南非遗传承之路',
          content: '视频拍得很专业，让我对非遗有了更深入的了解。',
          time: '2026-03-02 14:30'
        }
      ],
      dialogVisible: false,
      editForm: {
        username: '',
        email: '',
        password: ''
      }
    }
  },
  computed: {
    isLoggedIn() {
      return !!localStorage.getItem('user');
    }
  },
  mounted() {
    this.loadUserInfo();
  },
  methods: {
    loadUserInfo() {
      const userStr = localStorage.getItem('user');
      if (userStr) {
        try {
          const user = JSON.parse(userStr);
          this.userInfo.username = user.username;
          this.userInfo.email = user.email || '';
          this.userInfo.loginTime = new Date().toLocaleString('zh-CN');
        } catch (e) {
          console.error('解析用户信息失败:', e);
        }
      }
    },
    handleLogout() {
      localStorage.removeItem('user');
      this.$router.push('/');
      this.$message.success('退出登录成功');
    },
    editProfile() {
      // 填充表单数据
      this.editForm.username = this.userInfo.username;
      this.editForm.email = this.userInfo.email || '';
      this.editForm.password = '';
      // 打开对话框
      this.dialogVisible = true;
    },
    saveProfile() {
      // 保存用户资料
      const userStr = localStorage.getItem('user');
      if (userStr) {
        try {
          const user = JSON.parse(userStr);
          // 更新用户信息
          user.username = this.editForm.username;
          user.email = this.editForm.email;
          if (this.editForm.password) {
            user.password = this.editForm.password;
          }
          // 保存到本地存储
          localStorage.setItem('user', JSON.stringify(user));
          // 更新页面显示的用户信息
          this.userInfo.username = this.editForm.username;
          this.userInfo.email = this.editForm.email;
          // 关闭对话框
          this.dialogVisible = false;
          // 显示成功消息
          this.$message.success('资料更新成功');
        } catch (e) {
          console.error('保存用户信息失败:', e);
          this.$message.error('保存失败，请重试');
        }
      }
    }
  }
}
</script>

<style scoped>
.user-view {
  width: 100%;
  padding: 20px;
  background-color: var(--background-color);
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
}

.user-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.user-view h2 {
  font-size: 2rem;
  margin-bottom: 30px;
  color: var(--primary-color);
  text-align: center;
  position: relative;
  padding-bottom: 15px;
}

.user-view h2::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 80px;
  height: 3px;
  background-color: var(--accent-color);
  border-radius: 2px;
}

.logged-in {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
  max-width: 600px;
}

.user-card {
  width: 100%;
  margin: 0;
}

.card-header {
  font-weight: bold;
}

.info-item {
  margin: 10px 0;
  display: flex;
  align-items: center;
}

.label {
  width: 100px;
  font-weight: 500;
}

.value {
  flex: 1;
}

.action-buttons {
  margin-top: 20px;
}

.history-list {
  margin-top: 10px;
}

.history-item {
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.history-title {
  flex: 1;
}

.history-date {
  font-size: 12px;
  color: #999;
}

.login-prompt {
  text-align: center;
  padding: 40px 20px;
}

.prompt-text {
  margin: 20px 0;
  color: #666;
  line-height: 1.5;
}

.login-buttons {
  margin-top: 30px;
}

.favorites-list {
  margin-top: 10px;
}

.favorite-item {
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.favorite-title {
  flex: 1;
}

.favorite-category {
  font-size: 12px;
  color: #667eea;
  background: #f0f0ff;
  padding: 2px 8px;
  border-radius: 10px;
}

.comments-list {
  margin-top: 10px;
}

.comment-item {
  padding: 15px 0;
  border-bottom: 1px solid #f0f0f0;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.comment-title {
  font-weight: 600;
  flex: 1;
}

.comment-time {
  font-size: 12px;
  color: #999;
}

.comment-text {
  margin: 0;
  color: #666;
  line-height: 1.5;
  font-size: 14px;
}
</style>