<template>
  <div class="navbar">
    <div class="navbar-container">
      <div class="navbar-logo">
        <router-link to="/" class="logo-link">
          <img src="/logo.png" alt="河南非遗" class="navbar-logo-img">
        </router-link>
      </div>
      
      <!-- 桌面端导航菜单 -->
      <div class="navbar-menu">
        <router-link to="/" class="menu-item" :class="{ active: $route.path === '/' }">首页</router-link>
        <router-link to="/heritage" class="menu-item" :class="{ active: $route.path === '/heritage' }">非遗项目</router-link>
        <router-link to="/promotion" class="menu-item" :class="{ active: $route.path === '/promotion' }">非遗宣传</router-link>
        <router-link to="/news" class="menu-item" :class="{ active: $route.path === '/news' }">文化资讯</router-link>
        <router-link to="/activity" class="menu-item" :class="{ active: $route.path === '/activity' }">非遗活动</router-link>
        <router-link to="/notice" class="menu-item" :class="{ active: $route.path === '/notice' }">公告</router-link>
        <router-link to="/user" class="menu-item" :class="{ active: $route.path === '/user' }">个人中心</router-link>
      </div>
      
      <!-- 移动端汉堡菜单按钮 -->
      <div class="mobile-menu-btn" @click="toggleMobileMenu">
        <i :class="mobileMenuOpen ? 'el-icon-close' : 'el-icon-menu'" class="menu-icon"></i>
      </div>
      
      <div class="navbar-auth">
        <div v-if="isLoggedIn" class="user-avatar">
          <el-dropdown>
            <div class="avatar-container">
              <img src="https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=default%20user%20avatar%20profile%20picture%20simple%20icon&image_size=square" alt="用户头像" class="avatar">
              <span class="username">{{ getUsername() }}</span>
              <i class="el-icon-arrow-down"></i>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item><router-link to="/user">个人中心</router-link></el-dropdown-item>
                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
        <router-link to="/login" class="auth-item" v-else>登录</router-link>
        <router-link to="/admin" class="auth-item admin-link" v-if="isLoggedIn">管理后台</router-link>
      </div>
    </div>
    
    <!-- 移动端导航菜单 -->
    <div class="mobile-menu" :class="{ open: mobileMenuOpen }">
      <div class="mobile-menu-content">
        <router-link to="/" class="mobile-menu-item" @click="closeMobileMenu">首页</router-link>
        <router-link to="/heritage" class="mobile-menu-item" @click="closeMobileMenu">非遗项目</router-link>
        <router-link to="/promotion" class="mobile-menu-item" @click="closeMobileMenu">非遗宣传</router-link>
        <router-link to="/news" class="mobile-menu-item" @click="closeMobileMenu">文化资讯</router-link>
        <router-link to="/activity" class="mobile-menu-item" @click="closeMobileMenu">非遗活动</router-link>
        <router-link to="/notice" class="mobile-menu-item" @click="closeMobileMenu">公告</router-link>
        <router-link to="/user" class="mobile-menu-item" @click="closeMobileMenu">个人中心</router-link>
        <router-link to="/admin" class="mobile-menu-item admin-menu-item" v-if="isLoggedIn" @click="closeMobileMenu">管理后台</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Navbar',
  data() {
    return {
      mobileMenuOpen: false
    };
  },
  computed: {
    isLoggedIn() {
      return !!localStorage.getItem('user');
    }
  },
  methods: {
    getUsername() {
      const userStr = localStorage.getItem('user');
      if (userStr) {
        try {
          const user = JSON.parse(userStr);
          return user.username || '用户';
        } catch (e) {
          return '用户';
        }
      }
      return '用户';
    },
    handleLogout() {
      localStorage.removeItem('user');
      this.$router.push('/');
      this.$message.success('退出登录成功');
    },
    toggleMobileMenu() {
      this.mobileMenuOpen = !this.mobileMenuOpen;
      // 控制body滚动
      if (this.mobileMenuOpen) {
        document.body.style.overflow = 'hidden';
      } else {
        document.body.style.overflow = 'auto';
      }
    },
    closeMobileMenu() {
      this.mobileMenuOpen = false;
      document.body.style.overflow = 'auto';
    },
    handleScroll() {
      const navbar = document.querySelector('.navbar');
      if (navbar) {
        if (window.scrollY > 50) {
          navbar.classList.add('scrolled');
        } else {
          navbar.classList.remove('scrolled');
        }
      }
    },
    handleResize() {
      // 当窗口大小变化时，如果是桌面端，关闭移动端菜单
      if (window.innerWidth > 768) {
        this.mobileMenuOpen = false;
        document.body.style.overflow = 'auto';
      }
    }
  },
  mounted() {
    // 监听滚动事件，添加导航栏滚动效果
    window.addEventListener('scroll', this.handleScroll);
    // 监听窗口大小变化，确保移动端菜单正确显示
    window.addEventListener('resize', this.handleResize);
  },
  beforeUnmount() {
    window.removeEventListener('scroll', this.handleScroll);
    window.removeEventListener('resize', this.handleResize);
  }
}
</script>

