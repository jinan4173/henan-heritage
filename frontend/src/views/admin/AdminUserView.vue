<template>
  <div class="admin-user-view">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>用户管理</h2>
    </div>
    
    <!-- 操作栏 -->
    <div class="action-bar">
      <div class="search-bar">
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="danger" @click="handleBatchDelete" :disabled="selectedRows.length === 0">批量删除</el-button>
        <el-input v-model="searchQuery" placeholder="搜索用户名或邮箱" style="width: 300px;"></el-input>
        <el-button type="primary" @click="handleSearch">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>
    </div>
    
    <!-- 用户列表 -->
    <el-table :data="filteredUsers" style="width: 100%" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="序号" width="80">
        <template #default="scope">
          {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" min-width="200" />
      <el-table-column prop="email" label="邮箱" min-width="200" />
      <el-table-column prop="isAdmin" label="管理员" width="100">
        <template #default="scope">
          <el-switch 
            v-model="scope.row.isAdmin" 
            :active-value="1" 
            :inactive-value="0" 
            @change="handleAdminChange(scope.row)"
          />
        </template>
      </el-table-column>
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
      <el-table-column prop="updatedAt" label="更新时间" width="180" />
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
    
    <!-- 添加/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form :model="formData" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="formData.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="管理员" prop="isAdmin">
          <el-switch v-model="formData.isAdmin" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch v-model="formData.status" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSave">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import api from '../../api/index'

const users = ref([])
const total = ref(0)
const pageSize = ref(10)
const currentPage = ref(1)
const searchQuery = ref('')
const dialogVisible = ref(false)
const dialogTitle = ref('添加用户')
const formRef = ref(null)
const selectedRows = ref([])

// 处理选择变化
const handleSelectionChange = (val) => {
  selectedRows.value = val
}

// 批量删除
const handleBatchDelete = async () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请选择要删除的用户')
    return
  }
  
  try {
    const ids = selectedRows.value.map(item => item.id)
    const response = await api.delete('/user/delete/batch', {
      data: { ids }
    })
    if (response.success) {
      ElMessage.success('批量删除成功')
      loadUsers()
      selectedRows.value = []
    } else {
      ElMessage.error('批量删除失败')
    }
  } catch (error) {
    console.error('批量删除失败:', error)
    ElMessage.error('批量删除失败')
  }
}

const formData = reactive({
  id: '',
  username: '',
  password: '',
  email: '',
  isAdmin: 0,
  status: 1
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ]
}

// 过滤后的用户列表
const filteredUsers = computed(() => {
  if (!searchQuery.value) {
    return users.value
  }
  return users.value.filter(user => 
    user.username.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    user.email.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

onMounted(() => {
  loadUsers()
})

const loadUsers = async () => {
  try {
    const res = await api.get('/user/list')
    if (res.success) {
      users.value = res.data
      total.value = users.value.length
    } else {
      ElMessage.error('获取用户列表失败')
    }
  } catch (error) {
    console.error('获取用户列表失败:', error)
    ElMessage.error('获取用户列表失败')
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

const handleAdd = () => {
  // 重置表单
  formRef.value?.resetFields()
  Object.assign(formData, {
    id: '',
    username: '',
    password: '',
    email: '',
    isAdmin: 0,
    status: 1
  })
  dialogTitle.value = '添加用户'
  dialogVisible.value = true
}

const handleEdit = (row) => {
  // 填充表单
  Object.assign(formData, {
    id: row.id,
    username: row.username,
    password: '', // 编辑时密码为空，不修改密码
    email: row.email,
    isAdmin: row.isAdmin || 0,
    status: row.status
  })
  dialogTitle.value = '编辑用户'
  dialogVisible.value = true
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个用户吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const res = await api.delete(`/user/delete/${id}`)
    if (res.success) {
      ElMessage.success('删除成功')
      await loadUsers()
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

const handleSave = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        let res
        if (formData.id) {
          // 编辑用户
          res = await api.post('/user/update', formData)
        } else {
          // 添加用户
          res = await api.post('/user/save', formData)
        }
        if (res.success) {
          ElMessage.success(formData.id ? '更新成功' : '添加成功')
          dialogVisible.value = false
          await loadUsers()
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

const handleCurrentChange = (current) => {
  currentPage.value = current
  // 这里可以添加分页逻辑
}

const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  // 这里可以添加分页逻辑
}

// 处理状态变更
const handleStatusChange = async (user) => {
  try {
    const response = await api.post('/user/update', user)
    if (response.success) {
      ElMessage.success('状态更新成功')
    } else {
      ElMessage.error('状态更新失败')
      // 恢复原来的状态
      loadUsers()
    }
  } catch (error) {
    console.error('状态更新失败:', error)
    ElMessage.error('状态更新失败')
    // 恢复原来的状态
    loadUsers()
  }
}

// 处理管理员状态变更
const handleAdminChange = async (user) => {
  try {
    const response = await api.post('/user/update', user)
    if (response.success) {
      ElMessage.success('管理员状态更新成功')
    } else {
      ElMessage.error('管理员状态更新失败')
      // 恢复原来的状态
      loadUsers()
    }
  } catch (error) {
    console.error('管理员状态更新失败:', error)
    ElMessage.error('管理员状态更新失败')
    // 恢复原来的状态
    loadUsers()
  }
}
</script>

<style scoped>
.admin-user-view {
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

.admin-yes {
  display: inline-block;
  padding: 2px 8px;
  border-radius: 10px;
  background-color: #fdf6ec;
  color: #e6a23c;
  font-size: 12px;
}

.admin-no {
  display: inline-block;
  padding: 2px 8px;
  border-radius: 10px;
  background-color: #ecf5ff;
  color: #409eff;
  font-size: 12px;
}
</style>