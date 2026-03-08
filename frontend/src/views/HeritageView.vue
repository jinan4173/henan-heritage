<template>
  <div class="heritage-view">
    <!-- 顶部横幅 -->
    <div class="hero-section">
      <div class="hero-content">
        <h1>河南省非物质文化遗产</h1>
        <p>探索河南丰富的非物质文化遗产资源，感受传统文化的魅力</p>
        <div class="hero-actions">
          <router-link to="/heritage?tab=heritage" class="hero-btn hero-btn-primary">浏览项目</router-link>
          <router-link to="/heritage?tab=inheritor" class="hero-btn hero-btn-secondary">了解传承人</router-link>
        </div>
      </div>
    </div>

    <div class="container">
      <!-- 河南非遗地图分布 -->
      <div class="map-section scroll-reveal">
        <div class="section-header">
          <h2>非遗项目地理分布</h2>
          <p>探索河南省各地区的非遗项目分布情况</p>
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
              :style="{ animationDelay: `${index * 0.1}s` }"
            >
              <div class="stat-number">{{ item.count }}</div>
              <div class="stat-name">{{ item.name }}</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 分类和地区导航 -->
      <div class="nav-container scroll-reveal">
        <div class="nav-content-wrapper">
          <!-- 分类导航 -->
          <div class="nav-section">
            <h3>非遗分类</h3>
            <el-select v-model="categoryId" placeholder="选择分类" @change="handleFilter" class="nav-select">
              <el-option label="全部" :value="null" />
              <el-option 
                v-for="category in categories" 
                :key="category.id"
                :label="category.name" 
                :value="category.id"
              />
            </el-select>
          </div>

          <!-- 地区导航 -->
          <div class="nav-section">
            <h3>地区分布</h3>
            <el-select v-model="regionId" placeholder="选择地区" @change="handleFilter" class="nav-select">
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

      <el-tabs v-model="activeTab" type="border-card" class="tabs-container scroll-reveal">
        <el-tab-pane label="非遗项目" name="heritage">
          <div class="tab-content">
            <div class="heritage-grid">
              <div 
                v-for="(item, index) in heritageItems" 
                :key="item.id"
                class="heritage-card"
                @click.stop="goToDetail(item.id)"
                :style="{ animationDelay: `${index * 0.1}s` }"
              >
                <div class="card-image">
                  <img :src="item.coverImage || getDefaultCover(item)" :alt="item.title" />
                  <div class="card-overlay">
                    <span class="view-details">查看详情</span>
                  </div>
                </div>
                <div class="card-content">
                  <div class="card-header">
                    <h3>{{ item.title }}</h3>
                  </div>
                  <div class="card-meta">
                    <span class="meta-item category-tag">{{ item.category?.name || item.categoryName }}</span>
                    <span class="meta-item region-tag">{{ item.region?.name || item.declarationRegion }}</span>
                  </div>
                  <p class="card-description">{{ item.description?.substring(0, 120) || '' }}...</p>
                  <div class="card-footer">
                    <div class="card-date">
                      <i class="el-icon-date"></i>
                      <span>{{ item.declarationDate || '2024-01-01' }}</span>
                    </div>
                    <div class="card-view">
                      <i class="el-icon-view"></i>
                      <span>{{ item.viewCount || 0 }} 浏览</span>
                    </div>
                  </div>
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
          </div>
        </el-tab-pane>

        <el-tab-pane label="传承人" name="inheritor">
          <div class="tab-content">
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
import { ElTabs, ElTabPane, ElSelect, ElOption, ElTable, ElTableColumn, ElButton, ElPagination } from 'element-plus'
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
  // 添加滚动触发动画监听
  console.log('添加滚动触发动画监听')
  window.addEventListener('scroll', handleScroll)
  // 初始检查
  handleScroll()
  console.log('onMounted函数调用完成')
})

onUnmounted(() => {
  // 移除窗口大小变化监听
  window.removeEventListener('resize', handleResize)
  // 移除滚动触发动画监听
  window.removeEventListener('scroll', handleScroll)
  // 销毁地图实例
  if (mapChart.value) {
    mapChart.value.dispose()
  }
})