<style scoped>
.navbar {
  background: var(--card-background);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 1000;
  border-bottom: 1px solid var(--border-color);
  transition: all var(--transition-normal);
}

.navbar.scrolled {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  padding: 0;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
}

.navbar-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 64px;
  transition: all var(--transition-normal);
}

.navbar-logo {
  flex: 0 0 auto;
}

.logo-link {
  text-decoration: none;
  color: var(--primary-color);
  font-weight: 600;
  display: flex;
  align-items: center;
  transition: all var(--transition-normal);
}

.navbar-logo-img {
  width: 64px;
  height: 64px;
  transition: all var(--transition-normal);
}

.logo-link:hover .navbar-logo-img {
  transform: scale(1.05);
  filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.2));
}

/* 桌面端导航菜单 */
.navbar-menu {
  flex: 1;
  display: flex;
  justify-content: center;
  gap: 24px;
}

.menu-item {
  text-decoration: none;
  color: var(--text-color);
  font-size: 0.95rem;
  font-weight: 400;
  padding: 8px 16px;
  border-radius: 20px;
  transition: all var(--transition-normal);
  position: relative;
  text-shadow: none;
  overflow: hidden;
}

.menu-item::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(0, 102, 204, 0.1), transparent);
  transition: left var(--transition-slow);
}

.menu-item:hover::before {
  left: 100%;
}

.menu-item:hover {
  color: var(--accent-color);
  background: rgba(0, 102, 204, 0.05);
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 102, 204, 0.1);
}

.menu-item.active {
  color: white;
  background: var(--accent-color);
  font-weight: 500;
  box-shadow: 0 4px 8px rgba(0, 102, 204, 0.2);
}

.menu-item.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 8px;
  height: 8px;
  background: white;
  border-radius: 50%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  animation: pulse 2s infinite;
}

/* 移动端汉堡菜单按钮 */
.mobile-menu-btn {
  display: none;
  cursor: pointer;
  padding: 8px;
  border-radius: 8px;
  transition: all var(--transition-normal);
}

.mobile-menu-btn:hover {
  background: rgba(0, 102, 204, 0.1);
}

.menu-icon {
  font-size: 24px;
  color: var(--primary-color);
  transition: all var(--transition-normal);
}

/* 移动端导航菜单 */
.mobile-menu {
  position: fixed;
  top: 64px;
  left: 0;
  width: 100%;
  height: calc(100vh - 64px);
  background: var(--card-background);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  transform: translateY(-100%);
  opacity: 0;
  visibility: hidden;
  transition: all var(--transition-normal);
  z-index: 999;
}

.mobile-menu.open {
  transform: translateY(0);
  opacity: 1;
  visibility: visible;
  animation: slideDown 0.3s ease;
}

.mobile-menu-content {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.mobile-menu-item {
  text-decoration: none;
  color: var(--text-color);
  font-size: 1.1rem;
  font-weight: 500;
  padding: 16px 20px;
  border-radius: 12px;
  transition: all var(--transition-normal);
  position: relative;
  overflow: hidden;
}

.mobile-menu-item::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 4px;
  height: 100%;
  background: var(--accent-color);
  transform: scaleY(0);
  transition: transform var(--transition-normal);
}

.mobile-menu-item:hover {
  color: var(--accent-color);
  background: rgba(0, 102, 204, 0.05);
  transform: translateX(8px);
}

.mobile-menu-item:hover::before {
  transform: scaleY(1);
}

.admin-menu-item {
  background: rgba(138, 26, 26, 0.1);
  color: var(--primary-color);
  margin-top: 16px;
}

.admin-menu-item:hover {
  background: rgba(138, 26, 26, 0.2);
  color: var(--primary-color);
}

.admin-menu-item::before {
  background: var(--primary-color);
}

/* 认证区域 */
.navbar-auth {
  flex: 0 0 auto;
  display: flex;
  gap: 12px;
  align-items: center;
}

.auth-item {
  text-decoration: none;
  color: var(--text-color);
  font-size: 0.85rem;
  font-weight: 400;
  padding: 6px 12px;
  border-radius: 20px;
  transition: all var(--transition-normal);
  text-shadow: none;
  border: 1px solid transparent;
}

