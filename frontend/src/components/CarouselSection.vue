<template>
  <div class="carousel-section">
    <el-carousel :interval="5000" height="500px" indicator-position="outside" :autoplay="true">
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
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/index.js'

const carouselList = ref([
  {
    id: 1,
    title: '河南非遗文化节',
    imageUrl: 'https://kkimgs.yisou.com/ims?kt=url&at=ori&key=aHR0cDovLzViMDk4OGU1OTUyMjUuY2RuLnNvaHVjcy5jb20vaW1hZ2VzLzIwMTkwNjAyL2UyZGJiYmY3MWY2YjQ5ZjViNzNkN2M3ZDg0ZDA0Y2FlLmpwZWc=&sign=yx:YIdMcl9pVz4MkAFT8DrbiBhVdA4=&tv=0_0',
    orderNum: 1,
    status: 1
  },
  {
    id: 2,
    title: '豫剧表演',
    imageUrl: 'https://kkimgs.yisou.com/ims?kt=url&at=ori&key=aHR0cHM6Ly93d3cubWVpbnZ0dXAubmV0L3VwbG9hZHMvaW1nMS8yMDI2MDExNy9iMjI1Zjk0MjNjOTY5MTVjMGE1N2RjMTMxYTJkMmRhNS5qcGc=&sign=yx:q6MLeIYK67FbqH-R-6YXofH27ZQ=&tv=0_0',
    orderNum: 2,
    status: 1
  },
  {
    id: 3,
    title: '少林功夫',
    imageUrl: 'https://kkimgs.yisou.com/ims?kt=url&at=ori&key=aHR0cDovL2kwLmhkc2xiLmNvbS9iZnMvYXJjaGl2ZS83ZGI3YTViNmI1ZWVhNWFkMjA1YmFmYjE3MWQ3MWQ2NDVmYzIyNjFlLmpwZ0AuYXZpZg==&sign=yx:N8l8432ZJWrPlcnxFQNP65XGvjg=&tv=0_0',
    orderNum: 3,
    status: 1
  },
  {
    id: 4,
    title: '洛阳牡丹',
    imageUrl: 'https://kkimgs.yisou.com/ims?kt=url&at=ori&key=aHR0cHM6Ly9wMC5zc2wuaW1nLjM2MGt1YWkuY29tL2RtZmQvX182MC90MTE1MDhjNzVjODZiZmMyM2RkMTQ2N2M5YmMud2VicA==&sign=yx:qUVMCHHamR5cx29mUAl_8Q1CF5Y=&tv=0_0',
    orderNum: 4,
    status: 1
  }
])

// 加载轮播图数据
const loadCarouselData = async () => {
  try {
    console.log('开始加载轮播图数据...')
    console.log('API基础URL:', api.defaults.baseURL)
    const data = await api.get('/carousel/enabled-list')
    console.log('轮播图数据:', data)
    if (data && data.success) {
      // 过滤出状态为1的轮播图
      carouselList.value = (data.data || []).filter(item => item.status === 1)
      console.log('过滤后的轮播图列表:', carouselList.value)
      console.log('轮播图数量:', carouselList.value.length)
    } else {
      console.error('轮播图数据加载失败:', data)
    }
  } catch (error) {
    console.error('加载轮播图数据失败:', error)
    console.error('错误信息:', error.message)
    console.error('错误响应:', error.response)
  }
}

onMounted(() => {
  loadCarouselData()
})
</script>

<style scoped>
.carousel-section {
  width: 100%;
  margin-bottom: 60px;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: var(--shadow-lg);
  position: relative;
  border: 1px solid var(--border-color);
}

.carousel-item {
  position: relative;
  height: 500px;
  cursor: pointer;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f0f0;
  transition: all var(--transition-normal);
}

.carousel-item::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, rgba(138, 26, 26, 0.1), rgba(0, 102, 204, 0.1));
  z-index: 1;
  transition: opacity var(--transition-normal);
}

.carousel-item:hover::before {
  opacity: 0.3;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-slow);
  position: relative;
  z-index: 0;
}

.carousel-item:hover .carousel-image {
  transform: scale(1.08) rotate(1deg);
}

.carousel-caption {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(to top, rgba(0,0,0,0.8), rgba(0,0,0,0));
  color: white;
  padding: 40px;
  transition: all var(--transition-normal);
  z-index: 2;
  transform: translateY(20px);
  opacity: 0.8;
}

.carousel-item:hover .carousel-caption {
  transform: translateY(0);
  opacity: 1;
}

.carousel-caption h3 {
  font-size: 1.8rem;
  margin-bottom: 15px;
  color: #FFFFFF;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
  position: relative;
  display: inline-block;
  padding-bottom: 8px;
  animation: fadeInUp 0.6s ease;
}

.carousel-caption h3::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 60px;
  height: 2px;
  background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
  transform: scaleX(0);
  transition: transform var(--transition-normal);
}

.carousel-item:hover .carousel-caption h3::after {
  transform: scaleX(1);
}

.carousel-caption p {
  font-size: 1.1rem;
  opacity: 0.9;
  color: #FFFFFF;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.5);
  max-width: 800px;
  margin: 0 auto;
  animation: fadeInUp 0.6s ease 0.2s both;
}

/* 装饰元素 */
.carousel-item::after {
  content: '';
  position: absolute;
  top: 20px;
  right: 20px;
  width: 40px;
  height: 40px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  z-index: 2;
  animation: pulse 2s ease-in-out infinite;
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

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
    opacity: 0.3;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.6;
  }
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .carousel-item {
    height: 450px;
  }
}

@media (max-width: 768px) {
  .carousel-item {
    height: 350px;
  }
  
  .carousel-caption {
    padding: 30px;
  }
  
  .carousel-caption h3 {
    font-size: 1.5rem;
  }
}

@media (max-width: 576px) {
  .carousel-item {
    height: 280px;
  }
  
  .carousel-caption {
    padding: 20px;
  }
  
  .carousel-caption h3 {
    font-size: 1.2rem;
  }
}
</style>