<template>
  <div class="home">
    <!-- 头部横幅 -->
    <div class="banner">
      <h1>河南非物质文化遗产数字化展示系统</h1>
      <p>{{ welcomeMessage }}</p>
    </div>

    <!-- 轮播图 -->
    <div class="carousel-section">
      <el-carousel :interval="5000" type="card" height="400px" indicator-position="outside" :autoplay="true">
        <el-carousel-item v-for="item in carouselList" :key="item.id">
          <div class="carousel-item">
            <img :src="item.imageUrl" :alt="item.title" class="carousel-image">
            <div class="carousel-caption">
              <h3>{{ item.title }}</h3>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- 河南非遗地图分布 -->
    <div class="map-section">
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

    <!-- 热门非遗项目 -->
    <div class="hot-heritage">
      <h2>热门非遗项目</h2>
      <div class="heritage-list">
        <div 
          v-for="item in heritageItems" 
          :key="item.id"
          class="heritage-item"
          @click="goToDetail(item.id)"
        >
          <div class="heritage-image">
            <img :src="item.coverImage || getDefaultCover(item)" :alt="item.title" />
          </div>
          <div class="heritage-info">
            <h3>{{ item.title }}</h3>
            <p class="category">{{ item.category?.name }}</p>
            <p class="region">{{ item.region?.name }}</p>
            <p class="description">{{ item.description?.substring(0, 100) || '' }}...</p>
          </div>
        </div>
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
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { heritageApi } from '../api/heritage'
import api from '../api/index.js'
import { ElSelect, ElOption, ElPagination, ElCarousel, ElCarouselItem } from 'element-plus'
import * as echarts from 'echarts'

const router = useRouter()
const heritageItems = ref([])
const carouselList = ref([])

// 非遗分类数据
const categories = ref([])

// 地区分布数据
const regions = ref([])

// 分页相关
const currentPage = ref(1)
const pageSize = ref(9) // 每页9项，三行三列
const total = ref(0)

// 地图相关
const henanGeoJson = ref(null)
const mapChart = ref(null)
const categoryStatistics = ref([])
const cityStatistics = ref([])

const welcomeMessage = computed(() => {
  try {
    const userStr = localStorage.getItem('user')
    if (userStr) {
      try {
        const user = JSON.parse(userStr)
        return `欢迎回来，${user.username}！一起探索河南非遗的魅力`
      } catch (e) {
        return '传承文化根脉，守护精神家园'
      }
    }
  } catch (e) {
    console.error('localStorage 访问失败:', e)
  }
  return '传承文化根脉，守护精神家园'
})

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

