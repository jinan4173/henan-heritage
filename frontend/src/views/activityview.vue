<template>
  <div class="activity-view">
    <div class="activity-container">
      <h2>活动信息</h2>
      <div class="activity-list">
        <el-card v-for="(activity, index) in activities" :key="activity.id" :class="['activity-item', index % 2 === 0 ? 'left-image' : 'right-image']" @click="viewDetail(activity.id)" style="cursor: pointer;">
          <div class="activity-content-wrapper">
            <div class="activity-image" v-if="activity.coverImage">
              <img :src="activity.coverImage" :alt="activity.title" />
            </div>
            <div class="activity-text-content">
              <div class="activity-header">
                <span>{{ activity.title }}</span>
                <span class="activity-date">{{ activity.date }}</span>
              </div>
              <div class="activity-content" v-html="truncateContent(activity.content)"></div>
              <div class="activity-footer">
                <el-button size="small" type="success" @click.stop="openRegistrationDialog(activity)">立即报名</el-button>
              </div>
            </div>
          </div>
        </el-card>
      </div>
    </div>
    
    <!-- 报名对话框 -->
    <el-dialog v-model="registrationDialogVisible" :title="registrationActivity?.title + ' - 活动报名'" width="500px">
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
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import api from '../api/index.js'

const router = useRouter()
const activities = ref([])
const registrationDialogVisible = ref(false)
const registrationActivity = ref(null)
const registrationForm = ref({
  name: '',
  phone: '',
  peopleCount: 1,
  remark: ''
})

onMounted(() => {
  loadActivities()
})

const loadActivities = async () => {
  try {
    const response = await api.get('/culture-news/list', { params: { type: 2 } })
    if (response.success) {
      // 只显示状态为1的活动
      activities.value = (response.data || []).filter(item => item.status === 1)
    }
  } catch (error) {
    console.error('加载活动失败:', error)
  }
}

const truncateContent = (content) => {
  if (!content) return ''
  // 移除HTML标签，只保留纯文本
  const plainText = content.replace(/<[^>]*>/g, '')
  // 截断内容，最多显示200个字符
  return plainText.length > 200 ? plainText.substring(0, 200) + '...' : plainText
}

const viewDetail = (id) => {
  router.push(`/activity/${id}`)
}

const openRegistrationDialog = (activity) => {
  registrationActivity.value = activity
  registrationForm.value = {
    name: '',
    phone: '',
    peopleCount: 1,
    remark: ''
  }
  registrationDialogVisible.value = true
}

const submitRegistration = async () => {
  if (!registrationActivity.value) {
    ElMessage.error('活动信息不存在')
    return
  }
  
  if (!registrationForm.value.name || !registrationForm.value.phone) {
    ElMessage.warning('请填写姓名和电话')
    return
  }
  
  try {
    const response = await api.post('/activity/registration/add', {
      activityId: registrationActivity.value.id,
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
.activity-view {
  width: 100%;
  padding: 0;
  background-color: var(--background-color);
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
}

.activity-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0;
}

.activity-view h2 {
  font-size: 2rem;
  margin: 0;
  padding: 40px 0 15px;
  color: var(--primary-color);
  text-align: center;
  position: relative;
}

.activity-view h2::after {
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

.activity-list {
  margin: 0;
  width: 100%;
}

.activity-item {
  margin: 0 0 20px;
  border-radius: 0;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  width: 100%;
}

.activity-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
}

.activity-content-wrapper {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  padding: 15px;
}

.activity-image {
  flex: 0 0 300px;
  overflow: hidden;
}

.activity-image img {
  width: 100%;
  height: 200px;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.activity-item:hover .activity-image img {
  transform: scale(1.05);
}

.activity-text-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.activity-header {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 10px;
  background: none;
  padding: 0;
}

.activity-header span:first-child {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
  line-height: 1.4;
}

.activity-date {
  font-size: 0.9rem;
  color: #999;
}

.activity-content {
  margin: 0;
  line-height: 1.6;
  color: #666;
  flex: 1;
  margin-bottom: 15px;
}

.activity-footer {
  margin: 0;
  text-align: right;
}

.left-image .activity-image {
  margin-right: 20px;
}

.right-image .activity-content-wrapper {
  flex-direction: row-reverse;
}

.right-image .activity-image {
  margin-left: 20px;
  margin-right: 0;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .activity-content-wrapper {
    flex-direction: column;
  }
  
  .activity-image {
    flex: 0 0 auto;
    margin-right: 0;
    margin-bottom: 15px;
  }
  
  .activity-image img {
    width: 100%;
    height: auto;
  }
  
  .right-image .activity-image {
    margin-left: 0;
  }
}
</style>