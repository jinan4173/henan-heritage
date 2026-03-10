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
          <img :src="heritageItem.coverImage || getDefaultCover(heritageItem)" :alt="heritageItem.title" class="hero-image" />
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
            <p v-if="heritageItem.description" class="description-text">{{ heritageItem.description }}</p>
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
            <el-icon v-if="isFavorite"><StarFilled /></el-icon>
            <el-icon v-else><Star /></el-icon>
            {{ isFavorite ? '已收藏' : '收藏' }}
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
import { heritageApi } from '../api/api'
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
  StarFilled,
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
const isFavorite = ref(false)
const favoriteId = ref(null)
const currentUser = ref(JSON.parse(localStorage.getItem('user')))

// 计算属性：从 mediaList 中过滤出图片
const images = computed(() => {
  if (!heritageItem.value?.mediaList) return []
  console.log('原始媒体资源列表:', heritageItem.value.mediaList)
  const filtered = heritageItem.value.mediaList.filter(item => {
    const isImage = item.mediaType === 'image'
    console.log(`过滤媒体资源: id=${item.id}, type=${item.mediaType}, isImage=${isImage}`)
    return isImage
  })
  console.log('过滤后的图片列表:', filtered)
  return filtered
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
    console.log('API响应:', heritageRes)
    if (heritageRes && heritageRes.code === 200) {
      heritageItem.value = heritageRes.data
      console.log('加载的非遗项目数据:', heritageItem.value)
      console.log('项目介绍:', heritageItem.value.description)
      console.log('媒体资源列表:', heritageItem.value.mediaList)
      console.log('媒体资源数量:', heritageItem.value.mediaList ? heritageItem.value.mediaList.length : 0)
      if (heritageItem.value.mediaList) {
        console.log('媒体资源详情:', heritageItem.value.mediaList)
        heritageItem.value.mediaList.forEach((media, index) => {
          console.log(`媒体 ${index + 1}:`, media)
          console.log(`媒体 ${index + 1} URL:`, media.mediaUrl)
          console.log(`媒体 ${index + 1} 类型:`, media.mediaType)
          console.log(`媒体 ${index + 1} 类型 (类型检查):`, typeof media.mediaType)
          console.log(`媒体 ${index + 1} 类型 (是否等于 "image"):`, media.mediaType === 'image')
        })
        console.log('过滤后的图片数量:', images.value.length)
      }
    }

    try {
      // 尝试使用API获取传承人列表
      const inheritorRes = await api.get('/inheritor/list')
      console.log('传承人API响应:', inheritorRes)
      
      if (inheritorRes && inheritorRes.code === 200) {
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
        console.log('传承人API调用失败:', inheritorRes?.message)
        relatedInheritors.value = []
      }
    } catch (error) {
      console.error('传承人API调用异常:', error)
      relatedInheritors.value = []
    }
  } catch (error) {
    console.error('加载详情失败:', error)
  } finally {
        loading.value = false
        // 检查收藏状态
        checkFavoriteStatus()
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

// 检查收藏状态
const checkFavoriteStatus = async () => {
  const id = route.params.id
  if (!id || !currentUser.value) return
  
  try {
    const response = await api.get('/favorite/check', {
      params: {
        userId: currentUser.value.id,
        heritageId: id
      }
    })
    if (response.code === 200) {
      isFavorite.value = response.data
    }
  } catch (error) {
    console.error('检查收藏状态失败:', error)
  }
}

const handleFavorite = async () => {
  if (!currentUser.value) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }
  
  const id = route.params.id
  if (!id) return
  
  try {
    if (isFavorite.value) {
      // 取消收藏
      await api.delete(`/favorite/delete/${favoriteId.value}`)
      ElMessage.success('取消收藏成功')
    } else {
      // 添加收藏
      const response = await api.post('/favorite/add', {
        userId: currentUser.value.id,
        heritageId: id
      })
      if (response.success) {
        if (response.data) {
          favoriteId.value = response.data
        }
        ElMessage.success('收藏成功')
      }
    }
    isFavorite.value = !isFavorite.value
  } catch (error) {
    console.error('收藏操作失败:', error)
    ElMessage.error('操作失败，请重试')
  }
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
  
  // 处理 Base64 编码的图片数据
  if (url.includes('base64,')) {
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
  prompt += ` elegant artistic traditional style high quality landscape`
  
  // 构建图片生成API URL，使用landscape尺寸适合详情页的横幅布局
  return `https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=${prompt}&image_size=landscape`
}
</script>

<style scoped>
.heritage-display {
  padding: 20px;
  min-height: 100vh;
  background-color: var(--background-color);
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

.back-button {
  margin-bottom: 20px;
  transition: all var(--transition-normal);
  border-radius: 30px;
  padding: 8px 24px;
}

.back-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.detail-content {
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: var(--shadow-lg);
  border: 1px solid var(--border-color);
}

.hero-section {
  position: relative;
  height: 400px;
  overflow: hidden;
  transition: all var(--transition-normal);
}

.hero-section:hover {
  transform: scale(1.01);
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
  transition: all var(--transition-normal);
}

.hero-overlay h1 {
  font-size: 2.8rem;
  color: white;
  margin-bottom: 20px;
  text-shadow: 0 2px 4px rgba(0,0,0,0.5);
  background: linear-gradient(45deg, #fff, rgba(255, 255, 255, 0.8));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  animation: textGlow 3s ease-in-out infinite alternate;
  position: relative;
  display: inline-block;
  padding-bottom: 10px;
}

.hero-overlay h1::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 80px;
  height: 3px;
  background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
  border-radius: 2px;
  animation: pulse 2s ease-in-out infinite;
}

.hero-meta {
  display: flex;
  gap: 15px;
  align-items: center;
  margin-top: 10px;
  flex-wrap: wrap;
}

.hero-id {
  color: rgba(255,255,255,0.8);
  font-size: 0.9rem;
  margin-left: auto;
  background: rgba(255, 255, 255, 0.1);
  padding: 4px 12px;
  border-radius: 12px;
  backdrop-filter: blur(10px);
}

.hero-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-slow);
}

.hero-section:hover .hero-image {
  transform: scale(1.05) rotate(1deg);
}

/* 装饰元素 */
.hero-section::before {
  content: '';
  position: absolute;
  top: 20px;
  left: 20px;
  width: 60px;
  height: 60px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  z-index: 2;
  animation: pulse 2s ease-in-out infinite;
}

.hero-section::after {
  content: '';
  position: absolute;
  bottom: 20px;
  right: 20px;
  width: 40px;
  height: 40px;
  border: 2px solid rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  z-index: 2;
  animation: pulse 2s ease-in-out infinite reverse;
}

/* 动画效果 */
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
    transform: scale(1);
    opacity: 0.3;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.6;
  }
}

