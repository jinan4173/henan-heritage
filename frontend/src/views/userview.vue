<template>
  <div class="user-view">
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
      <div class="user-info">
        <el-card>
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
      </div>
      
      <div class="user-activity">
        <el-card>
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
      </div>
      
      <div class="user-favorites">
        <el-card>
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
      </div>
    </div>
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
        }
      ]
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
      this.$message.info('编辑资料功能开发中');
    }
  }
}
</script>

<style scoped>
.user-view {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.user-info {
  margin-bottom: 20px;
}

.user-activity {
  margin-top: 20px;
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

.user-favorites {
  margin-top: 20px;
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
</style>