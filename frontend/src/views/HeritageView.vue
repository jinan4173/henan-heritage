<template>
  <div class="heritage-view">
    <div class="container">
      <h1>河南省非物质文化遗产</h1>

      <!-- 河南非遗地图分布 -->
      <div class="map-section">
        <!-- 选择器 -->
        <div class="map-controls">
          <el-select v-model="selectedRegion" placeholder="请选择地域" style="margin-right: 20px; width: 150px;">
            <el-option label="全部" value="" />
            <el-option 
              v-for="region in regions" 
              :key="region.id"
              :label="region.name" 
              :value="region.id"
            />
          </el-select>

          <el-select v-model="selectedCategory" placeholder="请选择分类" style="width: 150px;">
            <el-option label="全部" value="" />
            <el-option 
              v-for="category in categories" 
              :key="category.id"
              :label="category.name" 
              :value="category.id"
            />
          </el-select>
        </div>
        
        <!-- 地图和统计卡片布局 -->
        <div class="map-and-stats">
          <!-- 地图容器 -->
          <div id="henanMap" class="map-container"></div>
          
          <!-- 分类统计卡片 -->
          <div class="stats-container">
            <div 
              v-for="(item, index) in categoryStatistics" 
              :key="index"
              class="stat-card"
            >
              <div class="stat-number">{{ item.count }}</div>
              <div class="stat-name">{{ item.name }}</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 分类和地区导航 -->
      <div class="nav-container">
        <!-- 分类导航 -->
        <div class="nav-section">
          <div class="nav-content">
            <h2>非遗分类</h2>
            <el-select v-model="categoryId" placeholder="选择分类" @change="handleFilter">
              <el-option label="全部" value="" />
              <el-option 
                v-for="category in categories" 
                :key="category.id"
                :label="category.name" 
                :value="category.id"
              />
            </el-select>
          </div>
        </div>

        <!-- 地区导航 -->
        <div class="nav-section">
          <div class="nav-content">
            <h2>地区分布</h2>
            <el-select v-model="regionId" placeholder="选择地区" @change="handleFilter">
              <el-option label="全部" value="" />
              <el-option 
                v-for="region in regions" 
                :key="region.id"
                :label="region.name" 
                :value="region.id"
              />
            </el-select>
          </div>
        </div>
      </div>

      <el-tabs v-model="activeTab" type="border-card">
        <el-tab-pane label="非遗项目" name="heritage">


          <div class="heritage-grid">
            <div 
              v-for="item in heritageItems" 
              :key="item.id"
              class="heritage-card"
              @click="goToDetail(item.id)"
            >
              <div class="card-image">
                <img :src="item.coverImage" :alt="item.title" />
              </div>
              <div class="card-content">
                <div class="card-header">
                  <h3>{{ item.title }}</h3>
                </div>
                <div class="card-meta">
                  <span class="meta-item">类别: {{ item.category?.name || item.categoryName }}</span>
                  <span class="meta-item">申报地区: {{ item.region?.name || item.declarationRegion }}</span>
                </div>
                <p class="card-description">{{ item.description?.substring(0, 120) || '' }}...</p>
              </div>
            </div>
          </div>

          <div v-if="heritageItems.length === 0" class="empty-state">
            <el-empty description="暂无非遗项目" />
          </div>

          <!-- 分页 -->
          <div class="pagination">
            <el-pagination
              v-model:current-page="currentPage"
              :page-size="pageSize"
              layout="prev, pager, next, jumper"
              :total="total"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-tab-pane>

        <el-tab-pane label="传承人" name="inheritor">
          <div class="inheritor-table">
            <el-table :data="inheritorList" style="width: 100%" @row-click="viewInheritorDetail" size="small">
              <el-table-column type="index" label="序号" min-width="80" align="center" />
              <el-table-column prop="name" label="姓名" min-width="120" align="center" />
              <el-table-column prop="gender" label="性别" min-width="80" align="center" />
              <el-table-column prop="nation" label="民族" min-width="100" align="center" />
              <el-table-column prop="title" label="项目" min-width="180" align="center" />
              <el-table-column prop="category" label="类别" min-width="120" align="center" />
            </el-table>
          </div>

          <div v-if="inheritorList.length === 0" class="empty-state">
            <el-empty description="暂无传承人" />
          </div>

          <!-- 传承人分页 -->
          <div class="pagination">
            <el-pagination
              v-model:current-page="currentInheritorPage"
              v-model:page-size="inheritorPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="inheritorTotal"
              @size-change="handleInheritorSizeChange"
              @current-change="handleInheritorCurrentChange"
            />
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, onUnmounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { heritageApi } from '../api/heritage'
import api from '../api/index.js'
import { ElTabs, ElTabPane, ElSelect, ElOption, ElEmpty, ElTable, ElTableColumn, ElButton, ElPagination } from 'element-plus'
import * as echarts from 'echarts'

