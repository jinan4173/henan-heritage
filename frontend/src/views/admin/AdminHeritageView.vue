<template>
  <div class="admin-heritage">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>非遗项目管理</h2>
    </div>
    
    <!-- 操作栏 -->
    <div class="action-bar">
      <div class="search-bar">
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="danger" @click="handleBatchDelete" :disabled="selectedRows.length === 0">批量删除</el-button>
        <el-input v-model="searchQuery" placeholder="搜索项目名称" style="width: 300px;"></el-input>
        <el-button type="primary" @click="handleSearch">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>
    </div>

    <!-- 项目列表 -->
    <el-table :data="filteredHeritageItems" style="width: 100%" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="序号" width="80">
        <template #default="scope">
          {{ scope.row.serialNumber || ((currentPage - 1) * pageSize + scope.$index + 1) }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="项目名称" min-width="250" />
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
      <el-table-column prop="categoryName" label="类别" width="150" />
      <el-table-column prop="regionName" label="地区" width="120" />
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
      <el-table-column prop="declarationRegion" label="申报地区或单位" min-width="200" />
      <el-table-column prop="protectionUnit" label="保护单位" min-width="200" />
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
        <el-form-item label="项目名称" prop="title">
          <el-input v-model="formData.title" placeholder="请输入项目名称" />
        </el-form-item>



        <el-form-item label="序号" prop="serialNumber">
          <el-input v-model="formData.serialNumber" placeholder="请输入序号" />
        </el-form-item>

        <el-form-item label="类别" prop="categoryId">
          <el-select v-model="formData.categoryId" placeholder="请选择类别">
            <el-option
              v-for="category in categories"
              :key="category.id"
              :label="category.name"
              :value="category.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="地区" prop="regionId">
          <el-select v-model="formData.regionId" placeholder="请选择地区">
            <el-option
              v-for="region in regions"
              :key="region.id"
              :label="region.name"
              :value="region.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="保护单位" prop="protectionUnit">
          <el-input v-model="formData.protectionUnit" placeholder="请输入保护单位" />
        </el-form-item>

        <el-form-item label="申报地区或单位" prop="declarationRegion">
          <el-input v-model="formData.declarationRegion" placeholder="请输入申报地区或单位" />
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

        <el-form-item label="视频URL" prop="videoUrl">
          <el-input v-model="formData.videoUrl" placeholder="请输入视频URL" />
        </el-form-item>

        <el-form-item label="项目介绍" prop="description">
          <el-input
            v-model="formData.description"
            type="textarea"
            :rows="5"
            placeholder="请输入项目介绍"
          />
        </el-form-item>

        <el-form-item label="状态" prop="status">
          <el-switch v-model="formData.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
        
        <!-- 多媒体文件管理 -->
        <el-form-item label="多媒体文件">
          <div class="media-management">
            <el-button type="primary" size="small" @click="addMedia">
              <el-icon><Plus /></el-icon> 添加媒体文件
            </el-button>
            
            <div class="media-list">
              <div 
                v-for="(media, index) in mediaList" 
                :key="media.id || index"
                class="media-item"
              >
                <el-select v-model="media.mediaType" placeholder="媒体类型">
                  <el-option label="图片" value="image" />
                  <el-option label="视频" value="video" />
                </el-select>
                <el-input v-model="media.mediaUrl" placeholder="媒体 URL" style="margin-top: 10px" />
                <el-input v-model="media.source" placeholder="来源" style="margin-top: 10px" />
                <el-input v-model="media.description" placeholder="描述" style="margin-top: 10px" />
                <el-button 
                  type="danger" 
                  size="small" 
                  @click="removeMedia(index)"
                  style="margin-top: 10px"
                >
                  <el-icon><Delete /></el-icon> 删除
                </el-button>
              </div>
              <div v-if="mediaList.length === 0" class="empty-media">
                暂无媒体文件
              </div>
            </div>
          </div>
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
import { heritageApi } from '../../api/api'
import { imageApi } from '../../api/image'
import api from '../../api/index.js'
import { ElTable, ElTableColumn, ElButton, ElDialog, ElForm, ElFormItem, ElInput, ElSelect, ElOption, ElUpload, ElSwitch, ElPagination, ElIcon, ElImage } from 'element-plus'
import { Plus, Upload, Delete } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const heritageItems = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const selectedRows = ref([])
const dialogTitle = ref('添加非遗项目')
const formRef = ref(null)

// 媒体文件列表
const mediaList = ref([])

// 分类和地区列表
const categories = ref([])
const regions = ref([])

const formData = reactive({
  id: '',
  serialNumber: '',
  title: '',
  description: '',
  declarationRegion: '',
  protectionUnit: '',
  categoryId: '',
  regionId: '',
  regionName: '',
  categoryName: '',
  coverImage: '',
  videoUrl: '',
  status: 1
})

const rules = {
  title: [{ required: true, message: '请输入项目名称', trigger: 'blur' }]
}

// 过滤后的遗产项目列表
const filteredHeritageItems = computed(() => {
  return heritageItems.value
})

onMounted(() => {
  loadData()
  loadTotalCount()
  loadCategories()
  loadRegions()
})

// 加载分类列表
const loadCategories = async () => {
  try {
    const response = await api.get('/heritage/categories')
    if (response.code === 200) {
      categories.value = response.data || []
    }
  } catch (error) {
    console.error('加载分类数据失败:', error)
    categories.value = []
  }
}

// 加载地区列表
const loadRegions = async () => {
  try {
    const response = await api.get('/heritage/regions')
    if (response.code === 200) {
      regions.value = response.data || []
    }
  } catch (error) {
    console.error('加载地区数据失败:', error)
    regions.value = []
  }
}

// 处理选择变化
const handleSelectionChange = (val) => {
  selectedRows.value = val
}

// 批量删除
const handleBatchDelete = async () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请选择要删除的项目')
    return
  }
  
  try {
    const ids = selectedRows.value.map(item => item.id)
    const response = await api.delete('/heritage/delete/batch', {
      data: { ids }
    })
    if (response.success) {
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
    // 加载非遗项目
    await loadHeritageItems()
    // 加载总数
    await loadTotalCount()
  } catch (error) {
    console.error('加载数据失败:', error)
    ElMessage.error('加载数据失败')
  }
}

