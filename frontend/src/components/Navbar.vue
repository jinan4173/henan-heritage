<template>
  <div class="navbar">
    <div class="navbar-container">
      <div class="navbar-logo">
        <router-link to="/" class="logo-link">
          <h1>河南非遗</h1>
        </router-link>
      </div>
      <div class="navbar-menu">
        <router-link to="/" class="menu-item" :class="{ active: $route.path === '/' }">首页</router-link>
        <router-link to="/heritage" class="menu-item" :class="{ active: $route.path === '/heritage' }">非遗项目</router-link>
        <router-link to="/promotion" class="menu-item" :class="{ active: $route.path === '/promotion' }">非遗宣传</router-link>
        <router-link to="/news" class="menu-item" :class="{ active: $route.path === '/news' }">文化资讯</router-link>
        <router-link to="/activity" class="menu-item" :class="{ active: $route.path === '/activity' }">非遗活动</router-link>
        <router-link to="/notice" class="menu-item" :class="{ active: $route.path === '/notice' }">公告</router-link>
        <router-link to="/user" class="menu-item" :class="{ active: $route.path === '/user' }">个人中心</router-link>
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
  </div>
</template>

<script>
export default {
  name: 'Navbar',
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
    }
  }
}
</script>

<style scoped>
.navbar {
  background: var(--primary-color);
  box-shadow: 0 4px 16px rgba(200, 22, 29, 0.3);
  position: sticky;
  top: 0;
  z-index: 1000;
}

.navbar-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
}

.navbar-logo {
  flex: 0 0 auto;
}

.logo-link {
  text-decoration: none;
  color: #FFFFFF;
  font-weight: bold;
}

.logo-link h1 {
  font-size: 1.6rem;
  margin: 0;
  font-weight: bold;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.navbar-menu {
  flex: 1;
  display: flex;
  justify-content: center;
  gap: 30px;
}

.menu-item {
  text-decoration: none;
  color: #FFFFFF;
  font-size: 1rem;
  font-weight: 600;
  padding: 8px 14px;
  border-radius: 4px;
  transition: all 0.3s ease;
  position: relative;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.menu-item:hover {
  color: #FFFFFF;
  background: transparent;
}

.menu-item.active {
  color: #FFFFFF;
  font-weight: 600;
  background: rgba(255, 255, 255, 0.25);
}

.menu-item.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 20px;
  height: 2px;
  background: #FFFFFF;
  border-radius: 1px;
}

.navbar-auth {
  flex: 0 0 auto;
  display: flex;
  gap: 15px;
  align-items: center;
}

.auth-item {
  text-decoration: none;
  color: #FFFFFF;
  font-size: 0.9rem;
  font-weight: 600;
  padding: 6px 12px;
  border-radius: 4px;
  transition: all 0.3s ease;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.auth-item:hover {
  color: #FFFFFF;
  background: transparent;
}

.admin-link {
  background: rgba(255, 255, 255, 0.2);
  margin-left: 10px;
}

.admin-link:hover {
  background: rgba(255, 255, 255, 0.2);
  color: #FFFFFF;
}

.user-avatar {
  margin-right: 10px;
}

.avatar-container {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 6px 12px;
  border-radius: 4px;
  transition: all 0.3s ease;
}

.avatar-container:hover {
  background: transparent;
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  margin-right: 8px;
  object-fit: cover;
}

.username {
  font-size: 0.9rem;
  font-weight: 600;
  color: #FFFFFF;
  margin-right: 5px;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.el-icon-arrow-down {
  font-size: 12px;
  color: #FFFFFF;
}

.el-dropdown-menu {
  min-width: 120px;
}

.el-dropdown-menu .router-link-active {
  text-decoration: none;
  color: #1F3D3A;
}

.el-dropdown-menu__item {
  padding: 8px 16px;
}

.el-dropdown-menu__item:hover {
  background: #f5f5f5;
}

.el-dropdown-menu__item a {
  color: inherit;
  text-decoration: none;
  display: block;
  width: 100%;
  height: 100%;
}

@media (max-width: 768px) {
  .navbar-container {
    flex-direction: column;
    height: auto;
    padding: 15px 20px;
  }

  .navbar-logo {
    margin-bottom: 15px;
  }

  .navbar-menu {
    flex-wrap: wrap;
    gap: 15px;
    margin-bottom: 15px;
  }

  .navbar-auth {
    width: 100%;
    justify-content: center;
  }
}
</style>