// 处理滚动触发动画
const handleScroll = () => {
  const elements = document.querySelectorAll('.scroll-reveal')
  elements.forEach(element => {
    const elementTop = element.getBoundingClientRect().top
    const windowHeight = window.innerHeight
    if (elementTop < windowHeight - 100) {
      element.classList.add('revealed')
    }
  })
}

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
  console.log('ID类型:', typeof id)
  console.log('ID值:', id)
  if (id) {
    console.log('准备跳转到:', `/heritage/${id}`)
    router.push(`/heritage/${id}`).then(() => {
      console.log('跳转成功')
    }).catch((error) => {
      console.error('跳转失败:', error)
    })
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
/* 全局样式 */
.heritage-view {
  width: 100%;
  background-color: var(--background-color);
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
}

/* 顶部横幅 */
.hero-section {
  width: 100%;
  background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
  color: white;
  padding: 120px 20px;
  text-align: center;
  position: relative;
  overflow: hidden;
  margin-bottom: 40px;
}

.hero-section::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url('https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=henan%20chinese%20traditional%20pattern%20background%20subtle%20cultural%20heritage&image_size=landscape_16_9') center/cover;
  opacity: 0.1;
  z-index: 1;
  animation: backgroundMove 20s ease-in-out infinite;
}

/* 装饰元素 */
.hero-section::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: 
    radial-gradient(circle at 10% 20%, rgba(255, 255, 255, 0.1) 0%, transparent 20%),
    radial-gradient(circle at 90% 80%, rgba(255, 255, 255, 0.1) 0%, transparent 20%),
    radial-gradient(circle at 50% 50%, rgba(255, 255, 255, 0.05) 0%, transparent 30%);
  z-index: 1;
}

.hero-content {
  position: relative;
  z-index: 2;
  max-width: 800px;
  margin: 0 auto;
  animation: fadeInUp 1s ease;
}

.hero-content h1 {
  font-size: 3.5rem;
  margin-bottom: 20px;
  font-weight: 600;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
  background: linear-gradient(45deg, #fff, rgba(255, 255, 255, 0.8));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  animation: textGlow 3s ease-in-out infinite alternate;
  position: relative;
  display: inline-block;
  padding: 0 20px;
}

.hero-content h1::before,
.hero-content h1::after {
  content: '❖';
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  font-size: 1.5rem;
  color: rgba(255, 255, 255, 0.8);
  animation: pulse 2s ease-in-out infinite;
}

.hero-content h1::before {
  left: -30px;
}

.hero-content h1::after {
  right: -30px;
}

.hero-content p {
  font-size: 1.3rem;
  opacity: 0.9;
  margin-bottom: 30px;
  animation: fadeInUp 1s ease 0.2s both;
  position: relative;
  padding: 0 40px;
}

.hero-content p::before,
.hero-content p::after {
  content: '';
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 30px;
  height: 2px;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.6), transparent);
}

.hero-content p::before {
  left: 0;
}

.hero-content p::after {
  right: 0;
}

/* 按钮容器 */
.hero-actions {
  margin-top: 40px;
  display: flex;
  gap: 20px;
  justify-content: center;
  flex-wrap: wrap;
  animation: fadeInUp 1s ease 0.4s both;
}

.hero-btn {
  padding: 12px 30px;
  border-radius: 30px;
  font-size: 1.1rem;
  font-weight: 500;
  text-decoration: none;
  transition: all var(--transition-normal);
  position: relative;
  overflow: hidden;
  z-index: 1;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.hero-btn-primary {
  background: white;
  color: var(--primary-color);
}

.hero-btn-secondary {
  background: transparent;
  color: white;
  border: 2px solid white;
}

.hero-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
  transition: left var(--transition-slow);
  z-index: -1;
}

.hero-btn:hover::before {
  left: 100%;
}

.hero-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
}

.hero-btn-primary:hover {
  background: #f8f9fa;
  color: var(--accent-color);
}

