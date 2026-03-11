<template>
  <div class="admin-home">
    <!-- 页面头部 -->
    <div class="page-header">
      <h2>管理后台首页</h2>
      <div class="header-actions">
        <el-button type="primary" size="small" @click="refreshData">
          <el-icon><Refresh /></el-icon>
          刷新数据
        </el-button>
      </div>
    </div>

    <!-- 统计卡片区域 -->
    <div class="stats-grid">
      <div class="stat-card">
        <div class="stat-icon heritage-icon">
          <el-icon><Collection /></el-icon>
        </div>
        <div class="stat-content">
          <div class="stat-number">{{ stats.heritageCount }}</div>
          <div class="stat-label">非遗项目总数</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon inheritor-icon">
          <el-icon><UserFilled /></el-icon>
        </div>
        <div class="stat-content">
          <div class="stat-number">{{ stats.inheritorCount }}</div>
          <div class="stat-label">传承人数量</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon user-icon">
          <el-icon><Avatar /></el-icon>
        </div>
        <div class="stat-content">
          <div class="stat-number">{{ stats.userCount }}</div>
          <div class="stat-label">用户数量</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon activity-icon">
          <el-icon><Bell /></el-icon>
        </div>
        <div class="stat-content">
          <div class="stat-number">{{ stats.activityCount || 0 }}</div>
          <div class="stat-label">活动数量</div>
        </div>
      </div>
    </div>

    <!-- 图表区域 -->
    <div class="charts-container">
      <!-- 左侧图表 -->
      <div class="chart-wrapper">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>非遗项目分类分布</span>
            </div>
          </template>
          <div class="chart-content">
            <div ref="categoryChartRef" class="echart-container"></div>
          </div>
        </el-card>
      </div>

      <!-- 右侧图表 -->
      <div class="chart-wrapper">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>非遗项目地区分布</span>
            </div>
          </template>
          <div class="chart-content">
            <div ref="regionChartRef" class="echart-container"></div>
          </div>
        </el-card>
      </div>

      <!-- 底部图表 -->
      <div class="chart-wrapper full-width">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>用户注册趋势</span>
            </div>
          </template>
          <div class="chart-content">
            <div ref="userTrendChartRef" class="echart-container"></div>
          </div>
        </el-card>
      </div>

      <!-- 热门非遗排行 -->
      <div class="chart-wrapper full-width">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>热门非遗项目排行</span>
            </div>
          </template>
          <div class="chart-content">
            <div ref="popularChartRef" class="echart-container"></div>
          </div>
        </el-card>
      </div>
    </div>

    <!-- 最近活动区域 -->
    <div class="recent-activities">
      <el-card class="activity-card">
        <template #header>
          <div class="card-header">
            <span>最近活动</span>
            <el-button type="text" size="small">查看全部</el-button>
          </div>
        </template>
        <div class="activity-list">
          <el-timeline>
            <el-timeline-item
              v-for="(activity, index) in recentActivities"
              :key="index"
              :timestamp="activity.time"
              placement="top"
            >
              <el-card>
                <h4>{{ activity.title }}</h4>
                <p>{{ activity.description }}</p>
                <div class="activity-meta">
                  <span class="activity-status" :class="activity.status">{{ activity.statusText }}</span>
                </div>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch, nextTick } from 'vue'
import { Refresh, Collection, UserFilled, Avatar, Bell } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import api from '../../api/index.js'

const stats = ref({
  heritageCount: 0,
  userCount: 0,
  inheritorCount: 0,
  activityCount: 0
})

const categoryStats = ref({})
const regionStats = ref({})
const userTrend = ref({})
const popularHeritage = ref([])
const recentActivities = ref([])

// 图表引用
const categoryChartRef = ref(null)
const regionChartRef = ref(null)
const userTrendChartRef = ref(null)
const popularChartRef = ref(null)

// 图表实例
let categoryChart = null
let regionChart = null
let userTrendChart = null
let popularChart = null

