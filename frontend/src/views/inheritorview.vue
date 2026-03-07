<template>
  <div class="inheritor-view">
    <div class="inheritor-container">
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
    if (response && response.code === 200) {
      // 只显示状态为1的传承人
      inheritorList.value = (response.data || []).filter(item => item.status === 1)
      total.value = inheritorList.value.length
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
  width: 100%;
  padding: 20px;
  background-color: var(--background-color);
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
}

.inheritor-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.inheritor-view h2 {
  font-size: 2rem;
  margin-bottom: 30px;
  color: var(--primary-color);
  text-align: center;
  position: relative;
  padding-bottom: 15px;
}

.inheritor-view h2::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 80px;
  height: 3px;
  background-color: var(--accent-color);
  border-radius: 2px;
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