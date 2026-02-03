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
            <img :src="item.coverImage" :alt="item.title" />
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
})

onUnmounted(() => {
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
        carouselList.value = data.data || []
        console.log('轮播图列表:', carouselList.value)
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


</script>

<style scoped>
.home {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
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