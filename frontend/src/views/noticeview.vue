<template>
  <div class="notice-view">
    <h2>公告信息</h2>
    <div class="notice-list">
      <el-card v-for="notice in notices" :key="notice.id" class="notice-item">
        <template #header>
          <div class="notice-header">
            <span>{{ notice.title }}</span>
            <span class="notice-date">{{ formatDate(notice.createdAt) }}</span>
          </div>
        </template>
        <div class="notice-content">{{ truncateContent(notice.content) }}</div>
        <div class="notice-footer">
          <el-button size="small" type="primary" @click="viewDetail(notice.id)">查看详情</el-button>
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
  return content.length > 100 ? content.substring(0, 100) + '...' : content
}

const viewDetail = (id) => {
  router.push(`/notice/${id}`)
}
</script>

<style scoped>
.notice-view {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.notice-view h2 {
  font-size: 1.8rem;
  margin-bottom: 20px;
  color: #333;
  text-align: center;
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
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background: #f5f5f5;
}

.notice-header span:first-child {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
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