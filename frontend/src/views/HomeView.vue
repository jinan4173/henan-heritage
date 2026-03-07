<template>
  <div class="home">
    <div class="home-container">
      <!-- 头部横幅 -->
      <div class="banner">
        <h1>河南非物质文化遗产数字化展示系统</h1>
        <p>{{ welcomeMessage }}</p>
      </div>

      <!-- 轮播图 -->
      <CarouselSection />

      <!-- 最新动态/新闻 -->
      <NewsSection />



      <!-- 热门非遗项目 -->
      <HeritageSection />
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import CarouselSection from '../components/CarouselSection.vue'
import NewsSection from '../components/NewsSection.vue'
import HeritageSection from '../components/HeritageSection.vue'

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
</script>

<style scoped>
.home {
  width: 100%;
  padding: 0;
  background-color: var(--background-color);
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
}

.home-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0;
}

/* 头部横幅 */
.banner {
  background: linear-gradient(135deg, var(--primary-color) 0%, #2C5282 100%);
  color: #FFFFFF;
  text-align: center;
  padding: 80px 20px;
  border-radius: 0;
  margin: 0;
  box-shadow: 0 8px 24px rgba(26, 54, 93, 0.3);
  position: relative;
  overflow: hidden;
  width: 100%;
  box-sizing: border-box;
}

.banner::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image: url('https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=traditional%20chinese%20pattern%20background%20subtle%20elegant&image_size=landscape_16_9');
  background-size: cover;
  background-position: center;
  opacity: 0.1;
  z-index: 0;
}

.banner h1 {
  font-size: 3rem;
  margin-bottom: 20px;
  color: #FFFFFF;
  position: relative;
  z-index: 1;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.banner p {
  font-size: 1.2rem;
  opacity: 0.9;
  color: #FFFFFF;
  position: relative;
  z-index: 1;
  max-width: 800px;
  margin: 0 auto;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .home-container {
    max-width: 950px;
  }
  
  .banner h1 {
    font-size: 2.5rem;
  }
}

@media (max-width: 768px) {
  .home-container {
    max-width: 100%;
  }
  
  .home {
    padding: 15px;
  }
  
  .banner {
    padding: 60px 15px;
  }
  
  .banner h1 {
    font-size: 2rem;
  }
  
  .banner p {
    font-size: 1rem;
  }
}

@media (max-width: 576px) {
  .banner h1 {
    font-size: 1.8rem;
  }
  
  .banner p {
    font-size: 0.95rem;
  }
}
</style>