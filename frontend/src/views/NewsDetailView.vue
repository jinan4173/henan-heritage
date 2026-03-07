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
    }
  } catch (error) {
    console.error('加载资讯详情失败:', error)
  }
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN')
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
  margin: 20px;
  padding: 10px 0;
}

.news-detail {
  background: #fff;
  margin: 0 20px 40px;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.news-detail:hover {
  box-shadow: 0 5px 25px rgba(0, 0, 0, 0.15);
}

.news-title {
  font-size: 2.2rem;
  font-weight: bold;
  color: var(--primary-color);
  margin-bottom: 25px;
  text-align: center;
  position: relative;
  padding-bottom: 20px;
  line-height: 1.3;
}

.news-title::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 100px;
  height: 4px;
  background-color: var(--accent-color);
  border-radius: 2px;
}

.news-meta {
  text-align: center;
  margin-bottom: 35px;
  color: #999;
  font-size: 0.95rem;
  display: flex;
  justify-content: center;
  gap: 20px;
  flex-wrap: wrap;
}

.news-source {
  display: inline-block;
}

.news-image {
  margin-bottom: 40px;
  text-align: center;
  overflow: hidden;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.news-image:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
}

.news-image img {
  width: 100%;
  height: auto;
  border-radius: 10px;
  transition: transform 0.5s ease;
}

.news-image:hover img {
  transform: scale(1.02);
}

.news-content {
  line-height: 1.8;
  color: #333;
  margin-bottom: 40px;
  font-size: 1.05rem;
}

.news-content p {
  margin-bottom: 15px;
  text-align: justify;
}

.news-content img {
  max-width: 100%;
  height: auto;
  margin: 20px 0;
  border-radius: 5px;
}

.empty-content {
  text-align: center;
  padding: 80px 0;
}

.news-actions {
  text-align: center;
  margin-top: 40px;
  margin-bottom: 30px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.news-actions .el-button {
  margin: 0 10px;
  transition: all 0.3s ease;
}

.news-actions .el-button:hover {
  transform: translateY(-2px);
}



/* 响应式设计 */
@media (max-width: 768px) {
  .news-detail {
    margin: 0 10px 20px;
    padding: 20px;
  }
  
  .news-title {
    font-size: 1.8rem;
    padding-bottom: 15px;
  }
  
  .news-title::after {
    width: 80px;
    height: 3px;
  }
  
  .news-meta {
    gap: 10px;
    font-size: 0.85rem;
  }
  
  .news-image {
    margin-bottom: 20px;
  }
  
  .news-content {
    font-size: 1rem;
    line-height: 1.7;
  }
  
  .share-buttons {
    flex-direction: column;
  }
  
  .share-buttons .el-button {
    width: 100%;
  }
}

@media (max-width: 480px) {
  .news-title {
    font-size: 1.5rem;
  }
  
  .news-detail {
    padding: 15px;
  }
  
  .news-actions .el-button {
    display: block;
    width: 100%;
    margin: 10px 0;
  }
}
</style>