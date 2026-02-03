<template>
  <div class="admin-news-view">
    <div class="page-header">
      <h2>文化资讯管理</h2>
    </div>
    
    <!-- 操作栏 -->
    <div class="action-bar">
      <el-input v-model="searchQuery" placeholder="搜索资讯标题" style="width: 300px; margin-right: 10px;"></el-input>
      <el-button type="primary" @click="handleSearch">搜索</el-button>
      <el-button @click="resetSearch">重置</el-button>
      <el-button type="primary" @click="dialogVisible = true" style="margin-left: auto;">新增资讯</el-button>
    </div>
    
    <!-- 资讯列表 -->
    <el-table :data="filteredNewsList" style="width: 100%" border>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="title" label="标题"></el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" width="180"></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="scope">
          <el-button size="small" @click="editNews(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteNews(scope.row.id)">删除</el-button>
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
          <el-input v-model="form.title" placeholder="请输入资讯标题" style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input
            v-model="form.content"
            type="textarea"
            :rows="6"
            placeholder="请输入资讯内容"
            style="width: 100%"
          ></el-input>
        </el-form-item>
        <el-form-item label="封面图片">
          <el-input v-model="form.coverImage" placeholder="请输入封面图片URL" style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveNews">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import api from '../../api/index.js'
import { ElMessage } from 'element-plus'

const newsList = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('新增资讯')
const form = ref({
  id: null,
  title: '',
  content: '',
  coverImage: '',
  status: 1
})

// 过滤后的资讯列表
const filteredNewsList = computed(() => {
  if (!searchQuery.value) {
    return newsList.value
  }
  return newsList.value.filter(news => 
    news.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

onMounted(() => {
  fetchNewsList()
})

const fetchNewsList = async () => {
  try {
    const response = await api.get('/culture-news/list', { params: { type: 1 } })
    if (response.success) {
      newsList.value = response.data
      total.value = response.total
    }
  } catch (error) {
    console.error('获取资讯列表失败:', error)
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

const editNews = (news) => {
  form.value = { ...news }
  dialogTitle.value = '编辑资讯'
  dialogVisible.value = true
}

const deleteNews = async (id) => {
  try {
    const response = await api.delete(`/culture-news/delete/${id}`)
    if (response.success) {
      ElMessage.success('删除成功')
      fetchNewsList()
    }
  } catch (error) {
    console.error('删除资讯失败:', error)
    ElMessage.error('删除失败')
  }
}

const saveNews = async () => {
  try {
    const newsData = {
      ...form.value,
      type: 1 // 设置类型为新闻
    }
    let response
    if (newsData.id) {
      response = await api.post('/culture-news/update', newsData)
    } else {
      response = await api.post('/culture-news/save', newsData)
    }
    if (response.success) {
      ElMessage.success('保存成功')
      dialogVisible.value = false
      fetchNewsList()
      resetForm()
    }
  } catch (error) {
    console.error('保存资讯失败:', error)
    ElMessage.error('保存失败')
  }
}

const resetForm = () => {
  form.value = {
    id: null,
    title: '',
    content: '',
    coverImage: '',
    status: 1
  }
  dialogTitle.value = '新增资讯'
}

const handleSizeChange = (size) => {
  pageSize.value = size
  fetchNewsList()
}

const handleCurrentChange = (current) => {
  currentPage.value = current
  fetchNewsList()
}
</script>

<style scoped>
.admin-news-view {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.page-header {
  margin-bottom: 20px;
}

.page-header h2 {
  font-size: 1.5rem;
  margin: 0;
  color: #333;
}

.action-bar {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}

.dialog-footer {
  text-align: right;
}
</style>
