<template>
  <div class="notice-view">
    <div class="notice-container">
      <h2>公告信息</h2>
      <div class="notice-list">
        <el-card v-for="notice in notices" :key="notice.id" class="notice-item" @click="viewDetail(notice.id)" style="cursor: pointer;">
          <template #header>
            <div class="notice-header">
              <span>{{ notice.title }}</span>
              <span class="notice-date">{{ formatDate(notice.createTime) }}</span>
            </div>
          </template>
          <div class="notice-content" v-html="truncateContent(notice.content)"></div>
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
const notices = ref([])

onMounted(() => {
  loadNotices()
})

const loadNotices = async () => {
  try {
    const response = await api.get('/culture-news/list', { params: { type: 3 } })
    if (response.success) {
      // 只显示状态为1的公告
      notices.value = (response.data || []).filter(item => item.status === 1)
    }
  } catch (error) {
    console.error('加载公告失败:', error)
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
  // 截断内容，最多显示100个字符
  return plainText.length > 100 ? plainText.substring(0, 100) + '...' : plainText
}

const viewDetail = (id) => {
  router.push(`/notice/${id}`)
}
</script>

<style scoped>
.notice-view {
  width: 100%;
  padding: 20px;
  background-color: var(--background-color);
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
}

.notice-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.notice-view h2 {
  font-size: 2rem;
  margin-bottom: 30px;
  color: var(--primary-color);
  text-align: center;
  position: relative;
  padding-bottom: 15px;
}

.notice-view h2::after {
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

.notice-list {
  margin-top: 20px;
}

.notice-item {
  margin-bottom: 30px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.notice-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
}

.notice-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px;
  background: #f5f5f5;
}

.notice-header span:first-child {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.notice-date {
  font-size: 0.9rem;
  color: #999;
}

.notice-content {
  margin: 15px;
  line-height: 1.6;
  color: #666;
}

.notice-footer {
  margin: 15px;
  text-align: right;
}
</style>