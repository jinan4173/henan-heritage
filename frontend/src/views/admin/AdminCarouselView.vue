<template>
  <div class="admin-carousel">
    <div class="page-header">
      <h2>轮播图管理</h2>
    </div>

    <div class="action-bar">
      <div class="search-bar">
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="danger" @click="handleBatchDelete" :disabled="selectedRows.length === 0">批量删除</el-button>
        <el-input v-model="searchQuery" placeholder="搜索轮播图标题" style="width: 300px;"></el-input>
        <el-button type="primary" @click="handleSearch">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>
    </div>

    <el-table :data="carouselList" style="width: 100%" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="序号" width="80">
        <template #default="scope">
          {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题" min-width="200" />
      <el-table-column label="图片" width="200">
        <template #default="scope">
          <el-image 
            :src="scope.row.imageUrl" 
            fit="cover" 
            style="width: 100px; height: 60px;" 
            preview-src-list="[scope.row.imageUrl]"
          />
        </template>
      </el-table-column>
      <el-table-column prop="orderNum" label="排序序号" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="scope">
          <el-switch 
            v-model="scope.row.status" 
            :active-value="1" 
            :inactive-value="0" 
            @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>
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
    
    <!-- 添加/编辑轮播图对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="500px"
    >
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="form.title" placeholder="请输入轮播图标题" />
        </el-form-item>
        <el-form-item label="图片URL">
          <el-input v-model="form.imageUrl" placeholder="请输入轮播图图片URL" />
        </el-form-item>
        <el-form-item label="排序序号">
          <el-input-number v-model="form.orderNum" :min="0" :max="999" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../../api/index.js'
import { ElMessage } from 'element-plus'

const carouselList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const searchQuery = ref('')
const selectedRows = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const form = ref({
  id: '',
  title: '',
  imageUrl: '',
  orderNum: 0,
  status: 1
})

// 处理选择变化
const handleSelectionChange = (val) => {
  selectedRows.value = val
}

// 批量删除
const handleBatchDelete = async () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请选择要删除的轮播图')
    return
  }
  
  try {
    const ids = selectedRows.value.map(item => item.id)
    const response = await api.delete('/carousel/delete/batch', {
      data: { ids }
    })
    if (response.success) {
      ElMessage.success('批量删除成功')
      loadCarouselList()
      selectedRows.value = []
    } else {
      ElMessage.error('批量删除失败')
    }
  } catch (error) {
    console.error('批量删除失败:', error)
    ElMessage.error('批量删除失败')
  }
}

// 搜索
const handleSearch = () => {
  loadCarouselList()
}

// 重置搜索
const resetSearch = () => {
  searchQuery.value = ''
  loadCarouselList()
}

// 加载轮播图列表
const loadCarouselList = async () => {
  try {
    const response = await api.get('/carousel/list')
    if (response.success) {
      carouselList.value = response.data || []
      total.value = response.total || 0
    }
  } catch (error) {
    console.error('加载轮播图列表失败:', error)
    ElMessage.error('加载轮播图列表失败')
  }
}

// 打开添加对话框
const handleAdd = () => {
  dialogTitle.value = '添加轮播图'
  form.value = {
    id: '',
    title: '',
    imageUrl: '',
    orderNum: 0,
    status: 1
  }
  dialogVisible.value = true
}

// 打开编辑对话框
const handleEdit = (row) => {
  dialogTitle.value = '编辑轮播图'
  form.value = {
    id: row.id,
    title: row.title,
    imageUrl: row.imageUrl,
    orderNum: row.orderNum,
    status: row.status
  }
  dialogVisible.value = true
}

// 处理提交
const handleSave = async () => {
  try {
    let response
    if (form.value.id) {
      // 编辑
      response = await api.post('/carousel/update', form.value)
    } else {
      // 添加
      response = await api.post('/carousel/add', form.value)
    }
    if (response.success) {
      ElMessage.success('保存成功')
      dialogVisible.value = false
      loadCarouselList()
    } else {
      ElMessage.error('保存失败')
    }
  } catch (error) {
    console.error('提交轮播图数据失败:', error)
    ElMessage.error('保存失败')
  }
}

// 处理状态变更
const handleStatusChange = async (row) => {
  try {
    const response = await api.post('/carousel/update', row)
    if (response.success) {
      ElMessage.success('状态更新成功')
    } else {
      ElMessage.error('状态更新失败')
      loadCarouselList()
    }
  } catch (error) {
    console.error('更新轮播图状态失败:', error)
    ElMessage.error('状态更新失败')
    loadCarouselList()
  }
}

// 处理删除
const handleDelete = async (id) => {
  try {
    const response = await api.delete(`/carousel/delete/${id}`)
    if (response.success) {
      ElMessage.success('删除成功')
      loadCarouselList()
    } else {
      ElMessage.error('删除失败')
    }
  } catch (error) {
    console.error('删除轮播图失败:', error)
    ElMessage.error('删除失败')
  }
}

// 处理分页大小变化
const handleSizeChange = (size) => {
  pageSize.value = size
  loadCarouselList()
}

// 处理当前页变化
const handleCurrentChange = (current) => {
  currentPage.value = current
  loadCarouselList()
}

// 页面加载时初始化数据
onMounted(() => {
  loadCarouselList()
})
</script>

<style scoped>
.admin-carousel {
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
