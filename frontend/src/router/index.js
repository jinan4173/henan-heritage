import { createRouter, createWebHistory } from 'vue-router'

// 路由懒加载，使用动态导入减少首屏加载时间
const HomeView = () => import('../views/HomeView.vue')
const HeritageView = () => import('../views/HeritageView.vue')
const HeritageDisplayView = () => import('../views/HeritageDisplayView.vue')
const InheritorView = () => import('../views/InheritorView.vue')
const NewsDetailView = () => import('../views/NewsDetailView.vue')
const ActivityView = () => import('../views/ActivityView.vue')
const NewsView = () => import('../views/NewsView.vue')
const NoticeView = () => import('../views/NoticeView.vue')
const MediaDetailView = () => import('../views/MediaDetailView.vue')
const LoginView = () => import('../views/LoginView.vue')
const RegisterView = () => import('../views/RegisterView.vue')
const UserView = () => import('../views/UserView.vue')
const SearchView = () => import('../views/SearchView.vue')
const AdminLayout = () => import('../views/admin/AdminLayout.vue')
const AdminHomeView = () => import('../views/admin/AdminHomeView.vue')
const AdminHeritageView = () => import('../views/admin/AdminHeritageView.vue')
const AdminInheritorView = () => import('../views/admin/AdminInheritorView.vue')
const AdminContentManageView = () => import('../views/admin/AdminContentManageView.vue')
const AdminActivityView = () => import('../views/admin/AdminActivityView.vue')
const AdminNoticeView = () => import('../views/admin/AdminNoticeView.vue')
const AdminUserView = () => import('../views/admin/AdminUserView.vue')
const AdminCarouselView = () => import('../views/admin/AdminCarouselView.vue')
const AdminActivityRegistrationView = () => import('../views/admin/AdminActivityRegistrationView.vue')
const AdminCommentView = () => import('../views/admin/AdminCommentView.vue')
const AdminLoginView = () => import('../views/admin/AdminLoginView.vue')

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  // 添加滚动行为，提升用户体验
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  },
  routes: [
    // 前台路由
    {
      path: '/',
      name: 'home',
      component: HomeView,
      meta: { title: '首页' }
    },
    // 非遗项目相关路由
    {
      path: '/heritage',
      name: 'heritage',
      component: HeritageView,
      meta: { title: '非遗项目' }
    },
    {
      path: '/heritage/:id',
      name: 'heritageDetail',
      component: HeritageDisplayView,
      meta: { title: '非遗项目详情' }
    },
    // 传承人相关路由
    {
      path: '/inheritors',
      name: 'inheritors',
      component: InheritorView,
      meta: { title: '传承人' }
    },
    {
      path: '/inheritors/:id',
      name: 'inheritorDetail',
      component: NewsDetailView,
      meta: { title: '传承人详情' }
    },
    // 活动相关路由
    {
      path: '/activity',
      name: 'activity',
      component: ActivityView,
      meta: { title: '非遗活动' }
    },
    {
      path: '/activity/:id',
      name: 'activityDetail',
      component: NewsDetailView,
      meta: { title: '活动详情' }
    },
    // 资讯相关路由
    {
      path: '/news',
      name: 'news',
      component: NewsView,
      meta: { title: '文化资讯' }
    },
    {
      path: '/news/:id',
      name: 'newsDetail',
      component: NewsDetailView,
      meta: { title: '资讯详情' }
    },
    // 公告相关路由
    {
      path: '/notice',
      name: 'notice',
      component: NoticeView,
      meta: { title: '公告' }
    },
    {
      path: '/notice/:id',
      name: 'noticeDetail',
      component: NewsDetailView,
      meta: { title: '公告详情' }
    },
    // 媒体详情路由
    {
      path: '/media/:type/:index',
      name: 'mediaDetail',
      component: MediaDetailView,
      meta: { title: '媒体详情' }
    },
    // 用户相关路由
    {
      path: '/login',
      name: 'login',
      component: LoginView,
      meta: { title: '登录' }
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView,
      meta: { title: '注册' }
    },
    {
      path: '/user',
      name: 'user',
      component: UserView,
      meta: { title: '个人中心' }
    },
    {
      path: '/search',
      name: 'search',
      component: SearchView,
      meta: { title: '搜索结果' }
    },
    // 后台路由
    {
      path: '/admin',
      name: 'admin',
      component: AdminLayout,
      meta: { title: '管理后台' },
      children: [
        {
          path: '',
          name: 'adminHome',
          component: AdminHomeView,
          meta: { title: '管理后台首页' }
        },
        {
          path: 'heritage',
          name: 'adminHeritage',
          component: AdminHeritageView,
          meta: { title: '非遗项目管理' }
        },
        {
          path: 'inheritor',
          name: 'adminInheritor',
          component: AdminInheritorView,
          meta: { title: '传承人管理' }
        },
        {
          path: 'news',
          name: 'adminNews',
          component: AdminContentManageView,
          meta: { title: '文化资讯管理' }
        },
        {
          path: 'activity',
          name: 'adminActivity',
          component: AdminActivityView,
          meta: { title: '非遗活动管理' }
        },
        {
          path: 'notice',
          name: 'adminNotice',
          component: AdminNoticeView,
          meta: { title: '公告管理' }
        },
        {
          path: 'user',
          name: 'adminUser',
          component: AdminUserView,
          meta: { title: '用户管理' }
        },
        {
          path: 'carousel',
          name: 'adminCarousel',
          component: AdminCarouselView,
          meta: { title: '轮播图管理' }
        },
        {
          path: 'activity-registration',
          name: 'adminActivityRegistration',
          component: AdminActivityRegistrationView,
          meta: { title: '活动报名管理' }
        },
        {
          path: 'comment',
          name: 'adminComment',
          component: AdminCommentView,
          meta: { title: '评论管理' }
        }
      ]
    },
    {
      path: '/admin/login',
      name: 'adminLogin',
      component: AdminLoginView,
      meta: { title: '后台登录' }
    },
    // 404路由
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: HomeView,
      meta: { title: '页面不存在' }
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