<template>
  <div class="admin-inheritor-view">
    <div class="page-header">
      <h2>传承人管理</h2>
    </div>

    <div class="action-bar">
      <el-button type="primary" @click="handleAdd">添加传承人</el-button>
      <div class="search-bar">
        <el-input v-model="searchQuery" placeholder="搜索传承人姓名" style="width: 200px;"></el-input>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>
    </div>

    <el-table :data="inheritorList" style="width: 100%" border>
      <el-table-column label="序号" width="80">
        <template #default="scope">
          {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="120" />
      <el-table-column prop="gender" label="性别" width="80" />
      <el-table-column prop="nation" label="民族" width="100" />
      <el-table-column prop="title" label="项目" width="180" />
      <el-table-column prop="category" label="类别" width="120" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
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
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="formData" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formData.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="formData.gender" placeholder="请选择性别">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="民族" prop="nation">
          <el-input v-model="formData.nation" placeholder="请输入民族" />
        </el-form-item>
        <el-form-item label="项目" prop="title">
          <el-input v-model="formData.title" placeholder="请输入项目" />
        </el-form-item>
        <el-form-item label="类别" prop="category">
          <el-select v-model="formData.category" placeholder="请选择类别">
            <el-option
              v-for="category in categories"
              :key="category.id"
              :label="category.name"
              :value="category.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch v-model="formData.status" :active-value="1" :inactive-value="0" />
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
import api from '../../api/index.js'
import { ElMessage } from 'element-plus'

const inheritorList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const searchQuery = ref('')
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formData = ref({})
const formRef = ref(null)

// 类别列表
const categories = ref([])



const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  nation: [{ required: true, message: '请输入民族', trigger: 'blur' }],
  title: [{ required: true, message: '请输入项目', trigger: 'blur' }],
  category: [{ required: true, message: '请选择类别', trigger: 'change' }]
}

onMounted(() => {
  loadInheritors()
  loadCategories()
})

// 加载类别列表
const loadCategories = async () => {
  try {
    const response = await api.get('/heritage/categories')
    if (response.success) {
      categories.value = response.data || []
    }
  } catch (error) {
    console.error('加载类别数据失败:', error)
    categories.value = []
  }
}



const loadInheritors = async () => {
  try {
    let response
    if (searchQuery.value) {
      // 当搜索时，需要获取所有数据进行过滤
      response = await api.get('/inheritor/list?page=1&pageSize=1000')
      if (response.success) {
        let allData = response.data
        let filteredData = allData.filter(item => item.name.includes(searchQuery.value))
        total.value = filteredData.length
        // 对过滤后的数据进行分页
        const start = (currentPage.value - 1) * pageSize.value
        const end = start + pageSize.value
        inheritorList.value = filteredData.slice(start, end)
      }
    } else {
      // 正常分页查询
      response = await api.get(`/inheritor/list?page=${currentPage.value}&pageSize=${pageSize.value}`)
      if (response.success) {
        inheritorList.value = response.data
        total.value = response.total || 0
      }
    }
  } catch (error) {
    console.error('加载传承人失败:', error)
    ElMessage.error('加载传承人失败')
  }
}

const handleSearch = () => {
  loadInheritors()
}

const resetSearch = () => {
  searchQuery.value = ''
  loadInheritors()
}

const handleSizeChange = (size) => {
  pageSize.value = size
  loadInheritors()
}

const handleCurrentChange = (current) => {
  currentPage.value = current
  loadInheritors()
}

const handleAdd = () => {
  dialogTitle.value = '添加传承人'
  formData.value = {
    name: '',
    gender: '',
    nation: '',
    title: '',
    category: '',
    status: 1
  }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑传承人'
  formData.value = { ...row }
  dialogVisible.value = true
}

const handleDelete = async (id) => {
  try {
    const response = await api.delete(`/inheritor/delete/${id}`)
    if (response.success) {
      ElMessage.success('删除成功')
      loadInheritors()
    } else {
      ElMessage.error('删除失败')
    }
  } catch (error) {
    console.error('删除传承人失败:', error)
    ElMessage.error('删除传承人失败')
  }
}

const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    let response
    if (formData.value.id) {
      // 编辑
      response = await api.post('/inheritor/update', formData.value)
    } else {
      // 添加
      response = await api.post('/inheritor/save', formData.value)
    }
    if (response.success) {
      ElMessage.success('操作成功')
      dialogVisible.value = false
      loadInheritors()
    } else {
      ElMessage.error('操作失败')
    }
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败')
  }
}
</script>

<style scoped>
.admin-inheritor-view {
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
