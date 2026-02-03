<template>
  <div id="app">
    <!-- 只在非管理系统页面显示导航栏和页脚 -->
    <Navbar v-if="!isAdminRoute" />
    <main class="main-content">
      <router-view />
    </main>
    <Footer v-if="!isAdminRoute" />
  </div>
</template>

<script>
import Navbar from './components/Navbar.vue'
import Footer from './components/Footer.vue'
import { computed } from 'vue'
import { useRoute } from 'vue-router'

export default {
  name: 'App',
  components: {
    Navbar,
    Footer
  },
  setup() {
    const route = useRoute()
    
    // 计算当前是否为管理系统路由
    const isAdminRoute = computed(() => {
      return route.path.startsWith('/admin')
    })
    
    return {
      isAdminRoute
    }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  font-size: 16px;
  line-height: 1.5;
  color: #555555;
  background-color: #F5F2EB;
}

#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.main-content {
  flex: 1;
}
</style>