const categoryData = computed(() => {
  return Object.entries(categoryStats.value).map(([name, value]) => ({
    name,
    value
  }))
})

const regionData = computed(() => {
  return Object.entries(regionStats.value).map(([name, value]) => ({
    name,
    value
  }))
})

const userTrendData = computed(() => {
  return Object.entries(userTrend.value).map(([month, count]) => ({
    month,
    count
  }))
})

const popularData = computed(() => {
  return popularHeritage.value
    .map(item => ({
      name: item.title,
      value: item.popularity
    }))
    .sort((a, b) => b.value - a.value)
    .reverse()
})

onMounted(() => {
  fetchStats()
  fetchCategoryStats()
  fetchRegionStats()
  fetchUserTrend()
  fetchPopularHeritage()
  fetchRecentActivities()
  
  // 等待DOM更新后初始化图表
  nextTick(() => {
    initCharts()
  })
})

// 监听数据变化，更新图表
watch([categoryData, regionData, userTrendData, popularData], () => {
  updateCharts()
}, { deep: true })

// 初始化图表
const initCharts = () => {
  if (categoryChartRef.value) {
    categoryChart = echarts.init(categoryChartRef.value)
  }
  if (regionChartRef.value) {
    regionChart = echarts.init(regionChartRef.value)
  }
  if (userTrendChartRef.value) {
    userTrendChart = echarts.init(userTrendChartRef.value)
  }
  if (popularChartRef.value) {
    popularChart = echarts.init(popularChartRef.value)
  }
  updateCharts()
}

// 更新图表
const updateCharts = () => {
  updateCategoryChart()
  updateRegionChart()
  updateUserTrendChart()
  updatePopularChart()
}

// 更新分类分布图表
const updateCategoryChart = () => {
  if (!categoryChart) return
  
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      data: categoryData.value.map(item => item.name)
    },
    series: [
      {
        name: '非遗分类',
        type: 'pie',
        radius: '70%',
        center: ['50%', '50%'],
        data: categoryData.value,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
  
  categoryChart.setOption(option)
}

// 更新地区分布图表
const updateRegionChart = () => {
  if (!regionChart) return
  
  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: regionData.value.map(item => item.name),
        axisTick: {
          alignWithLabel: true
        },
        axisLabel: {
          rotate: 45
        }
      }
    ],
    yAxis: [
      {
        type: 'value'
      }
    ],
    series: [
      {
        name: '项目数量',
        type: 'bar',
        barWidth: '60%',
        data: regionData.value.map(item => item.value),
        itemStyle: {
          color: '#409EFF'
        },
        label: {
          show: true,
          position: 'top',
          formatter: '{c}'
        }
      }
    ]
  }
  
  regionChart.setOption(option)
}

// 更新用户趋势图表
const updateUserTrendChart = () => {
  if (!userTrendChart) return
  
  const option = {
    tooltip: {
      trigger: 'axis'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: userTrendData.value.map(item => item.month)
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '注册用户',
        type: 'line',
        stack: 'Total',
        data: userTrendData.value.map(item => item.count),
        areaStyle: {
          color: 'rgba(64, 158, 255, 0.1)'
        },
        lineStyle: {
          color: '#409EFF',
          width: 2
        },
        symbol: 'circle',
        symbolSize: 8
      }
    ]
  }
  
  userTrendChart.setOption(option)
}

// 更新热门非遗排行图表
const updatePopularChart = () => {
  if (!popularChart) return
  
  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'value'
    },
    yAxis: {
      type: 'category',
      data: popularData.value.map(item => item.name),
      axisLabel: {
        interval: 0,
        rotate: 0
      }
    },
    series: [
      {
        name: '热度',
        type: 'bar',
        data: popularData.value.map(item => item.value),
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: '#667eea' },
            { offset: 1, color: '#764ba2' }
          ])
        },
        label: {
          show: true,
          position: 'right',
          formatter: '{c}'
        }
      }
    ]
  }
  
  popularChart.setOption(option)
}

