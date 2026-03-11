<template>
  <div class="user-view">
    <div class="user-container">
      <h2>个人中心</h2>
      
      <!-- 未登录状态 -->
      <div v-if="!isLoggedIn" class="not-logged-in">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>登录提示</span>
            </div>
          </template>
          <div class="login-prompt">
            <el-empty description="请先登录" />
            <p class="prompt-text">登录后可以查看个人信息和收藏的非遗项目</p>
            <div class="login-buttons">
              <el-button type="primary" @click="$router.push('/login')">立即登录</el-button>
            </div>
          </div>
        </el-card>
      </div>
      
      <!-- 登录状态 -->
      <div v-else class="logged-in">
        <el-card class="user-card">
          <template #header>
            <div class="card-header">
              <span>用户信息</span>
            </div>
          </template>
          <div class="info-item">
            <span class="label">用户名：</span>
            <span class="value">{{ userInfo.username }}</span>
          </div>
          <div class="info-item">
            <span class="label">邮箱：</span>
            <span class="value">{{ userInfo.email || '未设置' }}</span>
          </div>
          <div class="info-item">
            <span class="label">登录时间：</span>
            <span class="value">{{ userInfo.loginTime }}</span>
          </div>
          <div class="action-buttons">
            <el-button type="primary" @click="editProfile">编辑资料</el-button>
            <el-button type="danger" @click="handleLogout">退出登录</el-button>
          </div>
        </el-card>
        

        
        <el-card class="user-card">
          <template #header>
            <div class="card-header">
              <span>我的收藏</span>
            </div>
          </template>
          <div class="favorites-list">
            <el-empty v-if="favoritesList.length === 0" description="暂无收藏项目" />
            <div v-else v-for="item in favoritesList" :key="item.id" class="favorite-item">
              <span class="favorite-title" @click="viewFavorite(item)">{{ item.title }}</span>
              <div class="favorite-actions">
                <span class="favorite-category">{{ item.category }}</span>
                <el-button type="text" size="small" @click.stop="removeFavorite(item.id)" style="color: #f56c6c;">
                  取消收藏
                </el-button>
              </div>
            </div>
          </div>
        </el-card>
        
        <el-card class="user-card">
          <template #header>
            <div class="card-header">
              <span>我的评论</span>
            </div>
          </template>
          <div class="comments-list">
            <el-empty v-if="commentsList.length === 0" description="暂无评论" />
            <div v-else v-for="comment in commentsList" :key="comment.id" class="comment-item">
              <div class="comment-content">
                <div class="comment-header">
                  <span class="comment-title">
                    {{ comment.username }} 对「{{ comment.title }}」的评价
                  </span>
                  <div class="comment-actions">
                    <span class="comment-time">{{ comment.time }}</span>
                    <el-button type="text" size="small" @click="removeComment(comment.id)" style="color: #f56c6c;">
                      删除
                    </el-button>
                  </div>
                </div>
                <p class="comment-text">{{ comment.content }}</p>
              </div>
            </div>
          </div>
        </el-card>
      </div>
    </div>

    <!-- 编辑资料对话框 -->
    <el-dialog v-model="dialogVisible" title="编辑资料" width="500px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="editForm.password" type="password" placeholder="请输入新密码（可选）" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveProfile">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import api from '../api/index'

