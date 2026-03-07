<template>
  <el-dialog
    v-model="dialogVisible"
    title="新增内容"
    width="800px"
    :close-on-click-modal="false"
  >
    <el-form
      :model="form"
      :rules="rules"
      ref="formRef"
      label-width="100px"
    >
      <el-form-item label="资讯标题" prop="title">
        <el-input v-model="form.title" placeholder="请输入资讯标题" style="width: 100%"></el-input>
      </el-form-item>
      
      <el-form-item label="资讯封面" prop="coverUrl">
        <el-upload
            class="avatar-uploader"
            action="/api/image/upload"
            :show-file-list="false"
            :on-success="handleImageUpload"
            :before-upload="beforeImageUpload"
          >
          <el-button type="primary">上传</el-button>
        </el-upload>
        <div v-if="form.coverUrl" style="margin-top: 10px;">
          <img :src="form.coverUrl" style="width: 100px; height: 100px; object-fit: cover;" />
        </div>
      </el-form-item>
      
      <el-form-item label="发布时间" prop="publishTime">
        <el-date-picker
          v-model="form.publishTime"
          type="datetime"
          placeholder="请选择发布时间"
          style="width: 100%"
          :locale="zhCn"
          :picker-options="{
            shortcuts: [
              { text: '今天', value: new Date() },
              { text: '昨天', value: new Date(Date.now() - 86400000) },
              { text: '一周前', value: new Date(Date.now() - 604800000) }
            ]
          }"
        />
      </el-form-item>
      
      <el-form-item label="资讯简介" prop="summary">
        <el-input
          v-model="form.summary"
          type="textarea"
          :rows="3"
          placeholder="请输入资讯简介"
          style="width: 100%"
        ></el-input>
      </el-form-item>
      
      <el-form-item label="资讯内容" prop="content">
        <div style="border: 1px solid #dcdfe6; border-radius: 4px; overflow: hidden;">
          <Toolbar
            :editor="editorRef"
            :defaultConfig="toolbarConfig"
            :mode="'default'"
            style="border-bottom: 1px solid #dcdfe6;"
          />
          <Editor
            v-model="form.content"
            :defaultConfig="editorConfig"
            :mode="'default'"
            style="height: 400px; overflow-y: hidden;"
            @onCreated="onCreated"
          />
        </div>
      </el-form-item>
    </el-form>
    
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定发布</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../api/index.js'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import '@wangeditor/editor/dist/css/style.css'

// 弹窗可见性
const dialogVisible = ref(false)

// 表单引用
const formRef = ref(null)

// 编辑器实例
const editorRef = ref(null)

// 表单数据
const form = reactive({
  title: '',
  publishTime: new Date(),
  coverUrl: '',
  summary: '',
  content: ''
})

// 编辑器配置
const editorConfig = {
  placeholder: '请输入资讯内容',
  MENU_CONF: {
    uploadImage: {
      server: '/api/image/upload',
      fieldName: 'file',
      maxFileSize: 2 * 1024 * 1024, // 2MB
      allowedFileTypes: ['image/jpeg', 'image/png'],
      onSuccess: (file, res) => {
        if (res.success) {
          return res.filePath
        } else {
          throw new Error('上传失败')
        }
      },
      onError: (file, err, res) => {
        ElMessage.error('图片上传失败')
      }
    },
    uploadVideo: {
      server: '/api/image/upload',
      fieldName: 'file',
      maxFileSize: 10 * 1024 * 1024, // 10MB
      allowedFileTypes: ['video/mp4', 'video/webm'],
      onSuccess: (file, res) => {
        if (res.success) {
          return res.filePath
        } else {
          throw new Error('上传失败')
        }
      },
      onError: (file, err, res) => {
        ElMessage.error('视频上传失败')
      }
    }
  }
}

// 工具栏配置
const toolbarConfig = {
  excludeKeys: []
}

// 表单校验规则
const rules = reactive({
  title: [
    { required: true, message: '请输入资讯标题', trigger: 'blur' }
  ],
  publishTime: [
    { required: true, message: '请选择发布时间', trigger: 'change' }
  ],
  coverUrl: [
    { required: true, message: '请上传资讯封面', trigger: 'blur' }
  ],
  summary: [
    { required: true, message: '请输入资讯简介', trigger: 'blur' }
  ],
  content: [
    { required: true, message: '请输入资讯内容', trigger: 'blur' }
  ]
})



// 初始化组件
onMounted(() => {
  // 组件初始化逻辑
})

// 编辑器创建完成
const onCreated = (editor) => {
  editorRef.value = editor
}

// 处理图片上传
const handleImageUpload = (response) => {
  if (response.success) {
    form.coverUrl = response.filePath
    ElMessage.success('图片上传成功')
  } else {
    ElMessage.error('图片上传失败')
  }
}

// 图片上传前的验证
const beforeImageUpload = (file) => {
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isJpgOrPng) {
    ElMessage.error('只能上传JPG/PNG图片!')
  }
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过2MB!')
  }
  return isJpgOrPng && isLt2M
}

// 提交表单
const submitForm = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    
    // 准备提交数据
    const submitData = {
      title: form.title,
      content: form.content,
      coverImage: form.coverUrl,
      summary: form.summary,
      date: form.publishTime ? form.publishTime.toISOString() : '',
      type: 1, // 固定类型为资讯
      status: 1 // 默认状态为启用
    }
    
    // 调用接口
    const response = await api.post('/culture-news/save', submitData)
    
    if (response.success) {
      ElMessage.success('发布成功')
      dialogVisible.value = false
      // 重置表单
      resetForm()
      // 触发父组件的刷新方法
      if (props.onSuccess) {
        props.onSuccess()
      }
    } else {
      ElMessage.error('发布失败: ' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('提交失败:', error)
    if (error.name === 'ValidationError') {
      ElMessage.error('请检查表单填写')
    } else {
      ElMessage.error('发布失败')
    }
  }
}

// 重置表单
const resetForm = () => {
  form.title = ''
  form.publishTime = new Date()
  form.coverUrl = ''
  form.summary = ''
  form.content = ''
  if (formRef.value) {
    formRef.value.resetFields()
  }
}

// 定义组件属性
const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  onSuccess: {
    type: Function,
    default: null
  }
})

// 监听visible属性变化
const emit = defineEmits(['update:visible'])

// 监听visible属性变化
watch(() => props.visible, (newVal) => {
  dialogVisible.value = newVal
})

// 监听dialogVisible变化
watch(dialogVisible, (newVal) => {
  emit('update:visible', newVal)
})
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}

.avatar-uploader {
  margin-bottom: 10px;
}

.avatar-uploader .el-button {
  margin-right: 10px;
}
</style>