<template>
  <div class="media-detail-view">
    <div class="container">
      <!-- 返回按钮 -->
      <el-button type="primary" plain @click="goBack" class="back-button">
        <el-icon><ArrowLeft /></el-icon> 返回列表
      </el-button>

      <!-- 加载状态 -->
      <el-skeleton v-if="loading" :rows="10" animated />

      <!-- 详情内容 -->
      <div v-else-if="mediaItem" class="detail-content">
        <!-- 媒体展示 -->
        <div class="media-display">
          <div v-if="mediaType === 'image'" class="image-container">
            <img :src="mediaItem.url" :alt="mediaItem.title" class="detail-image" />
          </div>
          <div v-else-if="mediaType === 'video'" class="video-container">
            <div class="video-player">
              <video :src="mediaItem.videoUrl || ''" controls preload="metadata" class="detail-video">
                您的浏览器不支持视频播放
              </video>
            </div>
          </div>
        </div>

        <!-- 媒体信息 -->
        <div class="media-info">
          <h1>{{ mediaItem.title }}</h1>
          <p class="description">{{ mediaItem.description }}</p>
          
          <!-- 操作按钮 -->
          <div class="action-buttons">
            <el-button type="primary" @click="handleShare">
              <el-icon><Share /></el-icon> 分享
            </el-button>
            <el-button type="success" @click="handleFavorite">
              <el-icon><Star /></el-icon> {{ isFavorite ? '已收藏' : '收藏' }}
            </el-button>
          </div>
        </div>

        <!-- 评论区 -->
        <div class="comments-section">
          <h2>评论</h2>
          
          <!-- 评论输入 -->
          <div class="comment-input">
            <el-input
              v-model="newComment"
              type="textarea"
              :rows="3"
              placeholder="写下您的评论..."
            />
            <el-button type="primary" @click="submitComment" class="submit-comment">
              发表评论
            </el-button>
          </div>

          <!-- 评论列表 -->
          <div class="comments-list">
            <div v-for="(comment, index) in comments" :key="index" class="comment-item">
              <div class="comment-avatar">
                <el-icon><UserFilled /></el-icon>
              </div>
              <div class="comment-content">
                <div class="comment-header">
                  <span class="comment-author">{{ comment.author }}</span>
                  <span class="comment-time">{{ comment.time }}</span>
                </div>
                <p class="comment-text">{{ comment.content }}</p>
              </div>
            </div>
            
            <div v-if="comments.length === 0" class="no-comments">
              <el-empty description="暂无评论" />
            </div>
          </div>
        </div>
      </div>

      <!-- 错误状态 -->
      <div v-else class="error-state">
        <el-empty description="媒体不存在">
          <el-button type="primary" @click="goBack">返回列表</el-button>
        </el-empty>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ArrowLeft, Share, Star, UserFilled } from '@element-plus/icons-vue'
import { ElButton, ElSkeleton, ElEmpty, ElInput, ElMessage } from 'element-plus'
import api from '../api/index.js'

const router = useRouter()
const route = useRoute()
const mediaType = ref('')
const mediaIndex = ref(0)
const mediaItem = ref(null)
const loading = ref(true)
const isFavorite = ref(false)
const newComment = ref('')
const comments = ref([])
const userId = ref(1) // 模拟用户ID，实际应从登录状态获取

// 宣传媒体数据
const promotionMedia = ref([])

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

onMounted(() => {
  loadMediaDetail()
  loadComments()
  checkFavoriteStatus()
})

const loadMediaDetail = async () => {
  loading.value = true
  try {
    // 先加载宣传媒体数据
    await loadPromotionMedia()
    
    const { type, index } = route.params
    mediaType.value = type
    mediaIndex.value = parseInt(index)
    
    // 从后端数据中获取媒体信息
    if (promotionMedia.value.length > mediaIndex.value) {
      const media = promotionMedia.value[mediaIndex.value]
      mediaItem.value = {
        id: media.id,
        url: media.cover_image,
        title: media.title,
        description: media.content,
        videoUrl: media.link_url
      }
    }
  } catch (error) {
    console.error('加载媒体详情失败:', error)
  } finally {
    loading.value = false
  }
}

const loadComments = async () => {
  try {
    // 从后端获取评论
    const response = await api.get('/comment/listByMedia', {
      params: { mediaId: mediaItem.value.id }
    })
    if (response && response.code === 200) {
      comments.value = response.data.map(comment => ({
        id: comment.id,
        userId: comment.userId,
        author: comment.userId ? '用户' + comment.userId : '匿名用户',
        time: comment.createTime ? new Date(comment.createTime).toLocaleString('zh-CN') : new Date().toLocaleString('zh-CN'),
        content: comment.content
      }))
    } else {
      comments.value = []
    }
  } catch (error) {
    console.error('加载评论失败:', error)
    comments.value = []
  }
}