export default {
  name: 'UserView',
  data() {
    return {
      userInfo: {
        username: '',
        email: '',
        loginTime: ''
      },
      favoritesList: [],
      commentsList: [],
      dialogVisible: false,
      editForm: {
        username: '',
        email: '',
        password: ''
      }
    }
  },
  computed: {
    isLoggedIn() {
      return !!localStorage.getItem('user');
    }
  },
  mounted() {
    if (!this.isLoggedIn) {
      // 如果未登录，直接跳转到登录页面
      this.$router.push('/login');
      this.$message.info('请先登录');
      return;
    }
    this.loadUserInfo();
    this.loadFavorites();
    this.loadComments();
  },
  methods: {
    loadUserInfo() {
      const userStr = localStorage.getItem('user');
      if (userStr) {
        try {
          const user = JSON.parse(userStr);
          this.userInfo.username = user.username;
          this.userInfo.email = user.email || '';
          this.userInfo.loginTime = new Date().toLocaleString('zh-CN');
        } catch (e) {
          console.error('解析用户信息失败:', e);
        }
      }
    },
    handleLogout() {
      localStorage.removeItem('user');
      this.$router.push('/');
      this.$message.success('退出登录成功');
    },
    editProfile() {
      // 填充表单数据
      this.editForm.username = this.userInfo.username;
      this.editForm.email = this.userInfo.email || '';
      this.editForm.password = '';
      // 打开对话框
      this.dialogVisible = true;
    },
    saveProfile() {
      // 保存用户资料
      const userStr = localStorage.getItem('user');
      if (userStr) {
        try {
          const user = JSON.parse(userStr);
          // 更新用户信息
          user.username = this.editForm.username;
          user.email = this.editForm.email;
          if (this.editForm.password) {
            user.password = this.editForm.password;
          }
          // 保存到本地存储
          localStorage.setItem('user', JSON.stringify(user));
          // 更新页面显示的用户信息
          this.userInfo.username = this.editForm.username;
          this.userInfo.email = this.editForm.email;
          // 关闭对话框
          this.dialogVisible = false;
          // 显示成功消息
          this.$message.success('资料更新成功');
        } catch (e) {
          console.error('保存用户信息失败:', e);
          this.$message.error('保存失败，请重试');
        }
      }
    },
    async loadFavorites() {
      const userStr = localStorage.getItem('user');
      if (!userStr) return;
      
      try {
        const user = JSON.parse(userStr);
        const response = await api.get('/favorite/listByUser', {
          params: { userId: user.id }
        });
        
        if (response.success) {
          this.favoritesList = await this.processFavorites(response.data);
        }
      } catch (error) {
        console.error('获取收藏列表失败:', error);
        this.$message.error('获取收藏列表失败，请重试');
      }
    },
    async processFavorites(favorites) {
      const processedFavorites = [];
      
      for (const favorite of favorites) {
        let title = '未知';
        let category = '其他';
        
        if (favorite.heritageId) {
          // 非遗项目收藏
          try {
            const heritageResponse = await api.get(`/heritage/get/${favorite.heritageId}`);
            if (heritageResponse.success) {
              title = heritageResponse.data.title || '未知非遗项目';
              category = heritageResponse.data.category_name || '传统技艺';
            }
          } catch (error) {
            console.error('获取非遗项目信息失败:', error);
          }
        } else if (favorite.mediaId) {
          // 媒体资源收藏
          try {
            // 尝试从不同的媒体类型中获取信息
            const mediaTypes = [
              { url: `/culture-news/get/${favorite.mediaId}`, type: 'news', label: '资讯' },
              { url: `/activity/get/${favorite.mediaId}`, type: 'activity', label: '活动' }
            ];
            
            for (const item of mediaTypes) {
              try {
                const mediaResponse = await api.get(item.url);
                if (mediaResponse.success) {
                  title = mediaResponse.data.title || '未知媒体';
                  category = item.label + '收藏';
                  favorite.mediaType = item.type;
                  break;
                }
              } catch (error) {
                // 忽略错误，尝试下一个类型
              }
            }
          } catch (error) {
            console.error('获取媒体信息失败:', error);
          }
        }
        
        processedFavorites.push({
          id: favorite.id,
          title,
          category,
          heritageId: favorite.heritageId,
          mediaId: favorite.mediaId,
          mediaType: favorite.mediaType || 'news'
        });
      }
      
      return processedFavorites;
    },
    async removeFavorite(id) {
      try {
        await api.delete(`/favorite/delete/${id}`);
        this.$message.success('取消收藏成功');
        // 重新加载收藏列表
        this.loadFavorites();
      } catch (error) {
        console.error('取消收藏失败:', error);
        this.$message.error('取消收藏失败，请重试');
      }
    },
    viewFavorite(item) {
      if (item.heritageId) {
        // 跳转到非遗项目详情页
        this.$router.push(`/heritage/${item.heritageId}`);
      } else if (item.mediaId) {
        // 跳转到媒体详情页，根据媒体类型选择正确的路由
        if (item.mediaType === 'news') {
          this.$router.push(`/news/${item.mediaId}`);
        } else if (item.mediaType === 'activity') {
          this.$router.push(`/activity/${item.mediaId}`);
        } else {
          // 默认为资讯详情
          this.$router.push(`/news/${item.mediaId}`);
        }
      }
    },
    async loadComments() {
      const userStr = localStorage.getItem('user');
      if (!userStr) return;
      
      try {
        const user = JSON.parse(userStr);
        const response = await api.get('/comment/listByUser', {
          params: { userId: user.id }
        });
        
        if (response.code === 200) {
          this.commentsList = await this.processComments(response.data);
        }
      } catch (error) {
        console.error('获取评论列表失败:', error);
        this.$message.error('获取评论列表失败，请重试');
      }
    },
    async processComments(comments) {
      const processedComments = [];
      
      for (const comment of comments) {
        let title = '未知';
        
        // 尝试获取评论相关的内容标题
        if (comment.mediaId) {
          try {
            // 尝试从不同的媒体类型中获取信息
            const mediaTypes = [
              `/culture-news/get/${comment.mediaId}`,
              `/activity/get/${comment.mediaId}`
            ];
            
            for (const url of mediaTypes) {
              try {
                const mediaResponse = await api.get(url);
                if (mediaResponse.code === 200) {
                  title = mediaResponse.data.title || '未知媒体';
                  break;
                }
              } catch (error) {
                // 忽略错误，尝试下一个类型
              }
            }
          } catch (error) {
            console.error('获取评论相关媒体信息失败:', error);
          }
        }
        
        processedComments.push({
          id: comment.id,
          title,
          content: comment.content,
          time: comment.createTime ? new Date(comment.createTime).toLocaleString('zh-CN') : new Date().toLocaleString('zh-CN'),
          username: this.userInfo.username || '游客'
        });
      }
      
      return processedComments;
    },
    async removeComment(id) {
      try {
        await api.delete(`/comment/delete/${id}`);
        this.$message.success('删除评论成功');
        // 重新加载评论列表
        this.loadComments();
      } catch (error) {
        console.error('删除评论失败:', error);
        this.$message.error('删除评论失败，请重试');
      }
    }
  }
}</script>

