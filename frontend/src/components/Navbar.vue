<template>
  <div class="navbar" :class="{ scrolled: isScrolled }">
    <div class="navbar-container">
      <div class="navbar-logo">
        <router-link to="/" class="logo-link">
          <img src="/logo.png" alt="河南非遗" class="navbar-logo-img">
        </router-link>
      </div>
      
      <!-- 桌面端导航菜单 -->
      <div class="navbar-menu">
        <router-link to="/" class="menu-item" :class="{ active: $route.path === '/' }">
          <span class="menu-text">首页</span>
        </router-link>
        <router-link to="/heritage" class="menu-item" :class="{ active: $route.path === '/heritage' }">
          <span class="menu-text">非遗项目</span>
        </router-link>
        <router-link to="/news" class="menu-item" :class="{ active: $route.path === '/news' }">
          <span class="menu-text">文化资讯</span>
        </router-link>
        <router-link to="/activity" class="menu-item" :class="{ active: $route.path === '/activity' }">
          <span class="menu-text">非遗活动</span>
        </router-link>
        <router-link to="/notice" class="menu-item" :class="{ active: $route.path === '/notice' }">
          <span class="menu-text">公告</span>
        </router-link>
        <div class="navbar-search">
          <el-input
            v-model="searchKeyword"
            placeholder="搜索非遗项目 / 活动 / 资讯"
            size="small"
            clearable
            @keyup.enter.native="handleSearch"
            class="search-input"
          >
            <template #suffix>
              <i class="el-icon-search search-icon" @click="handleSearch"></i>
            </template>
          </el-input>
        </div>
      </div>
      
      <!-- 移动端汉堡菜单按钮 -->
      <div class="mobile-menu-btn" @click="toggleMobileMenu">
        <i :class="mobileMenuOpen ? 'el-icon-close' : 'el-icon-menu'" class="menu-icon"></i>
      </div>
      
      <div class="navbar-auth">
        <div v-if="isLoggedIn" class="user-avatar">
          <el-dropdown>
            <div class="avatar-container">
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
        <router-link to="/login" class="auth-item" v-else>
          <span class="auth-text">登录</span>
        </router-link>
        <router-link to="/admin" class="auth-item admin-link" v-if="isAdmin">
          <span class="auth-text">管理后台</span>
        </router-link>
      </div>
    </div>
    
    <!-- 移动端导航菜单 -->
    <div class="mobile-menu" :class="{ open: mobileMenuOpen }">
      <div class="mobile-menu-content">
        <div class="mobile-search">
          <el-input
            v-model="searchKeyword"
            placeholder="搜索非遗项目 / 活动 / 资讯"
            size="small"
            clearable
            @keyup.enter.native="handleSearchAndClose"
            class="search-input"
          >
            <template #suffix>
              <i class="el-icon-search search-icon" @click="handleSearchAndClose"></i>
            </template>
          </el-input>
        </div>
        <router-link to="/" class="mobile-menu-item" @click="closeMobileMenu">
          <span class="menu-text">首页</span>
        </router-link>
        <router-link to="/heritage" class="mobile-menu-item" @click="closeMobileMenu">
          <span class="menu-text">非遗项目</span>
        </router-link>
        <router-link to="/news" class="mobile-menu-item" @click="closeMobileMenu">
          <span class="menu-text">文化资讯</span>
        </router-link>
        <router-link to="/activity" class="mobile-menu-item" @click="closeMobileMenu">
          <span class="menu-text">非遗活动</span>
        </router-link>
        <router-link to="/notice" class="mobile-menu-item" @click="closeMobileMenu">
          <span class="menu-text">公告</span>
        </router-link>
        <router-link to="/admin" class="mobile-menu-item admin-menu-item" v-if="isAdmin" @click="closeMobileMenu">
          <span class="menu-text">管理后台</span>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Navbar',
  data() {
    return {
      mobileMenuOpen: false,
      searchKeyword: '',
      isScrolled: false
    };
  },
  computed: {
    isLoggedIn() {
      return !!localStorage.getItem('user');
    },
    isAdmin() {
      const userStr = localStorage.getItem('user');
      if (userStr) {
        try {
          const user = JSON.parse(userStr);
          return user.isAdmin || user.role === 'admin';
        } catch (e) {
          return false;
        }
      }
      return false;
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
      this.isScrolled = window.scrollY > 50;
    },
    handleResize() {
      if (window.innerWidth > 768) {
        this.mobileMenuOpen = false;
        document.body.style.overflow = 'auto';
      }
    },
    handleSearch() {
      const keyword = (this.searchKeyword || '').trim();
      if (!keyword) return;
      this.$router.push({
        name: 'search',
        query: { keyword }
      });
    },
    handleSearchAndClose() {
      this.handleSearch();
      this.closeMobileMenu();
    }
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll);
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
  background: rgba(255, 255, 255, 0.95);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  position: sticky;
  top: 0;
  z-index: 1000;
  border-bottom: 1px solid #e0e0e0;
  transition: all 0.3s ease;
  height: 72px;
}

