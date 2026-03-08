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
#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.main-content {
  flex: 1;
}

/* 所有页面的特殊样式 */
.login-view, .register-view, .home, .heritage-view, .inheritor-view, .userview, .noticeview, .activityview, .newsview, .PromotionView, .NewsDetailView, .NewPromotionView, .HeritageDisplayView {
  margin: 0 !important;
  padding: 0 !important;
}

/* 确保所有页面的main-content没有背景和圆角 */
body:has(.login-view) .main-content,
body:has(.register-view) .main-content,
body:has(.home) .main-content,
body:has(.heritage-view) .main-content,
body:has(.inheritor-view) .main-content,
body:has(.userview) .main-content,
body:has(.noticeview) .main-content,
body:has(.activityview) .main-content,
body:has(.newsview) .main-content,
body:has(.PromotionView) .main-content,
body:has(.NewsDetailView) .main-content,
body:has(.NewPromotionView) .main-content,
body:has(.HeritageDisplayView) .main-content {
  background-color: transparent !important;
  border-radius: 0 !important;
  box-shadow: none !important;
  min-height: 100vh !important;
  margin: 0 !important;
  padding: 0 !important;
}
</style>