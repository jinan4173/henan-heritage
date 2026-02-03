<template>
  <div class="heritage-display">
    <div class="container">
      <!-- 返回按钮 -->
      <el-button type="primary" plain @click="goBack" class="back-button">
        <el-icon><ArrowLeft /></el-icon> 返回列表
      </el-button>

      <!-- 加载状态 -->
      <el-skeleton v-if="loading" :rows="10" animated />

      <!-- 详情内容 -->
      <div v-else-if="heritageItem" class="detail-content">
        <!-- 标题区域 -->
        <div class="hero-section">
          <div class="hero-overlay">
            <h1>{{ heritageItem.title }}</h1>
            <div class="hero-meta">
              <el-tag v-if="heritageItem.category?.name" type="primary">{{ heritageItem.category?.name }}</el-tag>
              <el-tag v-if="heritageItem.region?.name" type="success">{{ heritageItem.region?.name }}</el-tag>
              <span class="hero-id">编号: {{ String(heritageItem.id).padStart(4, '0') }}</span>
            </div>
          </div>
          <img :src="heritageItem.coverImage || 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=traditional%20Chinese%20culture%20heritage%20intangible%20cultural%20heritage%20elegant%20artistic&image_size=landscape'" :alt="heritageItem.title" class="hero-image" />
        </div>

        <!-- 基本信息卡片 -->
        <div class="info-cards">
          <div class="info-card">
            <div class="card-icon">
              <el-icon><FolderOpened /></el-icon>
            </div>
            <div class="card-content">
              <span class="card-label">类别</span>
              <span class="card-value">{{ heritageItem.categoryName || heritageItem.category?.name || '未分类' }}</span>
            </div>
          </div>
          <div class="info-card">
            <div class="card-icon">
              <el-icon><Location /></el-icon>
            </div>
            <div class="card-content">
              <span class="card-label">申报地区或单位</span>
              <span class="card-value">{{ heritageItem.declarationRegion || heritageItem.region?.name || '未知' }}</span>
            </div>
          </div>
          <div class="info-card">
            <div class="card-icon">
              <el-icon><OfficeBuilding /></el-icon>
            </div>
            <div class="card-content">
              <span class="card-label">保护单位</span>
              <span class="card-value">{{ heritageItem.protectionUnit || '未知' }}</span>
            </div>
          </div>
        </div>

        <!-- 项目介绍 -->
        <div class="section">
          <div class="section-header">
            <el-icon><Document /></el-icon>
            <h2>项目介绍</h2>
          </div>
          <div class="section-content">
            <p v-if="heritageItem.projectIntroduction" class="description-text">{{ heritageItem.projectIntroduction }}</p>
            <p v-else class="empty-text">暂无项目介绍</p>
          </div>
        </div>

        <!-- 图片画廊 -->
        <div v-if="images.length > 0" class="section">
          <div class="section-header">
            <el-icon><Picture /></el-icon>
            <h2>图片画廊</h2>
          </div>
          <div class="gallery-container">
            <el-carousel :interval="4000" type="card" height="300px">
              <el-carousel-item v-for="(image, index) in images" :key="image.id || index">
                <img :src="getImageUrl(image.mediaUrl)" :alt="heritageItem.title" class="gallery-image" />
              </el-carousel-item>
            </el-carousel>
          </div>
        </div>

        <!-- 视频展示 -->
        <div v-if="videos.length > 0" class="section">
          <div class="section-header">
            <el-icon><VideoCamera /></el-icon>
            <h2>视频展示</h2>
          </div>
          <div class="video-container">
            <!-- 检查是否是第三方视频平台链接 -->
            <template v-if="isThirdPartyVideo(videos[0].mediaUrl)">
              <!-- 第三方视频平台链接，使用 iframe 嵌入 -->
              <div class="iframe-container">
                <iframe :src="getEmbedUrl(videos[0].mediaUrl)" frameborder="0" allowfullscreen></iframe>
              </div>
            </template>
            <template v-else>
              <!-- 直接视频文件链接，使用 video 标签 -->
              <video :src="videos[0].mediaUrl" controls preload="metadata">
                您的浏览器不支持视频播放
              </video>
            </template>
            <div v-if="videos.length > 1" class="video-list">
              <h4>更多视频</h4>
              <div class="video-thumbs">
                <div 
                  v-for="(video, index) in videos.slice(1)" 
                  :key="video.id || index"
                  class="video-thumb"
                  @click="playVideo(video.mediaUrl)"
                >
                  <div class="thumb-overlay">
                    <el-icon><VideoCamera /></el-icon>
                  </div>
                  <span>{{ video.description || `视频 ${index + 2}` }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 相关传承人 -->
        <div v-if="relatedInheritors.length > 0" class="section">
          <div class="section-header">
            <el-icon><User /></el-icon>
            <h2>相关传承人</h2>
          </div>
          <div class="inheritor-list">
            <div 
              v-for="inheritor in relatedInheritors" 
              :key="inheritor.id" 
              class="inheritor-card"
              @click="goToInheritor(inheritor.id)"
            >
              <div class="inheritor-avatar">
                <el-icon><UserFilled /></el-icon>
              </div>
              <div class="inheritor-info">
                <h4>{{ inheritor.name }}</h4>
                <p>{{ heritageItem.title }}</p>
                <el-tag size="small">{{ heritageItem.categoryName || heritageItem.category?.name }}</el-tag>
              </div>
            </div>
          </div>
        </div>

        <!-- 操作按钮 -->
        <div class="action-buttons">
          <el-button type="primary" @click="handleShare">
            <el-icon><Share /></el-icon> 分享
          </el-button>
          <el-button type="success" @click="handleFavorite">
            <el-icon><Star /></el-icon> 收藏
          </el-button>
        </div>
      </div>

      <!-- 错误状态 -->
      <div v-else class="error-state">
        <el-empty description="非遗项目不存在">
          <el-button type="primary" @click="goBack">返回列表</el-button>
        </el-empty>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { heritageApi } from '../api/heritage'
import api from '../api/index.js'
import { ElButton, ElSkeleton, ElEmpty, ElIcon, ElTag, ElCarousel, ElCarouselItem, ElMessage } from 'element-plus'
import { 
  ArrowLeft, 
  FolderOpened, 
  Location, 
  Calendar, 
  View, 
  Document, 
  Picture, 
  VideoCamera,
  User,
  UserFilled,
  Share,
  Star,
  Timer,
  List,
  Key,
  OfficeBuilding
} from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const heritageItem = ref(null)
const relatedInheritors = ref([])
const loading = ref(true)

// 计算属性：从 mediaList 中过滤出图片
const images = computed(() => {
  if (!heritageItem.value?.mediaList) return []
  return heritageItem.value.mediaList.filter(item => item.mediaType === 'image')
})

// 计算属性：从 mediaList 中过滤出视频
const videos = computed(() => {
  if (!heritageItem.value?.mediaList) return []
  return heritageItem.value.mediaList.filter(item => item.mediaType === 'video')
})

onMounted(() => {
  loadData()
})

const loadData = async () => {
  try {
    const id = route.params.id
    if (!id) {
      router.push('/heritage')
      return
    }

    const heritageRes = await heritageApi.getById(id)
    if (heritageRes.success) {
      heritageItem.value = heritageRes.data
      console.log('加载的非遗项目数据:', heritageItem.value)
      console.log('媒体资源列表:', heritageItem.value.mediaList)
    }

    try {
      // 尝试使用API获取传承人列表
      const inheritorRes = await api.get('/inheritor/list')
      console.log('传承人API响应:', inheritorRes)
      
      if (inheritorRes.success) {
        console.log('传承人列表:', inheritorRes.data)
        console.log('传承人数据结构:', inheritorRes.data[0] ? Object.keys(inheritorRes.data[0]) : '无数据')
        
        const currentProjectTitle = heritageItem.value?.title
        const currentCategoryName = heritageItem.value?.categoryName || heritageItem.value?.category?.name
        
        console.log('当前项目标题:', currentProjectTitle)
        console.log('当前项目分类:', currentCategoryName)
        
        // 根据当前项目名称匹配相关传承人
        if (inheritorRes.data && inheritorRes.data.length > 0) {
          // 过滤出与当前项目相关的传承人
          const matchedInheritors = inheritorRes.data.filter(inheritor => {
            // 检查传承人的项目是否包含当前项目的标题
            const match = inheritor.project && currentProjectTitle && 
                   inheritor.project.includes(currentProjectTitle)
            console.log(`传承人 ${inheritor.name} 项目 ${inheritor.project} 匹配 ${currentProjectTitle}: ${match}`)
            return match
          })
          
          console.log('匹配到的传承人:', matchedInheritors)
          
          // 如果匹配到传承人，使用匹配到的
          if (matchedInheritors.length > 0) {
            relatedInheritors.value = matchedInheritors.slice(0, 4)
            console.log('使用项目名称匹配到的传承人:', relatedInheritors.value)
          } else if (currentCategoryName) {
            // 如果没有匹配到传承人，使用分类匹配
            const categoryMatchedInheritors = inheritorRes.data.filter(inheritor => {
              return inheritor.category === currentCategoryName
            })
            
            console.log('按分类匹配到的传承人:', categoryMatchedInheritors)
            if (categoryMatchedInheritors.length > 0) {
              relatedInheritors.value = categoryMatchedInheritors.slice(0, 4)
            } else {
              // 如果没有匹配到传承人，使用默认传承人数据
              relatedInheritors.value = inheritorRes.data.slice(0, 4)
              console.log('使用默认传承人数据:', relatedInheritors.value)
            }
          } else {
            // 如果没有分类信息，使用默认传承人数据
            relatedInheritors.value = inheritorRes.data.slice(0, 4)
            console.log('使用默认传承人数据:', relatedInheritors.value)
          }
          
          console.log('最终使用的传承人结果:', relatedInheritors.value)
        } else {
          console.log('无传承人数据')
        }
      } else {
        console.log('传承人API调用失败:', inheritorRes.message)
        // 使用默认传承人数据
        relatedInheritors.value = [
          { id: 1, name: '传承人1' },
          { id: 2, name: '传承人2' },
          { id: 3, name: '传承人3' },
          { id: 4, name: '传承人4' }
        ]
      }
    } catch (error) {
      console.error('传承人API调用异常:', error)
      // 使用默认传承人数据
      relatedInheritors.value = [
        { id: 1, name: '传承人1' },
        { id: 2, name: '传承人2' },
        { id: 3, name: '传承人3' },
        { id: 4, name: '传承人4' }
      ]
      console.log('使用默认传承人数据:', relatedInheritors.value)
    }
  } catch (error) {
    console.error('加载详情失败:', error)
  } finally {
    loading.value = false
  }
}

const formatDate = (date) => {
  if (!date) return '未知'
  return new Date(date).toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const goBack = () => {
  router.push('/heritage')
}

const goToInheritor = (id) => {
  router.push(`/inheritor/${id}`)
}

const handleShare = () => {
  if (navigator.share) {
    navigator.share({
      title: heritageItem.value.title,
      text: heritageItem.value.description?.substring(0, 100),
      url: window.location.href
    })
  } else {
    navigator.clipboard.writeText(window.location.href)
    ElMessage.success('链接已复制到剪贴板')
  }
}

const handleFavorite = () => {
  ElMessage.success('收藏成功')
}

// 检查是否是第三方视频平台链接
const isThirdPartyVideo = (url) => {
  const thirdPartyDomains = ['bilibili.com', 'youtube.com', 'youku.com', 'tudou.com', 'iqiyi.com', 'qq.com', 'v.qq.com']
  return thirdPartyDomains.some(domain => url.includes(domain))
}

// 获取嵌入 URL
const getEmbedUrl = (url) => {
  // 处理 B 站链接
  if (url.includes('bilibili.com')) {
    // 提取 BV 号
    const bvMatch = url.match(/BV[\w]+/)
    if (bvMatch) {
      const bvId = bvMatch[0]
      // 确保不自动播放，添加 autoplay=0 参数
      return `https://player.bilibili.com/player.html?aid=&bvid=${bvId}&cid=&page=1&autoplay=0`
    }
  }
  // 处理 YouTube 链接
  if (url.includes('youtube.com')) {
    // 提取视频 ID
    const videoIdMatch = url.match(/v=([^&]+)/)
    if (videoIdMatch) {
      const videoId = videoIdMatch[1]
      // 确保不自动播放，不添加 autoplay 参数（YouTube 默认不自动播放）
      return `https://www.youtube.com/embed/${videoId}`
    }
  }
  // 其他平台的处理可以在这里添加
  return url
}

// 处理图片 URL
const getImageUrl = (url) => {
  if (!url) return '';
  
  // 处理完整的 HTTP URL
  if (url.startsWith('http://') || url.startsWith('https://')) {
    return url;
  }
  
  // 处理本地文件路径（如 D:\henan-heritage\uploads\xxx.png）
  if (url.includes('\\') || url.includes('D:') || url.includes('C:') || url.includes('E:')) {
    // 提取文件名
    const fileName = url.split(/[\\/]/).pop();
    // 构建正确的 HTTP URL，使用相对路径
    return `/uploads/${fileName}`;
  }
  
  // 处理相对路径（如 /uploads/xxx.jpg）
  if (url.startsWith('/uploads/')) {
    // 直接使用相对路径，让浏览器通过同源策略访问
    return url;
  }
  
  // 其他情况返回空字符串
  return '';
};

// 播放视频方法
const playVideo = (videoUrl) => {
  const videoElement = document.querySelector('.video-container video')
  const iframeContainer = document.querySelector('.video-container .iframe-container')
  
  if (isThirdPartyVideo(videoUrl)) {
    // 第三方视频平台链接，更新 iframe
    if (iframeContainer) {
      const iframe = iframeContainer.querySelector('iframe')
      if (iframe) {
        iframe.src = getEmbedUrl(videoUrl)
      }
    }
  } else {
    // 直接视频文件链接，更新 video 标签
    if (videoElement) {
      videoElement.src = videoUrl
      // 移除自动播放，让用户手动点击播放
    }
  }
}
</script>

<style scoped>
.heritage-display {
  padding: 20px;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8eb 100%);
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

.back-button {
  margin-bottom: 20px;
}

.detail-content {
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
}

.hero-section {
  position: relative;
  height: 400px;
  overflow: hidden;
}

.hero-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(to bottom, rgba(0,0,0,0.2), rgba(0,0,0,0.8));
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: flex-end;
  padding: 40px;
  z-index: 1;
}

.hero-overlay h1 {
  font-size: 2.5rem;
  color: white;
  margin-bottom: 0;
  text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
}

.hero-meta {
  display: flex;
  gap: 10px;
  align-items: center;
}

.hero-id {
  color: rgba(255,255,255,0.8);
  font-size: 0.9rem;
  margin-left: auto;
}

.hero-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.info-cards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  padding: 30px;
  background: #f8f9fa;
}

