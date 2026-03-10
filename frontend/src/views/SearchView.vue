<template>
  <div class="search-view">
    <div class="container">
      <h2>搜索结果</h2>
      <p class="keyword-tip">关键字：<span class="keyword">{{ keyword }}</span></p>

      <el-tabs v-model="activeTab">
        <el-tab-pane label="非遗项目" name="heritage">
          <div v-if="heritageResults.length === 0" class="empty-wrapper">
            <el-empty description="未找到相关非遗项目" />
          </div>
          <div v-else class="card-grid">
            <div
              v-for="item in heritageResults"
              :key="item.id"
              class="result-card"
              @click="goToHeritage(item.id)"
            >
              <h3>{{ item.title }}</h3>
              <p class="result-desc">{{ item._desc }}</p>
            </div>
          </div>
        </el-tab-pane>

        <el-tab-pane label="活动 / 资讯 / 公告" name="news">
          <div v-if="newsResults.length === 0" class="empty-wrapper">
            <el-empty description="未找到相关活动 / 资讯 / 公告" />
          </div>
          <div v-else class="card-grid">
            <div
              v-for="item in newsResults"
              :key="item.id"
              class="result-card"
              @click="goToNews(item)"
            >
              <div class="tag">{{ getNewsTypeLabel(item.type) }}</div>
              <h3>{{ item.title }}</h3>
              <p class="result-desc">{{ item._desc }}</p>
            </div>
          </div>
        </el-tab-pane>

        <el-tab-pane label="传承人" name="inheritor">
          <div v-if="inheritorResults.length === 0" class="empty-wrapper">
            <el-empty description="未找到相关传承人" />
          </div>
          <div v-else class="card-grid">
            <div
              v-for="item in inheritorResults"
              :key="item.id"
              class="result-card"
              @click="goToInheritor(item.id)"
            >
              <h3>{{ item.name }}</h3>
              <p class="result-desc">{{ item._desc }}</p>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElTabs, ElTabPane, ElEmpty } from 'element-plus'
import api from '../api/index.js'
import { heritageApi, inheritorApi } from '../api/api.js'

const route = useRoute()
const router = useRouter()

const keyword = ref('')
const activeTab = ref('heritage')
const heritageResults = ref([])
const newsResults = ref([])
const inheritorResults = ref([])

const doSearch = async () => {
  const kw = (keyword.value || '').trim().toLowerCase()
  if (!kw) {
    heritageResults.value = []
    newsResults.value = []
    inheritorResults.value = []
    return
  }

  // 非遗项目
  try {
    const heritageRes = await heritageApi.list(1, 1, 1000)
    if (heritageRes && heritageRes.code === 200) {
      const list = heritageRes.data || []
      heritageResults.value = list
        .filter(item => {
          const title = (item.title || '').toLowerCase()
          const desc = (item.description || '').toLowerCase()
          return title.includes(kw) || desc.includes(kw)
        })
        .map(item => ({
          ...item,
          _desc: (item.description || '').replace(/<[^>]*>/g, '').slice(0, 80) + (item.description && item.description.length > 80 ? '...' : '')
        }))
    } else {
      heritageResults.value = []
    }
  } catch (e) {
    console.error('搜索非遗项目失败:', e)
    heritageResults.value = []
  }

  // 活动 / 资讯 / 公告
  try {
    const newsRes = await api.get('/culture-news/list', { params: { status: 1 } })
    if (newsRes && newsRes.success) {
      const list = newsRes.data || []
      newsResults.value = list
        .filter(item => {
          const title = (item.title || '').toLowerCase()
          const summary = (item.summary || '').toLowerCase()
          const content = (item.content || '').toLowerCase()
          return title.includes(kw) || summary.includes(kw) || content.includes(kw)
        })
        .map(item => {
          const raw = item.summary || item.content || ''
          const plain = raw.replace(/<[^>]*>/g, '')
          return {
            ...item,
            _desc: plain.slice(0, 80) + (plain.length > 80 ? '...' : '')
          }
        })
    } else {
      newsResults.value = []
    }
  } catch (e) {
    console.error('搜索文化资讯失败:', e)
    newsResults.value = []
  }

  // 传承人
  try {
    const inheritorRes = await inheritorApi.list(1, 1000)
    if (inheritorRes && inheritorRes.code === 200) {
      const list = inheritorRes.data || []
      inheritorResults.value = list
        .filter(item => {
          const name = (item.name || '').toLowerCase()
          const project = (item.project || '').toLowerCase()
          return name.includes(kw) || project.includes(kw)
        })
        .map(item => ({
          ...item,
          _desc: (item.project || '').slice(0, 80) + (item.project && item.project.length > 80 ? '...' : '')
        }))
    } else {
      inheritorResults.value = []
    }
  } catch (e) {
    console.error('搜索传承人失败:', e)
    inheritorResults.value = []
  }
}

const syncFromRoute = () => {
  keyword.value = (route.query.keyword || '').toString()
  doSearch()
}

watch(
  () => route.query.keyword,
  () => {
    syncFromRoute()
  }
)

onMounted(() => {
  syncFromRoute()
})

const goToHeritage = (id) => {
  router.push(`/heritage/${id}`)
}

const goToNews = (item) => {
  router.push(`/news/${item.id}`)
}

const goToInheritor = (id) => {
  router.push(`/inheritors/${id}`)
}

const getNewsTypeLabel = (type) => {
  if (type === 2) return '活动'
  if (type === 3) return '公告'
  return '资讯'
}
</script>

<style scoped>
.search-view {
  width: 100%;
  padding: 40px 0;
  background-color: var(--background-color);
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

h2 {
  font-size: 2rem;
  margin-bottom: 10px;
  color: var(--primary-color);
}

.keyword-tip {
  margin-bottom: 20px;
  color: var(--light-text);
}

.keyword {
  color: var(--accent-color);
  font-weight: 600;
}

.card-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 20px;
  margin-top: 10px;
}

.result-card {
  background: #fff;
  border-radius: 10px;
  padding: 16px 18px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.06);
  cursor: pointer;
  transition: all 0.25s ease;
  position: relative;
}

.result-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.12);
}

.result-card h3 {
  font-size: 1rem;
  margin: 0 0 8px;
  color: #333;
}

.result-desc {
  font-size: 0.9rem;
  color: #666;
  margin: 0;
  line-height: 1.5;
}

.tag {
  position: absolute;
  top: 10px;
  right: 12px;
  font-size: 0.75rem;
  padding: 2px 8px;
  border-radius: 10px;
  background: rgba(0, 102, 204, 0.1);
  color: #0066cc;
}

.empty-wrapper {
  margin-top: 20px;
}

@media (max-width: 992px) {
  .card-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 768px) {
  .search-view {
    padding: 24px 0;
  }

  .container {
    padding: 0 16px;
  }

  .card-grid {
    grid-template-columns: 1fr;
  }
}
</style>

