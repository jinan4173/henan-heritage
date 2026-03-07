<template>
  <div class="admin-comment-view">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>评论管理</h2>
    </div>
    
    <!-- 操作栏 -->
    <div class="action-bar">
      <div class="search-bar">
        <el-button type="danger" @click="handleBatchDelete" :disabled="selectedRows.length === 0">批量删除</el-button>
        <el-input v-model="searchQuery" placeholder="搜索评论内容" style="width: 300px;"></el-input>
        <el-button type="primary" @click="handleSearch">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>
    </div>
    
    <!-- 评论列表 -->
    <el-table :data="filteredComments" style="width: 100%" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="序号" width="80">
        <template #default="scope">
          {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="id" label="评论ID" width="100" />
      <el-table-column prop="userId" label="用户ID" width="100" />
      <el-table-column prop="mediaId" label="媒体ID" width="100" />
      <el-table-column prop="content" label="评论内容" min-width="300" />
      <el-table-column prop="createTime" label="评论时间" width="180" />
      <el-table-column label="操作" width="150">
        <template #default="scope">
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
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '../../api/index'

const comments = ref([])
const total = ref(0)
const pageSize = ref(10)
const currentPage = ref(1)
const searchQuery = ref('')
const selectedRows = ref([])

// 处理选择变化
const handleSelectionChange = (val) => {
  selectedRows.value = val
}

// 批量删除
const handleBatchDelete = async () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请选择要删除的评论')
    return
  }
  
  try {
    const ids = selectedRows.value.map(item => item.id)
    const response = await api.delete('/comment/delete/batch', {
      data: ids
    })
    if (response.code === 200) {
      ElMessage.success('批量删除成功')
      loadComments()
      selectedRows.value = []
    } else {
      ElMessage.error('批量删除失败')
    }
  } catch (error) {
    console.error('批量删除失败:', error)
    ElMessage.error('批量删除失败')
  }
}

// 过滤后的评论列表
const filteredComments = computed(() => {
  if (!searchQuery.value) {
    return comments.value
  }
  return comments.value.filter(comment => 
    comment.content.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

onMounted(() => {
  loadComments()
})

const loadComments = async () => {
  try {
    console.log('开始获取评论列表...')
    const res = await api.get('/comment/list')
    console.log('获取评论列表响应:', res)
    if (res.code === 200) {
      comments.value = res.data
      total.value = comments.value.length
      console.log('获取评论列表成功:', comments.value)
    } else {
      console.error('获取评论列表失败:', res)
      ElMessage.error('获取评论列表失败')
    }
  } catch (error) {
    console.error('获取评论列表失败:', error)
    ElMessage.error('获取评论列表失败')
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

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这条评论吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const res = await api.delete(`/comment/delete/${id}`)
    if (res.code === 200) {
      ElMessage.success('删除成功')
      await loadComments()
    } else {
      ElMessage.error(res.message || '删除失败')
    }
  } catch (error) {
    if (error.message !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

const handleCurrentChange = (current) => {
  currentPage.value = current
  // 这里可以添加分页逻辑
}

const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  // 这里可以添加分页逻辑
}
</script>

<style scoped>
.admin-comment-view {
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
</style>