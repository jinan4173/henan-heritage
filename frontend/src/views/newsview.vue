<template>
  <div class="news-view">
    <h2>文化资讯</h2>
    <div class="news-list">
      <el-card v-for="news in newsList" :key="news.id" class="news-item">
        <div class="news-image" v-if="news.coverImage">
          <img :src="news.coverImage" :alt="news.title" />
        </div>
        <template #header>
          <div class="news-header">
            <span>{{ news.title }}</span>
            <span class="news-date">{{ formatDate(news.createdAt) }}</span>
          </div>
        </template>
        <div class="news-content">{{ truncateContent(news.content) }}</div>
        <div class="news-footer">
          <el-button size="small" type="primary" @click="viewDetail(news.id)">查看详情</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api/index.js'

const router = useRouter()
const newsList = ref([])

onMounted(() => {
  loadNews()
})

const loadNews = async () => {
  try {
    const response = await api.get('/culture-news/list', { params: { type: 1 } })
    if (response.success) {
      newsList.value = response.data
    }
  } catch (error) {
    console.error('加载新闻失败:', error)
  }
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN')
}

const truncateContent = (content) => {
  if (!content) return ''
  return content.length > 100 ? content.substring(0, 100) + '...' : content
}

const viewDetail = (id) => {
  router.push(`/news/${id}`)
}
</script>

<style scoped>
.news-view {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.news-view h2 {
  font-size: 1.8rem;
  margin-bottom: 20px;
  color: #333;
  text-align: center;
}

.news-list {
  margin-top: 20px;
}

.news-item {
  margin-bottom: 30px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.news-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
}

.news-image {
  height: 200px;
  overflow: hidden;
}

.news-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.news-item:hover .news-image img {
  transform: scale(1.05);
}

.news-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background: #f5f5f5;
}

.news-header span:first-child {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
}

.news-date {
  font-size: 0.9rem;
  color: #999;
}

.news-content {
  margin: 15px;
  line-height: 1.6;
  color: #666;
}

.news-footer {
  margin: 15px;
  text-align: right;
}
</style>