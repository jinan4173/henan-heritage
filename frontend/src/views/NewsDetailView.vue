<template>
  <div class="news-detail-view">
    <el-breadcrumb separator="/" class="breadcrumb">
      <el-breadcrumb-item><a href="/">首页</a></el-breadcrumb-item>
      <el-breadcrumb-item v-if="isNewsPage"><a href="/news">文化资讯</a></el-breadcrumb-item>
      <el-breadcrumb-item v-else-if="isActivityPage"><a href="/activity">活动信息</a></el-breadcrumb-item>
      <el-breadcrumb-item v-else-if="isNoticePage"><a href="/notice">公告信息</a></el-breadcrumb-item>
      <el-breadcrumb-item>{{ detailTitle }}</el-breadcrumb-item>
    </el-breadcrumb>
    
    <div class="news-detail">
      <h1 class="news-title">{{ news.title }}</h1>
      <div class="news-meta">
        <span class="news-date">{{ formatDate(news.createdAt) }}</span>
        <span class="news-source" v-if="news.source">来源：{{ news.source }}</span>
      </div>
      
      <div class="news-image" v-if="news.coverImage">
        <img :src="news.coverImage" :alt="news.title" />
      </div>
      
      <div class="news-content" v-if="news.content" v-html="news.content">
      </div>
      <div class="empty-content" v-else>
        <el-empty description="暂无内容" />
      </div>
      
      <div class="news-actions">
        <el-button type="primary" @click="goBack">返回列表</el-button>
        <el-button type="success" @click="openRegistrationDialog" v-if="isActivityPage">立即报名</el-button>
        <el-button :type="isFavorite ? 'warning' : 'info'" @click="toggleFavorite">
          <el-icon v-if="isFavorite"><StarFilled /></el-icon>
          <el-icon v-else><Star /></el-icon>
          {{ isFavorite ? '已收藏' : '收藏' }}
        </el-button>
        <el-button type="info" @click="shareContent">
          <el-icon><Share /></el-icon>
          分享
        </el-button>
      </div>
      
      <!-- 评论区 -->
      <div class="comments-section">
        <h3 class="section-title">评论 ({{ comments.length }})</h3>
        
        <!-- 评论输入 -->
        <div class="comment-input-section" v-if="isLoggedIn">
          <el-input
            v-model="commentContent"
            type="textarea"
            :rows="3"
            placeholder="写下你的评论..."
          ></el-input>
          <div class="comment-actions">
            <el-button type="primary" @click="submitComment">发表评论</el-button>
          </div>
        </div>
        <div class="login-prompt" v-else>
          <el-button type="primary" @click="goToLogin">登录后评论</el-button>
        </div>
        
        <!-- 评论列表 -->
        <div class="comments-list">
          <div class="comment-item" v-for="comment in comments" :key="comment.id">
            <div class="comment-header">
              <span class="comment-author">{{ comment.username || '匿名用户' }}</span>
              <span class="comment-time">{{ formatDate(comment.createTime) }}</span>
              <el-button type="text" size="small" @click="deleteComment(comment.id)" v-if="isCommentOwner(comment)">删除</el-button>
            </div>
            <div class="comment-content">{{ comment.content }}</div>
          </div>
          <div class="empty-comments" v-if="comments.length === 0">
            <el-empty description="暂无评论" />
          </div>
        </div>
      </div>
      
      <!-- 报名对话框 -->
      <el-dialog v-model="registrationDialogVisible" :title="news.title + ' - 活动报名'" width="500px">
        <el-form :model="registrationForm" label-width="80px">
          <el-form-item label="姓名" required>
            <el-input v-model="registrationForm.name" placeholder="请输入您的姓名"></el-input>
          </el-form-item>
          <el-form-item label="电话" required>
            <el-input v-model="registrationForm.phone" placeholder="请输入您的电话"></el-input>
          </el-form-item>

          <el-form-item label="人数" required>
            <el-input-number v-model="registrationForm.peopleCount" :min="1" :max="10" label="人数"></el-input-number>
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="registrationForm.remark" type="textarea" :rows="3" placeholder="请输入备注信息"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="registrationDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitRegistration">提交报名</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
    
    <!-- 返回顶部按钮 -->
    <el-backtop :right="40" :bottom="40" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import api from '../api/index.js'
import { Star, StarFilled, Share } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const news = ref({})
const registrationDialogVisible = ref(false)
const registrationForm = ref({
  name: '',
  phone: '',
  email: '',
  peopleCount: 1,
  remark: ''
})

// 收藏相关
const isFavorite = ref(false)
const favoriteId = ref(null)

// 评论相关
const comments = ref([])
const commentContent = ref('')

// 计算属性：用户是否已登录
const isLoggedIn = computed(() => {
  try {
    const userStr = localStorage.getItem('user')
    return !!userStr
  } catch (e) {
    console.error('localStorage 访问失败:', e)
    return false
  }
})

