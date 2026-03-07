<template>
  <div class="admin-notice-view">
    <div class="page-header">
      <h2>公告管理</h2>
    </div>

    <div class="action-bar">
      <div class="search-bar">
        <el-button type="primary" @click="dialogVisible = true">新增</el-button>
        <el-button type="danger" @click="handleBatchDelete" :disabled="selectedNoticeIds.length === 0">批量删除</el-button>
        <el-input v-model="searchQuery" placeholder="搜索公告标题" style="width: 300px;"></el-input>
        <el-button type="primary" @click="handleSearch">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>
    </div>

    <el-table :data="filteredNoticeList" style="width: 100%" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="序号" width="80">
        <template #default="scope">
          {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题"></el-table-column>
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
          <el-button size="small" @click="editNotice(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteNotice(scope.row.id)">删除</el-button>
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
          <el-input v-model="form.title" placeholder="请输入公告标题" style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input
            v-model="form.content"
            type="textarea"
            :rows="6"
            placeholder="请输入公告内容"
            style="width: 100%"
          ></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" active-value="1" inactive-value="0"></el-switch>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveNotice">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import api from '../../api/index.js'
import { ElMessage } from 'element-plus'

const noticeList = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('新增公告')
const form = ref({
  id: null,
  title: '',
  content: '',
  status: 1
})
const selectAll = ref(false)
const selectedNoticeIds = ref([])

// 过滤后的公告列表
const filteredNoticeList = computed(() => {
  if (!searchQuery.value) {
    return noticeList.value
  }
  return noticeList.value.filter(notice => 
    notice.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

onMounted(() => {
  fetchNoticeList()
})

const fetchNoticeList = async () => {
  try {
    const response = await api.get('/culture-news/list', { params: { type: 3 } })
    if (response.success) {
      noticeList.value = response.data
      total.value = response.total
    }
  } catch (error) {
    console.error('获取公告列表失败:', error)
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

const editNotice = (notice) => {
  form.value = { ...notice }
  dialogTitle.value = '编辑公告'
  dialogVisible.value = true
}

const deleteNotice = async (id) => {
  try {
    const response = await api.delete(`/culture-news/delete/${id}`)
    if (response.success) {
      ElMessage.success('删除成功')
      fetchNoticeList()
    }
  } catch (error) {
    console.error('删除公告失败:', error)
    ElMessage.error('删除失败')
  }
}

const saveNotice = async () => {
  try {
    const noticeData = {
      ...form.value,
      type: 3 // 设置类型为公告
    }
    let response
    if (noticeData.id) {
      response = await api.post('/culture-news/update', noticeData)
    } else {
      response = await api.post('/culture-news/save', noticeData)
    }
    if (response.success) {
      ElMessage.success('保存成功')
      dialogVisible.value = false
      fetchNoticeList()
      resetForm()
    }
  } catch (error) {
    console.error('保存公告失败:', error)
    ElMessage.error('保存失败')
  }
}

const resetForm = () => {
  form.value = {
    id: null,
    title: '',
    content: '',
    status: 1
  }
  dialogTitle.value = '新增公告'
}

const handleSizeChange = (size) => {
  pageSize.value = size
  fetchNoticeList()
}

const handleCurrentChange = (current) => {
  currentPage.value = current
  fetchNoticeList()
}

// 处理状态变更
const handleStatusChange = async (notice) => {
  try {
    const noticeData = {
      ...notice,
      type: 3 // 确保类型为公告
    }
    const response = await api.post('/culture-news/update', noticeData)
    if (response.success) {
      ElMessage.success('状态更新成功')
    } else {
      ElMessage.error('状态更新失败')
      // 恢复原来的状态
      fetchNoticeList()
    }
  } catch (error) {
    console.error('状态更新失败:', error)
    ElMessage.error('状态更新失败')
    // 恢复原来的状态
    fetchNoticeList()
  }
}

// 处理选择变化
const handleSelectionChange = (selection) => {
  selectedNoticeIds.value = selection.map(item => item.id)
  selectAll.value = selection.length === filteredNoticeList.value.length
}

// 处理全选
const handleSelectAll = (value) => {
  selectAll.value = value
  if (value) {
    selectedNoticeIds.value = filteredNoticeList.value.map(item => item.id)
  } else {
    selectedNoticeIds.value = []
  }
}

// 处理批量删除
const handleBatchDelete = async () => {
  if (selectedNoticeIds.value.length === 0) {
    ElMessage.warning('请选择要删除的公告')
    return
  }
  
  try {
    const response = await api.post('/culture-news/batch-delete', {
      ids: selectedNoticeIds.value
    })
    if (response.success) {
      ElMessage.success('批量删除成功')
      fetchNoticeList()
      selectedNoticeIds.value = []
      selectAll.value = false
    } else {
      ElMessage.error('批量删除失败')
    }
  } catch (error) {
    console.error('批量删除失败:', error)
    ElMessage.error('批量删除失败')
  }
}
</script>

<style scoped>
.admin-notice-view {
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