const checkFavoriteStatus = async () => {
  try {
    // 从后端检查收藏状态
    const response = await api.get('/favorite/check', {
      params: {
        userId: userId.value,
        mediaId: mediaItem.value.id
      }
    })
    if (response && response.code === 200) {
      isFavorite.value = response.data
    }
  } catch (error) {
    console.error('检查收藏状态失败:', error)
  }
}

const goBack = () => {
  router.push('/promotion')
}

const handleShare = () => {
  if (navigator.share) {
    navigator.share({
      title: mediaItem.value.title,
      text: mediaItem.value.description,
      url: window.location.href
    })
  } else {
    navigator.clipboard.writeText(window.location.href)
    ElMessage.success('链接已复制到剪贴板')
  }
}

const handleFavorite = async () => {
  try {
    if (isFavorite.value) {
      // 取消收藏
      await api.delete(`/favorite/delete/${mediaItem.value.id}`)
      ElMessage.success('取消收藏成功')
    } else {
      // 添加收藏
      const response = await api.post('/favorite/add', {
        userId: userId.value,
        mediaId: mediaItem.value.id
      })
      if (response.code === 200) {
        ElMessage.success('收藏成功')
      } else {
        ElMessage.error('收藏失败')
      }
    }
    isFavorite.value = !isFavorite.value
  } catch (error) {
    console.error('操作收藏失败:', error)
    ElMessage.error('操作失败，请重试')
  }
}

const submitComment = async () => {
  if (!newComment.value.trim()) {
    ElMessage.warning('请输入评论内容')
    return
  }
  
  try {
    // 实际提交评论到后端
    const response = await api.post('/comment/add', {
      userId: userId.value,
      mediaId: mediaItem.value.id,
      content: newComment.value,
      status: 1
    })
    
    if (response.code === 200) {
      // 重新获取评论列表
      await loadComments()
      newComment.value = ''
      ElMessage.success('评论发表成功')
    } else {
      ElMessage.error('发表失败: ' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('发表评论失败:', error)
    ElMessage.error('发表失败，请重试')
  }
}
</script>

<style scoped>
.media-detail-view {
  min-height: 100vh;
  background-color: #f5f7fa;
  padding: 20px;
}

.container {
  max-width: 1000px;
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
  padding: 30px;
}

.media-display {
  margin-bottom: 30px;
}

.image-container {
  text-align: center;
  margin-bottom: 30px;
}

.detail-image {
  max-width: 100%;
  max-height: 600px;
  object-fit: contain;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.video-container {
  margin-bottom: 30px;
}

.video-player {
  position: relative;
  padding-bottom: 56.25%; /* 16:9 比例 */
  height: 0;
  overflow: hidden;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.detail-video {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.media-info {
  margin-bottom: 40px;
}

.media-info h1 {
  font-size: 2rem;
  margin-bottom: 15px;
  color: #333;
}

.description {
  font-size: 1.1rem;
  line-height: 1.6;
  color: #666;
  margin-bottom: 20px;
}

.action-buttons {
  display: flex;
  gap: 20px;
  margin-top: 20px;
}

.comments-section {
  border-top: 1px solid #eee;
  padding-top: 30px;
}

.comments-section h2 {
  font-size: 1.5rem;
  margin-bottom: 20px;
  color: #333;
}

.comment-input {
  margin-bottom: 30px;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.submit-comment {
  align-self: flex-end;
}

.comments-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.comment-item {
  display: flex;
  gap: 15px;
  padding: 20px;
  background: #f9f9f9;
  border-radius: 8px;
}

.comment-avatar {
  width: 40px;
  height: 40px;
  background: #667eea;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 1.2rem;
  flex-shrink: 0;
}

.comment-content {
  flex: 1;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.comment-author {
  font-weight: 600;
  color: #333;
}

.comment-time {
  font-size: 0.9rem;
  color: #999;
}

.comment-text {
  line-height: 1.5;
  color: #666;
  margin: 0;
}

.no-comments {
  margin: 40px 0;
  text-align: center;
}

@media (max-width: 768px) {
  .detail-content {
    padding: 20px;
  }
  
  .media-info h1 {
    font-size: 1.5rem;
  }
  
  .action-buttons {
    flex-direction: column;
  }
  
  .submit-comment {
    align-self: stretch;
  }
}
</style>