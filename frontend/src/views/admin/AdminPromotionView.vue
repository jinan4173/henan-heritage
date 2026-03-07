<template>
  <div class="admin-promotion">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>非遗宣传管理</h2>
    </div>
    
    <!-- 操作栏 -->
    <div class="action-bar">
      <div class="search-bar">
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="danger" @click="handleBatchDelete" :disabled="selectedRows.length === 0">批量删除</el-button>
        <el-input v-model="searchQuery" placeholder="搜索宣传标题" style="width: 300px;"></el-input>
        <el-button type="primary" @click="handleSearch">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>
    </div>

    <!-- 宣传列表 -->
    <el-table :data="filteredPromotions" style="width: 100%" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="序号" width="80">
        <template #default="scope">
          {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="宣传标题" min-width="250" />
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
      <el-table-column prop="type" label="宣传类型" width="150">
        <template #default="scope">
          {{ getPromotionTypeText(scope.row.type) }}
        </template>
      </el-table-column>
      <el-table-column label="封面" width="100">
        <template #default="scope">
          <el-image
            :src="scope.row.coverImage || getDefaultCover(scope.row)"
            :preview-src-list="[scope.row.coverImage || getDefaultCover(scope.row)]"
            fit="cover"
            style="width: 80px; height: 80px; border-radius: 4px"
          />
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column prop="updateTime" label="更新时间" width="180" />
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">
            编辑
          </el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="800px"
    >
      <el-form :model="formData" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="宣传标题" prop="title">
          <el-input v-model="formData.title" placeholder="请输入宣传标题" />
        </el-form-item>

        <el-form-item label="宣传类型" prop="type">
          <el-select v-model="formData.type" placeholder="请选择宣传类型">
            <el-option label="活动宣传" value="activity" />
            <el-option label="项目宣传" value="heritage" />
            <el-option label="传承人宣传" value="inheritor" />
            <el-option label="文化资讯" value="news" />
          </el-select>
        </el-form-item>

        <el-form-item label="封面图片" prop="coverImage">
          <el-upload
            class="upload-demo"
            action=""
            :auto-upload="false"
            :on-change="handleCoverUpload"
            :limit="1"
            list-type="picture"
          >
            <el-button type="primary">
              <el-icon><Upload /></el-icon> 上传封面
            </el-button>
          </el-upload>
          <!-- 预览封面图片 -->
          <div v-if="formData.coverImage" class="cover-preview">
            <el-image
              :src="formData.coverImage"
              fit="cover"
              style="width: 200px; height: 150px; border-radius: 8px; margin-bottom: 10px"
            />
          </div>
          <el-input v-model="formData.coverImage" placeholder="封面图片路径" />
        </el-form-item>

        <el-form-item label="宣传视频" prop="videoUrl">
          <el-upload
            class="upload-demo"
            action=""
            :auto-upload="false"
            :on-change="handleVideoUpload"
            :limit="1"
            list-type="picture"
          >
            <el-button type="primary">
              <el-icon><Upload /></el-icon> 上传视频
            </el-button>
          </el-upload>
          <!-- 预览视频 -->
          <div v-if="formData.videoUrl" class="video-preview">
            <video
              :src="formData.videoUrl"
              controls
              style="width: 300px; border-radius: 8px; margin-bottom: 10px"
            />
          </div>
          <el-input v-model="formData.videoUrl" placeholder="视频路径" />
        </el-form-item>

        <el-form-item label="宣传内容" prop="content">
          <div style="border: 1px solid #dcdfe6; border-radius: 4px; overflow: hidden;">
            <Toolbar
              :editor="editorRef"
              :defaultConfig="toolbarConfig"
              :mode="'default'"
              style="border-bottom: 1px solid #dcdfe6;"
            />
            <Editor
              v-model="formData.content"
              :defaultConfig="editorConfig"
              :mode="'default'"
              style="height: 400px; overflow-y: hidden;"
              @onCreated="onCreated"
            />
          </div>
        </el-form-item>

        <el-form-item label="链接地址" prop="linkUrl">
          <el-input v-model="formData.linkUrl" placeholder="请输入链接地址" />
        </el-form-item>

        <el-form-item label="状态" prop="status">
          <el-switch v-model="formData.status" :active-value="1" :inactive-value="0" />
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
import { ref, reactive, onMounted, computed } from 'vue'
import api from '../../api/index.js'
import { imageApi } from '../../api/api.js'
import { ElTable, ElTableColumn, ElButton, ElDialog, ElForm, ElFormItem, ElInput, ElSelect, ElOption, ElUpload, ElSwitch, ElPagination, ElIcon, ElImage } from 'element-plus'
import { Upload, Delete } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import '@wangeditor/editor/dist/css/style.css'

const promotions = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const selectedRows = ref([])
const dialogTitle = ref('添加非遗宣传')
const formRef = ref(null)

// 编辑器实例
const editorRef = ref(null)

// 编辑器配置
const editorConfig = {
  placeholder: '请输入宣传内容',
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

const formData = reactive({
  id: '',
  title: '',
  content: '',
  coverImage: '',
  videoUrl: '',
  linkUrl: '',
  type: 'activity',
  status: 1,
  createTime: '',
  updateTime: ''
})

const rules = {
  title: [{ required: true, message: '请输入宣传标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入宣传内容', trigger: 'blur' }]
}





// 过滤后的宣传列表
const filteredPromotions = computed(() => {
  return promotions.value
})

onMounted(() => {
  loadData()
  loadTotalCount()
})

// 处理选择变化
const handleSelectionChange = (val) => {
  selectedRows.value = val
}

// 批量删除
const handleBatchDelete = async () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请选择要删除的宣传')
    return
  }
  
  try {
    const ids = selectedRows.value.map(item => item.id)
    const response = await api.delete('/promotion/delete/batch', {
      data: { ids }
    })
    if (response.code === 200) {
      ElMessage.success('批量删除成功')
      loadData()
      selectedRows.value = []
    } else {
      ElMessage.error('批量删除失败')
    }
  } catch (error) {
    console.error('批量删除失败:', error)
    ElMessage.error('批量删除失败')
  }
}

