<template>
  <div class="news-detail-view">
    <el-breadcrumb separator="/" class="breadcrumb">
      <el-breadcrumb-item><a href="/">首页</a></el-breadcrumb-item>
      <el-breadcrumb-item v-if="isNewsPage"><a href="/news">文化资讯</a></el-breadcrumb-item>
      <el-breadcrumb-item v-else-if="isActivityPage"><a href="/activity">活动信息</a></el-breadcrumb-item>
      <el-breadcrumb-item v-else-if="isNoticePage"><a href="/notice">公告信息</a></el-breadcrumb-item>
      <el-breadcrumb-item>{{ detailTitle }}</el-breadcrumb-item>
    </el-breadcrumb>
    
    <div class="news-detail">
      <h1 class="news-title">{{ news.title }}</h1>
      <div class="news-meta">
        <span class="news-date">{{ formatDate(news.createdAt) }}</span>
      </div>
      
      <div class="news-image" v-if="news.coverImage">
        <img :src="news.coverImage" :alt="news.title" />
      </div>
      
      <div class="news-content" v-if="news.content">
        {{ news.content }}
      </div>
      <div class="empty-content" v-else>
        <el-empty description="暂无内容" />
      </div>
      
      <div class="news-actions">
        <el-button type="primary" @click="goBack">返回列表</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import api from '../api/index.js'

const router = useRouter()
const route = useRoute()
const news = ref({})

// 计算属性：根据当前路由判断是哪个页面
const isNewsPage = computed(() => {
  return route.path.startsWith('/news/')
})

const isActivityPage = computed(() => {
  return route.path.startsWith('/activity/')
})

const isNoticePage = computed(() => {
  return route.path.startsWith('/notice/')
})

// 计算属性：根据当前路由显示不同的详情页标题
const detailTitle = computed(() => {
  if (isNewsPage.value) {
    return '资讯详情'
  } else if (isActivityPage.value) {
    return '活动详情'
  } else if (isNoticePage.value) {
    return '公告详情'
  }
  return '详情'
})

onMounted(() => {
  loadNewsDetail()
})

const loadNewsDetail = async () => {
  const id = route.params.id
  if (!id) {
    goBack()
    return
  }
  
  try {
    const response = await api.get(`/culture-news/get/${id}`)
    if (response.success) {
      news.value = response.data
      console.log('加载详情成功:', news.value)
    }
  } catch (error) {
    console.error('加载资讯详情失败:', error)
  }
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN')
}

const goBack = () => {
  if (isNewsPage.value) {
    router.push('/news')
  } else if (isActivityPage.value) {
    router.push('/activity')
  } else if (isNoticePage.value) {
    router.push('/notice')
  } else {
    router.push('/')
  }
}
</script>

<style scoped>
.news-detail-view {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

.breadcrumb {
  margin-bottom: 20px;
}

.news-detail {
  background: #fff;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.news-title {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
  text-align: center;
}

.news-meta {
  text-align: center;
  margin-bottom: 30px;
  color: #999;
  font-size: 0.9rem;
}

.news-image {
  margin-bottom: 30px;
  text-align: center;
}

.news-image img {
  max-width: 100%;
  height: auto;
  border-radius: 5px;
}

.news-content {
  line-height: 1.8;
  color: #333;
  margin-bottom: 30px;
}

.empty-content {
  text-align: center;
  padding: 60px 0;
}

.news-actions {
  text-align: center;
  margin-top: 40px;
}
</style>