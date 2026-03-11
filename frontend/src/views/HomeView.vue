<template>
  <div class="home">
    <!-- 头部横幅 -->
    <Banner :welcome-message="welcomeMessage" />

    <!-- 轮播图 -->
    <div class="container">
      <CarouselSection />
    </div>

    <!-- 热门非遗项目 -->
    <section class="section heritage-section">
      <div class="container">
        <HeritageSection />
      </div>
    </section>

    <!-- 最新动态/新闻 -->
    <section class="section">
      <div class="container">
        <NewsSection />
      </div>
    </section>

    <!-- 最新活动 -->
    <section class="section">
      <div class="container">
        <ActivityPreviewSection />
      </div>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted, onUnmounted } from 'vue'
import CarouselSection from '../components/CarouselSection.vue'
import NewsSection from '../components/NewsSection.vue'
import HeritageSection from '../components/HeritageSection.vue'
import ActivityPreviewSection from '../components/ActivityPreviewSection.vue'
import Banner from '../components/Banner.vue'

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

// 图片懒加载实现
const initLazyLoad = () => {
  if ('IntersectionObserver' in window) {
    const imageObserver = new IntersectionObserver((entries, observer) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          const img = entry.target
          const src = img.getAttribute('data-src')
          if (src) {
            img.src = src
            img.classList.remove('lazy')
            imageObserver.unobserve(img)
          }
        }
      })
    })

    document.querySelectorAll('img.lazy').forEach(img => {
      imageObserver.observe(img)
    })
  }
}

// 滚动动画实现
const initScrollAnimation = () => {
  if ('IntersectionObserver' in window) {
    const sectionObserver = new IntersectionObserver((entries, observer) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          entry.target.classList.add('animate-in')
          observer.unobserve(entry.target)
        }
      })
    }, {
      threshold: 0.1
    })

    document.querySelectorAll('.section').forEach(section => {
      sectionObserver.observe(section)
    })
  }
}

// 监听窗口滚动，添加视差效果
const handleScroll = () => {
  const scrollY = window.scrollY
  const banner = document.querySelector('.banner')
  if (banner) {
    banner.style.transform = `translateY(${scrollY * 0.5}px)`
  }
}

onMounted(() => {
  // 初始化图片懒加载
  initLazyLoad()
  
  // 初始化滚动动画
  initScrollAnimation()
  
  // 添加页面加载完成动画
  document.body.classList.add('loaded')
  
  // 添加滚动事件监听
  window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
  // 移除滚动事件监听
  window.removeEventListener('scroll', handleScroll)
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

/* 容器样式 */
.container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  box-sizing: border-box;
}

/* 章节样式 */
.section {
  padding: 80px 0;
  width: 100%;
  opacity: 0;
  transform: translateY(50px);
  transition: opacity 0.8s ease, transform 0.8s ease;
}

.section.animate-in {
  opacity: 1;
  transform: translateY(0);
}

.heritage-section {
  background-color: var(--background-color);
}

/* 图片懒加载样式 */
img.lazy {
  opacity: 0;
  transition: opacity 0.3s ease;
}

img.lazy.loaded {
  opacity: 1;
}

/* 动画效果 */
@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

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

/* 响应式设计 */
@media (max-width: 1200px) {
  .container {
    padding: 0 15px;
  }
  
  .section {
    padding: 70px 0;
  }
}

@media (max-width: 768px) {
  .section {
    padding: 60px 0;
  }
  
  .container {
    padding: 0 15px;
  }
}

@media (max-width: 576px) {
  .section {
    padding: 40px 0;
  }
  
  .container {
    padding: 0 10px;
  }
}
</style>