.hero-btn-secondary:hover {
  background: white;
  color: var(--primary-color);
}

/* 动画效果 */
@keyframes backgroundMove {
  0%, 100% {
    transform: scale(1) translate(0, 0);
  }
  50% {
    transform: scale(1.05) translate(0, 5px);
  }
}

@keyframes textGlow {
  0% {
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3), 0 0 20px rgba(255, 255, 255, 0.2);
  }
  100% {
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3), 0 0 30px rgba(255, 255, 255, 0.4);
  }
}

@keyframes pulse {
  0%, 100% {
    transform: translateY(-50%) scale(1);
    opacity: 0.8;
  }
  50% {
    transform: translateY(-50%) scale(1.2);
    opacity: 1;
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .hero-section {
    padding: 80px 20px;
  }
  
  .hero-content h1 {
    font-size: 2.5rem;
    padding: 0 10px;
  }
  
  .hero-content h1::before,
  .hero-content h1::after {
    font-size: 1.2rem;
    left: -20px;
    right: -20px;
  }
  
  .hero-content p {
    font-size: 1.1rem;
    padding: 0 20px;
  }
  
  .hero-actions {
    flex-direction: column;
    align-items: center;
  }
  
  .hero-btn {
    width: 200px;
    text-align: center;
  }
}

@media (max-width: 576px) {
  .hero-section {
    padding: 60px 15px;
  }
  
  .hero-content h1 {
    font-size: 2rem;
  }
  
  .hero-content h1::before,
  .hero-content h1::after {
    display: none;
  }
  
  .hero-content p {
    font-size: 1rem;
    padding: 0 10px;
  }
  
  .hero-content p::before,
  .hero-content p::after {
    width: 20px;
  }
}

/* 容器 */
.container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0 20px;
  box-sizing: border-box;
}

/* 滚动触发动画 */
.scroll-reveal {
  opacity: 0;
  transform: translateY(30px);
  transition: all 0.8s ease;
}

.scroll-reveal.revealed {
  opacity: 1;
  transform: translateY(0);
}

/* 地图区域 */
.map-section {
  margin-bottom: 60px;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: var(--shadow-lg);
  width: 100%;
  padding: 40px;
  background: var(--card-background);
  border: 1px solid var(--border-color);
  position: relative;
  overflow: hidden;
}

.map-section::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 4px;
  background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
}

.map-section .section-header {
  text-align: center;
  margin-bottom: 40px;
  position: relative;
  z-index: 1;
}

.map-section .section-header h2 {
  font-size: 2rem;
  color: var(--primary-color);
  margin-bottom: 15px;
  position: relative;
  display: inline-block;
  padding-bottom: 15px;
  font-weight: 600;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.map-section .section-header h2::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 100px;
  height: 3px;
  background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
  border-radius: 2px;
  animation: pulse 2s ease-in-out infinite;
}

.map-section .section-header p {
  color: var(--light-text);
  font-size: 1.1rem;
  margin: 0;
  max-width: 600px;
  margin: 0 auto;
}

.map-and-stats {
  display: flex;
  gap: 30px;
  min-height: 500px;
  width: 100%;
  align-items: flex-start;
  position: relative;
  z-index: 1;
}

.map-container {
  flex: 1;
  min-height: 500px;
  height: 500px;
  background: white;
  border: none;
  border-radius: 16px;
  overflow: hidden;
  width: 100%;
  box-shadow: var(--shadow-md);
  position: relative;
  transition: all var(--transition-normal);
}

.map-container:hover {
  box-shadow: var(--shadow-lg);
  transform: translateY(-2px);
}

.stats-container {
  width: 320px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  padding: 24px;
  background: rgba(0, 102, 204, 0.05);
  border-radius: 16px;
  overflow-y: auto;
  max-height: 500px;
  box-shadow: var(--shadow-sm);
  border: 1px solid rgba(0, 102, 204, 0.1);
  position: relative;
}

.stats-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 4px;
  background: linear-gradient(90deg, var(--accent-color), var(--primary-color));
}