.info-cards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  padding: 30px;
  background: rgba(138, 26, 26, 0.05);
  border-bottom: 1px solid var(--border-color);
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
  border-radius: 16px;
  padding: 24px;
  display: flex;
  align-items: center;
  gap: 15px;
  box-shadow: var(--shadow-md);
  transition: all var(--transition-normal);
  position: relative;
  overflow: hidden;
  border: 1px solid var(--border-color);
}

.info-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 4px;
  background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
  transform: scaleX(0);
  transition: transform var(--transition-normal);
}

.info-card:hover {
  transform: translateY(-5px);
  box-shadow: var(--shadow-lg);
  border-color: var(--accent-color);
}

.info-card:hover::before {
  transform: scaleX(1);
}

.card-icon {
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 1.8rem;
  transition: all var(--transition-normal);
  position: relative;
  overflow: hidden;
  z-index: 1;
}

.card-icon::before {
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

.info-card:hover .card-icon::before {
  left: 100%;
}

.card-content {
  display: flex;
  flex-direction: column;
  flex: 1;
}

.card-label {
  font-size: 0.9rem;
  color: var(--light-text);
  margin-bottom: 8px;
  font-weight: 500;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.card-value {
  font-size: 1.2rem;
  font-weight: 600;
  color: var(--text-color);
  transition: color var(--transition-normal);
}

.info-card:hover .card-value {
  color: var(--accent-color);
}

.section {
  padding: 40px;
  border-bottom: 1px solid var(--border-color);
  position: relative;
  overflow: hidden;
}

.section::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 4px;
  height: 100%;
  background: linear-gradient(to bottom, var(--primary-color), var(--accent-color));
  transform: scaleY(0);
  transition: transform var(--transition-normal);
}

.section:hover::before {
  transform: scaleY(1);
}

.section:last-of-type {
  border-bottom: none;
}

.section-header {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 25px;
  position: relative;
  z-index: 1;
}

.section-header h2 {
  font-size: 1.8rem;
  color: var(--primary-color);
  margin: 0;
  position: relative;
  padding-bottom: 12px;
  font-weight: 600;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.section-header h2::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 80px;
  height: 3px;
  background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
  border-radius: 2px;
  animation: pulse 2s ease-in-out infinite;
}

.section-header .el-icon {
  font-size: 1.8rem;
  color: var(--accent-color);
  transition: all var(--transition-normal);
}

.section:hover .section-header .el-icon {
  transform: scale(1.1) rotate(5deg);
  color: var(--primary-color);
}

.description-text {
  font-size: 1.1rem;
  line-height: 2;
  color: var(--text-color);
  text-indent: 2em;
  transition: color var(--transition-normal);
  position: relative;
  z-index: 1;
}

.section:hover .description-text {
  color: var(--text-color);
}

.gallery-container {
  padding: 0 20px;
  position: relative;
  z-index: 1;
}

.gallery-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 12px;
  transition: transform var(--transition-normal);
}

.gallery-container:hover .gallery-image {
  transform: scale(1.02);
}

.video-container {
  background: #000;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: var(--shadow-md);
  position: relative;
  z-index: 1;
}

