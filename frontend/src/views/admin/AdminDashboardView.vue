<template>
  <div class="admin-dashboard">
    <!-- 侧边栏 -->
    <div class="sidebar">
      <div class="sidebar-header">
        <h2>管理系统</h2>
      </div>
      <div class="sidebar-menu">
        <el-menu :default-active="activeMenu" class="menu">
          <el-menu-item index="home" @click="navigate('home')">
            <el-icon><SwitchButton /></el-icon>
            <span>管理后台首页</span>
          </el-menu-item>
          <el-menu-item index="heritage" @click="navigate('heritage')">
            <el-icon><Collection /></el-icon>
            <span>非遗项目管理</span>
          </el-menu-item>
          <el-menu-item index="news" @click="navigate('news')">
            <el-icon><Message /></el-icon>
            <span>文化资讯管理</span>
          </el-menu-item>
          <el-menu-item index="activity" @click="navigate('activity')">
            <el-icon><Date /></el-icon>
            <span>活动管理</span>
          </el-menu-item>
          <el-menu-item index="notice" @click="navigate('notice')">
            <el-icon><Bell /></el-icon>
            <span>公告管理</span>
          </el-menu-item>
          <el-menu-item index="inheritor" @click="navigate('inheritor')">
            <el-icon><UserFilled /></el-icon>
            <span>传承人管理</span>
          </el-menu-item>
          <el-menu-item index="user" @click="navigate('user')">
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="logout" @click="handleLogout">
            <el-icon><SwitchButton /></el-icon>
            <span>退出登录</span>
          </el-menu-item>
        </el-menu>
      </div>
    </div>

    <!-- 主内容区 -->
    <div class="main-content">
      <!-- 顶部导航 -->
      <div class="top-nav">
        <div class="nav-title">
          {{ getPageTitle() }}
        </div>
        <div class="user-info">
          <span>{{ user?.username }}</span>
        </div>
      </div>

      <!-- 内容区域 -->
      <div class="content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMenu, ElMenuItem, ElIcon } from 'element-plus'
import { Collection, Grid, Location, User, SwitchButton } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const user = ref(null)

onMounted(() => {
  // 不需要检查登录状态，直接访问管理后台
  const userStr = localStorage.getItem('user')
  if (userStr) {
    user.value = JSON.parse(userStr)
  } else {
    // 未登录，使用默认管理员信息
    user.value = {
      id: 1,
      username: 'admin',
      email: 'admin@example.com'
    }
  }
})

const activeMenu = computed(() => {
  const path = route.path
  if (path === '/admin') return 'home'
  if (path.includes('heritage')) return 'heritage'
  if (path.includes('news')) return 'news'
  if (path.includes('activity')) return 'activity'
  if (path.includes('notice')) return 'notice'
  if (path.includes('inheritor')) return 'inheritor'
  if (path.includes('user')) return 'user'
  return 'home'
})

const navigate = (menu) => {
  if (menu === 'home') {
    router.push('/admin')
  } else {
    router.push(`/admin/${menu}`)
  }
}

const getPageTitle = () => {
  const path = route.path
  if (path === '/admin') return '管理后台首页'
  if (path.includes('heritage')) return '非遗项目管理'
  if (path.includes('category')) return '分类管理'
  if (path.includes('region')) return '地区管理'
  if (path.includes('news')) return '文化资讯管理'
  if (path.includes('activity')) return '活动管理'
  if (path.includes('notice')) return '公告管理'
  if (path.includes('inheritor')) return '传承人管理'
  if (path.includes('user')) return '用户管理'
  return '后台管理'
}

const handleLogout = () => {
  // 清除本地存储
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  // 跳转到登录页
  router.push('/admin')
}
</script>

<style scoped>
.admin-dashboard {
  display: flex;
  min-height: 100vh;
  background: #f5f7fa;
}

.sidebar {
  width: 250px;
  background: #2c3e50;
  color: white;
  display: flex;
  flex-direction: column;
}

.sidebar-header {
  padding: 20px;
  border-bottom: 1px solid #34495e;
}

.sidebar-header h2 {
  font-size: 1.2rem;
  margin: 0;
}

.sidebar-menu {
  flex: 1;
  padding: 20px 0;
}

.menu {
  background: transparent !important;
  border-right: none !important;
}

.menu .el-menu-item {
  color: #ecf0f1;
  height: 60px;
  line-height: 60px;
  margin: 0 10px;
  border-radius: 8px;
  /* 移除过渡效果 */
  transition: none;
}

.menu .el-menu-item:hover {
  background: transparent !important;
  color: currentColor !important;
  /* 移除所有效果 */
  transition: none !important;
  box-shadow: none !important;
  transform: none !important;
}

.menu .el-menu-item.is-active {
  background: #3498db !important;
  color: white !important;
  /* 移除阴影效果 */
  box-shadow: none;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.top-nav {
  background: white;
  padding: 0 30px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.nav-title {
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
}

.user-info {
  font-size: 0.9rem;
  color: #666;
}

.content {
  flex: 1;
  padding: 30px;
  overflow-y: auto;
}

@media (max-width: 768px) {
  .sidebar {
    width: 200px;
  }
  
  .content {
    padding: 20px;
  }
}
</style>