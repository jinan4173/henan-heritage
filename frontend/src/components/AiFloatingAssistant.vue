<template>
  <div 
    class="ai-floating-assistant" 
    :class="{ expanded: isExpanded }"
    :style="{ left: position.x + 'px', top: position.y + 'px' }"
  >
    <div class="ai-toggle-btn" @click="handleToggle" @mousedown="startDrag" @touchstart="startDrag" v-if="!isExpanded">
      <el-icon :size="28"><ChatDotRound /></el-icon>
      <span class="ai-badge" v-if="unreadCount > 0">{{ unreadCount }}</span>
    </div>

    <div class="ai-chat-panel" v-if="isExpanded" @click.stop>
      <div class="chat-header" @mousedown="startDrag" @touchstart="startDrag">
        <div class="chat-title">
          <el-icon :size="20"><ChatDotRound /></el-icon>
          <span>AI 助手</span>
        </div>
        <div class="chat-actions">
          <el-button text @click.stop="minimize" title="最小化">
            <el-icon><Minus /></el-icon>
          </el-button>
          <el-button text @click.stop="closeChat" title="关闭">
            <el-icon><Close /></el-icon>
          </el-button>
        </div>
      </div>

      <div class="chat-messages" ref="messagesEl">
        <div v-for="(m, idx) in messages" :key="idx" class="msg" :class="m.role">
          <div class="bubble">
            <div class="content">{{ m.content }}</div>
          </div>
        </div>
        <div v-if="loading" class="msg assistant">
          <div class="bubble">
            <div class="content">正在思考…</div>
          </div>
        </div>
      </div>

      <div class="chat-input" @click.stop>
        <el-input
          v-model="input"
          type="textarea"
          :rows="2"
          placeholder="输入问题..."
          @keydown.enter="handleEnter"
          resize="none"
          @click.stop
        />
        <el-button type="primary" size="small" @click.stop="send" :loading="loading" :disabled="!input.trim()">
          发送
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import { ChatDotRound, Minus, Close } from '@element-plus/icons-vue'

const isExpanded = ref(false)
const input = ref('')
const loading = ref(false)
const messagesEl = ref(null)
const unreadCount = ref(0)

const position = ref({ x: 100, y: 100 })
const isDragging = ref(false)
const dragOffset = ref({ x: 0, y: 0 })
const dragStarted = ref(false)

onMounted(() => {
  // 设置默认位置在右下角
  const buttonWidth = 56
  const buttonHeight = 56
  const padding = 16
  position.value = {
    x: window.innerWidth - buttonWidth - padding,
    y: window.innerHeight - buttonHeight - padding
  }
})

onUnmounted(() => {
  document.removeEventListener('mousemove', onDrag)
  document.removeEventListener('mouseup', stopDrag)
  document.removeEventListener('touchmove', onDrag)
  document.removeEventListener('touchend', stopDrag)
})

const messages = ref([
  {
    role: 'assistant',
    content: '你好！我是河南非遗AI助手，有什么可以帮你的吗？'
  }
])

const startDrag = (e) => {
  e.preventDefault()
  isDragging.value = true
  dragStarted.value = false
  const clientX = e.type.includes('touch') ? e.touches[0].clientX : e.clientX
  const clientY = e.type.includes('touch') ? e.touches[0].clientY : e.clientY
  dragOffset.value = {
    x: clientX - position.value.x,
    y: clientY - position.value.y
  }
  document.addEventListener('mousemove', onDrag)
  document.addEventListener('mouseup', stopDrag)
  document.addEventListener('touchmove', onDrag)
  document.addEventListener('touchend', stopDrag)
}

const onDrag = (e) => {
  if (!isDragging.value) return
  // 标记拖拽已开始
  dragStarted.value = true
  const clientX = e.type.includes('touch') ? e.touches[0].clientX : e.clientX
  const clientY = e.type.includes('touch') ? e.touches[0].clientY : e.clientY
  
  // 未展开时允许随意拖拽
  if (!isExpanded.value) {
    position.value = {
      x: clientX - dragOffset.value.x,
      y: clientY - dragOffset.value.y
    }
    return
  }
  
  // 展开后需要考虑面板尺寸
  const panelWidth = 360
  const panelHeight = 480
  const windowWidth = window.innerWidth
  const windowHeight = window.innerHeight
  
  let newX = clientX - dragOffset.value.x
  let newY = clientY - dragOffset.value.y
  
  newX = Math.min(newX, windowWidth - panelWidth - 16)
  newY = Math.min(newY, windowHeight - panelHeight - 16)
  newX = Math.max(newX, 16)
  newY = Math.max(newY, 16)
  
  position.value = {
    x: newX,
    y: newY
  }
}

const stopDrag = () => {
  isDragging.value = false
  document.removeEventListener('mousemove', onDrag)
  document.removeEventListener('mouseup', stopDrag)
  document.removeEventListener('touchmove', onDrag)
  document.removeEventListener('touchend', stopDrag)
}