const loadHeritageItems = async () => {
  try {
    let res
    if (searchQuery.value) {
      // 当搜索时，需要获取所有数据进行过滤
      res = await heritageApi.list(undefined, 1, 1000)
      if (res.code === 200) {
        let allData = res.data
        let filteredData = allData.filter(item => 
          item.title.toLowerCase().includes(searchQuery.value.toLowerCase())
        )
        total.value = filteredData.length
        // 对过滤后的数据进行分页
        const start = (currentPage.value - 1) * pageSize.value
        const end = start + pageSize.value
        heritageItems.value = filteredData.slice(start, end)
      }
    } else {
      // 正常分页查询
      res = await heritageApi.list(undefined, currentPage.value, pageSize.value)
      if (res.code === 200) {
        heritageItems.value = res.data
        total.value = res.total || 0
      }
    }
  } catch (error) {
    console.error('加载非遗项目失败:', error)
    ElMessage.error('加载非遗项目失败')
  }
}

// 获取所有项目总数（包括禁用的）
const loadTotalCount = async () => {
  try {
    const response = await api.get('/dashboard/stats')
    if (response.success) {
      total.value = response.heritageCount || 0
    }
  } catch (error) {
    console.error('获取总数失败:', error)
  }
}

// 搜索方法
const handleSearch = () => {
  currentPage.value = 1 // 重置到第一页
  loadHeritageItems()
}

// 重置搜索
const resetSearch = () => {
  searchQuery.value = ''
  currentPage.value = 1 // 重置到第一页
  loadHeritageItems()
}

const handleAdd = () => {
  // 重置表单
  formRef.value?.resetFields()
  Object.assign(formData, {
    id: '',
    serialNumber: '',
    title: '',
    description: '',
    declarationRegion: '',
    protectionUnit: '',
    categoryId: '',
    regionId: '',
    regionName: '',
    categoryName: '',
    coverImage: '',
    videoUrl: '',
    status: 1
  })
  // 重置媒体文件列表
  mediaList.value = []
  dialogTitle.value = '添加非遗项目'
  dialogVisible.value = true
}