@media (max-width: 1200px) {
  .info-cards {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 992px) {
  .info-cards {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .info-cards {
    grid-template-columns: 1fr;
  }
}

.info-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 15px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
  transition: transform 0.3s ease;
}

.info-card:hover {
  transform: translateY(-5px);
}

.card-icon {
  width: 50px;
  height: 50px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 1.5rem;
}

.card-content {
  display: flex;
  flex-direction: column;
}

.card-label {
  font-size: 0.85rem;
  color: #999;
  margin-bottom: 5px;
}

.card-value {
  font-size: 1.1rem;
  font-weight: 600;
  color: #333;
}

.section {
  padding: 30px;
  border-bottom: 1px solid #eee;
}

.section:last-of-type {
  border-bottom: none;
}

.section-header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;
}

.section-header h2 {
  font-size: 1.5rem;
  color: #333;
  margin: 0;
}

.section-header .el-icon {
  font-size: 1.5rem;
  color: #667eea;
}

.description-text {
  font-size: 1.1rem;
  line-height: 2;
  color: #444;
  text-indent: 2em;
}

.gallery-container {
  padding: 0 20px;
}

.gallery-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 8px;
}

.video-container {
  background: #000;
  border-radius: 12px;
  overflow: hidden;
}

.video-container video {
  width: 100%;
  max-height: 500px;
}

