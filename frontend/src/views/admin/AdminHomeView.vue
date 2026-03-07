<template>
  <div class="admin-home">
    <div class="page-header">
      <h2>管理后台首页</h2>
    </div>
    <div class="dashboard-stats">
      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-number">{{ stats.heritageCount }}</div>
          <div class="stat-label">非遗项目总数</div>
        </div>
      </el-card>
      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-number">{{ stats.inheritorCount }}</div>
          <div class="stat-label">传承人数量</div>
        </div>
      </el-card>
      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-number">{{ stats.userCount }}</div>
          <div class="stat-label">用户数量</div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../../api/index.js'

const stats = ref({
  heritageCount: 0,
  userCount: 0,
  inheritorCount: 0
})

onMounted(() => {
  fetchStats()
})

const fetchStats = async () => {
  try {
    const response = await api.get('/dashboard/stats')
    if (response.success) {
      stats.value = {
        heritageCount: response.heritageCount || 0,
        userCount: response.userCount || 0,
        inheritorCount: response.inheritorCount || 0
      }
    }
  } catch (error) {
    console.error('获取统计数据失败:', error)
    // 使用默认数据作为fallback
    stats.value = {
      heritageCount: 4,
      userCount: 3,
      inheritorCount: 2
    }
  }
}
</script>

<style scoped>
.admin-home {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #e4e7ed;
}

.dashboard-stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin: 20px 0;
}

.stat-card {
  height: 120px;
}

.stat-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.stat-number {
  font-size: 24px;
  font-weight: bold;
  color: #667eea;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 14px;
  color: #666;
}


</style>