import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // 前台路由
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/heritage',
      name: 'heritage',
      component: () => import('../views/HeritageView.vue')
    },
    {
      path: '/heritage/:id',
      name: 'heritageDetail',
      component: () => import('../views/HeritageDisplayView.vue')
    },
    {
      path: '/activity',
      name: 'activity',
      component: () => import('../views/activityview.vue')
    },
    {
      path: '/activity/:id',
      name: 'activityDetail',
      component: () => import('../views/NewsDetailView.vue')
    },
    {
      path: '/inheritor',
      name: 'inheritor',
      component: () => import('../views/inheritorview.vue')
    },
    {
      path: '/news',
      name: 'news',
      component: () => import('../views/newsview.vue')
    },
    {
      path: '/news/:id',
      name: 'newsDetail',
      component: () => import('../views/NewsDetailView.vue')
    },
    {
      path: '/notice',
      name: 'notice',
      component: () => import('../views/noticeview.vue')
    },
    {
      path: '/notice/:id',
      name: 'noticeDetail',
      component: () => import('../views/NewsDetailView.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/loginview.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/registerview.vue')
    },
    {
      path: '/user',
      name: 'user',
      component: () => import('../views/userview.vue')
    },
    // 后台路由
    {
      path: '/admin',
      name: 'admin',
      component: () => import('../views/admin/AdminLayout.vue'),
      children: [
        {
          path: '',
          name: 'adminHome',
          component: () => import('../views/admin/AdminHomeView.vue')
        },
        {
          path: 'dashboard',
          name: 'adminDashboard',
          component: () => import('../views/admin/AdminDashboardView.vue')
        },
        {
          path: 'heritage',
          name: 'adminHeritage',
          component: () => import('../views/admin/AdminHeritageView.vue')
        },

        {
          path: 'content',
          name: 'adminContent',
          component: () => import('../views/admin/AdminContentManageView.vue')
        },
        {
          path: 'inheritor',
          name: 'adminInheritor',
          component: () => import('../views/admin/AdminInheritorView.vue')
        },
        {
          path: 'user',
          name: 'adminUser',
          component: () => import('../views/admin/AdminUserView.vue')
        },
        {
          path: 'carousel',
          name: 'adminCarousel',
          component: () => import('../views/admin/AdminCarouselView.vue')
        }
      ]
    },
    {
      path: '/admin/login',
      name: 'adminLogin',
      component: () => import('../views/admin/AdminLoginView.vue')
    }
  ]
})

export default router