const router = useRouter()
const route = useRoute()
const activeTab = ref('heritage')
const heritageItems = ref([])
const inheritorList = ref([])
const categoryId = ref(null)
const regionId = ref(null)

// 非遗分类数据
const categories = ref([])

// 地区分布数据
const regions = ref([])

// 分页相关
const currentPage = ref(1)
const pageSize = ref(12) // 每页12项，四行三列
const total = ref(0)



// 加载分类数据
const loadCategories = async () => {
  try {
    const response = await api.get('/heritage/categories')
    if (response && response.success) {
      categories.value = response.data || []
    }
  } catch (error) {
    console.error('加载分类数据失败:', error)
    categories.value = []
  }
}

// 加载地区数据
const loadRegions = async () => {
  try {
    const response = await api.get('/heritage/regions')
    if (response && response.success) {
      regions.value = response.data || []
    }
  } catch (error) {
    console.error('加载地区数据失败:', error)
    regions.value = []
  }
}

// 传承人分页相关
const currentInheritorPage = ref(1)
const inheritorPageSize = ref(20) // 每页20个传承人
const inheritorTotal = ref(0)

// 存储地图数据
const henanGeoJson = ref(null)

// 地图控制数据
const selectedRegion = ref('')
const selectedCategory = ref('')
const mapChart = ref(null)

// 分类统计数据
const categoryStatistics = ref([])

// 地图相关
const cityStatistics = ref([])

onMounted(() => {
  if (route.query.categoryId) {
    categoryId.value = parseInt(route.query.categoryId)
  }
  if (route.query.regionId) {
    regionId.value = parseInt(route.query.regionId)
  }
  if (route.query.tab) {
    activeTab.value = route.query.tab
  }
  // 加载分类和地区数据
  loadCategories()
  loadRegions()
  loadData()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  if (mapChart.value) {
    mapChart.value.dispose()
  }
})

watch(() => route.query, () => {
  if (route.query.categoryId) {
    categoryId.value = parseInt(route.query.categoryId)
  }
  if (route.query.regionId) {
    regionId.value = parseInt(route.query.regionId)
  }
  if (route.query.tab) {
    activeTab.value = route.query.tab
  }
  loadHeritageItems()
}, { deep: true })

const loadData = async () => {
  console.log('开始加载数据...')
  try {
    console.log('加载非遗项目数据...')
    await loadHeritageItems()
    console.log('加载传承人数据...')
    await loadInheritors()
    // 加载地市统计数据
    console.log('加载地市统计数据...')
    await loadCityStatistics()
    // 加载分类统计数据
    console.log('加载分类统计数据...')
    await loadCategoryStatistics()
    console.log('数据加载完成')
  } catch (error) {
    console.error('加载数据失败:', error)
  }
}

const loadHeritageItems = async () => {
  try {
    // 根据是否有筛选条件，调用不同的API方法
    let res
    if (categoryId.value || regionId.value) {
      res = await heritageApi.filter(categoryId.value, regionId.value, 1)
      if (res && res.success) {
        heritageItems.value = res.data || []
        total.value = res.data?.length || 0
      } else {
        heritageItems.value = []
        total.value = 0
      }
    } else {
      res = await heritageApi.list(null, currentPage.value, pageSize.value)
      if (res && res.success) {
        heritageItems.value = res.data || []
        total.value = res.total || 0
      } else {
        heritageItems.value = []
        total.value = 0
      }
    }
  } catch (error) {
    console.error('加载非遗项目失败:', error)
    heritageItems.value = []
    total.value = 0
  }
}

// 分页方法
const handleCurrentChange = (current) => {
  currentPage.value = current
  loadHeritageItems()
}

const loadInheritors = async () => {
  console.log('开始加载传承人数据...')
  console.log('当前页码:', currentInheritorPage.value)
  console.log('每页大小:', inheritorPageSize.value)
  try {
    const response = await api.get('/inheritor/list', {
      params: {
        page: currentInheritorPage.value,
        pageSize: inheritorPageSize.value
      }
    })
    console.log('传承人API响应:', response)
    if (response && response.success) {
      console.log('传承人数据:', response.data)
      console.log('传承人总数:', response.total)
      inheritorList.value = response.data || []
      inheritorTotal.value = response.total || 0
      console.log('加载传承人成功，共', inheritorList.value.length, '条数据')
    } else {
      console.log('传承人API调用失败:', response)
      inheritorList.value = []
      inheritorTotal.value = 0
    }
  } catch (error) {
    console.error('加载传承人失败:', error)
    inheritorList.value = []
    inheritorTotal.value = 0
  }
}

