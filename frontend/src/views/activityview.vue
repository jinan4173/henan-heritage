<template>
  <div class="activity-view">
    <h2>活动信息</h2>
    <div class="activity-list">
      <el-card v-for="activity in activities" :key="activity.id" class="activity-item">
        <div class="activity-image" v-if="activity.coverImage">
          <img :src="activity.coverImage" :alt="activity.title" />
        </div>
        <template #header>
          <div class="activity-header">
            <span>{{ activity.title }}</span>
            <span class="activity-date">{{ activity.date }}</span>
          </div>
        </template>
        <div class="activity-content">{{ truncateContent(activity.content) }}</div>
        <div class="activity-footer">
          <el-button size="small" type="primary" @click="viewDetail(activity.id)">查看详情</el-button>
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
const activities = ref([])

onMounted(() => {
  loadActivities()
})

const loadActivities = async () => {
  try {
    const response = await api.get('/culture-news/list', { params: { type: 2 } })
    if (response.success) {
      // 只显示状态为1的活动
      activities.value = (response.data || []).filter(item => item.status === 1)
    }
  } catch (error) {
    console.error('加载活动失败:', error)
  }
}

const truncateContent = (content) => {
  if (!content) return ''
  return content.length > 100 ? content.substring(0, 100) + '...' : content
}

const viewDetail = (id) => {
  router.push(`/activity/${id}`)
}
</script>

<style scoped>
.activity-view {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.activity-view h2 {
  font-size: 1.8rem;
  margin-bottom: 20px;
  color: #333;
  text-align: center;
}

.activity-list {
  margin-top: 20px;
}

.activity-item {
  margin-bottom: 30px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.activity-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
}

.activity-image {
  height: 200px;
  overflow: hidden;
}

.activity-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.activity-item:hover .activity-image img {
  transform: scale(1.05);
}

.activity-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background: #f5f5f5;
}

.activity-header span:first-child {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
}

.activity-date {
  font-size: 0.9rem;
  color: #999;
}

.activity-content {
  margin: 15px;
  line-height: 1.6;
  color: #666;
}

.activity-footer {
  margin: 15px;
  text-align: right;
}
</style>