// 计算属性：当前用户
const currentUser = computed(() => {
  try {
    const userStr = localStorage.getItem('user')
    if (userStr) {
      return JSON.parse(userStr)
    }
  } catch (e) {
    console.error('localStorage 访问失败:', e)
  }
  return null
})

// 计算属性：根据当前路由判断是哪个页面
const isNewsPage = computed(() => {
  return route.path.startsWith('/news/')
})

const isActivityPage = computed(() => {
  return route.path.startsWith('/activity/')
})

const isNoticePage = computed(() => {
  return route.path.startsWith('/notice/')
})

// 计算属性：根据当前路由显示不同的详情页标题
const detailTitle = computed(() => {
  if (isNewsPage.value) {
    return '资讯详情'
  } else if (isActivityPage.value) {
    return '活动详情'
  } else if (isNoticePage.value) {
    return '公告详情'
  }
  return '详情'
})

onMounted(() => {
  loadNewsDetail()
})

const loadNewsDetail = async () => {
  const id = route.params.id
  if (!id) {
    goBack()
    return
  }
  
  try {
    const response = await api.get(`/culture-news/get/${id}`)
    if (response.success) {
      news.value = response.data
      console.log('加载详情成功:', news.value)
      // 加载评论
      await loadComments()
      // 检查收藏状态
      await checkFavoriteStatus()
    }
  } catch (error) {
    console.error('加载资讯详情失败:', error)
  }
}

// 加载评论
const loadComments = async () => {
  const id = route.params.id
  if (!id) return
  
  try {
    const response = await api.get('/comment/listByMedia', { params: { mediaId: id } })
    if (response.code === 200) {
      comments.value = response.data || []
    }
  } catch (error) {
    console.error('加载评论失败:', error)
  }
}

// 检查收藏状态
const checkFavoriteStatus = async () => {
  const id = route.params.id
  if (!id || !currentUser.value) return
  
  try {
    // 先检查是否已收藏
    const response = await api.get('/favorite/check', {
      params: {
        userId: currentUser.value.id,
        mediaId: id
      }
    })
    if (response.success) {
      isFavorite.value = response.data
      
      // 如果已收藏，获取收藏ID
      if (isFavorite.value) {
        const favoritesResponse = await api.get('/favorite/listByUser', {
          params: { userId: currentUser.value.id }
        })
        if (favoritesResponse.success) {
          const favorite = favoritesResponse.data.find(fav => fav.mediaId === id)
          if (favorite) {
            favoriteId.value = favorite.id
          }
        }
      }
    }
  } catch (error) {
    console.error('检查收藏状态失败:', error)
  }
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString('zh-CN')
}

const goBack = () => {
  if (isNewsPage.value) {
    router.push('/news')
  } else if (isActivityPage.value) {
    router.push('/activity')
  } else if (isNoticePage.value) {
    router.push('/notice')
  } else {
    router.push('/')
  }
}

const openRegistrationDialog = () => {
  registrationForm.value = {
    name: '',
    phone: '',
    peopleCount: 1,
    remark: ''
  }
  registrationDialogVisible.value = true
}

const submitRegistration = async () => {
  if (!news.value.id) {
    ElMessage.error('活动信息不存在')
    return
  }
  
  if (!registrationForm.value.name || !registrationForm.value.phone) {
    ElMessage.warning('请填写姓名和电话')
    return
  }
  
  try {
    const response = await api.post('/activity/registration/add', {
      activityId: news.value.id,
      ...registrationForm.value
    })
    
    ElMessage.success('报名成功！我们会尽快与您联系')
    registrationDialogVisible.value = false
  } catch (error) {
    console.error('报名失败:', error)
    ElMessage.error('报名失败，请稍后重试')
  }
}

// 切换收藏状态
const toggleFavorite = async () => {
  if (!currentUser.value) {
    goToLogin()
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
      console.log('开始添加收藏，用户ID:', currentUser.value.id, '媒体ID:', id)
      const response = await api.post('/favorite/add', {
        userId: currentUser.value.id,
        mediaId: id
      })
      console.log('收藏 API 响应:', response)
      if (response.success) {
        console.log('收藏成功，返回数据:', response.data)
        if (response.data) {
          favoriteId.value = response.data
        }
        ElMessage.success('收藏成功')
      } else {
        console.log('收藏失败，响应:', response)
        ElMessage.error('收藏失败，请稍后重试')
      }
    }
    isFavorite.value = !isFavorite.value
  } catch (error) {
    console.error('收藏操作失败:', error)
    ElMessage.error('操作失败，请稍后重试')
  }
}

