import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // 前台路由
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeView.vue'),
      meta: { title: '首页' }
    },
    // 非遗项目相关路由
    {
      path: '/heritage',
      name: 'heritage',
      component: () => import('../views/HeritageView.vue'),
      meta: { title: '非遗项目' }
    },
    {
      path: '/heritage/:id',
      name: 'heritageDetail',
      component: () => import('../views/HeritageDisplayView.vue'),
      meta: { title: '非遗项目详情' }
    },
    // 传承人相关路由
    {
      path: '/inheritors',
      name: 'inheritors',
      component: () => import('../views/inheritorview.vue'),
      meta: { title: '传承人' }
    },
    {
      path: '/inheritors/:id',
      name: 'inheritorDetail',
      component: () => import('../views/NewsDetailView.vue'),
      meta: { title: '传承人详情' }
    },
    // 活动相关路由
    {
      path: '/activity',
      name: 'activity',
      component: () => import('../views/activityview.vue'),
      meta: { title: '非遗活动' }
    },
    {
      path: '/activity/:id',
      name: 'activityDetail',
      component: () => import('../views/NewsDetailView.vue'),
      meta: { title: '活动详情' }
    },
    // 资讯相关路由
    {
      path: '/news',
      name: 'news',
      component: () => import('../views/newsview.vue'),
      meta: { title: '文化资讯' }
    },
    {
      path: '/news/:id',
      name: 'newsDetail',
      component: () => import('../views/NewsDetailView.vue'),
      meta: { title: '资讯详情' }
    },
    // 公告相关路由
    {
      path: '/notice',
      name: 'notice',
      component: () => import('../views/noticeview.vue'),
      meta: { title: '公告' }
    },
    {
      path: '/notice/:id',
      name: 'noticeDetail',
      component: () => import('../views/NewsDetailView.vue'),
      meta: { title: '公告详情' }
    },
    // 宣传相关路由
    {
      path: '/promotion',
      name: 'promotion',
      component: () => import('../views/NewPromotionView.vue'),
      meta: { title: '非遗宣传' }
    },
    // 媒体详情路由
    {
      path: '/media/:type/:index',
      name: 'mediaDetail',
      component: () => import('../views/MediaDetailView.vue'),
      meta: { title: '媒体详情' }
    },
    // 用户相关路由
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/loginview.vue'),
      meta: { title: '登录' }
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/registerview.vue'),
      meta: { title: '注册' }
    },
    {
      path: '/user',
      name: 'user',
      component: () => import('../views/userview.vue'),
      meta: { title: '个人中心' }
    },
    // 后台路由
    {
      path: '/admin',
      name: 'admin',
      component: () => import('../views/admin/AdminLayout.vue'),
      meta: { title: '管理后台' },
      children: [
        {
          path: '',
          name: 'adminHome',
          component: () => import('../views/admin/AdminHomeView.vue'),
          meta: { title: '管理后台首页' }
        },
        {
          path: 'heritage',
          name: 'adminHeritage',
          component: () => import('../views/admin/AdminHeritageView.vue'),
          meta: { title: '非遗项目管理' }
        },
        {
          path: 'inheritor',
          name: 'adminInheritor',
          component: () => import('../views/admin/AdminInheritorView.vue'),
          meta: { title: '传承人管理' }
        },
        {
          path: 'promotion',
          name: 'adminPromotion',
          component: () => import('../views/admin/AdminPromotionView.vue'),
          meta: { title: '非遗宣传管理' }
        },
        {
          path: 'news',
          name: 'adminNews',
          component: () => import('../views/admin/AdminContentManageView.vue'),
          meta: { title: '文化资讯管理' }
        },
        {
          path: 'activity',
          name: 'adminActivity',
          component: () => import('../views/admin/AdminActivityView.vue'),
          meta: { title: '非遗活动管理' }
        },
        {
          path: 'notice',
          name: 'adminNotice',
          component: () => import('../views/admin/AdminNoticeView.vue'),
          meta: { title: '公告管理' }
        },
        {
          path: 'user',
          name: 'adminUser',
          component: () => import('../views/admin/AdminUserView.vue'),
          meta: { title: '用户管理' }
        },
        {
          path: 'carousel',
          name: 'adminCarousel',
          component: () => import('../views/admin/AdminCarouselView.vue'),
          meta: { title: '轮播图管理' }
        },
        {
          path: 'activity-registration',
          name: 'adminActivityRegistration',
          component: () => import('../views/admin/AdminActivityRegistrationView.vue'),
          meta: { title: '活动报名管理' }
        },
        {
          path: 'comment',
          name: 'adminComment',
          component: () => import('../views/admin/AdminCommentView.vue'),
          meta: { title: '评论管理' }
        }
      ]
    },
    {
      path: '/admin/login',
      name: 'adminLogin',
      component: () => import('../views/admin/AdminLoginView.vue'),
      meta: { title: '后台登录' }
    }
  ]
})

// 全局前置守卫，设置页面标题
router.beforeEach((to, from, next) => {
  // 设置页面标题
  document.title = to.meta.title ? `${to.meta.title} - 河南非遗` : '河南非遗';
  next();
});

export default router