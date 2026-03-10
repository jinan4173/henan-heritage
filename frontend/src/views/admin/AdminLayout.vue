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
            <el-icon><Document /></el-icon>
            <span>文化资讯管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/activity">
            <el-icon><Bell /></el-icon>
            <span>非遗活动管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/notice">
            <el-icon><Message /></el-icon>
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
          <el-menu-item index="/admin/activity-registration">
            <el-icon><Ticket /></el-icon>
            <span>活动报名管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/comment">
            <el-icon><ChatLineSquare /></el-icon>
            <span>评论管理</span>
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
  Document,
  User,
  Picture,
  Bell,
  Message,
  Ticket,
  ChatLineSquare
} from '@element-plus/icons-vue'

const route = useRoute()

const activeMenu = computed(() => {
  return route.path || '/admin'
})
</script>

<style scoped>
/* 布局样式 */
.admin-container {
  min-height: 100vh;
  display: flex;
  background-color: var(--background-color);
}

/* 侧边导航栏 */
.admin-sidebar {
  width: 240px;
  background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
  color: #fff;
  flex-shrink: 0;
  box-shadow: 2px 0 16px rgba(0, 0, 0, 0.2);
  transition: all var(--transition-normal);
  position: relative;
  overflow: hidden;
}

.admin-sidebar::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url('https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=chinese%20traditional%20pattern%20background%20subtle&image_size=square') center/cover;
  opacity: 0.05;
  z-index: 1;
}

.sidebar-header {
  padding: 24px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  position: relative;
  z-index: 2;
  animation: fadeIn 0.5s ease;
}

.sidebar-logo {
  font-size: 1.3rem;
  font-weight: 600;
  margin: 0;
  color: #fff;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
  background: linear-gradient(45deg, #fff, rgba(255, 255, 255, 0.8));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.sidebar-nav {
  padding: 24px 0;
  position: relative;
  z-index: 2;
}

.sidebar-menu {
  background: transparent !important;
  border-right: none !important;
  border-radius: 8px;
  border: none;
}

.sidebar-menu .el-menu-item {
  border-radius: 8px;
  margin: 8px 12px;
  height: 52px;
  line-height: 52px;
  color: rgba(255, 255, 255, 0.8);
  transition: all var(--transition-normal);
  position: relative;
  overflow: hidden;
  font-weight: 500;
}

.sidebar-menu .el-menu-item::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.1), transparent);
  transition: left var(--transition-slow);
}

.sidebar-menu .el-menu-item:hover::before {
  left: 100%;
}

.sidebar-menu .el-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.1) !important;
  color: #fff !important;
  transform: translateX(8px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.sidebar-menu .el-menu-item.is-active {
  background-color: rgba(255, 255, 255, 0.2) !important;
  color: #fff !important;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
  transform: translateX(8px);
}

.sidebar-menu .el-menu-item.is-active::after {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 60%;
  background: var(--secondary-color);
  border-radius: 0 2px 2px 0;
}

.sidebar-menu .el-menu-item .el-icon {
  margin-right: 16px;
  font-size: 18px;
  transition: all var(--transition-normal);
}

.sidebar-menu .el-menu-item:hover .el-icon {
  transform: scale(1.1);
  color: var(--secondary-color);
}

.sidebar-menu .el-menu-item.is-active .el-icon {
  color: var(--secondary-color);
  transform: scale(1.1);
}

/* 主内容区域 */
.admin-main {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
  background: linear-gradient(135deg, #f8f9fa, #e9ecef);
}

.main-content {
  background-color: var(--card-background);
  border-radius: 16px;
  box-shadow: var(--shadow-lg);
  min-height: calc(100vh - 48px);
  overflow: hidden;
  animation: fadeInUp 0.8s ease;
  border: 1px solid var(--border-color);
}

/* 动画效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 响应式设计 */
@media (max-width: 992px) {
  .admin-sidebar {
    width: 200px;
  }
  
  .sidebar-logo {
    font-size: 1.1rem;
  }
  
  .sidebar-menu .el-menu-item {
    font-size: 0.9rem;
    height: 48px;
    line-height: 48px;
  }
  
  .sidebar-menu .el-menu-item .el-icon {
    margin-right: 12px;
    font-size: 16px;
  }
}

@media (max-width: 768px) {
  .admin-sidebar {
    width: 180px;
  }
  
  .sidebar-header {
    padding: 20px;
  }
  
  .sidebar-logo {
    font-size: 1rem;
  }
  
  .sidebar-menu .el-menu-item {
    font-size: 0.85rem;
    margin: 6px 8px;
  }
  
  .sidebar-menu .el-menu-item .el-icon {
    margin-right: 8px;
  }
  
  .admin-main {
    padding: 16px;
  }
  
  .main-content {
    min-height: calc(100vh - 32px);
    border-radius: 12px;
  }
}

@media (max-width: 480px) {
  .admin-sidebar {
    width: 160px;
  }
  
  .sidebar-menu .el-menu-item {
    font-size: 0.8rem;
    height: 44px;
    line-height: 44px;
  }
  
  .sidebar-menu .el-menu-item .el-icon {
    font-size: 14px;
  }
}
</style>