// 传承人分页方法
const handleInheritorCurrentChange = (current) => {
  currentInheritorPage.value = current
  loadInheritors()
}

const handleInheritorSizeChange = (size) => {
  inheritorPageSize.value = size
  currentInheritorPage.value = 1
  loadInheritors()
}

const handleFilter = () => {
  router.push({
    path: '/heritage',
    query: {
      categoryId: categoryId.value,
      regionId: regionId.value,
      tab: activeTab.value
    }
  })
}

const goToDetail = (id) => {
  router.push(`/heritage/${id}`)
}

const viewInheritorDetail = (row) => {
  router.push(`/inheritor/${row.id}`)
}

// 加载分类统计数据
const loadCategoryStatistics = async () => {
  try {
    // 从后端API获取分类统计数据
    const response = await fetch('/api/statistics/category')
    if (response.ok) {
      const data = await response.json()
      console.log('分类统计数据:', data)
      // 确保categoryStatistics.value是一个数组
      if (data && data.data && Array.isArray(data.data)) {
        categoryStatistics.value = data.data
      } else {
        categoryStatistics.value = []
      }
    } else {
      console.error('获取分类统计数据失败:', response.status)
      // 使用模拟数据作为回退
      categoryStatistics.value = [
        { name: '民间文学', count: 121 },
        { name: '传统音乐', count: 105 },
        { name: '传统舞蹈', count: 162 },
        { name: '传统戏剧', count: 216 },
        { name: '传统技艺', count: 482 },
        { name: '传统美术', count: 243 },
        { name: '传统医药', count: 166 },
        { name: '曲艺', count: 85 },
        { name: '民俗', count: 67 },
        { name: '传统体育、游艺与杂技', count: 129 }
      ]
    }
  } catch (error) {
    console.error('加载分类统计数据失败:', error)
    // 使用模拟数据作为回退
    categoryStatistics.value = [
      { name: '民间文学', count: 121 },
      { name: '传统音乐', count: 105 },
      { name: '传统舞蹈', count: 162 },
      { name: '传统戏剧', count: 216 },
      { name: '传统技艺', count: 482 },
      { name: '传统美术', count: 243 },
      { name: '传统医药', count: 166 },
      { name: '曲艺', count: 85 },
      { name: '民俗', count: 67 },
      { name: '传统体育、游艺与杂技', count: 129 }
    ]
  }
}

// 加载地市统计数据
const loadCityStatistics = async () => {
  try {
    // 从后端API获取地市统计数据
    const response = await fetch('/api/statistics/city')
    if (response.ok) {
      const data = await response.json()
      console.log('地市统计数据:', data)
      // 确保cityStatistics.value是一个数组
      if (data && data.data && Array.isArray(data.data)) {
        cityStatistics.value = data.data
      } else {
        cityStatistics.value = []
      }
      // 初始化地图
      setTimeout(async () => {
        await initMap()
      }, 100)
    } else {
      console.error('获取地市统计数据失败:', response.status)
      // 使用模拟数据
      await useMockData()
    }
  } catch (error) {
    console.error('加载地市统计数据失败:', error)
    // 使用模拟数据
    await useMockData()
  }
}

// 使用模拟数据
const useMockData = async () => {
  cityStatistics.value = [
    {
      city: '郑州',
      count: 25
    },
    {
      city: '开封',
      count: 18
    },
    {
      city: '洛阳',
      count: 32
    },
    {
      city: '平顶山',
      count: 15
    },
    {
      city: '安阳',
      count: 20
    },
    {
      city: '鹤壁',
      count: 8
    },
    {
      city: '新乡',
      count: 16
    },
    {
      city: '焦作',
      count: 14
    },
    {
      city: '濮阳',
      count: 12
    },
    {
      city: '许昌',
      count: 10
    },
    {
      city: '漯河',
      count: 7
    },
    {
      city: '三门峡',
      count: 9
    },
    {
      city: '南阳',
      count: 22
    },
    {
      city: '商丘',
      count: 13
    },
    {
      city: '信阳',
      count: 17
    },
    {
      city: '周口',
      count: 19
    },
    {
      city: '驻马店',
      count: 11
    }
  ]
  await initMap()
}

