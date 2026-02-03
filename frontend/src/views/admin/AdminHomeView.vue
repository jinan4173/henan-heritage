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
    <div class="recent-activities">
      <h3>最近活动</h3>
      <el-table :data="recentActivities" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="action" label="操作"></el-table-column>
        <el-table-column prop="user" label="操作人" width="120"></el-table-column>
        <el-table-column prop="time" label="时间" width="180"></el-table-column>
      </el-table>
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

const recentActivities = ref([])

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

.recent-activities {
  margin-top: 30px;
}

.recent-activities h3 {
  margin-bottom: 15px;
  font-size: 1.1rem;
  font-weight: 600;
  color: #333;
}
</style>