const handleToggle = () => {
  // 如果有拖拽行为，则不执行展开
  if (!dragStarted.value) {
    toggleChat()
  }
  // 重置拖拽状态
  dragStarted.value = false
}

const toggleChat = () => {
  isExpanded.value = !isExpanded.value
  if (isExpanded.value) {
    // 确保展开后面板在视窗内
    const panelWidth = 360
    const panelHeight = 480
    const windowWidth = window.innerWidth
    const windowHeight = window.innerHeight
    
    // 调整位置，确保面板不超出视窗
    if (position.value.x + panelWidth > windowWidth) {
      position.value.x = windowWidth - panelWidth - 16
    }
    if (position.value.y + panelHeight > windowHeight) {
      position.value.y = windowHeight - panelHeight - 16
    }
    if (position.value.x < 16) {
      position.value.x = 16
    }
    if (position.value.y < 16) {
      position.value.y = 16
    }
    
    unreadCount.value = 0
    scrollToBottom()
  }
}

const minimize = () => {
  isExpanded.value = false
  // 最小化后调整到右侧
  position.value = {
    x: window.innerWidth - 80,
    y: position.value.y
  }
}

const closeChat = () => {
  isExpanded.value = false
  // 关闭后调整到右侧
  position.value = {
    x: window.innerWidth - 80,
    y: position.value.y
  }
}

const scrollToBottom = async () => {
  await nextTick()
  if (messagesEl.value) {
    messagesEl.value.scrollTop = messagesEl.value.scrollHeight
  }
}

const handleEnter = (e) => {
  if (e.shiftKey) return
  e.preventDefault()
  send()
}

const send = async () => {
  const text = input.value.trim()
  if (!text || loading.value) return

  messages.value.push({
    role: 'user',
    content: text
  })
  // 强制清空输入框
  input.value = ''
  // 确保DOM更新
  await nextTick()
  await scrollToBottom()

  loading.value = true
  try {
    const recentMessages = messages.value.slice(-10)
    const payload = {
      model: 'deepseek-ai/DeepSeek-R1',
      messages: recentMessages.map(m => ({
        role: m.role === 'assistant' ? 'assistant' : m.role,
        content: m.content
      }))
    }
    const response = await fetch('https://api.siliconflow.cn/v1/chat/completions', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer sk-qpxrbarqpqdqzbyxsuwvapaqszmtkvgjrqstuaakoynqmdyx'
      },
      body: JSON.stringify(payload)
    })
    const data = await response.json()
    if (data && data.choices && data.choices[0]) {
      messages.value.push({
        role: 'assistant',
        content: data.choices[0].message?.content || ''
      })
      await scrollToBottom()
    } else {
      ElMessage.error('AI 返回失败')
    }
  } catch (err) {
    ElMessage.error('AI 调用失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.ai-floating-assistant {
  position: fixed;
  z-index: 9999;
  pointer-events: auto;
}

.ai-toggle-btn {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background: linear-gradient(135deg, #9d2932 0%, #c94043 100%);
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 4px 16px rgba(157, 41, 50, 0.4);
  transition: transform 0.3s, box-shadow 0.3s;
  position: relative;
}

.ai-toggle-btn:hover {
  transform: scale(1.08);
  box-shadow: 0 6px 20px rgba(157, 41, 50, 0.5);
}

.ai-badge {
  position: absolute;
  top: -4px;
  right: -4px;
  background: #f56c6c;
  color: #fff;
  font-size: 12px;
  min-width: 20px;
  height: 20px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 6px;
}

.ai-chat-panel {
  width: 360px;
  height: 480px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.15);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.chat-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  background: linear-gradient(135deg, #9d2932 0%, #c94043 100%);
  color: #fff;
  cursor: move;
}

.chat-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 600;
}

.chat-actions {
  display: flex;
  gap: 4px;
}

.chat-actions .el-button {
  color: #fff;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 12px;
  background: #f8f5f0;
}

.msg {
  display: flex;
  margin: 8px 0;
}

.msg.user {
  justify-content: flex-end;
}

.msg.assistant {
  justify-content: flex-start;
}

.bubble {
  max-width: 80%;
  border-radius: 10px;
  padding: 8px 12px;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.msg.user .bubble {
  background: rgba(157, 41, 50, 0.1);
}

.content {
  font-size: 14px;
  line-height: 1.5;
  white-space: pre-wrap;
  word-break: break-word;
}

.chat-input {
  padding: 12px;
  background: #fff;
  border-top: 1px solid #eee;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.chat-input .el-button {
  align-self: flex-end;
  background: #9d2932;
  border-color: #9d2932;
}

.chat-input .el-button:hover {
  background: #c94043;
  border-color: #c94043;
}

@media (max-width: 480px) {
  .ai-floating-assistant {
    bottom: 16px;
    right: 16px;
  }

  .ai-chat-panel {
    width: calc(100vw - 32px);
    height: calc(100vh - 120px);
    max-height: 480px;
  }
}
</style>