.stat-card {
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: var(--shadow-sm);
  text-align: center;
  transition: all var(--transition-normal);
  height: 100px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  animation: fadeInUp 0.6s ease;
  position: relative;
  overflow: hidden;
  border: 1px solid var(--border-color);
  cursor: pointer;
}

.stat-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 3px;
  background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
  transform: scaleX(0);
  transition: transform var(--transition-normal);
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: var(--shadow-md);
  border-color: var(--accent-color);
}

.stat-card:hover::before {
  transform: scaleX(1);
}

.stat-number {
  font-size: 2rem;
  font-weight: bold;
  color: var(--accent-color);
  margin-bottom: 8px;
  transition: color var(--transition-normal);
  font-family: 'Arial', sans-serif;
}

.stat-card:hover .stat-number {
  color: var(--primary-color);
  transform: scale(1.05);
  transition: transform var(--transition-normal);
}

.stat-name {
  font-size: 0.9rem;
  color: var(--text-color);
  line-height: 1.3;
  font-weight: 500;
  transition: color var(--transition-normal);
}

.stat-card:hover .stat-name {
  color: var(--accent-color);
}

/* 地图加载动画 */
.map-loading {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  color: var(--accent-color);
}

.map-loading .loading-spinner {
  width: 40px;
  height: 40px;
  border: 3px solid rgba(0, 102, 204, 0.2);
  border-top: 3px solid var(--accent-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

.map-loading .loading-text {
  font-size: 1rem;
  font-weight: 500;
}

/* 响应式设计 */
@media (max-width: 1200px) {
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
    min-width: 150px;
  }
}

@media (max-width: 768px) {
  .map-section {
    padding: 30px 20px;
  }
  
  .map-container {
    height: 400px;
    min-height: 400px;
  }
  
  .stats-container {
    gap: 15px;
    padding: 20px;
  }
  
  .stat-card {
    padding: 15px;
    height: 90px;
  }
  
  .stat-number {
    font-size: 1.8rem;
  }
  
  .stat-name {
    font-size: 0.85rem;
  }
}

@media (max-width: 576px) {
  .map-section {
    padding: 20px 15px;
  }
  
  .map-container {
    height: 350px;
    min-height: 350px;
  }
  
  .stats-container {
    grid-template-columns: 1fr;
  }
  
  .stat-card {
    min-width: 100%;
  }
}

/* 动画效果 */
@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

@keyframes pulse {
  0%, 100% {
    opacity: 1;
    transform: translateX(-50%) scaleX(1);
  }
  50% {
    opacity: 0.8;
    transform: translateX(-50%) scaleX(1.1);
  }
}

/* 导航容器 */
.nav-container {
  margin-bottom: 60px;
  width: 100%;
}

.nav-content-wrapper {
  display: flex;
  gap: 40px;
  justify-content: center;
  flex-wrap: wrap;
  background: var(--card-background);
  padding: 30px;
  border-radius: 16px;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-color);
}

.nav-section {
  flex: 1;
  min-width: 300px;
  max-width: 400px;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.nav-section h3 {
  font-size: 1.2rem;
  color: var(--primary-color);
  margin: 0;
  font-weight: 600;
}

.nav-select {
  width: 100%;
  border-radius: 8px;
}

/* 标签页容器 */
.tabs-container {
  width: 100%;
  margin-bottom: 40px;
  border-radius: 16px !important;
  overflow: hidden;
  box-shadow: var(--shadow-lg);
  border: 1px solid var(--border-color) !important;
}

.tab-content {
  padding: 30px;
}

/* 非遗项目网格 */
.heritage-grid {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
  margin-bottom: 40px;
  box-sizing: border-box;
  padding: 0;
}

.heritage-card {
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: var(--shadow-md);
  cursor: pointer;
  transition: all var(--transition-normal);
  position: relative;
  z-index: 1;
  animation: fadeInUp 0.6s ease;
  border: 1px solid var(--border-color);
  transform-style: preserve-3d;
  perspective: 1000px;
  pointer-events: auto;
}

.heritage-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 4px;
  background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
  transform: scaleX(0);
  transition: transform var(--transition-normal);
}

