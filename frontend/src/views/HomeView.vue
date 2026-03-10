<template>
  <div class="home">
    <!-- 头部横幅 -->
    <div class="banner">
      <div class="container">
        <div class="banner-content">
          <p>{{ welcomeMessage }}</p>
          <div class="banner-actions">
            <router-link to="/heritage" class="hero-btn hero-btn-primary">探索非遗项目</router-link>
            <router-link to="/news" class="hero-btn hero-btn-secondary">了解文化资讯</router-link>
          </div>
        </div>
      </div>
    </div>

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

/* 头部横幅 */
.banner {
  background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
  color: white;
  text-align: center;
  padding: 120px 20px;
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
  width: 100%;
  height: 100%;
  background: url('https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=henan%20chinese%20traditional%20pattern%20background%20subtle%20cultural%20heritage&image_size=landscape_16_9') center/cover;
  opacity: 0.1;
  z-index: 1;
  animation: backgroundMove 20s ease-in-out infinite;
}

/* 装饰元素 */
.banner::after {
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

.banner-content {
  position: relative;
  z-index: 2;
  max-width: 800px;
  margin: 0 auto;
  text-align: center;
}

.logo-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  animation: fadeInDown 1s ease;
}

.logo {
  width: 200px;
  height: 200px;
  object-fit: contain;
  animation: logoFloat 3s ease-in-out infinite;
}

@keyframes logoFloat {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

.banner p {
  font-size: 1.3rem;
  opacity: 0.9;
  color: white;
  margin-bottom: 30px;
  animation: fadeInUp 1s ease 0.2s both;
  position: relative;
  padding: 0 40px;
}

.banner p::before,
.banner p::after {
  content: '';
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 30px;
  height: 2px;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.6), transparent);
}

.banner p::before {
  left: 0;
}

.banner p::after {
  right: 0;
}

.banner-actions {
  display: flex;
  gap: 20px;
  justify-content: center;
  animation: fadeInUp 1s ease 0.4s both;
  flex-wrap: wrap;
  margin-top: 40px;
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

/* 章节样式 */
.section {
  padding: 80px 0;
  width: 100%;
}

.heritage-section {
  background-color: var(--card-background);
  box-shadow: 0 -4px 12px rgba(0, 0, 0, 0.05);
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

/* 滚动动画 */
.section {
  opacity: 0;
  transform: translateY(50px);
  transition: opacity 0.8s ease, transform 0.8s ease;
}

.section.animate-in {
  opacity: 1;
  transform: translateY(0);
}

/* 视差效果 */
.banner {
  transition: transform 0.1s ease-out;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .logo {
    width: 180px;
    height: 180px;
  }
  
  .container {
    padding: 0 15px;
  }
  
  .section {
    padding: 70px 0;
  }
}

@media (max-width: 768px) {
  .banner {
    padding: 80px 15px;
  }
  
  .logo {
    width: 150px;
    height: 150px;
  }
  
  .banner p {
    font-size: 1.1rem;
    padding: 0 20px;
  }
  
  .banner-actions {
    flex-direction: column;
    align-items: center;
  }
  
  .hero-btn {
    width: 200px;
    text-align: center;
  }
  
  .section {
    padding: 60px 0;
  }
  
  .container {
    padding: 0 15px;
  }
}

@media (max-width: 576px) {
  .banner {
    padding: 60px 15px;
  }
  
  .logo {
    width: 120px;
    height: 120px;
  }
  
  .banner p {
    font-size: 1rem;
    padding: 0 10px;
  }
  
  .banner p::before,
  .banner p::after {
    width: 20px;
  }
  
  .section {
    padding: 40px 0;
  }
  
  .container {
    padding: 0 10px;
  }
  
  .banner-actions {
    gap: 15px;
  }
  
  .hero-btn {
    padding: 10px 24px;
    font-size: 1rem;
  }
}
</style>