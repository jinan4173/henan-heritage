<template>
  <div class="map-section">
    <h2>非遗项目分布</h2>
    <div class="map-controls">
      <el-select v-model="selectedCategoryId" placeholder="选择分类" class="filter-item" @change="handleFilter">
        <el-option label="全部" value=""></el-option>
        <el-option v-for="category in categories" :key="category.id" :label="category.name" :value="category.id"></el-option>
      </el-select>
      <el-select v-model="selectedRegionId" placeholder="选择地区" class="filter-item" @change="handleFilter">
        <el-option label="全部" value=""></el-option>
        <el-option v-for="region in regions" :key="region.id" :label="region.name" :value="region.id"></el-option>
      </el-select>
    </div>
    <div class="map-and-stats">
      <div class="map-container">
        <div id="henanMap" style="width: 100%; height: 100%;"></div>
      </div>
      <div class="stats-container">
        <div v-for="(stat, index) in categoryStatistics" :key="index" class="stat-card">
          <div class="stat-number">{{ stat.count }}</div>
          <div class="stat-name">{{ stat.name }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch } from 'vue'
import api from '../api/index.js'
import * as echarts from 'echarts'

// 非遗分类数据
const categories = ref([])

// 地区分布数据
const regions = ref([])

// 筛选相关
const selectedCategoryId = ref(null)
const selectedRegionId = ref(null)

// 地图相关
const henanGeoJson = ref(null)
const mapChart = ref(null)
const categoryStatistics = ref([])
const cityStatistics = ref([])