// 分享内容
const shareContent = () => {
  const url = window.location.href
  const title = news.value.title
  
  if (navigator.share) {
    // 使用Web Share API
    navigator.share({
      title: title,
      url: url
    }).catch(err => {
      console.error('分享失败:', err)
      fallbackShare(url, title)
    })
  } else {
    // 回退方案：复制链接
    fallbackShare(url, title)
  }
}

// 回退分享方案
const fallbackShare = (url, title) => {
  navigator.clipboard.writeText(url).then(() => {
    ElMessage.success('链接已复制到剪贴板')
  }).catch(err => {
    console.error('复制失败:', err)
    ElMessage.error('复制失败，请手动复制链接')
  })
}

// 提交评论
const submitComment = async () => {
  if (!currentUser.value) {
    goToLogin()
    return
  }
  
  if (!commentContent.value.trim()) {
    ElMessage.warning('请输入评论内容')
    return
  }
  
  const id = route.params.id
  if (!id) return
  
  try {
    const response = await api.post('/comment/add', {
      userId: currentUser.value.id,
      mediaId: id,
      content: commentContent.value.trim()
    })
    if (response.code === 200) {
      ElMessage.success('评论发表成功')
      commentContent.value = ''
      await loadComments()
    }
  } catch (error) {
    console.error('发表评论失败:', error)
    ElMessage.error('发表评论失败，请稍后重试')
  }
}

// 删除评论
const deleteComment = async (commentId) => {
  try {
    const response = await api.delete(`/comment/delete/${commentId}`)
    if (response.code === 200) {
      ElMessage.success('评论删除成功')
      await loadComments()
    }
  } catch (error) {
    console.error('删除评论失败:', error)
    ElMessage.error('删除评论失败，请稍后重试')
  }
}

// 检查是否是评论的所有者
const isCommentOwner = (comment) => {
  return currentUser.value && comment.userId === currentUser.value.id
}

// 跳转到登录页面
const goToLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
.news-detail-view {
  padding: 0;
  max-width: 1000px;
  margin: 0 auto;
  background-color: var(--background-color);
  min-height: 100vh;
}

.breadcrumb {
  margin: 20px 20px 0;
  padding: 12px 0;
  font-size: 0.85rem;
  background: var(--card-background);
  padding: 12px 20px;
  border-radius: 8px;
  box-shadow: var(--shadow-sm);
  animation: fadeIn 0.5s ease;
}

.news-detail {
  background: var(--card-background);
  margin: 20px;
  padding: 40px;
  border-radius: 12px;
  box-shadow: var(--shadow-md);
  transition: all var(--transition-normal);
  border: 1px solid var(--border-color);
  animation: fadeInUp 0.8s ease;
}

.news-detail:hover {
  box-shadow: var(--shadow-lg);
  transform: translateY(-4px);
}

.news-title {
  font-size: 2.2rem;
  font-weight: 600;
  color: var(--primary-color);
  margin-bottom: 24px;
  text-align: center;
  position: relative;
  padding-bottom: 20px;
  line-height: 1.2;
  background: linear-gradient(45deg, var(--primary-color), var(--accent-color));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.news-title::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 80px;
  height: 3px;
  background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
  border-radius: 2px;
}

.news-meta {
  text-align: center;
  margin-bottom: 32px;
  color: var(--light-text);
  font-size: 0.9rem;
  display: flex;
  justify-content: center;
  gap: 20px;
  flex-wrap: wrap;
  padding: 16px;
  background: rgba(0, 102, 204, 0.05);
  border-radius: 8px;
}

.news-source {
  display: inline-block;
  padding: 4px 12px;
  background: var(--accent-color);
  color: white;
  border-radius: 12px;
  font-size: 0.8rem;
}

.news-image {
  margin-bottom: 32px;
  text-align: center;
  overflow: hidden;
  border-radius: 12px;
  box-shadow: var(--shadow-md);
  transition: all var(--transition-normal);
  position: relative;
}

.news-image:hover {
  transform: scale(1.02);
  box-shadow: var(--shadow-lg);
}

.news-image img {
  width: 100%;
  height: auto;
  border-radius: 12px;
  transition: transform var(--transition-slow);
}

.news-image:hover img {
  transform: scale(1.05);
}

.news-content {
  line-height: 1.8;
  color: var(--text-color);
  margin-bottom: 32px;
  font-size: 1.1rem;
  padding: 24px;
  background: rgba(248, 245, 240, 0.8);
  border-radius: 8px;
  border-left: 4px solid var(--accent-color);
}

.news-content p {
  margin-bottom: 20px;
  text-align: justify;
  animation: fadeIn 0.5s ease;
}

.news-content img {
  max-width: 100%;
  height: auto;
  margin: 24px auto;
  border-radius: 8px;
  box-shadow: var(--shadow-md);
  display: block;
  transition: transform var(--transition-normal);
}

.news-content img:hover {
  transform: scale(1.02);
  box-shadow: var(--shadow-lg);
}