.heritage-card:hover {
  transform: translateY(-10px) rotateX(2deg);
  box-shadow: var(--shadow-lg);
}

.heritage-card:hover::before {
  transform: scaleX(1);
}

.card-image {
  height: 240px;
  overflow: hidden;
  position: relative;
  border-radius: 16px 16px 0 0;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-slow);
}

.heritage-card:hover .card-image img {
  transform: scale(1.1) rotate(2deg);
}

.card-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.8), transparent);
  display: flex;
  align-items: flex-end;
  justify-content: center;
  padding: 20px;
  opacity: 0;
  transition: opacity var(--transition-normal);
  border-radius: 16px 16px 0 0;
}

.heritage-card:hover .card-overlay {
  opacity: 1;
}

.view-details {
  background: var(--accent-color);
  color: white;
  padding: 10px 24px;
  border-radius: 30px;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all var(--transition-normal);
  box-shadow: 0 4px 12px rgba(0, 102, 204, 0.3);
  position: relative;
  overflow: hidden;
  z-index: 1;
}

.view-details::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
  transition: left var(--transition-slow);
  z-index: -1;
}

.view-details:hover::before {
  left: 100%;
}

.heritage-card:hover .view-details {
  background: var(--primary-color);
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(138, 26, 26, 0.4);
}

.card-content {
  padding: 24px;
  position: relative;
  z-index: 1;
}

.card-header {
  margin-bottom: 16px;
  position: relative;
}

.card-header h3 {
  font-size: 1.4rem;
  margin: 0;
  color: var(--text-color);
  font-weight: 600;
  line-height: 1.3;
  transition: color var(--transition-normal);
  position: relative;
  display: inline-block;
  padding-bottom: 8px;
}

.card-header h3::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 40px;
  height: 2px;
  background: var(--accent-color);
  transform: scaleX(0);
  transition: transform var(--transition-normal);
}

.heritage-card:hover .card-header h3 {
  color: var(--accent-color);
}

.heritage-card:hover .card-header h3::after {
  transform: scaleX(1);
}

.card-meta {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
  flex-wrap: wrap;
}

.meta-item {
  padding: 6px 16px;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 500;
  transition: all var(--transition-normal);
  position: relative;
  overflow: hidden;
  z-index: 1;
}

.meta-item::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
  transition: left var(--transition-slow);
  z-index: -1;
}

.meta-item:hover::before {
  left: 100%;
}

.category-tag {
  background: rgba(138, 26, 26, 0.1);
  color: var(--primary-color);
  border: 1px solid rgba(138, 26, 26, 0.2);
}

.category-tag:hover {
  background: rgba(138, 26, 26, 0.2);
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(138, 26, 26, 0.2);
}

.region-tag {
  background: rgba(0, 102, 204, 0.1);
  color: var(--accent-color);
  border: 1px solid rgba(0, 102, 204, 0.2);
}

.region-tag:hover {
  background: rgba(0, 102, 204, 0.2);
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 102, 204, 0.2);
}

.card-description {
  font-size: 1rem;
  color: var(--light-text);
  line-height: 1.6;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  transition: color var(--transition-normal);
}

.heritage-card:hover .card-description {
  color: var(--text-color);
}

/* 卡片底部装饰 */
.card-footer {
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px solid var(--border-color);
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.85rem;
  color: var(--light-text);
}

.card-date {
  display: flex;
  align-items: center;
  gap: 4px;
}

.card-date i {
  font-size: 12px;
}

.card-view {
  display: flex;
  align-items: center;
  gap: 4px;
  transition: color var(--transition-normal);
}

.card-view i {
  font-size: 12px;
}

.heritage-card:hover .card-view {
  color: var(--accent-color);
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .heritage-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .heritage-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  
  .card-image {
    height: 200px;
  }
  
  .card-content {
    padding: 20px;
  }
  
  .card-header h3 {
    font-size: 1.2rem;
  }
  
  .meta-item {
    font-size: 0.8rem;
    padding: 4px 12px;
  }
  
  .card-description {
    font-size: 0.9rem;
  }
  
  .view-details {
    padding: 8px 20px;
    font-size: 0.85rem;
  }
}