.navbar.scrolled {
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.12);
  background: rgba(255, 255, 255, 0.99);
  height: 64px;
}

.navbar-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.navbar-logo {
  flex: 0 0 auto;
}

.logo-link {
  text-decoration: none;
  color: #9d2932;
  font-weight: 600;
  display: flex;
  align-items: center;
}

.navbar-logo-img {
  width: 64px;
  height: 64px;
  transition: all 0.3s ease;
}

.navbar.scrolled .navbar-logo-img {
  width: 56px;
  height: 56px;
}

/* 桌面端导航菜单 */
.navbar-menu {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 24px;
  justify-content: center;
}

.navbar-search {
  flex: 0 0 240px;
  margin-left: 24px;
}

.search-input {
  border-radius: 25px !important;
}

.search-input .el-input__wrapper {
  border-radius: 25px !important;
  border: 1px solid #e0e0e0;
}

.search-icon {
  cursor: pointer;
  color: #666;
  font-size: 18px;
}

.menu-item {
  text-decoration: none;
  color: #333;
  font-size: 1rem;
  font-weight: 500;
  padding: 12px 24px;
  border-radius: 30px;
  transition: all 0.3s ease;
  background: rgba(255, 255, 255, 0.8);
  border: 1px solid transparent;
}

.menu-item:hover {
  color: #9d2932;
  background: rgba(157, 41, 50, 0.05);
  border-color: rgba(157, 41, 50, 0.2);
}

.menu-item.active {
  color: white;
  background: #9d2932;
  font-weight: 600;
  border-color: #9d2932;
}

/* 移动端汉堡菜单按钮 */
.mobile-menu-btn {
  display: none;
  cursor: pointer;
  padding: 10px;
  border-radius: 10px;
  background: rgba(0, 102, 204, 0.05);
  border: 1px solid #e0e0e0;
}

.menu-icon {
  font-size: 24px;
  color: #9d2932;
}

/* 移动端导航菜单 */
.mobile-menu {
  position: fixed;
  top: 70px;
  left: 0;
  width: 100%;
  height: calc(100vh - 70px);
  background: #fff;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
  transform: translateY(-100%);
  opacity: 0;
  visibility: hidden;
  transition: all 0.35s ease;
  z-index: 999;
  border-top: 1px solid #e0e0e0;
}

.navbar.scrolled .mobile-menu {
  top: 60px;
  height: calc(100vh - 60px);
}

.mobile-menu.open {
  transform: translateY(0);
  opacity: 1;
  visibility: visible;
}

.mobile-menu-content {
  padding: 24px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  height: 100%;
  overflow-y: auto;
}

.mobile-search {
  margin-bottom: 8px;
}

.mobile-search .el-input {
  width: 100%;
}

.mobile-menu-item {
  text-decoration: none;
  color: #333;
  font-size: 1.1rem;
  font-weight: 500;
  padding: 18px 24px;
  border-radius: 12px;
  transition: all 0.3s ease;
  background: rgba(0, 0, 0, 0.02);
  border: 1px solid #e0e0e0;
}

.mobile-menu-item:hover {
  color: #0066cc;
  background: rgba(0, 102, 204, 0.05);
  border-color: #0066cc;
}

.admin-menu-item {
  background: rgba(138, 26, 26, 0.1);
  color: #9d2932;
  margin-top: 16px;
  border-color: rgba(138, 26, 26, 0.2);
}

.admin-menu-item:hover {
  background: rgba(138, 26, 26, 0.2);
  color: #9d2932;
  border-color: #9d2932;
}

/* 认证区域 */
.navbar-auth {
  flex: 0 0 auto;
  display: flex;
  gap: 16px;
  align-items: center;
}