// 刷新数据
const refreshData = () => {
  fetchStats()
  fetchCategoryStats()
  fetchRegionStats()
  fetchUserTrend()
  fetchPopularHeritage()
  fetchRecentActivities()
}

const fetchStats = async () => {
  try {
    const response = await api.get('/dashboard/stats')
    if (response.success) {
      stats.value = {
        heritageCount: response.data?.heritageCount || 0,
        userCount: response.data?.userCount || 0,
        inheritorCount: response.data?.inheritorCount || 0,
        activityCount: response.data?.activityCount || 0
      }
    }
  } catch (error) {
    console.error('获取统计数据失败:', error)
    // 使用默认数据作为fallback
    stats.value = {
      heritageCount: 4,
      userCount: 3,
      inheritorCount: 2,
      activityCount: 1
    }
  }
}

const fetchCategoryStats = async () => {
  try {
    const response = await api.get('/dashboard/category-stats')
    if (response.success) {
      categoryStats.value = response.data
    }
  } catch (error) {
    console.error('获取分类统计数据失败:', error)
    // 使用默认数据作为fallback
    categoryStats.value = {
      '传统音乐': 12,
      '传统舞蹈': 8,
      '传统戏剧': 15,
      '传统技艺': 20
    }
  }
}

const fetchRegionStats = async () => {
  try {
    const response = await api.get('/dashboard/region-stats')
    if (response.success) {
      regionStats.value = response.data
    }
  } catch (error) {
    console.error('获取地区统计数据失败:', error)
    // 使用默认数据作为fallback
    regionStats.value = {
      '郑州市': 15,
      '洛阳市': 12,
      '开封市': 10,
      '安阳市': 8,
      '新乡市': 6
    }
  }
}

const fetchUserTrend = async () => {
  try {
    console.log('开始获取用户注册趋势...')
    const response = await api.get('/dashboard/user-trend')
    console.log('用户注册趋势响应:', response)
    if (response.success) {
      console.log('用户注册趋势数据:', response.data)
      userTrend.value = response.data
    } else {
      console.log('获取用户注册趋势失败:', response.message)
      // 使用默认数据作为fallback
      userTrend.value = {
        '2025-10': 5,
        '2025-11': 8,
        '2025-12': 12,
        '2026-01': 15,
        '2026-02': 10,
        '2026-03': 18
      }
    }
  } catch (error) {
    console.error('获取用户趋势数据失败:', error)
    // 使用默认数据作为fallback
    userTrend.value = {
      '2025-10': 5,
      '2025-11': 8,
      '2025-12': 12,
      '2026-01': 15,
      '2026-02': 10,
      '2026-03': 18
    }
  }
}

const fetchPopularHeritage = async () => {
  try {
    const response = await api.get('/dashboard/popular-heritage')
    if (response.success) {
      popularHeritage.value = response.data
    }
  } catch (error) {
    console.error('获取热门非遗项目失败:', error)
    // 使用默认数据作为fallback
    popularHeritage.value = [
      { id: 10, title: '豫剧', category: '传统戏剧', region: '河南省', popularity: 10 },
      { id: 9, title: '少林功夫', category: '传统体育', region: '郑州市', popularity: 9 },
      { id: 8, title: '洛阳牡丹', category: '传统技艺', region: '洛阳市', popularity: 8 },
      { id: 7, title: '开封汴绣', category: '传统美术', region: '开封市', popularity: 7 },
      { id: 6, title: '安阳甲骨文', category: '传统文字', region: '安阳市', popularity: 6 }
    ]
  }
}

