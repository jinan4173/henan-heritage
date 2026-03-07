<template>
  <div class="news-view">
    <div class="news-container">
      <h2>文化资讯</h2>
      <div class="news-list">
        <el-card v-for="(news, index) in newsList" :key="news.id" :class="['news-item', index % 2 === 0 ? 'left-image' : 'right-image']" @click="viewDetail(news.id)" style="cursor: pointer;">
          <div class="news-content-wrapper">
            <div class="news-image" v-if="news.coverImage">
              <img :src="news.coverImage" :alt="news.title" />
            </div>
            <div class="news-text-content">
              <div class="news-header">
                <span>{{ news.title }}</span>
                <span class="news-date">{{ formatDate(news.createTime) }}</span>
              </div>
              <div class="news-content" v-html="truncateContent(news.content)"></div>
            </div>
          </div>
        </el-card>
      </div>
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
      // 只显示状态为1的新闻
      newsList.value = (response.data || []).filter(item => item.status === 1)
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
  // 移除HTML标签，只保留纯文本
  const plainText = content.replace(/<[^>]*>/g, '')
  // 截断内容，最多显示200个字符
  return plainText.length > 200 ? plainText.substring(0, 200) + '...' : plainText
}

const viewDetail = (id) => {
  router.push(`/news/${id}`)
}
</script>

<style scoped>
.news-view {
  width: 100%;
  padding: 0;
  background-color: var(--background-color);
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
}

.news-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0;
}

.news-view h2 {
  font-size: 2rem;
  margin: 0;
  padding: 40px 0 15px;
  color: var(--primary-color);
  text-align: center;
  position: relative;
}

.news-view h2::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 80px;
  height: 3px;
  background-color: var(--accent-color);
  border-radius: 2px;
}

.news-list {
  margin: 0;
  width: 100%;
}

.news-item {
  margin: 0 0 20px;
  border-radius: 0;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  width: 100%;
}

.news-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
}

.news-content-wrapper {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  padding: 15px;
}

.news-image {
  flex: 0 0 300px;
  overflow: hidden;
}

.left-image .news-image {
  margin-right: 20px;
}

.right-image .news-content-wrapper {
  flex-direction: row-reverse;
}

.right-image .news-image {
  margin-left: 20px;
  margin-right: 0;
}

.news-image img {
  width: 100%;
  height: 200px;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.news-item:hover .news-image img {
  transform: scale(1.05);
}

.news-text-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.news-header {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 10px;
  background: none;
  padding: 0;
}

.news-header span:first-child {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
  line-height: 1.4;
}

.news-date {
  font-size: 0.9rem;
  color: #999;
}

.news-content {
  margin: 0;
  line-height: 1.6;
  color: #666;
  flex: 1;
}

.news-footer {
  margin: 15px;
  text-align: right;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .news-content-wrapper {
    flex-direction: column;
  }
  
  .news-image {
    flex: 0 0 auto;
    margin-right: 0;
    margin-bottom: 15px;
  }
  
  .news-image img {
    width: 100%;
    height: auto;
  }
}
</style>