<style scoped>
.user-view {
  width: 100%;
  padding: 20px;
  background-color: var(--background-color);
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
}

.user-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.user-view h2 {
  font-size: 2rem;
  margin-bottom: 30px;
  color: var(--primary-color);
  text-align: center;
  position: relative;
  padding-bottom: 15px;
}

.user-view h2::after {
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

.logged-in {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
  max-width: 600px;
}

.user-card {
  width: 100%;
  margin: 0;
}

.card-header {
  font-weight: bold;
}

.info-item {
  margin: 10px 0;
  display: flex;
  align-items: center;
}

.label {
  width: 100px;
  font-weight: 500;
}

.value {
  flex: 1;
}

.action-buttons {
  margin-top: 20px;
}



.login-prompt {
  text-align: center;
  padding: 40px 20px;
}

.prompt-text {
  margin: 20px 0;
  color: #666;
  line-height: 1.5;
}

.login-buttons {
  margin-top: 30px;
}

.favorites-list {
  margin-top: 10px;
}

.favorite-item {
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.favorite-title {
  flex: 1;
  cursor: pointer;
  transition: color 0.3s;
}

.favorite-title:hover {
  color: #667eea;
  text-decoration: underline;
}

.favorite-actions {
  display: flex;
  align-items: center;
  gap: 10px;
}

.favorite-category {
  font-size: 12px;
  color: #667eea;
  background: #f0f0ff;
  padding: 2px 8px;
  border-radius: 10px;
}

.comments-list {
  margin-top: 10px;
}

.comment-item {
  padding: 15px 0;
  border-bottom: 1px solid #f0f0f0;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.comment-title {
  font-weight: 600;
  flex: 1;
}

.comment-actions {
  display: flex;
  align-items: center;
  gap: 10px;
}

.comment-time {
  font-size: 12px;
  color: #999;
}

.comment-text {
  margin: 0;
  color: #666;
  line-height: 1.5;
  font-size: 14px;
}
</style>