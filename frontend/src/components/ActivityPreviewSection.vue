<template>
  <div class="activity-preview-section">
    <h2>最新活动</h2>
    <div class="activity-list">
      <div
        v-for="activity in activities"
        :key="activity.id"
        class="activity-card"
        @click="goToActivity(activity.id)"
      >
        <div class="activity-image" v-if="activity.coverImage">
          <img :src="activity.coverImage" :alt="activity.title" />
        </div>
        <div class="activity-content">
          <div class="activity-header">
            <h3>{{ activity.title }}</h3>
            <span class="activity-date">{{ formatDate(activity.createTime) }}</span>
          </div>
          <p class="activity-desc">{{ getDescription(activity) }}</p>
          <div class="activity-footer">
            <el-button type="primary" size="small" @click.stop="goToActivity(activity.id)">
              查看详情
            </el-button>
          </div>
        </div>
      </div>
      <div v-if="activities.length === 0" class="empty-state">
        <el-empty description="暂无活动" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElButton, ElEmpty } from 'element-plus'
import api from '../api/index.js'

const router = useRouter()
const activities = ref([])

const loadActivities = async () => {
  try {
    // type=2 表示活动，status=1 表示启用
    const response = await api.get('/culture-news/list', { params: { type: 2, status: 1 } })
    if (response && response.success) {
      const list = response.data || []
      activities.value = list.slice(0, 4)
    } else {
      activities.value = []
    }
  } catch (error) {
    console.error('加载最新活动失败:', error)
    activities.value = []
  }
}

onMounted(() => {
  loadActivities()
})

const goToActivity = (id) => {
  if (!id) return
  router.push(`/activity/${id}`)
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  if (isNaN(date.getTime())) return ''
  const y = date.getFullYear()
  const m = String(date.getMonth() + 1).padStart(2, '0')
  const d = String(date.getDate()).padStart(2, '0')
  return `${y}-${m}-${d}`
}

const getDescription = (activity) => {
  if (!activity) return ''
  const content = activity.summary || activity.content || ''
  const plainText = content.replace(/<[^>]*>/g, '')
  return plainText.length > 80 ? plainText.substring(0, 80) + '...' : plainText
}
</script>

<style scoped>
.activity-preview-section {
  padding: 32px 40px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
}

.activity-preview-section h2 {
  font-size: 1.8rem;
  margin-bottom: 24px;
  color: var(--primary-color);
  text-align: left;
  position: relative;
  padding-bottom: 12px;
}

.activity-preview-section h2::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 80px;
  height: 3px;
  background-color: var(--accent-color);
  border-radius: 2px;
}

.activity-list {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 20px;
}

.activity-card {
  background: #f9f9f9;
  border-radius: 10px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
}

.activity-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.12);
  background: #ffffff;
}

.activity-image {
  width: 100%;
  height: 140px;
  overflow: hidden;
}

.activity-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.activity-card:hover .activity-image img {
  transform: scale(1.05);
}

.activity-content {
  padding: 12px 14px 14px;
  display: flex;
  flex-direction: column;
  flex: 1;
}

.activity-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 8px;
  margin-bottom: 8px;
}

.activity-header h3 {
  font-size: 0.95rem;
  font-weight: 600;
  color: #333;
  margin: 0;
  line-height: 1.4;
  flex: 1;
}

.activity-date {
  font-size: 0.75rem;
  color: #999;
  white-space: nowrap;
}

.activity-desc {
  font-size: 0.85rem;
  color: #666;
  line-height: 1.5;
  margin: 0 0 10px;
  flex: 1;
}

.activity-footer {
  display: flex;
  justify-content: flex-end;
}

.empty-state {
  grid-column: 1 / -1;
}

@media (max-width: 1200px) {
  .activity-list {
    grid-template-columns: repeat(3, minmax(0, 1fr));
  }
}

@media (max-width: 992px) {
  .activity-preview-section {
    padding: 24px;
  }

  .activity-list {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 768px) {
  .activity-preview-section {
    padding: 20px 16px;
  }

  .activity-list {
    grid-template-columns: 1fr;
  }
}
</style>