// 初始化地图
const initMap = async () => {
  console.log('开始初始化地图...')
  const mapContainer = document.getElementById('henanMap')
  console.log('地图容器:', mapContainer)
  if (!mapContainer) {
    console.error('地图容器不存在')
    return
  }
  console.log('地图容器尺寸:', mapContainer.offsetWidth, 'x', mapContainer.offsetHeight)

  // 销毁现有实例
  if (mapChart.value) {
    console.log('销毁现有地图实例')
    mapChart.value.dispose()
  }

  // 创建ECharts实例
  try {
    console.log('创建ECharts实例')
    mapChart.value = echarts.init(mapContainer)
    console.log('ECharts实例创建成功')
  } catch (error) {
    console.error('ECharts实例创建失败:', error)
    return
  }

  // 加载并注册地图
  try {
    console.log('开始加载地图数据...')
    if (!henanGeoJson.value) {
      const response = await fetch('/src/assets/json/henan.geojson')
      if (!response.ok) {
        throw new Error('Failed to load map data')
      }
      henanGeoJson.value = await response.json()
      console.log('地图数据加载成功')
    }
    console.log('地图数据类型:', typeof henanGeoJson.value)
    console.log('地图数据结构:', henanGeoJson.value.type)
    echarts.registerMap('henan', henanGeoJson.value)
    console.log('地图注册成功')
  } catch (error) {
    console.error('地图注册失败:', error)
    return
  }

  // 准备地图数据 - 使用各城市数据
  const mapData = cityStatistics.value.map(item => ({
    name: item.city,
    value: item.count
  }))

  console.log('地图数据:', mapData)

  // 配置选项
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}<br/>非遗项目数量: {c}项'
    },
    series: [
      {
        name: '非遗项目数量',
        type: 'map',
        map: 'henan',
        data: mapData,
        label: {
          show: true,
          fontSize: 12,
          color: '#666'
        },
        itemStyle: {
          areaColor: '#e6f7fa',
          borderColor: '#333',
          borderWidth: 1
        },
        emphasis: {
          label: {
            show: true,
            color: '#333'
          },
          itemStyle: {
            areaColor: '#ffcc80'
          }
        },
        // 河南省地图缩放和中心点 - 进一步向上调整，减少上方空白
        zoom: 0.9,
        center: [113.9, 34.3]
      }
    ]
  }

  // 如果有数据，添加visualMap
  if (mapData.length > 0) {
    const maxValue = Math.max(...mapData.map(item => item.value))
    option.visualMap = {
      type: 'continuous',
      min: 0,
      max: maxValue,
      left: 'left',
      bottom: 'bottom',
      text: ['高', '低'],
      calculable: true,
      inRange: {
        color: ['#e0f7fa', '#b2ebf2', '#80deea', '#4dd0e1', '#26c6da', '#00bcd4', '#00acc1', '#0097a7', '#00838f', '#006064']
      }
    }
  }

  // 设置选项
  try {
    console.log('设置地图选项')
    mapChart.value.setOption(option)
    console.log('地图配置成功')
  } catch (error) {
    console.error('地图配置失败:', error)
  }
}

// 处理窗口大小变化
const handleResize = () => {
  if (mapChart.value) {
    mapChart.value.resize()
  }
}




</script>

<style scoped>
.heritage-view {
  padding: 20px;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

/* 地图样式 */
.map-section {
  margin-bottom: 50px;
}

.map-section h2 {
  font-size: 1.8rem;
  margin-bottom: 30px;
  color: #2B2B2B;
  text-align: center;
}

.map-controls {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 30px;
}

.map-and-stats {
  display: flex;
  gap: 30px;
  align-items: flex-start;
}

.map-container {
  flex: 1;
  height: 550px;
  overflow: hidden;
}

.stats-container {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 15px;
  width: 300px;
}

.stat-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  padding: 20px;
  color: white;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-number {
  font-size: 1.8rem;
  font-weight: bold;
  margin-bottom: 8px;
}

.stat-name {
  font-size: 0.9rem;
  opacity: 0.9;
}



.container h1 {
  font-size: 2rem;
  margin-bottom: 30px;
  color: #333;
  text-align: center;
}

.nav-container {
  display: flex;
  gap: 40px;
  margin-bottom: 40px;
  justify-content: center;
  flex-wrap: wrap;
}

.nav-section {
  flex: 1;
  min-width: 300px;
  max-width: 500px;
}

.nav-section h2 {
  font-size: 1.8rem;
  margin-bottom: 20px;
  color: #2B2B2B;
  text-align: center;
}

.nav-content {
  display: flex;
  align-items: center;
  padding: 20px 0;
  gap: 20px;
}

.nav-content h2 {
  font-size: 1.2rem;
  margin: 0;
  color: #2B2B2B;
  white-space: nowrap;
}

.nav-content .el-select {
  width: 200px;
}

.filter-container {
  display: flex;
  gap: 30px;
  margin-bottom: 30px;
  justify-content: center;
  align-items: center;
}

.filter-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.filter-item label {
  font-size: 1rem;
  color: #666;
  white-space: nowrap;
}

.filter-item .el-select {
  min-width: 150px;
}

.heritage-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 25px;
  margin-bottom: 30px;
}

