<template>
  <div class="heritage-view">
    <div class="container">
      <h1>河南省非物质文化遗产</h1>

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

      <!-- 分类和地区导航 -->
      <div class="nav-container">
        <!-- 分类导航 -->
        <div class="nav-section">
          <div class="nav-content">
            <h2>非遗分类</h2>
            <el-select v-model="categoryId" placeholder="选择分类" @change="handleFilter">
              <el-option label="全部" :value="null" />
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
              <el-option label="全部" :value="null" />
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
                <img :src="item.coverImage || getDefaultCover(item)" :alt="item.title" />
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
              v-model:page-size="pageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="handleSizeChange"
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

        <el-tab-pane label="多媒体展示" name="media">
          <!-- 图片展示 -->
          <div v-if="mediaImages.length > 0" class="section">
            <div class="section-header">
              <el-icon><Picture /></el-icon>
              <h2>非遗图片</h2>
            </div>
            <div class="media-grid">
              <div 
                v-for="(image, index) in mediaImages" 
                :key="image.id || index"
                class="media-card"
              >
                <div class="media-image-container">
                  <img :src="getImageUrl(image.mediaUrl)" :alt="image.description || '非遗图片'" class="media-image" />
                </div>
                <div class="media-card-content">
                  <h4>{{ image.description || `图片 ${index + 1}` }}</h4>
                  <p>{{ image.heritageTitle || '非遗项目' }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- 视频展示 -->
          <div v-if="mediaVideos.length > 0" class="section">
            <div class="section-header">
              <el-icon><VideoCamera /></el-icon>
              <h2>非遗视频</h2>
            </div>
            <div class="video-grid">
              <div 
                v-for="(video, index) in mediaVideos" 
                :key="video.id || index"
                class="video-card"
              >
                <div class="video-thumbnail">
                  <div class="play-button">
                    <el-icon><VideoCamera /></el-icon>
                  </div>
                  <span class="video-title">{{ video.description || `视频 ${index + 1}` }}</span>
                </div>
                <div class="video-card-content">
                  <p>{{ video.heritageTitle || '非遗项目' }}</p>
                </div>
              </div>
            </div>
          </div>

          <div v-if="mediaImages.length === 0 && mediaVideos.length === 0" class="empty-state">
            <el-empty description="暂无多媒体内容" />
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, onUnmounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { heritageApi } from '../api/api'
import api from '../api/index.js'
import { ElTabs, ElTabPane, ElSelect, ElOption, ElEmpty, ElTable, ElTableColumn, ElButton, ElPagination } from 'element-plus'
import { Picture, VideoCamera } from '@element-plus/icons-vue'
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

// 地图相关
const mapChart = ref(null)
const categoryStatistics = ref([])
const cityStatistics = ref([])
const henanGeoJson = ref(null)

// 媒体相关
const mediaItems = ref([])

// 计算属性：过滤图片
const mediaImages = computed(() => {
  return mediaItems.value.filter(item => item.mediaType === 'image')
})

// 计算属性：过滤视频
const mediaVideos = computed(() => {
  return mediaItems.value.filter(item => item.mediaType === 'video')
})

// 分页相关
const currentPage = ref(1)
const pageSize = ref(12) // 每页12项，四行三列
const total = ref(0)

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

// 加载媒体数据
const loadMediaItems = async () => {
  try {
    // 从所有非遗项目中提取媒体资源
    const allMedia = []
    
    // 遍历非遗项目，收集媒体资源
    for (const item of heritageItems.value) {
      if (item.mediaList && item.mediaList.length > 0) {
        item.mediaList.forEach(media => {
          allMedia.push({
            ...media,
            heritageTitle: item.title,
            heritageId: item.id
          })
        })
      }
    }
    
    mediaItems.value = allMedia
  } catch (error) {
    console.error('加载媒体数据失败:', error)
    mediaItems.value = []
  }
}

// 处理图片URL
const getImageUrl = (url) => {
  if (!url) return ''
  
  // 处理完整的HTTP URL
  if (url.startsWith('http://') || url.startsWith('https://')) {
    return url
  }
  
  // 处理本地文件路径
  if (url.includes('\\') || url.includes('D:') || url.includes('C:') || url.includes('E:')) {
    const fileName = url.split(/[\\/]/).pop()
    return `/uploads/${fileName}`
  }
  
  // 处理相对路径
  if (url.startsWith('/uploads/')) {
    return url
  }
  
  return ''
}

// 传承人分页相关
const currentInheritorPage = ref(1)
const inheritorPageSize = ref(20) // 每页20个传承人
const inheritorTotal = ref(0)

onMounted(() => {
  console.log('onMounted函数被调用')
  if (route.query.categoryId) {
    categoryId.value = parseInt(route.query.categoryId)
    console.log('设置categoryId:', categoryId.value)
  }
  if (route.query.regionId) {
    regionId.value = parseInt(route.query.regionId)
    console.log('设置regionId:', regionId.value)
  }
  if (route.query.tab) {
    activeTab.value = route.query.tab
    console.log('设置activeTab:', activeTab.value)
  }
  // 加载分类和地区数据
  console.log('准备调用loadCategories函数')
  loadCategories()
  console.log('准备调用loadRegions函数')
  loadRegions()
  console.log('准备调用loadData函数')
  loadData()
  // 加载地图数据
  console.log('准备调用loadCategoryStatistics函数')
  loadCategoryStatistics()
  console.log('准备调用loadCityStatistics函数')
  loadCityStatistics()
  // 添加窗口大小变化监听
  console.log('添加窗口大小变化监听')
  window.addEventListener('resize', handleResize)
  console.log('onMounted函数调用完成')
})

onUnmounted(() => {
  // 移除窗口大小变化监听
  window.removeEventListener('resize', handleResize)
  // 销毁地图实例
  if (mapChart.value) {
    mapChart.value.dispose()
  }
})

watch(() => route.query, () => {
  if (route.query.categoryId) {
    categoryId.value = parseInt(route.query.categoryId)
  } else {
    categoryId.value = null
  }
  if (route.query.regionId) {
    regionId.value = parseInt(route.query.regionId)
  } else {
    regionId.value = null
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
    console.log('加载媒体数据...')
    await loadMediaItems()
    console.log('加载传承人数据...')
    await loadInheritors()
    console.log('数据加载完成')
  } catch (error) {
    console.error('加载数据失败:', error)
  }
}

const loadHeritageItems = async () => {
  try {
    // 根据是否有筛选条件，调用不同的API方法
    let res
    // 检查是否有筛选条件（包括0值）
    const hasFilter = categoryId.value !== null || regionId.value !== null
    if (hasFilter) {
        console.log('调用筛选API，条件:', { categoryId: categoryId.value, regionId: regionId.value })
        res = await heritageApi.filter(categoryId.value, regionId.value, 1, currentPage.value, pageSize.value)
        console.log('筛选API响应:', res)
        if (res && res.code === 200) {
          heritageItems.value = res.data || []
          total.value = res.total || 0
          console.log('筛选结果:', heritageItems.value.length, '项，总数:', total.value)
          console.log('heritageItems内容:', heritageItems.value)
        } else {
          heritageItems.value = []
          total.value = 0
          console.log('筛选API调用失败:', res)
        }
      } else {
        res = await heritageApi.list(1, currentPage.value, pageSize.value)
        console.log('列表API响应:', res)
        if (res && res.code === 200) {
          heritageItems.value = res.data || []
          total.value = res.total || 0
          console.log('列表结果:', heritageItems.value.length, '项，总数:', total.value)
          console.log('heritageItems内容:', heritageItems.value)
        } else {
          heritageItems.value = []
          total.value = 0
          console.log('列表API调用失败:', res)
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

const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
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
    if (response && response.code === 200) {
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
  const query = {}
  if (categoryId.value !== null) {
    query.categoryId = categoryId.value
  }
  if (regionId.value !== null) {
    query.regionId = regionId.value
  }
  if (activeTab.value) {
    query.tab = activeTab.value
  }
  router.push({
    path: '/heritage',
    query: query
  })
}

const goToDetail = (id) => {
  console.log('点击了非遗项目，ID:', id)
  if (id) {
    router.push(`/heritage/${id}`)
  } else {
    console.error('ID不存在')
  }
}

const viewInheritorDetail = (row) => {
  router.push(`/inheritor/${row.id}`)
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

// 加载分类统计数据
const loadCategoryStatistics = async () => {
  try {
    console.log('开始加载分类统计数据...')
    const response = await api.get('/statistics/category')
    console.log('分类统计API响应:', response)
    if (response && response.code === 200 && Array.isArray(response.data)) {
      categoryStatistics.value = response.data
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
  console.log('loadCityStatistics函数被调用')
  try {
    console.log('开始加载地市统计数据...')
    const response = await api.get('/statistics/city')
    console.log('地市统计API响应:', response)
    if (response && response.code === 200 && Array.isArray(response.data)) {
      cityStatistics.value = response.data
      console.log('地市统计数据加载完成:', cityStatistics.value)
      // 直接调用initMap，不使用setTimeout
      console.log('准备调用initMap函数')
      await initMap()
      console.log('initMap函数调用完成')
    } else {
      console.warn('地市统计数据格式不正确，显示空数据')
      cityStatistics.value = []
      // 即使没有数据也初始化地图
      console.log('准备调用initMap函数（空数据）')
      await initMap()
      console.log('initMap函数调用完成（空数据）')
    }
  } catch (error) {
    console.error('加载地市统计数据失败:', error)
    cityStatistics.value = []
    // 即使出错也初始化地图
    console.log('准备调用initMap函数（错误）')
    await initMap()
    console.log('initMap函数调用完成（错误）')
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
  
  // 确保地图容器有足够的尺寸
  if (mapContainer.offsetWidth === 0 || mapContainer.offsetHeight === 0) {
    console.error('地图容器尺寸为0，无法初始化地图')
    return
  }

  // 清空容器并添加加载提示
  mapContainer.innerHTML = '<div style="text-align: center; font-size: 16px; color: #1890ff;">地图加载中...</div>'

  if (mapChart.value) {
    console.log('销毁现有地图实例')
    mapChart.value.dispose()
  }

  try {
    console.log('创建ECharts实例')
    mapChart.value = echarts.init(mapContainer)
    console.log('ECharts实例创建成功')
  } catch (error) {
    console.error('ECharts实例创建失败:', error)
    mapContainer.innerHTML = '<div style="text-align: center; font-size: 16px; color: #ff4d4f;">地图初始化失败</div>'
    return
  }

  try {
    console.log('开始加载地图数据...')
    if (!henanGeoJson.value) {
      try {
        const response = await fetch('/assets/json/henan.geojson')
        console.log('地图数据请求状态:', response.status)
        if (!response.ok) {
          throw new Error(`Failed to load map data: ${response.status}`)
        }
        henanGeoJson.value = await response.json()
        console.log('地图数据加载成功')
        console.log('地图数据类型:', typeof henanGeoJson.value)
        console.log('地图数据结构:', henanGeoJson.value.type)
      } catch (fetchError) {
        console.error('地图数据加载失败:', fetchError)
        mapContainer.innerHTML = '<div style="text-align: center; font-size: 16px; color: #ff4d4f;">地图数据加载失败</div>'
        return
      }
    }
    
    try {
      echarts.registerMap('henan', henanGeoJson.value)
      console.log('地图注册成功')
    } catch (registerError) {
      console.error('地图注册失败:', registerError)
      mapContainer.innerHTML = '<div style="text-align: center; font-size: 16px; color: #ff4d4f;">地图注册失败</div>'
      return
    }
    
    // 初始化地图数据
    const mapData = cityStatistics.value.map(item => {
      return {
        name: item.city,
        value: item.count
      };
    })

    console.log('地图数据:', mapData)

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
          zoom: 1.2,
          center: [113.8, 33.8],
          roam: true
        }
      ]
    }

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

    try {
      console.log('设置地图选项')
      console.log('地图数据长度:', mapData.length)
      console.log('地图选项:', option)
      const result = mapChart.value.setOption(option)
      console.log('setOption返回值:', result)
      console.log('地图配置成功')
      // 强制刷新地图
      mapChart.value.resize()
      console.log('地图大小调整成功')
      // 添加窗口大小变化监听
      window.addEventListener('resize', () => {
        if (mapChart.value) {
          mapChart.value.resize()
        }
      })
    } catch (error) {
      console.error('地图配置失败:', error)
      mapContainer.innerHTML = '<div style="text-align: center; font-size: 16px; color: #ff4d4f;">地图配置失败</div>'
    }
  } catch (error) {
    console.error('地图初始化失败:', error)
    mapContainer.innerHTML = '<div style="text-align: center; font-size: 16px; color: #ff4d4f;">地图初始化失败</div>'
    return
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
  width: 100%;
  padding: 20px;
  background-color: var(--background-color);
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
}

.container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.container h1 {
  font-size: 2rem;
  margin-bottom: 30px;
  color: var(--primary-color);
  text-align: center;
  position: relative;
  padding-bottom: 15px;
}

.container h1::after {
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
  color: var(--primary-color);
  text-align: center;
  position: relative;
  padding-bottom: 10px;
}

.nav-section h2::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 60px;
  height: 3px;
  background-color: var(--accent-color);
  border-radius: 2px;
}

.nav-content {
  display: flex;
  align-items: center;
  padding: 20px 0;
  gap: 20px;
  flex-direction: column;
}

.nav-content h2 {
  font-size: 1.2rem;
  margin: 0;
  color: var(--primary-color);
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
  width: 100%;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 25px;
  margin-bottom: 30px;
  box-sizing: border-box;
  padding: 0;
}

.inheritor-table {
  margin-top: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  overflow: hidden;
  width: 100%;
  max-width: 100%;
  box-sizing: border-box;
  padding: 0;
}

/* 分页样式 */
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 40px;
}

.heritage-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  z-index: 1;
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

/* 确保el-tabs组件内部内容宽度一致 */
.el-tabs {
  width: 100%;
  box-sizing: border-box;
}

.el-tabs__content {
  width: 100%;
  box-sizing: border-box;
  padding: 0;
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
}

@media (max-width: 1200px) {
  .container {
    max-width: 900px;
  }
  
  .heritage-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .container h1 {
    font-size: 1.8rem;
  }
}

@media (max-width: 992px) {
  .container {
    max-width: 750px;
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
  
  .heritage-view {
    padding: 10px;
  }
}

/* 地图区域样式 */
.map-section {
  margin-bottom: 40px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  width: 100%;
  padding: 20px;
}

.map-and-stats {
  display: flex;
  gap: 20px;
  min-height: 500px;
  width: 100%;
}

.map-container {
  flex: 1;
  min-height: 500px;
  height: 500px;
  background: white;
  border: none;
  border-radius: 10px;
  overflow: hidden;
  width: 100%;
}

.stats-container {
  width: 280px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
  padding: 10px;
  background: #f9f9f9;
  border-radius: 10px;
  overflow-y: auto;
  max-height: 500px;
}

.stat-card {
  background: white;
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  text-align: center;
  transition: all 0.3s ease;
  height: 80px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
}

.stat-number {
  font-size: 1.5rem;
  font-weight: bold;
  color: #667eea;
  margin-bottom: 2px;
}

.stat-name {
  font-size: 0.8rem;
  color: #666;
  line-height: 1.2;
}

@media (max-width: 992px) {
  .map-and-stats {
    flex-direction: column;
  }
  
  .stats-container {
    width: 100%;
    flex-direction: row;
    flex-wrap: wrap;
    max-height: none;
  }
  
  .stat-card {
    flex: 1;
    min-width: 120px;
  }
}

@media (max-width: 576px) {
  .stat-card {
    min-width: 100px;
  }
  
  .stat-number {
    font-size: 1.5rem;
  }
  
  .stat-name {
    font-size: 0.8rem;
  }
}

/* 媒体展示样式 */
.section {
  margin-bottom: 30px;
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
}

.section-header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 2px solid var(--primary-color);
}

.section-header h2 {
  font-size: 1.3rem;
  color: var(--primary-color);
  margin: 0;
}

.media-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
}

.media-card {
  background: white;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.media-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
}

.media-image-container {
  height: 200px;
  overflow: hidden;
}

.media-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.media-card:hover .media-image {
  transform: scale(1.05);
}

.media-card-content {
  padding: 15px;
}

.media-card-content h4 {
  margin: 0 0 10px 0;
  font-size: 1.1rem;
  color: #333;
}

.media-card-content p {
  margin: 0;
  font-size: 0.9rem;
  color: #666;
}

.video-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
}

.video-card {
  background: white;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.video-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
}

.video-thumbnail {
  height: 150px;
  background: #333;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  cursor: pointer;
}

.play-button {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 60px;
  height: 60px;
  background: rgba(0, 0, 0, 0.6);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  transition: all 0.3s ease;
}

.video-thumbnail:hover .play-button {
  background: rgba(0, 0, 0, 0.8);
  transform: translate(-50%, -50%) scale(1.1);
}

.video-title {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 10px;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.8), transparent);
  font-size: 0.9rem;
  text-align: center;
}

.video-card-content {
  padding: 15px;
}

.video-card-content p {
  margin: 0;
  font-size: 0.9rem;
  color: #666;
}

@media (max-width: 768px) {
  .media-grid,
  .video-grid {
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  }
  
  .media-image-container {
    height: 150px;
  }
  
  .video-thumbnail {
    height: 120px;
  }
  
  .section {
    padding: 15px;
  }
  
  .section-header h2 {
    font-size: 1.1rem;
  }
}
</style>