// 添加媒体文件
const addMedia = () => {
  mediaList.value.push({
    id: '',
    heritageItemId: formData.id,
    mediaType: 'image',
    mediaUrl: '',
    source: '',
    description: ''
  })
}

// 删除媒体文件
const removeMedia = (index) => {
  mediaList.value.splice(index, 1)
}

const handleEdit = (row) => {
  // 填充表单
  Object.assign(formData, row)
  // 加载媒体文件列表
  console.log('编辑时的媒体资源列表:', row.mediaList)
  if (row.mediaList && Array.isArray(row.mediaList)) {
    mediaList.value = [...row.mediaList]
    console.log('加载了媒体资源:', mediaList.value.length)
  } else {
    mediaList.value = []
    console.log('没有媒体资源')
  }
  dialogTitle.value = '编辑非遗项目'
  dialogVisible.value = true
}

const handleDelete = async (id) => {
  try {
    const res = await heritageApi.delete(id)
    if (res.success) {
      ElMessage.success('删除成功')
      await loadHeritageItems()
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
        // 根据选择的categoryId和regionId自动填充categoryName和regionName
        if (formData.categoryId) {
          const selectedCategory = categories.value.find(cat => cat.id === formData.categoryId)
          if (selectedCategory) {
            formData.categoryName = selectedCategory.name
          }
        }
        
        if (formData.regionId) {
          const selectedRegion = regions.value.find(reg => reg.id === formData.regionId)
          if (selectedRegion) {
            formData.regionName = selectedRegion.name
          }
        }
        
        let res
        let heritageId = formData.id
        
        if (heritageId) {
          // 编辑
          res = await heritageApi.update(formData)
        } else {
          // 添加
          res = await heritageApi.save(formData)
          heritageId = res.id // 获取新添加的项目 ID
        }
        
        if (res.success) {
          // 保存媒体资源
          await saveMediaResources(heritageId)
          
          ElMessage.success(formData.id ? '更新成功' : '添加成功')
          dialogVisible.value = false
          await loadHeritageItems()
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

// 保存媒体资源
const saveMediaResources = async (heritageId) => {
  try {
    // 首先删除所有现有的媒体资源
    await api.delete(`/heritage/deleteMediaByHeritageId/${heritageId}`)
    
    // 然后保存新的媒体资源
    for (const media of mediaList.value) {
      if (media.mediaUrl) {
        media.heritageItemId = heritageId
        await api.post('/heritage/saveMedia', media)
      }
    }
  } catch (error) {
    console.error('保存媒体资源失败:', error)
    // 媒体资源保存失败不影响主项目的保存
  }
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



// 为没有封面图片的项目生成独特的默认封面
const getDefaultCover = (item) => {
  // 构建基于项目信息的prompt
  let prompt = `traditional Chinese culture heritage ${encodeURIComponent(item.title)}`
  if (item.categoryName) {
    prompt += ` ${encodeURIComponent(item.categoryName)}`
  }
  if (item.regionName) {
    prompt += ` ${encodeURIComponent(item.regionName)}`
  }
  prompt += ` elegant artistic traditional style high quality`
  
  // 构建图片生成API URL，使用square尺寸用于后台管理页面
  return `https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=${prompt}&image_size=square`
}

const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  loadHeritageItems()
}

const handleCurrentChange = (page) => {
  currentPage.value = page
  loadHeritageItems()
}

// 处理状态变更
const handleStatusChange = async (heritage) => {
  try {
    const response = await heritageApi.update(heritage)
    if (response.success) {
      ElMessage.success('状态更新成功')
    } else {
      ElMessage.error('状态更新失败')
      // 恢复原来的状态
      loadHeritageItems()
    }
  } catch (error) {
    console.error('状态更新失败:', error)
    ElMessage.error('状态更新失败')
    // 恢复原来的状态
    loadHeritageItems()
  }
}
</script>

<style scoped>
.admin-heritage {
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

/* 媒体文件管理样式 */
.media-management {
  margin-top: 10px;
}

.media-list {
  margin-top: 15px;
}

.media-item {
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 15px;
  background: #f9f9f9;
}

.media-item .el-select {
  width: 100%;
}

.empty-media {
  text-align: center;
  color: #999;
  padding: 30px;
  background: #f5f7fa;
  border-radius: 8px;
  border: 1px dashed #dcdfe6;
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