onMounted(() => {
  loadData()
  loadCarouselData()
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

const loadData = async () => {
  try {
    // 加载热门非遗项目（带分页）
    console.log('开始加载热门非遗项目...')
    console.log('currentPage:', currentPage.value)
    console.log('pageSize:', pageSize.value)
    const heritageRes = await heritageApi.list(1, currentPage.value, pageSize.value)
    console.log('heritageRes:', heritageRes)
    if (heritageRes && heritageRes.success) {
      console.log('heritageRes.data:', heritageRes.data)
      console.log('heritageRes.total:', heritageRes.total)
      heritageItems.value = heritageRes.data || []
      total.value = heritageRes.total || 0
    } else {
      console.log('API 调用失败或返回的数据结构不正确')
      heritageItems.value = []
      total.value = 0
    }
    
    // 加载地图相关数据
    console.log('开始加载地图相关数据...')
    // 加载分类统计数据
    await loadCategoryStatistics()
    // 加载地市统计数据
    await loadCityStatistics()
  } catch (error) {
    console.error('加载数据失败:', error)
    heritageItems.value = []
    total.value = 0
  }
}



// 分页方法
const handleCurrentChange = (current) => {
  currentPage.value = current
  loadData()
}

const goToDetail = (id) => {
  if (id) {
    router.push(`/heritage/${id}`)
  }
}

// 加载轮播图数据
const loadCarouselData = async () => {
  try {
    console.log('开始加载轮播图数据...')
    const response = await fetch('/api/carousel/enabled-list')
    console.log('轮播图API响应:', response)
    if (response.ok) {
      const data = await response.json()
      console.log('轮播图数据:', data)
      if (data && data.success) {
        // 过滤出状态为1的轮播图
        carouselList.value = (data.data || []).filter(item => item.status === 1)
        console.log('过滤后的轮播图列表:', carouselList.value)
      } else {
        console.error('轮播图数据加载失败:', data)
        carouselList.value = []
      }
    } else {
      console.error('轮播图API调用失败:', response.status)
      carouselList.value = []
    }
  } catch (error) {
    console.error('加载轮播图数据失败:', error)
    carouselList.value = []
  }
}

// 加载分类统计数据
const loadCategoryStatistics = async () => {
  try {
    console.log('开始加载分类统计数据...')
    // 从后端API获取分类统计数据
    const response = await fetch('http://localhost:8081/api/statistics/category')
    console.log('分类统计API响应:', response)
    if (response.ok) {
      const data = await response.json()
      console.log('分类统计数据:', data)
      if (data && data.success && Array.isArray(data.data)) {
        categoryStatistics.value = data.data
        console.log('分类统计数据加载完成:', categoryStatistics.value)
      } else {
        console.warn('分类统计数据格式不正确，显示空数据')
        categoryStatistics.value = []
      }
    } else {
      console.error('获取分类统计数据失败:', response.status)
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
    const response = await fetch('http://localhost:8081/api/statistics/city')
    console.log('地市统计API响应:', response)
    if (response.ok) {
      const data = await response.json()
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
    } else {
      console.error('获取地市统计数据失败:', response.status)
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
        restore: {},
        saveAsImage: {}
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

// 处理窗口大小变化
const handleResize = () => {
  if (mapChart.value) {
    mapChart.value.resize()
  }
}

// 为没有封面图片的项目生成独特的默认封面
const getDefaultCover = (item) => {
  // 构建基于项目信息的prompt
  let prompt = `traditional Chinese culture heritage ${encodeURIComponent(item.title)}`
  if (item.categoryName) {
    prompt += ` ${encodeURIComponent(item.categoryName)}`
  }
  if (item.regionName) {
    prompt += ` ${encodeURIComponent(item.regionName)}`
  }
  prompt += ` elegant artistic traditional style high quality`
  
  // 构建图片生成API URL
  return `https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=${prompt}&image_size=portrait_4_3`
}


</script>

<style scoped>
.home {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
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
  border: 1px solid #ddd;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  padding: 10px;
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

.banner {
  background: #1F3D3A;
  color: #FFFFFF;
  text-align: center;
  padding: 60px 20px;
  border-radius: 10px;
  margin-bottom: 40px;
}

.banner h1 {
  font-size: 2.5rem;
  margin-bottom: 10px;
  color: #FFFFFF;
}

.banner p {
  font-size: 1.2rem;
  opacity: 0.9;
  color: #FFFFFF;
}

/* 轮播图样式 */
.carousel-section {
  margin-bottom: 40px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.carousel-item {
  position: relative;
  height: 100%;
  cursor: pointer;
  overflow: hidden;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.carousel-item:hover .carousel-image {
  transform: scale(1.05);
}

.carousel-caption {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(to top, rgba(0,0,0,0.8), rgba(0,0,0,0));
  color: white;
  padding: 30px;
  transition: all 0.3s ease;
}

.carousel-caption h3 {
  font-size: 1.5rem;
  margin-bottom: 10px;
  color: #FFFFFF;
}

.carousel-caption p {
  font-size: 1rem;
  opacity: 0.9;
  color: #FFFFFF;
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
  flex-direction: column;
  align-items: center;
  padding: 20px 0;
}

.nav-content .el-select {
  width: 200px;
}



.hot-heritage {
  margin-bottom: 40px;
}

.hot-heritage h2 {
  font-size: 1.8rem;
  margin-bottom: 20px;
  color: #2B2B2B;
  text-align: center;
}

.heritage-list {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin-bottom: 30px;
}

.heritage-item {
  background: white;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.3s ease;
  height: 100%;
}

.heritage-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
}

/* 分页样式 */
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 30px;
}

.heritage-image {
  height: 200px;
  overflow: hidden;
}

.heritage-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.heritage-item:hover .heritage-image img {
  transform: scale(1.1);
}

.heritage-info {
  padding: 15px;
}

.heritage-info h3 {
  font-size: 1.2rem;
  margin-bottom: 10px;
  color: #2B2B2B;
}

.heritage-info .category,
.heritage-info .region {
  font-size: 0.9rem;
  color: #555555;
  margin-bottom: 5px;
}

.heritage-info .description {
  font-size: 0.9rem;
  color: #888888;
  line-height: 1.4;
  margin-top: 10px;
}

@media (max-width: 1200px) {
  .heritage-list {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 1400px) {
  .home {
    max-width: 1000px;
  }
}

@media (max-width: 1200px) {
  .home {
    max-width: 900px;
  }
  
  .heritage-list {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .banner h1 {
    font-size: 2.2rem;
  }
  
  .banner p {
    font-size: 1.1rem;
  }
}

@media (max-width: 992px) {
  .home {
    max-width: 750px;
  }
  
  .nav-container {
    flex-direction: column;
    align-items: center;
  }
  
  .nav-section {
    width: 100%;
    max-width: 400px;
  }
}

@media (max-width: 768px) {
  .home {
    max-width: 100%;
    padding: 15px;
  }
  
  .banner {
    padding: 40px 15px;
  }
  
  .banner h1 {
    font-size: 1.8rem;
  }
  
  .banner p {
    font-size: 1rem;
  }
  
  .heritage-list {
    grid-template-columns: 1fr;
  }
  
  .nav-section h2 {
    font-size: 1.5rem;
  }
  
  .hot-heritage h2 {
    font-size: 1.5rem;
  }
  

}

@media (max-width: 576px) {
  .banner h1 {
    font-size: 1.5rem;
  }
  
  .banner p {
    font-size: 0.9rem;
  }
  
  .nav-section h2 {
    font-size: 1.3rem;
  }
  
  .hot-heritage h2 {
    font-size: 1.3rem;
  }
  
  .heritage-info h3 {
    font-size: 1.1rem;
  }
  
  .heritage-info .description {
    font-size: 0.85rem;
  }
}
</style>