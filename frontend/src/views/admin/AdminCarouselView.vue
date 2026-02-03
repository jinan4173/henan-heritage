<template>
  <div class="admin-carousel">
    <div class="page-header">
      <h1>轮播图管理</h1>
      <el-button type="primary" @click="openAddDialog">
        <el-icon><Plus /></el-icon>
        <span>添加轮播图</span>
      </el-button>
    </div>
    
    <div class="content">
      <el-table :data="carouselList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" />
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
        <el-table-column label="状态" width="120">
          <template #default="scope">
            <el-switch 
              v-model="scope.row.status" 
              active-value="1" 
              inactive-value="0" 
              @change="handleStatusChange(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="openEditDialog(scope.row)">
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
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
          <el-switch v-model="form.status" active-value="1" inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Plus, Edit, Delete } from '@element-plus/icons-vue'

const carouselList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const form = ref({
  id: '',
  title: '',
  imageUrl: '',
  orderNum: 0,
  status: 1
})

// 加载轮播图列表
const loadCarouselList = async () => {
  try {
    const response = await fetch('/api/carousel/list')
    if (response.ok) {
      const data = await response.json()
      if (data.success) {
        carouselList.value = data.data || []
      }
    }
  } catch (error) {
    console.error('加载轮播图列表失败:', error)
  }
}

// 打开添加对话框
const openAddDialog = () => {
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
const openEditDialog = (row) => {
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
const handleSubmit = async () => {
  try {
    let url = '/api/carousel/add'
    let method = 'POST'
    
    if (form.value.id) {
      url = '/api/carousel/update'
      method = 'PUT'
    }
    
    const response = await fetch(url, {
      method,
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(form.value)
    })
    
    if (response.ok) {
      const data = await response.json()
      if (data.success) {
        dialogVisible.value = false
        loadCarouselList()
      }
    }
  } catch (error) {
    console.error('提交轮播图数据失败:', error)
  }
}

// 处理状态变更
const handleStatusChange = async (row) => {
  try {
    const response = await fetch('/api/carousel/update', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(row)
    })
    
    if (!response.ok) {
      // 如果更新失败，恢复原来的状态
      loadCarouselList()
    }
  } catch (error) {
    console.error('更新轮播图状态失败:', error)
    loadCarouselList()
  }
}

// 处理删除
const handleDelete = async (id) => {
  try {
    const response = await fetch(`/api/carousel/delete/${id}`, {
      method: 'DELETE'
    })
    
    if (response.ok) {
      const data = await response.json()
      if (data.success) {
        loadCarouselList()
      }
    }
  } catch (error) {
    console.error('删除轮播图失败:', error)
  }
}

// 页面加载时初始化数据
onMounted(() => {
  loadCarouselList()
})
</script>

<style scoped>
.admin-carousel {
  padding: 20px;
  min-height: calc(100vh - 40px);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eaeaea;
}

.page-header h1 {
  font-size: 1.5rem;
  margin: 0;
  color: #2B2B2B;
}

.content {
  background: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
}

.dialog-footer {
  text-align: right;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .admin-carousel {
    padding: 10px;
  }
  
  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  
  .content {
    padding: 10px;
  }
}
</style>
