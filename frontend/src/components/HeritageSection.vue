<template>
  <div class="hot-heritage">
    <h2>热门非遗项目</h2>
    
    <div class="heritage-list">
      <HeritageCard 
        v-for="item in heritageItems" 
        :key="item.id"
        :heritage="item"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { heritageApi } from '../api/api'
import HeritageCard from './HeritageCard.vue'

const heritageItems = ref([])

// 分页相关
const currentPage = ref(1)
const pageSize = ref(6) // 每页6项，两行三列
const total = ref(0)

// 筛选相关
const selectedCategoryId = ref(null)
const selectedRegionId = ref(null)

const loadData = async () => {
  try {
    // 加载热门非遗项目（带分页和筛选）
    console.log('开始加载热门非遗项目...')
    console.log('currentPage:', currentPage.value)
    console.log('pageSize:', pageSize.value)
    console.log('筛选条件:', { categoryId: selectedCategoryId.value, regionId: selectedRegionId.value })
    
    const heritageRes = await heritageApi.filter(selectedCategoryId.value, selectedRegionId.value, 1, currentPage.value, pageSize.value)
    console.log('heritageRes:', heritageRes)
    if (heritageRes && (heritageRes.success === true || heritageRes.code === 200)) {
      console.log('heritageRes.data:', heritageRes.data)
      heritageItems.value = heritageRes.data || []
      total.value = heritageRes.total || heritageItems.value.length || 0
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

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.hot-heritage {
  margin: 0;
  padding: 40px 0;
  width: 100%;
}

.hot-heritage h2 {
  font-size: 2rem;
  margin-bottom: 30px;
  color: var(--primary-color);
  text-align: center;
  position: relative;
  padding-bottom: 15px;
}

.hot-heritage h2::after {
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

.heritage-list {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
  margin-bottom: 40px;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .heritage-list {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .hot-heritage {
    padding: 30px 0;
  }
}

@media (max-width: 768px) {
  .heritage-list {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  
  .hot-heritage h2 {
    font-size: 1.8rem;
  }
  
  .hot-heritage {
    padding: 24px 0;
  }
}

@media (max-width: 576px) {
  .heritage-list {
    gap: 16px;
  }
  
  .hot-heritage h2 {
    font-size: 1.6rem;
  }
  
  .hot-heritage {
    padding: 20px 0;
  }
}
</style>