// 加载分类数据
const loadCategories = async () => {
  try {
    const response = await api.get('/heritage/categories')
    if (response && response.code === 200) {
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
    if (response && response.code === 200) {
      regions.value = response.data || []
    }
  } catch (error) {
    console.error('加载地区数据失败:', error)
    regions.value = []
  }
}

// 加载分类统计数据
const loadCategoryStatistics = async () => {
  try {
    console.log('开始加载分类统计数据...')
    // 从后端API获取分类统计数据
    const data = await api.get('/statistics/category')
    console.log('分类统计数据:', data)
    if (data && data.success && Array.isArray(data.data)) {
      categoryStatistics.value = data.data
      console.log('分类统计数据加载完成:', categoryStatistics.value)
    } else {
      console.warn('分类统计数据格式不正确，显示空数据')
      categoryStatistics.value = []
    }
  } catch (error) {
    console.error('加载分类统计数据失败:', error)
    categoryStatistics.value = []
  }
}

// 加载地市统计数据
const loadCityStatistics = async () => {
  try {
    // 从后端API获取地市统计数据
    console.log('开始加载地市统计数据...')
    const data = await api.get('/statistics/city')
    console.log('地市统计数据:', data)
    // 确保cityStatistics.value是一个数组
    if (data && data.success && Array.isArray(data.data)) {
      cityStatistics.value = data.data
      // 初始化地图
      setTimeout(async () => {
        await initMap()
      }, 100)
    } else {
      console.warn('地市统计数据格式不正确，显示空数据')
      cityStatistics.value = []
    }
  } catch (error) {
    console.error('加载地市统计数据失败:', error)
    cityStatistics.value = []
  }
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
      const response = await fetch('/assets/json/henan.geojson')
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
  const mapData = cityStatistics.value.map(item => {
    const cityData = {
      name: item.city,
      value: item.count
    };
    
    // 为特定城市添加自定义标签位置
    if (item.city === '安阳市') {
      cityData.label = {
        position: 'top',
        offset: [0, -200] // 向上偏移10像素
      };
    } else if (item.city === '濮阳市') {
      cityData.label = {
        position: 'left',
        offset: [-1000, 0] // 向左偏移10像素
      };
    }
    
    return cityData;
  })

  console.log('地图数据:', mapData)

  // 配置选项
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}<br/>非遗项目数量: {c}项'
    },
    toolbox: {
      feature: {
        restore: {}
      }
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
          color: '#666',
          // 自定义标签位置
          formatter: '{b}'
        },
        itemStyle: {
          areaColor: '#f0f9ff',
          borderColor: '#91d5ff',
          borderWidth: 1
        },
        emphasis: {
          label: {
            show: true,
            color: '#333',
            fontWeight: 'bold'
          },
          itemStyle: {
            areaColor: '#ffd666'
          }
        },
        // 河南省地图缩放和中心点 - 调整为更合适的视图
        zoom: 1.2,
        center: [113.8, 33.8],
        // 启用缩放和拖拽功能
        roam: true,
        // 使用 labelLayout 函数调整标签位置
        labelLayout: function(params) {
          // 计算城市中心位置
          const centerX = params.rect.x + params.rect.width / 2;
          const centerY = params.rect.y + params.rect.height / 2;
          
          // 为特定城市调整标签位置，避免重叠
          switch (params.name) {
            case '安阳市':
              // 安阳市标签调整
              return {
                x: centerX - 30,
                y: centerY - 40,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '鹤壁市':
              // 鹤壁市标签调整
              return {
                x: centerX + 10,
                y: centerY + 20,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '新乡市':
              // 新乡市标签调整
              return {
                x: centerX + 15,
                y: centerY - 10,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '濮阳市':
              // 濮阳市标签调整
              return {
                x: centerX - 5,
                y: centerY - 15,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '郑州市':
              // 郑州市标签调整
              return {
                x: centerX,
                y: centerY + 10,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '开封市':
              // 开封市标签调整
              return {
                x: centerX + 10,
                y: centerY - 5,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '洛阳市':
              // 洛阳市标签调整
              return {
                x: centerX - 15,
                y: centerY,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '平顶山市':
              // 平顶山市标签调整
              return {
                x: centerX + 10,
                y: centerY + 5,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '南阳市':
              // 南阳市标签调整
              return {
                x: centerX - 10,
                y: centerY,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '信阳市':
              // 信阳市标签调整
              return {
                x: centerX + 10,
                y: centerY,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '周口市':
              // 周口市标签调整
              return {
                x: centerX + 5,
                y: centerY - 10,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '驻马店市':
              // 驻马店市标签调整
              return {
                x: centerX,
                y: centerY + 10,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '商丘市':
              // 商丘市标签调整
              return {
                x: centerX + 15,
                y: centerY,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '许昌市':
              // 许昌市标签调整
              return {
                x: centerX,
                y: centerY - 10,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '漯河市':
              // 漯河市标签调整
              return {
                x: centerX,
                y: centerY + 15,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '三门峡市':
              // 三门峡市标签调整
              return {
                x: centerX - 20,
                y: centerY,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '焦作市':
              // 焦作市标签调整
              return {
                x: centerX + 15,
                y: centerY,
                align: 'center',
                verticalAlign: 'middle'
              };
            case '济源市':
              // 济源市标签调整
              return {
                x: centerX - 10,
                y: centerY - 10,
                align: 'center',
                verticalAlign: 'middle'
              };
            default:
              // 其他城市使用默认中心位置
              return {
                x: centerX,
                y: centerY,
                align: 'center',
                verticalAlign: 'middle'
              };
          }
        }
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

// 处理筛选
const handleFilter = () => {
  console.log('筛选条件变更:', { categoryId: selectedCategoryId.value, regionId: selectedRegionId.value })
  // 这里可以添加筛选逻辑
}

// 处理窗口大小变化
const handleResize = () => {
  if (mapChart.value) {
    mapChart.value.resize()
  }
}

onMounted(() => {
  loadCategories()
  loadRegions()
  loadCategoryStatistics()
  loadCityStatistics()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  if (mapChart.value) {
    mapChart.value.dispose()
  }
})
</script>

<style scoped>
.map-section {
  margin-bottom: 60px;
  padding: 40px 0;
  width: 100%;
}

.map-section h2 {
  font-size: 2rem;
  margin-bottom: 30px;
  color: var(--primary-color);
  text-align: center;
  position: relative;
  padding-bottom: 15px;
}

.map-section h2::after {
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

.map-controls {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 30px;
  gap: 15px;
  flex-wrap: wrap;
}

.map-and-stats {
  display: flex;
  gap: 30px;
  align-items: stretch;
  flex-wrap: wrap;
}

.map-container {
  flex: 1;
  min-width: 300px;
  height: 550px;
  overflow: hidden;
  border: 1px solid var(--border-color);
  background-color: var(--card-background);
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.stats-container {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: repeat(5, 1fr);
  gap: 15px;
  width: 300px;
  height: 550px;
  padding: 20px;
  border: 1px solid var(--border-color);
  background-color: var(--card-background);
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
}

.stat-card {
  background: linear-gradient(135deg, var(--primary-color) 0%, #2C5282 100%);
  border-radius: 12px;
  padding: 15px;
  color: white;
  text-align: center;
  box-shadow: 0 4px 15px rgba(26, 54, 93, 0.3);
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 80px;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(26, 54, 93, 0.4);
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

.filter-item {
  min-width: 200px;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .map-and-stats {
    flex-direction: column;
    align-items: center;
  }
  
  .map-container {
    width: 100%;
    max-width: 800px;
  }
  
  .stats-container {
    width: 100%;
    max-width: 800px;
    height: auto;
    grid-template-columns: repeat(5, 1fr);
    grid-template-rows: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .map-section h2 {
    font-size: 1.8rem;
  }
  
  .stats-container {
    grid-template-columns: repeat(2, 1fr);
    grid-template-rows: repeat(5, 1fr);
  }
}
</style>