.video-container video {
  width: 100%;
  max-height: 500px;
  border-radius: 16px;
}

.iframe-container {
  width: 100%;
  max-height: 500px;
  position: relative;
  padding-bottom: 56.25%; /* 16:9 比例 */
  height: 0;
  overflow: hidden;
  border-radius: 16px;
}

.iframe-container iframe {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border: 0;
  border-radius: 16px;
}

.video-list {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid var(--border-color);
  position: relative;
  z-index: 1;
}

.video-list h4 {
  margin-bottom: 15px;
  color: var(--text-color);
  font-size: 1.2rem;
  font-weight: 600;
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
  height: 100px;
  background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
  border-radius: 12px;
  cursor: pointer;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  transition: all var(--transition-normal);
  border: 1px solid var(--border-color);
}

.video-thumb:hover {
  transform: translateY(-5px);
  box-shadow: var(--shadow-md);
  border-color: var(--accent-color);
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
  font-size: 1.8rem;
  transition: all var(--transition-normal);
  backdrop-filter: blur(5px);
}

.video-thumb:hover .thumb-overlay {
  background: rgba(0,0,0,0.7);
  transform: scale(1.05);
}

.video-thumb span {
  position: absolute;
  bottom: 10px;
  left: 10px;
  right: 10px;
  color: white;
  font-size: 0.85rem;
  text-align: center;
  text-shadow: 1px 1px 2px rgba(0,0,0,0.8);
  z-index: 1;
  background: rgba(0, 0, 0, 0.5);
  padding: 4px 8px;
  border-radius: 8px;
  backdrop-filter: blur(10px);
}

.inheritor-list {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  position: relative;
  z-index: 1;
}

.inheritor-card {
  background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
  border-radius: 16px;
  padding: 24px;
  color: white;
  cursor: pointer;
  transition: all var(--transition-normal);
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-color);
  position: relative;
  overflow: hidden;
  z-index: 1;
}

.inheritor-card::before {
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

.inheritor-card:hover {
  transform: translateY(-8px) rotateX(5deg);
  box-shadow: var(--shadow-lg);
  border-color: var(--accent-color);
}

.inheritor-card:hover::before {
  left: 100%;
}

.inheritor-avatar {
  width: 80px;
  height: 80px;
  background: rgba(255,255,255,0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2.5rem;
  margin-bottom: 15px;
  transition: all var(--transition-normal);
  backdrop-filter: blur(10px);
}

.inheritor-card:hover .inheritor-avatar {
  transform: scale(1.1) rotate(5deg);
  background: rgba(255,255,255,0.3);
}

.inheritor-info h4 {
  margin: 0 0 8px 0;
  font-size: 1.2rem;
  font-weight: 600;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.inheritor-info p {
  margin: 0 0 12px 0;
  font-size: 0.95rem;
  opacity: 0.9;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}

.inheritor-info .el-tag {
  margin-top: 8px;
  background: rgba(255, 255, 255, 0.2);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.3);
  backdrop-filter: blur(10px);
  transition: all var(--transition-normal);
}

.inheritor-card:hover .inheritor-info .el-tag {
  background: rgba(255, 255, 255, 0.3);
  transform: scale(1.05);
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  padding: 40px;
  background: rgba(138, 26, 26, 0.05);
  border-top: 1px solid var(--border-color);
  position: relative;
  overflow: hidden;
}

.action-buttons::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 4px;
  background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
}

.action-buttons .el-button {
  border-radius: 30px;
  padding: 10px 30px;
  font-size: 1.1rem;
  font-weight: 500;
  transition: all var(--transition-normal);
  position: relative;
  overflow: hidden;
  z-index: 1;
}

.action-buttons .el-button::before {
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

.action-buttons .el-button:hover::before {
  left: 100%;
}

.action-buttons .el-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.2);
}

/* 动画效果 */
@keyframes pulse {
  0%, 100% {
    opacity: 1;
    transform: scaleX(1);
  }
  50% {
    opacity: 0.8;
    transform: scaleX(1.1);
  }
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .inheritor-list {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 992px) {
  .inheritor-list {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .section {
    padding: 30px;
  }
  
  .action-buttons {
    padding: 30px;
  }
}

@media (max-width: 768px) {
  .inheritor-list {
    grid-template-columns: 1fr;
  }
  
  .section {
    padding: 20px;
  }
  
  .action-buttons {
    flex-direction: column;
    align-items: center;
    padding: 20px;
  }
  
  .action-buttons .el-button {
    width: 200px;
    text-align: center;
  }
  
  .video-thumbs {
    flex-direction: column;
  }
  
  .video-thumb {
    min-width: 100%;
    max-width: 100%;
  }
}

@media (max-width: 576px) {
  .section-header h2 {
    font-size: 1.5rem;
  }
  
  .description-text {
    font-size: 1rem;
    line-height: 1.8;
  }
  
  .inheritor-card {
    padding: 20px;
  }
  
  .inheritor-avatar {
    width: 60px;
    height: 60px;
    font-size: 2rem;
  }
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
