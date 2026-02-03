<template>
  <div class="heritage-view">
    <div class="container">
      <h1>河南省非物质文化遗产</h1>

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
})

onUnmounted(() => {
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
        // 只显示状态为1的项目
        heritageItems.value = (res.data || []).filter(item => item.status === 1)
        total.value = heritageItems.value.length
      } else {
        heritageItems.value = []
        total.value = 0
      }
    } else {
      res = await heritageApi.list(null, currentPage.value, pageSize.value)
      if (res && res.success) {
        // 只显示状态为1的项目
        heritageItems.value = (res.data || []).filter(item => item.status === 1)
        total.value = heritageItems.value.length
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
.heritage-view {
  padding: 20px;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
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
</style>