/* 分页样式 */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
  gap: 20px;
  padding: 15px;
  background: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.heritage-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.3s ease;
}

.heritage-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.card-image {
  height: 220px;
  overflow: hidden;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.heritage-card:hover .card-image img {
  transform: scale(1.1);
}

.card-content {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 10px;
}

.card-header h3 {
  font-size: 1.3rem;
  margin: 0;
  color: #333;
  flex: 1;
  margin-right: 15px;
}

.item-id {
  background: #667eea;
  color: white;
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 500;
  white-space: nowrap;
}

.card-meta {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 15px;
}

.meta-item {
  background: #f0f0f0;
  padding: 5px 12px;
  border-radius: 15px;
  font-size: 0.9rem;
  color: #666;
}

.card-description {
  font-size: 1rem;
  color: #888;
  line-height: 1.5;
}

.inheritor-table {
  margin-top: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  overflow: hidden;
  width: 100%;
  max-width: 100%;
}

.inheritor-table .el-table {
  width: 100%;
  max-width: 100%;
}

.inheritor-table .el-table__header-wrapper {
  width: 100%;
}

.inheritor-table .el-table__header {
  width: 100% !important;
}

.inheritor-table .el-table__body-wrapper {
  width: 100%;
  overflow-x: auto;
}

.inheritor-table .el-table__body {
  width: 100% !important;
}

.empty-state {
  margin: 100px 0;
  text-align: center;
}

.el-table {
  border-radius: 10px;
  overflow: hidden;
}

.el-table th {
  background-color: #f5f5f5;
  font-weight: bold;
}

.el-table tr:hover {
  background-color: #f9f9f9;
}

@media (max-width: 1400px) {
  .container {
    max-width: 1000px;
  }
  
  .map-container {
    height: 500px;
  }
}

@media (max-width: 1200px) {
  .container {
    max-width: 900px;
  }
  
  .map-container {
    height: 450px;
  }
  
  .heritage-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .container h1 {
    font-size: 1.8rem;
  }
  
  .map-section h2 {
    font-size: 1.6rem;
  }
}

@media (max-width: 992px) {
  .container {
    max-width: 750px;
  }
  
  .map-container {
    height: 400px;
  }
  
  .filter-container {
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
  }
  
  .filter-item {
    width: 100%;
  }
  
  .filter-item .el-select {
    flex: 1;
    min-width: 200px;
  }
  
  .nav-container {
    flex-direction: column;
    align-items: center;
    gap: 20px;
  }
  
  .nav-section {
    width: 100%;
    max-width: 500px;
  }
}

@media (max-width: 768px) {
  .heritage-view {
    padding: 15px;
  }
  
  .container {
    max-width: 100%;
  }
  
  .container h1 {
    font-size: 1.5rem;
    margin-bottom: 20px;
  }
  
  .heritage-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  
  .card-header h3 {
    font-size: 1.2rem;
  }
  
  .card-description {
    font-size: 0.9rem;
  }
  
  .map-container {
    height: 350px;
  }
  
  .map-section h2 {
    font-size: 1.5rem;
  }
  
  .nav-content {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  
  .nav-content h2 {
    white-space: normal;
  }
  
  .nav-content .el-select {
    width: 100%;
  }
}

@media (max-width: 576px) {
  .container h1 {
    font-size: 1.3rem;
  }
  
  .filter-item label {
    font-size: 0.9rem;
  }
  
  .card-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  
  .item-id {
    align-self: flex-start;
  }
  
  .card-meta {
    flex-direction: column;
    gap: 10px;
  }
  
  .meta-item {
    font-size: 0.8rem;
  }
  
  .map-container {
    height: 300px;
  }
  
  .heritage-view {
    padding: 10px;
  }
}
</style>