const fetchRecentActivities = async () => {
  try {
    const response = await api.get('/culture-news/list', { params: { type: 2, status: 1 } })
    if (response.success) {
      const activities = response.data || []
      // 转换为时间轴格式
      recentActivities.value = activities.slice(0, 5).map(activity => {
        // 简单的状态映射，实际项目中可能需要更复杂的逻辑
        let status = 'info'
        let statusText = '计划中'
        
        const activityDate = new Date(activity.date)
        const now = new Date()
        
        if (activityDate < now) {
          status = 'success'
          statusText = '已完成'
        } else if (activityDate.getTime() - now.getTime() < 7 * 24 * 60 * 60 * 1000) {
          status = 'warning'
          statusText = '进行中'
        }
        
        return {
          title: activity.title,
          description: activity.summary || activity.content || '',
          time: activity.date,
          status,
          statusText
        }
      })
    }
  } catch (error) {
    console.error('获取最近活动失败:', error)
    // 使用默认数据作为fallback
    recentActivities.value = [
      {
        title: '非遗项目申报',
        description: '新增3个非遗项目申报',
        time: '2026-03-10 10:30',
        status: 'success',
        statusText: '已完成'
      },
      {
        title: '传承人培训',
        description: '举办传承人培训活动',
        time: '2026-03-09 14:00',
        status: 'warning',
        statusText: '进行中'
      },
      {
        title: '文化展览',
        description: '筹备非遗文化展览',
        time: '2026-03-08 09:15',
        status: 'info',
        statusText: '计划中'
      }
    ]
  }
}
</script>

<style scoped>
.admin-home {
  padding: 24px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 60px);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  padding-bottom: 16px;
  border-bottom: 1px solid #e4e7ed;
}

.page-header h2 {
  font-size: 20px;
  font-weight: 600;
  color: #303133;
  margin: 0;
}

.header-actions {
  display: flex;
  gap: 12px;
}

/* 统计卡片样式 */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 16px;
  margin-bottom: 24px;
}

.stat-card {
  background: #fff;
  border-radius: 6px;
  padding: 16px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.12);
}

.stat-icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  color: #fff;
}

.heritage-icon {
  background: linear-gradient(135deg, #409EFF, #667eea);
}

.inheritor-icon {
  background: linear-gradient(135deg, #67C23A, #85ce61);
}

.user-icon {
  background: linear-gradient(135deg, #E6A23C, #ebb563);
}

.activity-icon {
  background: linear-gradient(135deg, #F56C6C, #f78989);
}

.stat-content {
  flex: 1;
}

.stat-number {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 2px;
  line-height: 1.2;
}

.stat-label {
  font-size: 12px;
  color: #909399;
}

/* 图表容器 */
.charts-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(500px, 1fr));
  gap: 20px;
  margin-bottom: 32px;
}

.chart-wrapper {
  position: relative;
}

.chart-wrapper.full-width {
  grid-column: 1 / -1;
}

.chart-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.08);
  overflow: hidden;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  border-bottom: 1px solid #f0f0f0;
}

.card-header span {
  font-size: 16px;
  font-weight: 500;
  color: #303133;
}

.chart-content {
  padding: 20px;
  height: 350px;
}

.echart-container {
  width: 100%;
  height: 100%;
}

/* 最近活动 */
.recent-activities {
  margin-bottom: 32px;
}

.activity-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.08);
  overflow: hidden;
}

.activity-list {
  padding: 20px;
}

.activity-list .el-card {
  margin-bottom: 12px;
  border-radius: 6px;
  border: 1px solid #f0f0f0;
}

.activity-list h4 {
  font-size: 14px;
  font-weight: 500;
  color: #303133;
  margin: 0 0 8px 0;
}

.activity-list p {
  font-size: 12px;
  color: #909399;
  margin: 0 0 12px 0;
}

.activity-meta {
  display: flex;
  justify-content: flex-end;
}

.activity-status {
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 12px;
  font-weight: 400;
}

.activity-status.success {
  background-color: #f0f9eb;
  color: #67C23A;
}

.activity-status.warning {
  background-color: #fdf6ec;
  color: #E6A23C;
}

.activity-status.info {
  background-color: #ecf5ff;
  color: #409EFF;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .admin-home {
    padding: 16px;
  }

  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }

  .stats-grid {
    grid-template-columns: 1fr;
  }

  .charts-container {
    grid-template-columns: 1fr;
  }

  .chart-wrapper.full-width {
    grid-column: 1;
  }

  .chart-content {
    height: 250px;
  }
}
</style>