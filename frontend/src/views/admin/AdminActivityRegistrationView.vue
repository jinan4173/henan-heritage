<template>
  <div class="admin-activity-registration-view">
    <div class="page-header">
      <h2>活动报名管理</h2>
    </div>

    <div class="action-bar">
      <div class="search-bar">
        <el-input v-model="searchQuery" placeholder="搜索报名人姓名或活动" style="width: 300px;"></el-input>
        <el-button type="primary" @click="handleSearch">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>
    </div>

    <el-table :data="registrationList" style="width: 100%" border>
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="序号" width="80">
        <template #default="scope">
          {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="activityName" label="活动名称" width="200" />
      <el-table-column prop="name" label="报名人姓名" width="150" />
      <el-table-column prop="phone" label="联系电话" width="150" />
      <el-table-column prop="peopleCount" label="报名人数" width="100" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-select v-model="scope.row.status" @change="handleStatusChange(scope.row)" size="small">
            <el-option :value="1" label="待处理" />
            <el-option :value="2" label="已确认" />
            <el-option :value="3" label="已拒绝" />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="报名时间" width="180" />
      <el-table-column label="操作" width="150">
        <template #default="scope">
          <el-button size="small" @click="handleView(scope.row)">查看</el-button>
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

    <!-- 查看详情对话框 -->
    <el-dialog v-model="dialogVisible" title="报名详情" width="600px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="活动名称">
          <el-input v-model="form.activityName" disabled style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="报名人姓名">
          <el-input v-model="form.name" disabled style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.phone" disabled style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="报名人数">
          <el-input v-model="form.peopleCount" disabled style="width: 100%"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            v-model="form.remark"
            type="textarea"
            :rows="3"
            disabled
            style="width: 100%"
          ></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" @change="handleStatusChange(form)" style="width: 100%">
            <el-option :value="1" label="待处理" />
            <el-option :value="2" label="已确认" />
            <el-option :value="3" label="已拒绝" />
          </el-select>
        </el-form-item>
        <el-form-item label="报名时间">
          <el-input v-model="form.createTime" disabled style="width: 100%"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../../api/index.js'
import { ElMessage } from 'element-plus'

const registrationList = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const selectedRows = ref([])

const form = ref({
  id: null,
  activityId: null,
  activityName: '',
  name: '',
  phone: '',
  peopleCount: 1,
  remark: '',
  status: 1,
  createTime: ''
})

// 处理选择变化
const handleSelectionChange = (val) => {
  selectedRows.value = val
}

onMounted(() => {
  loadRegistrations()
})

const loadRegistrations = async () => {
  try {
    const response = await api.get('/activity/registration/all')
    console.log('loadRegistrations response:', response)
    if (response.code === 200) {
      registrationList.value = response.data
      total.value = response.data.length
    }
  } catch (error) {
    console.error('获取报名列表失败:', error)
  }
}

const handleView = (registration) => {
  form.value = { ...registration }
  dialogVisible.value = true
}

const handleDelete = async (id) => {
  try {
    const response = await api.delete(`/activity/registration/delete/${id}`)
    if (response === '删除成功') {
      ElMessage.success('删除成功')
      loadRegistrations()
    } else {
      ElMessage.error('删除失败')
    }
  } catch (error) {
    console.error('删除失败:', error)
    ElMessage.error('删除失败')
  }
}

const handleStatusChange = async (registration) => {
  try {
    const response = await api.put('/activity/registration/update', registration)
    if (response === '状态更新成功') {
      ElMessage.success('状态更新成功')
      loadRegistrations()
    } else {
      ElMessage.error('状态更新失败')
      loadRegistrations()
    }
  } catch (error) {
    console.error('状态更新失败:', error)
    ElMessage.error('状态更新失败')
    loadRegistrations()
  }
}

const handleSearch = () => {
  // 搜索逻辑
  loadRegistrations()
}

const resetSearch = () => {
  searchQuery.value = ''
  loadRegistrations()
}

const handleSizeChange = (size) => {
  pageSize.value = size
  loadRegistrations()
}

const handleCurrentChange = (current) => {
  currentPage.value = current
  loadRegistrations()
}
</script>

<style scoped>
.admin-activity-registration-view {
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