/* 传承人表格 */
.inheritor-table {
  margin-top: 20px;
  box-shadow: var(--shadow-md);
  border-radius: 12px;
  overflow: hidden;
  width: 100%;
  max-width: 100%;
  box-sizing: border-box;
  padding: 0;
  background: white;
  border: 1px solid var(--border-color);
}

.inheritor-table .el-table {
  width: 100%;
  max-width: 100%;
  border-radius: 12px;
  overflow: hidden;
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

.inheritor-table .el-table th {
  background-color: var(--primary-color);
  color: white;
  font-weight: 600;
  font-size: 0.9rem;
}

.inheritor-table .el-table tr:hover {
  background-color: rgba(0, 102, 204, 0.05);
}

/* 分页样式 */
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 40px;
}

.pagination .el-pagination__item {
  border-radius: 4px;
  transition: all var(--transition-normal);
}

.pagination .el-pagination__item:hover {
  color: var(--accent-color);
  border-color: var(--accent-color);
}

.pagination .el-pagination__item.is-current {
  background-color: var(--accent-color);
  border-color: var(--accent-color);
}

/* 空状态 */
.empty-state {
  margin: 100px 0;
  text-align: center;
}

/* 媒体展示样式 */
.section {
  margin-bottom: 40px;
  padding: 30px;
  background: rgba(0, 102, 204, 0.05);
  border-radius: 16px;
  border: 1px solid var(--border-color);
}

.section-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 2px solid var(--primary-color);
}

.section-header h3 {
  font-size: 1.4rem;
  color: var(--primary-color);
  margin: 0;
  font-weight: 600;
}



/* 响应式设计 */
@media (max-width: 1200px) {
  .container {
    max-width: 950px;
  }
  
  .heritage-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .hero-content h1 {
    font-size: 2.5rem;
  }
  
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
    min-width: 140px;
  }
}

@media (max-width: 992px) {
  .container {
    max-width: 750px;
  }
  
  .hero-section {
    padding: 80px 20px;
  }
  
  .hero-content h1 {
    font-size: 2.2rem;
  }
  
  .nav-content-wrapper {
    flex-direction: column;
    align-items: center;
    gap: 20px;
  }
  
  .nav-section {
    width: 100%;
    max-width: 500px;
  }
  
  .tab-content {
    padding: 20px;
  }
}

@media (max-width: 768px) {
  .container {
    padding: 0 15px;
  }
  
  .hero-section {
    padding: 60px 15px;
    margin-bottom: 30px;
  }
  
  .hero-content h1 {
    font-size: 1.8rem;
  }
  
  .hero-content p {
    font-size: 1rem;
  }
  
  .map-section {
    padding: 20px;
    margin-bottom: 40px;
  }
  
  .map-container {
    height: 400px;
    min-height: 400px;
  }
  
  .heritage-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  
  .card-image {
    height: 200px;
  }
  
  .card-content {
    padding: 20px;
  }
  

  
  .section {
    padding: 20px;
  }
  
  .nav-content-wrapper {
    padding: 20px;
  }
}

@media (max-width: 576px) {
  .hero-content h1 {
    font-size: 1.6rem;
  }
  
  .map-section .section-header h2 {
    font-size: 1.5rem;
  }
  
  .card-header h3 {
    font-size: 1.2rem;
  }
  
  .card-meta {
    flex-direction: column;
    gap: 8px;
  }
  
  .meta-item {
    font-size: 0.8rem;
  }
  
  .card-description {
    font-size: 0.9rem;
  }
  
  .section-header h3 {
    font-size: 1.2rem;
  }
  
  .media-card-content h4 {
    font-size: 1.1rem;
  }
  
  .video-title {
    font-size: 0.9rem;
  }
  
  .tab-content {
    padding: 15px;
  }
}

/* 动画效果 */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>