.auth-item:hover {
  color: var(--accent-color);
  background: rgba(0, 102, 204, 0.05);
  border-color: var(--accent-color);
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 102, 204, 0.1);
}

.admin-link {
  background: var(--primary-color);
  color: white;
  border-color: var(--primary-color);
  margin-left: 5px;
}

.admin-link:hover {
  background: #6a1515;
  border-color: #6a1515;
  color: white;
  box-shadow: 0 4px 8px rgba(138, 26, 26, 0.2);
}

.user-avatar {
  margin-right: 5px;
}

.avatar-container {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 6px 12px;
  border-radius: 20px;
  transition: all var(--transition-normal);
  border: 1px solid var(--border-color);
}

.avatar-container:hover {
  background: rgba(0, 102, 204, 0.05);
  border-color: var(--accent-color);
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 102, 204, 0.1);
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  margin-right: 8px;
  object-fit: cover;
  border: 2px solid var(--accent-color);
  transition: all var(--transition-normal);
}

.avatar-container:hover .avatar {
  transform: scale(1.1);
  box-shadow: 0 2px 8px rgba(0, 102, 204, 0.2);
}

.username {
  font-size: 0.85rem;
  font-weight: 500;
  color: var(--text-color);
  margin-right: 6px;
  text-shadow: none;
  transition: color var(--transition-normal);
}

.avatar-container:hover .username {
  color: var(--accent-color);
}

.el-icon-arrow-down {
  font-size: 12px;
  color: var(--text-color);
  transition: color var(--transition-normal);
}

.avatar-container:hover .el-icon-arrow-down {
  color: var(--accent-color);
  transform: rotate(180deg);
}

.el-dropdown-menu {
  min-width: 140px;
  border-radius: 8px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
  border: 1px solid var(--border-color);
  overflow: hidden;
  animation: dropdownFadeIn 0.3s ease;
}

.el-dropdown-menu .router-link-active {
  text-decoration: none;
  color: var(--accent-color);
}

.el-dropdown-menu__item {
  padding: 8px 16px;
  font-size: 0.85rem;
  transition: all var(--transition-normal);
}

.el-dropdown-menu__item:hover {
  background: rgba(0, 102, 204, 0.05);
  color: var(--accent-color);
  transform: translateX(4px);
}

.el-dropdown-menu__item a {
  color: inherit;
  text-decoration: none;
  display: block;
  width: 100%;
  height: 100%;
}

/* 动画效果 */
@keyframes dropdownFadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

@keyframes slideDown {
  from {
    opacity: 0;
    transform: translateY(-100%);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes pulse {
  0% {
    transform: translateX(-50%) scale(1);
    opacity: 1;
  }
  50% {
    transform: translateX(-50%) scale(1.2);
    opacity: 0.8;
  }
  100% {
    transform: translateX(-50%) scale(1);
    opacity: 1;
  }
}

/* 响应式设计 */
@media (max-width: 992px) {
  .navbar-menu {
    gap: 16px;
  }
  
  .menu-item {
    padding: 6px 12px;
    font-size: 0.9rem;
  }
}

@media (max-width: 768px) {
  .navbar-container {
    flex-direction: row;
    height: 64px;
    padding: 0 20px;
  }

  .navbar-logo {
    margin-bottom: 0;
  }

  .navbar-menu {
    display: none;
  }

  .mobile-menu-btn {
    display: block;
    order: 3;
  }

  .navbar-auth {
    order: 2;
    gap: 8px;
  }

  .navbar-logo {
    order: 1;
  }

  .auth-item {
    font-size: 0.8rem;
    padding: 4px 8px;
  }

  .avatar-container {
    padding: 4px 8px;
  }

  .avatar {
    width: 28px;
    height: 28px;
  }

  .username {
    font-size: 0.8rem;
  }
}

@media (max-width: 576px) {
  .navbar-container {
    padding: 0 15px;
  }
  
  .navbar-logo-img {
    width: 100px;
  }
  
  .navbar-auth {
    gap: 4px;
  }
  
  .auth-item {
    font-size: 0.75rem;
    padding: 3px 6px;
  }
  
  .avatar-container {
    padding: 3px 6px;
  }
  
  .avatar {
    width: 24px;
    height: 24px;
  }
  
  .username {
    font-size: 0.75rem;
    margin-right: 4px;
  }
  
  .mobile-menu-content {
    padding: 16px;
  }
  
  .mobile-menu-item {
    font-size: 1rem;
    padding: 14px 16px;
  }
}
</style>
