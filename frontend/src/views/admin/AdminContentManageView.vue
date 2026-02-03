<template>
  <div class="admin-content-manage-view">
    <div class="page-header">
      <h2>内容管理</h2>
    </div>
    
    <!-- 操作栏 -->
    <div class="action-bar">
      <el-button type="primary" @click="dialogVisible = true">新增内容</el-button>
      <div class="search-bar">
        <el-input v-model="searchQuery" placeholder="搜索标题" style="width: 300px;"></el-input>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>
    </div>
    
    <!-- 内容列表 -->
    <el-table :data="filteredContentList" style="width: 100%" border>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="title" label="标题"></el-table-column>
      <el-table-column prop="type" label="类型" width="100">
        <template #default="scope">
          <el-tag :type="getTypeTagType(scope.row.type)">
            {{ getTypeName(scope.row.type) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="date" label="日期" width="180"></el-table-column>
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
          <el-button size="small" @click="editContent(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteContent(scope.row.id)">删除</el-button>
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
          <el-input v-model="form.title" placeholder="请输入标题" style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input
            v-model="form.content"
            type="textarea"
            :rows="6"
            placeholder="请输入内容"
            style="width: 100%"
          ></el-input>
        </el-form-item>
        <el-form-item label="封面图片">
          <el-input v-model="form.coverImage" placeholder="请输入封面图片URL" style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-radio-group v-model="form.type">
            <el-radio label="1">资讯</el-radio>
            <el-radio label="2">活动</el-radio>
            <el-radio label="3">公告</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="日期">
          <el-input v-model="form.date" placeholder="请输入日期，如：2024-02-01 至 2024-02-15" style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveContent">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import api from '../../api/index.js'
import { ElMessage } from 'element-plus'

const contentList = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('新增内容')
const form = ref({
  id: null,
  title: '',
  content: '',
  coverImage: '',
  type: '1', // 默认类型为资讯
  date: '',
  status: 1 // 默认状态为启用
})

// 过滤后的内容列表
const filteredContentList = computed(() => {
  if (!searchQuery.value) {
    return contentList.value
  }
  return contentList.value.filter(content => 
    content.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

// 获取类型名称
const getTypeName = (type) => {
  switch (type) {
    case 1:
    case '1':
      return '资讯'
    case 2:
    case '2':
      return '活动'
    case 3:
    case '3':
      return '公告'
    default:
      return '未知'
  }
}

// 获取类型标签颜色
const getTypeTagType = (type) => {
  switch (type) {
    case 1:
    case '1':
      return 'info'
    case 2:
    case '2':
      return 'warning'
    case 3:
    case '3':
      return 'danger'
    default:
      return 'default'
  }
}

onMounted(() => {
  fetchContentList()
})

const fetchContentList = async () => {
  try {
    const response = await api.get('/culture-news/list')
    if (response.success) {
      contentList.value = response.data
      total.value = response.total
    }
  } catch (error) {
    console.error('获取内容列表失败:', error)
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

const editContent = (content) => {
  form.value = { ...content }
  dialogTitle.value = '编辑内容'
  dialogVisible.value = true
}

const deleteContent = async (id) => {
  try {
    const response = await api.delete(`/culture-news/delete/${id}`)
    if (response.success) {
      ElMessage.success('删除成功')
      fetchContentList()
    }
  } catch (error) {
    console.error('删除内容失败:', error)
    ElMessage.error('删除失败')
  }
}

const saveContent = async () => {
  try {
    let response
    if (form.value.id) {
      response = await api.post('/culture-news/update', form.value)
    } else {
      response = await api.post('/culture-news/save', form.value)
    }
    if (response.success) {
      ElMessage.success('保存成功')
      dialogVisible.value = false
      fetchContentList()
      resetForm()
    }
  } catch (error) {
    console.error('保存内容失败:', error)
    ElMessage.error('保存失败')
  }
}

const resetForm = () => {
  form.value = {
    id: null,
    title: '',
    content: '',
    coverImage: '',
    type: '1', // 默认类型为资讯
    date: '',
    status: 1 // 默认状态为启用
  }
  dialogTitle.value = '新增内容'
}

const handleSizeChange = (size) => {
  pageSize.value = size
  fetchContentList()
}

const handleCurrentChange = (current) => {
  currentPage.value = current
  fetchContentList()
}
</script>

<style scoped>
.admin-content-manage-view {
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