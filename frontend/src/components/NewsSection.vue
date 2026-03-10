<template>
  <div class="news-section">
    <h2>最新动态</h2>

    <div class="news-list">
      <el-card
        v-for="(news, index) in newsList"
        :key="news.id"
        class="news-card"
        :class="index % 2 === 0 ? 'left-image' : 'right-image'"
        @click="goToNewsDetail(news.id)"
      >
        <div class="news-card-wrapper">
          <div class="news-image" v-if="news.coverImage || getDefaultNewsCover(news)">
            <img :src="news.coverImage || getDefaultNewsCover(news)" :alt="news.title" />
          </div>
          <div class="news-text-content">
            <div class="news-header">
              <span class="news-title">{{ news.title }}</span>
              <span :class="['news-tag', news.type === 2 ? 'activity-tag' : news.type === 3 ? 'announcement-tag' : 'news-tag']">
                {{ news.type === 1 ? '资讯' : news.type === 2 ? '活动' : '公告' }}
              </span>
            </div>
            <div class="news-description" v-html="getNewsDescription(news)"></div>
            <div class="news-footer">
              <span class="news-date">{{ formatDate(news.createTime).full }}</span>
              <el-button 
                v-if="news.type === 2" 
                type="success" 
                size="small"
                @click.stop="showRegisterDialog(news)"
              >
                立即报名
              </el-button>
            </div>
          </div>
        </div>
      </el-card>
    </div>

    <div class="news-more">
      <el-button type="primary" @click="goToNewsList">查看更多</el-button>
    </div>
    
    <!-- 报名弹窗 -->
      <el-dialog
      v-model="registerDialogVisible"
      title="活动报名"
      width="500px"
    >
      <div class="register-form">
        <h3>{{ currentNews?.title }}</h3>
        <p class="activity-date">活动时间: {{ formatDate(currentNews?.createTime).full }}</p>
        <el-form :model="registerForm" label-width="80px">
          <el-form-item label="姓名">
            <el-input v-model="registerForm.name" placeholder="请输入姓名" />
          </el-form-item>
          <el-form-item label="电话">
            <el-input v-model="registerForm.phone" placeholder="请输入手机号码" />
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="registerForm.email" placeholder="请输入邮箱地址" />
          </el-form-item>
          <el-form-item label="备注">
            <el-input 
              v-model="registerForm.remark" 
              type="textarea" 
              placeholder="请输入备注信息" 
              :rows="3"
            />
          </el-form-item>
        </el-form>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="registerDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitRegister">提交报名</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api/index.js'
import { ElMessage } from 'element-plus'

const router = useRouter()
const newsList = ref([])

// 报名弹窗相关状态
const registerDialogVisible = ref(false)
const currentNews = ref(null)
const registerForm = ref({
  name: '',
  phone: '',
  email: '',
  remark: ''
})

// 加载新闻数据
const loadNewsData = async () => {
  try {
    console.log('开始加载文化资讯和活动数据...')
    // 加载文化资讯（包括新闻和活动）
    const data = await api.get('/culture-news/list', { params: { status: 1 } })
    console.log('文化资讯数据:', data)
    if (data && data.success === true) {
      // 取最新的5条数据，包括新闻和活动
      newsList.value = (data.data || []).slice(0, 5)
      console.log('过滤后的资讯列表:', newsList.value)
    } else {
      console.error('文化资讯数据加载失败:', data)
      newsList.value = []
    }
  } catch (error) {
    console.error('加载文化资讯数据失败:', error)
    newsList.value = []
  }
}

onMounted(() => {
  loadNewsData()
})

// 跳转到新闻详情
const goToNewsDetail = (id) => {
  if (id) {
    router.push(`/news/${id}`)
  }
}

// 跳转到新闻列表
const goToNewsList = () => {
  router.push('/news')
}

// 格式化日期
const formatDate = (dateStr) => {
  if (!dateStr) return { day: '01', month: '2026-01-01', full: '2026-01-01' }
  
  const date = new Date(dateStr)
  if (isNaN(date.getTime())) return { day: '01', month: '2026-01-01', full: '2026-01-01' }
  
  const day = String(date.getDate()).padStart(2, '0')
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const year = date.getFullYear()
  
  return { 
    day, 
    month: `${month}-${year}`,
    full: `${year}-${month}-${day}`
  }
}

// 为没有封面图片的新闻和活动生成默认封面
const getDefaultNewsCover = (news) => {
  // 构建基于新闻信息的prompt
  let prompt = `Chinese cultural news ${encodeURIComponent(news.title)}`
  if (news.type === 1) {
    prompt += ` news report`
  } else if (news.type === 2) {
    prompt += ` cultural activity event`
  } else {
    prompt += ` announcement`
  }
  prompt += ` professional journalistic style high quality`
  
  // 构建图片生成API URL
  return `https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=${prompt}&image_size=landscape_16_9`
}

// 获取新闻描述
const getNewsDescription = (news) => {
  if (news.summary) {
    return news.summary
  } else if (news.content) {
    // 保留基础富文本排版，首页只截断长度
    const plainText = news.content.replace(/<[^>]*>/g, '')
    const shortText = plainText.length > 120 ? plainText.substring(0, 120) + '...' : plainText
    return shortText
  } else {
    return '暂无内容'
  }
}

// 显示报名弹窗
const showRegisterDialog = (news) => {
  currentNews.value = news
  // 重置表单
  registerForm.value = {
    name: '',
    phone: '',
    email: '',
    remark: ''
  }
  registerDialogVisible.value = true
}

