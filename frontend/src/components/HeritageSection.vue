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
import api from '../api/index'
import HeritageCard from './HeritageCard.vue'

const heritageItems = ref([])

const loadData = async () => {
  try {
    // 加载热门非遗项目，与后台管理系统使用相同的API
    console.log('开始加载热门非遗项目...')
    
    const response = await api.get('/dashboard/popular-heritage')
    console.log('popular-heritage response:', response)
    if (response && response.success) {
      // 只取前6个热度最高的项目
      heritageItems.value = response.data.slice(0, 6) || []
      console.log('加载到的热门项目:', heritageItems.value)
    } else {
      console.log('API 调用失败或返回的数据结构不正确')
      heritageItems.value = []
    }
  } catch (error) {
    console.error('加载数据失败:', error)
    heritageItems.value = []
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