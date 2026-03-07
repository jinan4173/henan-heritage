<template>
  <div class="admin-activity-view">
    <div class="page-header">
      <h2>非遗活动管理</h2>
    </div>

    <div class="action-bar">
      <div class="search-bar">
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="danger" @click="handleBatchDelete" :disabled="selectedRows.length === 0">批量删除</el-button>
        <el-input v-model="searchQuery" placeholder="搜索活动标题" style="width: 300px;"></el-input>
        <el-button type="primary" @click="handleSearch">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>
    </div>

    <el-table :data="activityList" style="width: 100%" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="序号" width="80">
        <template #default="scope">
          {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="活动标题" min-width="200" />
      <el-table-column prop="date" label="日期" width="180" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-switch 
            v-model="scope.row.status" 
            :active-value="1" 
            :inactive-value="0" 
            @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" width="180" />
      <el-table-column label="操作" width="150">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
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
        <el-form-item label="活动标题" required>
          <el-input v-model="form.title" placeholder="请输入活动标题" style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="封面图片">
          <el-upload
            class="avatar-uploader"
            action="/api/image/upload"
            :show-file-list="false"
            :on-success="handleImageUpload"
            :before-upload="beforeImageUpload"
          >
            <el-button type="primary">上传</el-button>
          </el-upload>
          <div v-if="form.coverImage" style="margin-top: 10px;">
            <img :src="form.coverImage" style="width: 100px; height: 100px; object-fit: cover;" />
          </div>
        </el-form-item>
        <el-form-item label="日期" required>
          <el-date-picker
            v-model="form.date"
            type="datetime"
            placeholder="请选择日期"
            style="width: 100%"
            :picker-options="{
              shortcuts: [
                { text: '今天', value: new Date() },
                { text: '昨天', value: new Date(Date.now() - 86400000) },
                { text: '一周前', value: new Date(Date.now() - 604800000) }
              ]
            }"
          />
        </el-form-item>
        <el-form-item label="内容简介" required>
          <el-input
            v-model="form.summary"
            type="textarea"
            :rows="3"
            placeholder="请输入内容简介"
            style="width: 100%"
          ></el-input>
        </el-form-item>
        <el-form-item label="活动内容" required>
          <div style="border: 1px solid #dcdfe6; border-radius: 4px; overflow: hidden;">
            <Toolbar
              :editor="editorRef"
              :defaultConfig="toolbarConfig"
              :mode="'default'"
              style="border-bottom: 1px solid #dcdfe6;"
            />
            <Editor
              v-model="form.content"
              :defaultConfig="editorConfig"
              :mode="'default'"
              style="height: 400px; overflow-y: hidden;"
              @onCreated="onCreated"
            />
          </div>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSave">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../../api/index.js'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import '@wangeditor/editor/dist/css/style.css'

const activityList = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('添加活动')
const selectedRows = ref([])
const editorRef = ref(null)

const form = ref({
  id: null,
  title: '',
  content: '',
  coverImage: '',
  summary: '',
  date: '',
  status: 1
})

// 编辑器配置
const editorConfig = {
  placeholder: '请输入活动内容',
  MENU_CONF: {
    uploadImage: {
      server: '/api/image/upload',
      fieldName: 'file',
      maxFileSize: 2 * 1024 * 1024,
      allowedFileTypes: ['image/jpeg', 'image/png'],
      onSuccess: (file, res) => {
        if (res.success) {
          return res.filePath
        } else {
          throw new Error('上传失败')
        }
      },
      onError: (file, err, res) => {
        ElMessage.error('图片上传失败')
      }
    }
  }
}

// 工具栏配置
const toolbarConfig = {
  excludeKeys: []
}

// 处理选择变化
const handleSelectionChange = (val) => {
  selectedRows.value = val
}

// 批量删除
const handleBatchDelete = async () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请选择要删除的活动')
    return
  }
  
  try {
    const ids = selectedRows.value.map(item => item.id)
    const response = await api.post('/culture-news/batch-delete', {
      ids: ids
    })
    if (response.success) {
      ElMessage.success('批量删除成功')
      loadActivities()
      selectedRows.value = []
    } else {
      ElMessage.error('批量删除失败')
    }
  } catch (error) {
    console.error('批量删除失败:', error)
    ElMessage.error('批量删除失败')
  }
}

// 编辑器创建完成
const onCreated = (editor) => {
  editorRef.value = editor
}

onMounted(() => {
  loadActivities()
})

const loadActivities = async () => {
  try {
    const response = await api.get('/culture-news/list', { params: { type: 2 } })
    if (response.success) {
      activityList.value = response.data
      total.value = response.total || 0
    }
  } catch (error) {
    console.error('获取活动列表失败:', error)
  }
}

const handleAdd = () => {
  form.value = {
    id: null,
    title: '',
    content: '',
    coverImage: '',
    summary: '',
    date: '',
    status: 1
  }
  dialogTitle.value = '添加活动'
  dialogVisible.value = true
}

const handleEdit = (activity) => {
  form.value = { ...activity }
  dialogTitle.value = '编辑活动'
  dialogVisible.value = true
}

const handleSave = async () => {
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
      loadActivities()
    } else {
      ElMessage.error('保存失败')
    }
  } catch (error) {
    console.error('保存失败:', error)
    ElMessage.error('保存失败')
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个活动吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    console.log('开始删除，ID:', id)
    const response = await api.delete(`/culture-news/delete/${id}`)
    console.log('删除响应:', response)
    if (response.success) {
      ElMessage.success('删除成功')
      loadActivities()
    } else {
      ElMessage.error('删除失败: ' + (response.message || '未知错误'))
    }
  } catch (error) {
    if (error.message !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败: ' + error.message)
    }
  }
}

const handleStatusChange = async (activity) => {
  try {
    const activityData = {
      ...activity,
      type: 2 // 确保类型为活动
    }
    const response = await api.post('/culture-news/update', activityData)
    if (response.success) {
      ElMessage.success('状态更新成功')
    } else {
      ElMessage.error('状态更新失败')
      loadActivities()
    }
  } catch (error) {
    console.error('状态更新失败:', error)
    ElMessage.error('状态更新失败')
    loadActivities()
  }
}

const handleSearch = () => {
  // 搜索逻辑
  loadActivities()
}

const resetSearch = () => {
  searchQuery.value = ''
  loadActivities()
}

const handleSizeChange = (size) => {
  pageSize.value = size
  loadActivities()
}

const handleCurrentChange = (current) => {
  currentPage.value = current
  loadActivities()
}

// 处理图片上传
const handleImageUpload = (response) => {
  if (response.success) {
    form.value.coverImage = response.filePath
    ElMessage.success('图片上传成功')
  } else {
    ElMessage.error('图片上传失败')
  }
}

// 图片上传前的验证
const beforeImageUpload = (file) => {
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isJpgOrPng) {
    ElMessage.error('只能上传JPG/PNG图片!')
  }
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过2MB!')
  }
  return isJpgOrPng && isLt2M
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
  margin-bottom: 20px;
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