.empty-content {
  text-align: center;
  padding: 80px 0;
  background: rgba(248, 245, 240, 0.8);
  border-radius: 8px;
  margin: 20px 0;
}

.news-actions {
  text-align: center;
  margin-top: 32px;
  margin-bottom: 32px;
  padding-top: 24px;
  border-top: 1px solid var(--border-color);
  display: flex;
  justify-content: center;
  gap: 16px;
  flex-wrap: wrap;
}

.news-actions .el-button {
  transition: all var(--transition-normal);
  border-radius: 20px;
  padding: 8px 20px;
  font-weight: 500;
}

.news-actions .el-button:hover {
  transform: translateY(-3px);
  box-shadow: var(--shadow-md);
}

/* 评论区样式 */
.comments-section {
  margin-top: 40px;
  padding-top: 32px;
  border-top: 1px solid var(--border-color);
  animation: fadeInUp 1s ease 0.3s both;
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--primary-color);
  margin-bottom: 24px;
  padding-bottom: 12px;
  border-bottom: 2px solid var(--accent-color);
  position: relative;
}

.section-title::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 60px;
  height: 2px;
  background: var(--primary-color);
}

.comment-input-section {
  margin-bottom: 32px;
  background: rgba(248, 245, 240, 0.8);
  padding: 24px;
  border-radius: 12px;
  transition: all var(--transition-normal);
}

.comment-input-section:hover {
  box-shadow: var(--shadow-md);
}

.comment-actions {
  text-align: right;
  margin-top: 16px;
  border-top: none;
  padding-top: 0;
  margin-bottom: 0;
}

.login-prompt {
  text-align: center;
  padding: 40px 0;
  background: rgba(248, 245, 240, 0.8);
  border-radius: 12px;
  margin-bottom: 32px;
  transition: all var(--transition-normal);
}

.login-prompt:hover {
  box-shadow: var(--shadow-md);
}

.comments-list {
  margin-top: 32px;
}

.comment-item {
  background: var(--card-background);
  padding: 24px;
  border-radius: 12px;
  margin-bottom: 24px;
  transition: all var(--transition-normal);
  border: 1px solid var(--border-color);
  position: relative;
  overflow: hidden;
}

.comment-item::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 4px;
  height: 100%;
  background: linear-gradient(180deg, var(--primary-color), var(--accent-color));
}

.comment-item:hover {
  box-shadow: var(--shadow-lg);
  transform: translateY(-4px);
  border-color: var(--accent-color);
}

.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.comment-author {
  font-weight: 600;
  color: var(--accent-color);
  font-size: 1rem;
}

.comment-time {
  color: var(--light-text);
  font-size: 0.8rem;
  background: rgba(0, 102, 204, 0.05);
  padding: 2px 8px;
  border-radius: 10px;
}

.comment-content {
  line-height: 1.6;
  color: var(--text-color);
  margin-top: 12px;
  font-size: 1rem;
}

.empty-comments {
  text-align: center;
  padding: 60px 0;
  background: rgba(248, 245, 240, 0.8);
  border-radius: 12px;
  margin: 20px 0;
}

/* 动画效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
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
@media (max-width: 768px) {
  .news-detail {
    margin: 16px;
    padding: 32px 20px;
  }
  
  .news-title {
    font-size: 1.8rem;
    padding-bottom: 16px;
  }
  
  .news-title::after {
    width: 60px;
    height: 2px;
  }
  
  .news-meta {
    gap: 12px;
    font-size: 0.85rem;
    padding: 12px;
  }
  
  .news-image {
    margin-bottom: 24px;
  }
  
  .news-content {
    font-size: 1rem;
    line-height: 1.7;
    padding: 16px;
  }
  
  .news-actions {
    flex-direction: column;
    align-items: center;
  }
  
  .news-actions .el-button {
    width: 200px;
  }
  
  .comments-section {
    margin-top: 32px;
    padding-top: 24px;
  }
  
  .comment-item {
    padding: 20px;
  }
  
  .comment-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
}

@media (max-width: 480px) {
  .breadcrumb {
    margin: 12px;
    padding: 10px 16px;
  }
  
  .news-detail {
    margin: 12px;
    padding: 24px 16px;
  }
  
  .news-title {
    font-size: 1.5rem;
    padding-bottom: 12px;
  }
  
  .news-meta {
    gap: 8px;
    font-size: 0.8rem;
  }
  
  .news-content {
    font-size: 0.95rem;
    padding: 12px;
  }
  
  .news-actions .el-button {
    width: 100%;
    margin: 8px 0;
  }
  
  .comment-input-section {
    padding: 16px;
    margin-bottom: 24px;
  }
  
  .comment-item {
    padding: 16px;
  }
  
  .comments-section {
    margin-top: 24px;
    padding-top: 20px;
  }
}
</style>