const loadData = async () => {
  try {
    // 加载宣传数据
    await loadPromotions()
    // 加载总数
    await loadTotalCount()
  } catch (error) {
    console.error('加载数据失败:', error)
    ElMessage.error('加载数据失败')
  }
}

const loadPromotions = async () => {
  try {
    let res
    if (searchQuery.value) {
      // 当搜索时，需要获取所有数据进行过滤
      res = await api.get('/promotion/list', {
        params: { page: 1, limit: 1000, keyword: searchQuery.value }
      })
      console.log('Search response:', res)
      if (res.code === 200) {
        let allData = res.data
        console.log('All data:', allData)
        let filteredData = allData.filter(item => 
          item.title.toLowerCase().includes(searchQuery.value.toLowerCase())
        )
        total.value = filteredData.length
        // 对过滤后的数据进行分页
        const start = (currentPage.value - 1) * pageSize.value
        const end = start + pageSize.value
        promotions.value = filteredData.slice(start, end)
      }
    } else {
      // 正常分页查询
      res = await api.get('/promotion/list', {
        params: { page: currentPage.value, limit: pageSize.value }
      })
      console.log('List response:', res)
      if (res.code === 200) {
        promotions.value = res.data
        total.value = res.total || 0
      }
    }
  } catch (error) {
    console.error('加载宣传数据失败:', error)
    ElMessage.error('加载宣传数据失败')
  }
}

// 获取所有宣传总数
const loadTotalCount = async () => {
  try {
    const response = await api.get('/promotion/count')
    console.log('Count response:', response)
    if (response.code === 200) {
      total.value = response.data || 0
    }
  } catch (error) {
    console.error('获取总数失败:', error)
  }
}

// 搜索方法
const handleSearch = () => {
  currentPage.value = 1 // 重置到第一页
  loadPromotions()
}

