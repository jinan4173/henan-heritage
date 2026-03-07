<template>
  <div class="admin-content-manage-view">
    <div class="page-header">
      <h2>文化资讯管理</h2>
    </div>
    
    <!-- 操作栏 -->
    <div class="action-bar">
      <div class="search-bar">
        <el-button type="primary" @click="showAddDialog = true">新增</el-button>
        <el-button type="danger" @click="handleBatchDelete" :disabled="selectedRows.length === 0">批量删除</el-button>
        <el-input v-model="searchQuery" placeholder="搜索标题" style="width: 300px;"></el-input>
        <el-button type="primary" @click="handleSearch">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>
    </div>
    
    <!-- 内容列表 -->
    <el-table :data="filteredContentList" style="width: 100%" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="序号" width="80">
        <template #default="scope">
          {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题" min-width="200" />
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
    
    <!-- 新增内容对话框 -->
    <AddNewsDialog 
      v-model:visible="showAddDialog" 
      :on-success="handleAddSuccess"
    />
    
    <!-- 编辑对话框 -->
    <el-dialog v-model="editDialogVisible" :title="dialogTitle" width="800px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题" required>
          <el-input v-model="form.title" placeholder="请输入标题" style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="封面图片" required>
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
            :locale="zhCn"
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
        <el-form-item label="内容" required>
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
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveContent">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import api from '../../api/index.js'
import { ElMessage, ElMessageBox, ElDatePicker } from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import AddNewsDialog from '../../components/AddNewsDialog.vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import '@wangeditor/editor/dist/css/style.css'


const contentList = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showAddDialog = ref(false)
const editDialogVisible = ref(false)
const dialogTitle = ref('编辑内容')
const selectedRows = ref([])

// 处理选择变化
const handleSelectionChange = (val) => {
  selectedRows.value = val
}

// 批量删除
const handleBatchDelete = async () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请选择要删除的内容')
    return
  }
  
  try {
    const ids = selectedRows.value.map(item => item.id)
    const response = await api.post('/culture-news/batch-delete', {
      ids: ids
    })
    if (response.success) {
      ElMessage.success('批量删除成功')
      fetchContentList()
      selectedRows.value = []
    } else {
      ElMessage.error('批量删除失败')
    }
  } catch (error) {
    console.error('批量删除失败:', error)
    ElMessage.error('批量删除失败')
  }
}
const form = ref({
  id: null,
  title: '',
  content: '',
  coverImage: '',
  summary: '',
  date: '',
  status: 1 // 默认状态为启用
})

// 编辑器实例
const editorRef = ref(null)

// 编辑器配置
const editorConfig = {
  placeholder: '请输入资讯内容',
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
    },
    uploadVideo: {
      server: '/api/image/upload',
      fieldName: 'file',
      maxFileSize: 10 * 1024 * 1024, // 10MB
      allowedFileTypes: ['video/mp4', 'video/webm'],
      onSuccess: (file, res) => {
        if (res.success) {
          return res.filePath
        } else {
          throw new Error('上传失败')
        }
      },
      onError: (file, err, res) => {
        ElMessage.error('视频上传失败')
      }
    }
  }
}

// 工具栏配置
const toolbarConfig = {
  excludeKeys: []
}

// 编辑器创建完成
const onCreated = (editor) => {
  editorRef.value = editor
}

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
    const response = await api.get('/culture-news/list', { params: { type: 1 } })
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
  // 移除type字段，因为我们固定为资讯类型
  delete form.value.type
  // 确保summary字段存在
  if (!form.value.summary) {
    form.value.summary = ''
  }
  dialogTitle.value = '编辑内容'
  editDialogVisible.value = true
}

// 处理新增内容成功后的回调
const handleAddSuccess = () => {
  fetchContentList()
}

const deleteContent = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个内容吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await api.delete(`/culture-news/delete/${id}`)
    if (response.success) {
      ElMessage.success('删除成功')
      fetchContentList()
    } else {
      ElMessage.error('删除失败: ' + (response.message || '未知错误'))
    }
  } catch (error) {
    if (error.message !== 'cancel') {
      console.error('删除内容失败:', error)
      ElMessage.error('删除失败: ' + error.message)
    }
  }
}

const saveContent = async () => {
  try {
    const contentData = {
      ...form.value,
      type: 1 // 固定类型为资讯
    }
    let response
    if (contentData.id) {
      response = await api.post('/culture-news/update', contentData)
    } else {
      response = await api.post('/culture-news/save', contentData)
    }
    if (response.success) {
      ElMessage.success('保存成功')
      editDialogVisible.value = false
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
    summary: '',
    date: '',
    status: 1 // 默认状态为启用
  }
  dialogTitle.value = '编辑内容'
}

const handleSizeChange = (size) => {
  pageSize.value = size
  fetchContentList()
}

const handleCurrentChange = (current) => {
  currentPage.value = current
  fetchContentList()
}

// 处理状态变更
const handleStatusChange = async (content) => {
  try {
    const contentData = {
      ...content,
      type: 1 // 确保类型为资讯
    }
    const response = await api.post('/culture-news/update', contentData)
    if (response.success) {
      ElMessage.success('状态更新成功')
    } else {
      ElMessage.error('状态更新失败')
      // 恢复原来的状态
      fetchContentList()
    }
  } catch (error) {
    console.error('状态更新失败:', error)
    ElMessage.error('状态更新失败')
    // 恢复原来的状态
    fetchContentList()
  }
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

.status-active {
  display: inline-block;
  padding: 2px 8px;
  border-radius: 10px;
  background-color: #f0f9eb;
  color: #67c23a;
  font-size: 12px;
}

.status-inactive {
  display: inline-block;
  padding: 2px 8px;
  border-radius: 10px;
  background-color: #fef0f0;
  color: #f56c6c;
  font-size: 12px;
}



/* 上传按钮样式 */
.avatar-uploader {
  margin-bottom: 10px;
}

.avatar-uploader .el-button {
  margin-right: 10px;
}
</style>