.iframe-container {
  width: 100%;
  max-height: 500px;
  position: relative;
  padding-bottom: 56.25%; /* 16:9 比例 */
  height: 0;
  overflow: hidden;
}

.iframe-container iframe {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border: 0;
}

.video-list {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.video-list h4 {
  margin-bottom: 15px;
  color: #333;
}

.video-thumbs {
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
}

.video-thumb {
  flex: 1;
  min-width: 150px;
  max-width: 200px;
  height: 80px;
  background: #f0f0f0;
  border-radius: 8px;
  cursor: pointer;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  transition: all 0.3s ease;
}

.video-thumb:hover {
  background: #e0e0e0;
  transform: translateY(-3px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.thumb-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 1.5rem;
  transition: all 0.3s ease;
}

.video-thumb:hover .thumb-overlay {
  background: rgba(0,0,0,0.7);
}

.video-thumb span {
  position: absolute;
  bottom: 10px;
  left: 10px;
  right: 10px;
  color: white;
  font-size: 0.8rem;
  text-align: center;
  text-shadow: 1px 1px 2px rgba(0,0,0,0.8);
  z-index: 1;
}

.inheritor-list {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.inheritor-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  padding: 20px;
  color: white;
  cursor: pointer;
  transition: transform 0.3s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.inheritor-card:hover {
  transform: translateY(-5px);
}

.inheritor-avatar {
  width: 60px;
  height: 60px;
  background: rgba(255,255,255,0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
  margin-bottom: 10px;
}

.inheritor-info h4 {
  margin: 0 0 5px 0;
  font-size: 1.1rem;
}

.inheritor-info p {
  margin: 0 0 10px 0;
  font-size: 0.9rem;
  opacity: 0.9;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  padding: 30px;
  background: #f8f9fa;
}

.error-state {
  margin: 100px 0;
  text-align: center;
}

@media (max-width: 768px) {
  .hero-section {
    height: 300px;
  }

  .hero-overlay h1 {
    font-size: 1.8rem;
  }

  .hero-id {
    display: none;
  }

  .info-cards {
    grid-template-columns: repeat(2, 1fr);
  }

  .inheritor-list {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