// 重置搜索
const resetSearch = () => {
  searchQuery.value = ''
  currentPage.value = 1 // 重置到第一页
  loadPromotions()
}

const handleAdd = () => {
  // 重置表单
  formRef.value?.resetFields()
  Object.assign(formData, {
    id: '',
    title: '',
    content: '',
    coverImage: '',
    videoUrl: '',
    linkUrl: '',
    type: 'activity',
    status: 1,
    createTime: '',
    updateTime: ''
  })
  dialogTitle.value = '添加非遗宣传'
  dialogVisible.value = true
}

const handleEdit = (row) => {
  // 填充表单
  Object.assign(formData, row)
  dialogTitle.value = '编辑非遗宣传'
  dialogVisible.value = true
}

const handleDelete = async (id) => {
  try {
    const res = await api.delete(`/promotion/delete/${id}`)
    if (res.code === 200) {
      ElMessage.success('删除成功')
      await loadPromotions()
      await loadTotalCount()
    } else {
      ElMessage.error(res.message || '删除失败')
    }
  } catch (error) {
    console.error('删除失败:', error)
    ElMessage.error('删除失败')
  }
}

const handleSave = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        let res
        
        if (formData.id) {
          // 编辑
          res = await api.put('/promotion/update', formData)
        } else {
          // 添加
          res = await api.post('/promotion/save', formData)
        }
        
        if (res.code === 200) {
          ElMessage.success(formData.id ? '更新成功' : '添加成功')
          dialogVisible.value = false
          await loadPromotions()
          await loadTotalCount()
        } else {
          ElMessage.error(res.message || (formData.id ? '更新失败' : '添加失败'))
        }
      } catch (error) {
        console.error('保存失败:', error)
        ElMessage.error('保存失败')
      }
    }
  })
}

const handleCoverUpload = async (file) => {
  try {
    const res = await imageApi.upload(file.raw)
    if (res.success) {
      formData.coverImage = res.filePath
      ElMessage.success('上传成功')
    } else {
      ElMessage.error(res.message || '上传失败')
    }
  } catch (error) {
    console.error('上传失败:', error)
    ElMessage.error('上传失败')
  }
}

const handleVideoUpload = async (file) => {
  try {
    const res = await imageApi.upload(file.raw)
    if (res.success) {
      formData.videoUrl = res.filePath
      ElMessage.success('视频上传成功')
    } else {
      ElMessage.error(res.message || '上传失败')
    }
  } catch (error) {
    console.error('视频上传失败:', error)
    ElMessage.error('视频上传失败')
  }
}

// 编辑器创建完成
const onCreated = (editor) => {
  editorRef.value = editor
}

// 为没有封面图片的宣传生成默认封面
const getDefaultCover = (item) => {
  // 构建基于宣传信息的prompt
  let prompt = `traditional Chinese culture heritage promotion ${encodeURIComponent(item.title)}`
  prompt += ` elegant artistic traditional style high quality`
  
  // 构建图片生成API URL
  return `https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=${prompt}&image_size=square`
}

// 获取宣传类型文本
const getPromotionTypeText = (type) => {
  const typeMap = {
    'activity': '活动宣传',
    'heritage': '项目宣传',
    'inheritor': '传承人宣传',
    'news': '文化资讯'
  }
  return typeMap[type] || type
}

const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  loadPromotions()
}

const handleCurrentChange = (page) => {
  currentPage.value = page
  loadPromotions()
}

// 处理状态变更
const handleStatusChange = async (promotion) => {
  try {
    const response = await api.put('/promotion/update', promotion)
    if (response.code === 200) {
      ElMessage.success('状态更新成功')
    } else {
      ElMessage.error('状态更新失败')
      // 恢复原来的状态
      loadPromotions()
    }
  } catch (error) {
    console.error('状态更新失败:', error)
    ElMessage.error('状态更新失败')
    // 恢复原来的状态
    loadPromotions()
  }
}
</script>

<style scoped>
.admin-promotion {
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

.cover-preview {
  margin-top: 10px;
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
</style>