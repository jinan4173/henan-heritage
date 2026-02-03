<template>
  <div class="inheritor-view">
    <h2>非遗传承人</h2>
    <div class="inheritor-list">
      <el-table :data="inheritorList" style="width: 100%" @row-click="goToDetail">
        <el-table-column type="index" label="序号" width="80" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="gender" label="性别" width="80" />
        <el-table-column prop="nation" label="民族" width="100" />
        <el-table-column prop="project" label="项目" width="180" />
        <el-table-column prop="category" label="类别" width="120" />
      </el-table>
      <div class="pagination-container">
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
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api/index.js'
import { ElTable, ElTableColumn, ElPagination } from 'element-plus'

const router = useRouter()
const inheritorList = ref([])
const currentPage = ref(1)
const pageSize = ref(20) // 每页20个
const total = ref(0)

onMounted(() => {
  loadInheritors()
})

const loadInheritors = async () => {
  try {
    const response = await api.get('/inheritor/list', {
      params: {
        page: currentPage.value,
        pageSize: pageSize.value
      }
    })
    if (response.success) {
      inheritorList.value = response.data
      total.value = response.total || 0
    }
  } catch (error) {
    console.error('加载传承人失败:', error)
  }
}

const goToDetail = (row) => {
  router.push(`/inheritor/${row.id}`)
}

// 处理每页大小变化
const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  loadInheritors()
}

// 处理当前页码变化
const handleCurrentChange = (current) => {
  currentPage.value = current
  loadInheritors()
}
</script>

<style scoped>
.inheritor-view {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.inheritor-view h2 {
  font-size: 1.8rem;
  margin-bottom: 20px;
  color: #333;
  text-align: center;
}

.inheritor-list {
  margin-top: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  overflow: hidden;
}

.el-table {
  border-radius: 10px;
  overflow: hidden;
}

.el-table th {
  background-color: #f5f5f5;
  font-weight: bold;
}

.el-table tr:hover {
  background-color: #f9f9f9;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 15px;
  background: #f9f9f9;
  border-top: 1px solid #eee;
  border-radius: 0 0 10px 10px;
}
</style>