<template>
  <div class="new-promotion-view">
    <!-- 顶部横幅 -->
    <div class="hero-section">
      <div class="hero-content">
        <h1>非遗宣传</h1>
        <p>让非遗文化在现代生活中焕发新活力</p>
        <el-button type="primary" size="large" class="hero-button">了解更多</el-button>
      </div>
    </div>
    

    
    <!-- 媒体展示 -->
    <section class="media-section">
      <div class="container">
        <h2>非遗媒体展示</h2>
        <el-tabs v-model="activeMediaTab" type="border-card">
          <el-tab-pane label="非遗图片" name="images">
            <div class="media-grid">
              <div 
                v-for="(image, index) in promotionImages" 
                :key="index"
                class="media-card"
                @click="goToMediaDetail('image', index)"
              >
                <div class="media-image-container">
                  <img :src="image.url" :alt="image.title" class="media-image" />
                </div>
                <div class="media-card-content">
                  <h4>{{ image.title }}</h4>
                  <p>{{ image.description }}</p>
                </div>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="非遗视频" name="videos">
            <div class="video-grid">
              <div 
                v-for="(video, index) in promotionVideos" 
                :key="index"
                class="video-card"
                @click="goToMediaDetail('video', index)"
              >
                <div class="video-thumbnail">
                  <img :src="video.thumbnail" :alt="video.title" />
                  <div class="play-button">
                    <el-icon :size="48"><VideoPlay /></el-icon>
                  </div>
                </div>
                <div class="video-card-content">
                  <h4>{{ video.title }}</h4>
                  <p>{{ video.description }}</p>
                </div>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Collection, Star, Share, VideoPlay, Document, Picture, VideoCamera } from '@element-plus/icons-vue'
import api from '../api/index.js'

const activeMediaTab = ref('images')
const router = useRouter()
const promotionMedia = ref([])

const goToMediaDetail = (type, index) => {
  router.push(`/media/${type}/${index}`)
}

// 从后端获取宣传媒体数据
const loadPromotionMedia = async () => {
  try {
    const response = await api.get('/promotion/list', {
      params: { page: 1, limit: 10 }
    })
    if (response && response.code === 200) {
      promotionMedia.value = response.data || []
    }
  } catch (error) {
    console.error('加载宣传媒体数据失败:', error)
  }
}

// 计算属性：宣传图片
const promotionImages = ref([])

// 计算属性：宣传视频
const promotionVideos = ref([])

// 处理媒体数据
const processMediaData = () => {
  // 这里可以根据需要对数据进行处理，比如按类型分类
  promotionImages.value = promotionMedia.value.map(item => ({
    url: item.cover_image,
    title: item.title,
    description: item.content
  }))
  
  promotionVideos.value = promotionMedia.value.map(item => ({
    thumbnail: item.cover_image,
    title: item.title,
    description: item.content,
    videoUrl: item.link_url
  }))
}

onMounted(async () => {
  await loadPromotionMedia()
  processMediaData()
})


</script>

<style scoped>
.new-promotion-view {
  min-height: 100vh;
  background: #f5f7fa;
}

/* 顶部横幅 */
.hero-section {
  background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), 
              url('https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=traditional%20chinese%20cultural%20performance%20colorful%20stage&image_size=landscape_16_9');
  background-size: cover;
  background-position: center;
  color: white;
  text-align: center;
  padding: 120px 20px;
}

.hero-content h1 {
  font-size: 3rem;
  margin-bottom: 20px;
  font-weight: 700;
}

.hero-content p {
  font-size: 1.5rem;
  margin-bottom: 30px;
  opacity: 0.9;
}

.hero-button {
  font-size: 1.1rem;
  padding: 12px 30px;
}

/* 通用容器 */
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .hero-content h1 {
    font-size: 2.5rem;
  }
  
  .hero-content p {
    font-size: 1.2rem;
  }
  
  .media-section {
    padding: 60px 0;
  }
  
  .media-grid,
  .video-grid {
    grid-template-columns: 1fr;
  }
  
  .media-image-container {
    height: 150px;
  }
  
  .video-thumbnail {
    height: 150px;
  }
}

@media (max-width: 480px) {
  .hero-content h1 {
    font-size: 2rem;
  }
  
  .hero-content p {
    font-size: 1rem;
  }
  
  .contact-form {
    padding: 20px;
  }
}

/* 媒体展示样式 */
.media-section {
  padding: 80px 0;
  background: white;
}

.media-section h2 {
  text-align: center;
  font-size: 2.2rem;
  margin-bottom: 50px;
  color: #333;
  position: relative;
}

.media-section h2::after {
  content: '';
  position: absolute;
  bottom: -10px;
  left: 50%;
  transform: translateX(-50%);
  width: 80px;
  height: 3px;
  background: #C41E3A;
}

.media-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
  padding: 20px;
}

.media-card {
  background: #f9f9f9;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
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
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  padding: 20px;
}

.video-card {
  background: #f9f9f9;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.video-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
}

.video-thumbnail {
  position: relative;
  height: 180px;
  overflow: hidden;
  cursor: pointer;
}

.video-thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.video-card:hover .video-thumbnail img {
  transform: scale(1.05);
}

.play-button {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.6);
  border-radius: 50%;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  transition: all 0.3s ease;
}

.play-button:hover {
  background: rgba(0, 0, 0, 0.8);
  transform: translate(-50%, -50%) scale(1.1);
}

.video-card-content {
  padding: 15px;
}

.video-card-content h4 {
  margin: 0 0 10px 0;
  font-size: 1.1rem;
  color: #333;
}

.video-card-content p {
  margin: 0;
  font-size: 0.9rem;
  color: #666;
}

@media (max-width: 768px) {
  .media-section {
    padding: 60px 0;
  }
  
  .media-grid,
  .video-grid {
    grid-template-columns: 1fr;
  }
  
  .media-image-container {
    height: 150px;
  }
  
  .video-thumbnail {
    height: 150px;
  }
}
</style>