// 提交报名
const submitRegister = async () => {
  // 简单表单验证
  if (!registerForm.value.name) {
    ElMessage.error('请输入姓名')
    return
  }
  if (!registerForm.value.phone) {
    ElMessage.error('请输入电话')
    return
  }
  if (!registerForm.value.email) {
    ElMessage.error('请输入邮箱')
    return
  }
  
  try {
    // 调用API提交报名信息
    const response = await api.post('/activity-registration/add', {
      activityId: currentNews.value.id,
      name: registerForm.value.name,
      phone: registerForm.value.phone,
      email: registerForm.value.email,
      peopleCount: 1,
      remark: registerForm.value.remark
    })
    
    if (response.code === 200) {
      ElMessage.success('报名成功！')
      registerDialogVisible.value = false
    } else {
      ElMessage.error('报名失败: ' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('提交报名失败:', error)
    ElMessage.error('报名失败，请重试')
  }
}
</script>

<style scoped>
.news-section {
  margin: 0;
  padding: 40px;
  background: var(--card-background);
  border-radius: 0;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
  width: 100%;
}

.news-section h2 {
  font-size: 2rem;
  margin-bottom: 30px;
  color: var(--primary-color);
  text-align: center;
  position: relative;
  padding-bottom: 15px;
}

.news-section h2::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 80px;
  height: 3px;
  background-color: var(--accent-color);
  border-radius: 2px;
}

.news-list {
  margin-bottom: 40px;
}

.news-card {
  margin-bottom: 20px;
  border-radius: 0;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  min-height: 240px;
  display: flex;
  flex-direction: column;
}

.news-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
}

.news-card-wrapper {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  padding: 15px;
  flex: 1;
}

.news-image {
  flex: 0 0 300px;
  overflow: hidden;
}

.news-image img {
  width: 100%;
  height: 200px;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.news-card:hover .news-image img {
  transform: scale(1.05);
}

.news-text-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding-left: 20px;
  min-height: 0;
}

.news-header {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 10px;
}

.news-title {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
  line-height: 1.4;
}

.news-tag {
  padding: 6px 14px;
  border-radius: 16px;
  font-size: 0.8rem;
  font-weight: 500;
  flex-shrink: 0;
  transition: all 0.3s ease;
}

.news-tag.news-tag {
  background: rgba(59, 130, 246, 0.1);
  color: #3b82f6;
}

.news-tag.activity-tag {
  background: rgba(82, 196, 26, 0.1);
  color: #52c41a;
}

.news-tag.announcement-tag {
  background: rgba(255, 159, 64, 0.1);
  color: #ff9f40;
}

.news-description {
  margin: 0;
  line-height: 1.6;
  color: #666;
  flex: 1;
  margin-bottom: 15px;
  min-height: 60px;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.news-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.85rem;
  color: var(--light-text);
  margin-top: 5px;
  flex-wrap: wrap;
  gap: 10px;
}

.news-meta .news-date {
  background: none;
  color: var(--light-text);
  width: auto;
  padding: 0;
  flex-direction: row;
  gap: 5px;
}

.register-button {
  background: var(--primary-color);
  border-color: var(--primary-color);
  font-size: 0.8rem;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.register-button:hover {
  background: #C53030;
  border-color: #C53030;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(229, 62, 62, 0.3);
}

/* 报名弹窗样式 */
.register-form {
  padding: 20px 0;
}

.register-form h3 {
  margin-top: 0;
  margin-bottom: 15px;
  color: var(--primary-color);
  font-size: 1.2rem;
}

.activity-date {
  margin-bottom: 20px;
  color: var(--light-text);
  font-size: 0.9rem;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.news-source {
  background: #f5f5f5;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 0.8rem;
  color: var(--light-text);
}

.news-more {
  display: flex;
  justify-content: center;
  margin-top: 30px;
}

.news-more .el-button {
  background: var(--primary-color);
  border-color: var(--primary-color);
  transition: all 0.3s ease;
  padding: 10px 24px;
  font-size: 1rem;
}

.news-more .el-button:hover {
  background: #C53030;
  border-color: #C53030;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(229, 62, 62, 0.3);
}

/* 左右图片布局 */
.left-image .news-card-wrapper .news-image {
  margin-right: 20px;
}

.right-image .news-card-wrapper {
  flex-direction: row-reverse;
}

.right-image .news-card-wrapper .news-image {
  margin-left: 20px;
  margin-right: 0;
}

/* 响应式设计 */
@media (max-width: 992px) {
  .news-card-wrapper {
    flex-direction: column;
  }
  
  .news-image {
    flex: 0 0 auto;
    margin-right: 0;
    margin-bottom: 15px;
  }
  
  .news-image img {
    width: 100%;
    height: 200px;
    object-fit: cover;
  }
  
  .news-text-content {
    padding-left: 0;
  }
  
  .news-card {
    min-height: 320px;
  }
}

@media (max-width: 768px) {
  .news-section {
    padding: 24px;
  }
  
  .news-section h2 {
    font-size: 1.8rem;
  }
  
  .news-image img {
    height: 180px;
  }
  
  .news-card {
    min-height: 300px;
  }
}

@media (max-width: 576px) {
  .news-section {
    padding: 16px;
  }
  
  .news-card-wrapper {
    padding: 12px;
  }
  
  .news-image img {
    height: 150px;
  }
  
  .news-card {
    min-height: 280px;
  }
  
  .news-title {
    font-size: 1rem;
  }
  
  .news-description {
    min-height: 50px;
  }
}
</style>