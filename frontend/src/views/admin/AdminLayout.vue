<template>
  <div class="admin-container">
    <!-- 侧边导航栏 -->
    <aside class="admin-sidebar">
      <div class="sidebar-header">
        <h1 class="sidebar-logo">管理系统</h1>
      </div>
      <nav class="sidebar-nav">
        <el-menu
          :default-active="activeMenu"
          class="sidebar-menu"
          router
        >
          <el-menu-item index="/">
            <el-icon><House /></el-icon>
            <span>系统首页</span>
          </el-menu-item>
          <el-menu-item index="/admin">
            <el-icon><HomeFilled /></el-icon>
            <span>管理后台首页</span>
          </el-menu-item>
          <el-menu-item index="/admin/heritage">
            <el-icon><Collection /></el-icon>
            <span>非遗项目管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/inheritor">
            <el-icon><UserFilled /></el-icon>
            <span>传承人管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/news">
            <el-icon><Reading /></el-icon>
            <span>文化资讯管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/activity">
            <el-icon><Tickets /></el-icon>
            <span>活动管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/notice">
            <el-icon><Bell /></el-icon>
            <span>公告管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/user">
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/carousel">
            <el-icon><Picture /></el-icon>
            <span>轮播图管理</span>
          </el-menu-item>
        </el-menu>
      </nav>
    </aside>
    
    <!-- 主内容区域 -->
    <main class="admin-main">
      <div class="main-content">
        <router-view />
      </div>
    </main>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import {
  HomeFilled,
  House,
  Collection,
  UserFilled,
  Reading,
  Tickets,
  Bell,
  User,
  Picture
} from '@element-plus/icons-vue'

const route = useRoute()

const activeMenu = computed(() => {
  return route.path || '/admin'
})
</script>

<style scoped>
/* 布局样式 */
.admin-container {
  display: flex;
  min-height: 100vh;
  background: #f5f7fa;
}

/* 侧边导航栏 */
.admin-sidebar {
  width: 200px;
  background: #1f2937;
  color: #fff;
  flex-shrink: 0;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.1);
}

.sidebar-header {
  padding: 20px;
  border-bottom: 1px solid #374151;
}

.sidebar-logo {
  font-size: 1.2rem;
  font-weight: bold;
  margin: 0;
  color: #fff;
}

.sidebar-nav {
  padding: 20px 0;
}

.sidebar-menu {
  background: transparent !important;
  border-right: none !important;
}

.sidebar-menu .el-menu-item {
  margin: 0 10px;
  color: #d1d5db;
}

.sidebar-menu .el-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
  color: #fff;
}

.sidebar-menu .el-menu-item.is-active {
  background-color: #3b82f6;
  color: #fff;
}

.sidebar-menu .el-menu-item .el-icon {
  margin-right: 12px;
}

/* 主内容区域 */
.admin-main {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}

.main-content {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  min-height: calc(100vh - 40px);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .admin-sidebar {
    width: 160px;
  }
  
  .sidebar-logo {
    font-size: 1rem;
  }
  
  .sidebar-menu .el-menu-item {
    font-size: 0.9rem;
  }
  
  .sidebar-menu .el-menu-item .el-icon {
    margin-right: 8px;
  }
  
  .admin-main {
    padding: 10px;
  }
}
</style>