.auth-item {
  text-decoration: none;
  color: #333;
  font-size: 0.9rem;
  font-weight: 500;
  padding: 10px 20px;
  border-radius: 25px;
  transition: all 0.3s ease;
  border: 1px solid #e0e0e0;
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.9);
}

.auth-item:hover {
  color: #9d2932;
  background: rgba(157, 41, 50, 0.05);
  border-color: #9d2932;
}

.admin-link {
  background: #9d2932;
  color: white;
  border-color: #9d2932;
}

.admin-link:hover {
  background: #8a1a1a;
  border-color: #8a1a1a;
  color: white;
}

.user-avatar {
  margin-right: 0;
}

.avatar-container {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 10px 20px;
  border-radius: 25px;
  transition: all 0.3s ease;
  border: 1px solid #e0e0e0;
  background: rgba(255, 255, 255, 0.9);
}

.avatar-container:hover {
  background: rgba(157, 41, 50, 0.05);
  border-color: #9d2932;
}

.username {
  font-size: 0.9rem;
  font-weight: 500;
  color: #333;
  margin-right: 10px;
  transition: color 0.3s ease;
}

.avatar-container:hover .username {
  color: #9d2932;
}

.el-icon-arrow-down {
  font-size: 14px;
  color: #333;
  transition: all 0.3s ease;
}

.avatar-container:hover .el-icon-arrow-down {
  color: #9d2932;
}

.el-dropdown-menu {
  min-width: 150px;
  border-radius: 10px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
  border: 1px solid #e0e0e0;
  background: #fff;
}

.el-dropdown-menu .router-link-active {
  text-decoration: none;
  color: #0066cc;
}

.el-dropdown-menu__item {
  padding: 10px 16px;
  font-size: 0.85rem;
  transition: all 0.3s ease;
}

.el-dropdown-menu__item:hover {
  background: rgba(0, 102, 204, 0.05);
  color: #0066cc;
}

.el-dropdown-menu__item a {
  color: inherit;
  text-decoration: none;
  display: block;
  width: 100%;
  height: 100%;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .navbar-menu {
    gap: 20px;
  }
  
  .navbar-search {
    flex: 0 0 180px;
  }
  
  .menu-item {
    padding: 8px 16px;
    font-size: 0.95rem;
  }
}

@media (max-width: 992px) {
  .navbar-menu {
    gap: 16px;
  }
  
  .navbar-search {
    flex: 0 0 160px;
  }
  
  .menu-item {
    padding: 6px 12px;
    font-size: 0.9rem;
  }
  
  .auth-item {
    padding: 6px 12px;
    font-size: 0.8rem;
  }
}

@media (max-width: 768px) {
  .navbar {
    height: 64px;
  }
  
  .navbar.scrolled {
    height: 56px;
  }
  
  .navbar-container {
    flex-direction: row;
    height: 100%;
    padding: 0 20px;
  }

  .navbar-logo-img {
    width: 56px;
    height: 56px;
  }

  .navbar.scrolled .navbar-logo-img {
    width: 48px;
    height: 48px;
  }

  .navbar-menu {
    display: none;
  }

  .mobile-menu-btn {
    display: block;
    order: 3;
    padding: 8px;
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
    padding: 4px 10px;
  }

  .avatar-container {
    padding: 4px 10px;
  }

  .username {
    font-size: 0.8rem;
    margin-right: 6px;
  }
  
  .mobile-menu {
    top: 64px;
    height: calc(100vh - 64px);
  }
  
  .navbar.scrolled .mobile-menu {
    top: 56px;
    height: calc(100vh - 56px);
  }
}

@media (max-width: 576px) {
  .navbar-container {
    padding: 0 16px;
  }
  
  .navbar-logo-img {
    width: 48px;
    height: 48px;
  }
  
  .navbar.scrolled .navbar-logo-img {
    width: 44px;
    height: 44px;
  }
  
  .navbar-auth {
    gap: 6px;
  }
  
  .auth-item {
    font-size: 0.75rem;
    padding: 3px 8px;
  }
  
  .avatar-container {
    padding: 3px 8px;
  }
  
  .username {
    font-size: 0.75rem;
    margin-right: 4px;
  }
  
  .mobile-menu-content {
    padding: 20px;
  }
  
  .mobile-menu-item {
    font-size: 1rem;
    padding: 16px 20px;
  }
  
  .mobile-search {
    margin-bottom: 12px;
  }
}
</style>
