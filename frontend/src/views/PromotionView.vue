<template>
  <div class="promotion-view">
    <div class="page-header">
      <h1>非遗宣传</h1>
      <p>传承文化根脉，弘扬非遗精神</p>
    </div>
    
    <div class="promotion-content">
      <section class="promotion-section">
        <h2>宣传活动</h2>
        <div class="promotion-grid">
          <div class="promotion-card" v-for="item in promotionActivities" :key="item.id">
            <img :src="item.image" alt="宣传活动" class="card-image">
            <div class="card-content">
              <h3>{{ item.title }}</h3>
              <p>{{ item.description }}</p>
              <div class="card-footer">
                <span class="date">{{ item.date }}</span>
                <el-button type="primary" size="small">了解详情</el-button>
              </div>
            </div>
          </div>
        </div>
      </section>
      
      <section class="promotion-section">
        <h2>宣传资源</h2>
        <div class="resource-grid">
          <div class="resource-card" v-for="resource in promotionResources" :key="resource.id">
            <el-icon :size="48">{{ resource.icon }}</el-icon>
            <h3>{{ resource.title }}</h3>
            <p>{{ resource.description }}</p>
            <el-button type="info" size="small">下载</el-button>
          </div>
        </div>
      </section>
      
      <section class="promotion-section">
        <h2>宣传视频</h2>
        <div class="video-container">
          <div class="video-item" v-for="video in promotionVideos" :key="video.id">
            <div class="video-thumbnail">
              <img :src="video.thumbnail" alt="视频缩略图">
              <div class="play-button">
                <el-icon :size="48"><VideoPlay /></el-icon>
              </div>
            </div>
            <h3>{{ video.title }}</h3>
            <p>{{ video.description }}</p>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { VideoPlay, Document, Picture, VideoCamera, Share } from '@element-plus/icons-vue'

const promotionActivities = ref([
  {
    id: 1,
    title: '河南非遗走进校园活动',
    description: '通过讲座、展示等形式，让学生了解非遗文化的魅力',
    date: '2026-03-15',
    image: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=traditional%20cultural%20activity%20in%20school%20with%20students%20learning%20traditional%20crafts&image_size=landscape_16_9'
  },
  {
    id: 2,
    title: '非遗文化节',
    description: '举办大型非遗文化节，展示各类非遗项目',
    date: '2026-05-01',
    image: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=traditional%20cultural%20festival%20with%20performances%20and%20exhibitions&image_size=landscape_16_9'
  },
  {
    id: 3,
    title: '非遗传承人访谈',
    description: '邀请非遗传承人分享他们的故事和技艺',
    date: '2026-06-10',
    image: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=interview%20with%20traditional%20craftsman%20in%20workshop&image_size=landscape_16_9'
  }
])

const promotionResources = ref([
  {
    id: 1,
    title: '非遗宣传手册',
    description: '详细介绍河南非遗项目的宣传手册',
    icon: Document
  },
  {
    id: 2,
    title: '非遗图片集',
    description: '精选非遗项目的高清图片集',
    icon: Picture
  },
  {
    id: 3,
    title: '非遗宣传视频',
    description: '制作精美的非遗宣传视频',
    icon: VideoCamera
  },
  {
    id: 4,
    title: '社交媒体素材',
    description: '适合社交媒体分享的非遗宣传素材',
    icon: Share
  }
])

const promotionVideos = ref([
  {
    id: 1,
    title: '河南非遗传承之路',
    description: '记录河南非遗项目的传承历程',
    thumbnail: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=traditional%20culture%20documentary%20thumbnail&image_size=landscape_16_9'
  },
  {
    id: 2,
    title: '非遗技艺展示',
    description: '展示各类非遗技艺的精湛表演',
    thumbnail: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=traditional%20crafts%20demonstration%20thumbnail&image_size=landscape_16_9'
  },
  {
    id: 3,
    title: '非遗与现代生活',
    description: '探索非遗如何融入现代生活',
    thumbnail: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=traditional%20culture%20in%20modern%20life%20thumbnail&image_size=landscape_16_9'
  }
])

onMounted(() => {
  // 可以在这里添加数据加载逻辑
})
</script>

<style scoped>
.promotion-view {
  min-height: 100vh;
  background-color: var(--background-color);
}

.page-header {
  background: linear-gradient(135deg, var(--primary-color) 0%, #8B0000 100%);
  color: white;
  text-align: center;
  padding: 60px 20px;
  margin-bottom: 40px;
  border-radius: 16px;
  box-shadow: 0 8px 24px rgba(200, 22, 29, 0.3);
  position: relative;
  overflow: hidden;
}

.page-header::before {
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

.page-header h1 {
  font-size: 2.5rem;
  margin-bottom: 10px;
  font-weight: 600;
  position: relative;
  z-index: 1;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.page-header p {
  font-size: 1.2rem;
  opacity: 0.9;
  position: relative;
  z-index: 1;
  max-width: 800px;
  margin: 0 auto;
}

.promotion-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.promotion-section {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  padding: 30px;
  margin-bottom: 30px;
}

.promotion-section h2 {
  font-size: 1.8rem;
  margin-bottom: 20px;
  color: var(--primary-color);
  position: relative;
  padding-bottom: 10px;
}

.promotion-section h2::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 60px;
  height: 3px;
  background-color: var(--accent-color);
  border-radius: 2px;
}

.promotion-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.promotion-card {
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.promotion-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
}

.card-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.card-content {
  padding: 20px;
}

.card-content h3 {
  font-size: 1.2rem;
  margin-bottom: 10px;
  color: #333;
}

.card-content p {
  color: #666;
  line-height: 1.5;
  margin-bottom: 15px;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.date {
  color: #999;
  font-size: 0.9rem;
}

.resource-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 20px;
}

.resource-card {
  background: #f9f9f9;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  padding: 25px;
  text-align: center;
  transition: all 0.3s ease;
}

.resource-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 3px 15px rgba(0, 0, 0, 0.1);
}

.resource-card h3 {
  margin: 15px 0 10px;
  font-size: 1.1rem;
  color: #333;
}

.resource-card p {
  color: #666;
  font-size: 0.9rem;
  margin-bottom: 15px;
}

.video-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 20px;
}

.video-item {
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.video-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
}

.video-thumbnail {
  position: relative;
  width: 100%;
  height: 200px;
  overflow: hidden;
}

.video-thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.video-item:hover .video-thumbnail img {
  transform: scale(1.05);
}

.play-button {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.6);
  border-radius: 50%;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  cursor: pointer;
  transition: all 0.3s ease;
}

.play-button:hover {
  background: rgba(0, 0, 0, 0.8);
  transform: translate(-50%, -50%) scale(1.1);
}

.video-item h3 {
  font-size: 1.1rem;
  margin: 15px 20px 10px;
  color: #333;
}

.video-item p {
  color: #666;
  font-size: 0.9rem;
  margin: 0 20px 20px;
  line-height: 1.4;
}

@media (max-width: 768px) {
  .page-header {
    padding: 40px 20px;
  }
  
  .page-header h1 {
    font-size: 2rem;
  }
  
  .promotion-section {
    padding: 20px;
  }
  
  .promotion-grid,
  .resource-grid,
  .video-container {
    grid-template-columns: 1fr;
  }
  
  .video-container {
    grid-template-columns: 1fr;
  }
}
</style>