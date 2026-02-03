<template>
  <div class="admin-activity-view">
    <div class="page-header">
      <h2>活动管理</h2>
    </div>
    
    <!-- 操作栏 -->
    <div class="action-bar">
      <el-button type="primary" @click="dialogVisible = true">新增活动</el-button>
      <div class="search-bar">
        <el-input v-model="searchQuery" placeholder="搜索活动标题" style="width: 300px;"></el-input>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>
    </div>
    
    <!-- 活动列表 -->
    <el-table :data="filteredActivityList" style="width: 100%" border>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="title" label="标题"></el-table-column>
      <el-table-column prop="date" label="活动日期" width="180"></el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" width="180"></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="scope">
          <el-button size="small" @click="editActivity(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteActivity(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
    
    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="800px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="form.title" placeholder="请输入活动标题" style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input
            v-model="form.content"
            type="textarea"
            :rows="6"
            placeholder="请输入活动内容"
            style="width: 100%"
          ></el-input>
        </el-form-item>
        <el-form-item label="封面图片">
          <el-input v-model="form.coverImage" placeholder="请输入封面图片URL" style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="活动日期">
          <el-input v-model="form.date" placeholder="请输入活动日期，如：2024-02-01 至 2024-02-15" style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" active-value="1" inactive-value="0"></el-switch>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveActivity">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import api from '../../api/index.js'
import { ElMessage } from 'element-plus'

const activityList = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('新增活动')
const form = ref({
  id: null,
  title: '',
  content: '',
  coverImage: '',
  date: '',
  status: 1
})

// 过滤后的活动列表
const filteredActivityList = computed(() => {
  if (!searchQuery.value) {
    return activityList.value
  }
  return activityList.value.filter(activity => 
    activity.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

onMounted(() => {
  fetchActivityList()
})

const fetchActivityList = async () => {
  try {
    const response = await api.get('/culture-news/list', { params: { type: 2 } })
    if (response.success) {
      activityList.value = response.data
      total.value = response.total
    }
  } catch (error) {
    console.error('获取活动列表失败:', error)
  }
}

// 搜索方法
const handleSearch = () => {
  // 搜索逻辑已在computed中实现
}

// 重置搜索
const resetSearch = () => {
  searchQuery.value = ''
}

const editActivity = (activity) => {
  form.value = { ...activity }
  dialogTitle.value = '编辑活动'
  dialogVisible.value = true
}

const deleteActivity = async (id) => {
  try {
    const response = await api.delete(`/culture-news/delete/${id}`)
    if (response.success) {
      ElMessage.success('删除成功')
      fetchActivityList()
    }
  } catch (error) {
    console.error('删除活动失败:', error)
    ElMessage.error('删除失败')
  }
}

const saveActivity = async () => {
  try {
    const activityData = {
      ...form.value,
      type: 2 // 设置类型为活动
    }
    let response
    if (activityData.id) {
      response = await api.post('/culture-news/update', activityData)
    } else {
      response = await api.post('/culture-news/save', activityData)
    }
    if (response.success) {
      ElMessage.success('保存成功')
      dialogVisible.value = false
      fetchActivityList()
      resetForm()
    }
  } catch (error) {
    console.error('保存活动失败:', error)
    ElMessage.error('保存失败')
  }
}

const resetForm = () => {
  form.value = {
    id: null,
    title: '',
    content: '',
    coverImage: '',
    date: '',
    status: 1
  }
  dialogTitle.value = '新增活动'
}

const handleSizeChange = (size) => {
  pageSize.value = size
  fetchActivityList()
}

const handleCurrentChange = (current) => {
  currentPage.value = current
  fetchActivityList()
}
</script>

<style scoped>
.admin-activity-view {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #e4e7ed;
}

.page-header h2 {
  font-size: 1.5rem;
  margin: 0;
  color: #333;
  font-weight: 600;
}

.action-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  gap: 10px;
}

.action-bar .el-button:first-child {
  margin-right: auto;
}

.search-bar {
  display: flex;
  align-items: center;
  gap: 10px;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}

.dialog-footer {
  text-align: right;
}
</style>
