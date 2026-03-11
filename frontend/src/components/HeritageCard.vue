<template>
  <div class="heritage-card">
    <div class="card-img">
      <img :src="heritage.coverImage || 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=chinese%20intangible%20cultural%20heritage%20traditional%20art%20pattern&image_size=square'" :alt="heritage.title" class="img">
      <div class="card-overlay">
        <div class="overlay-content">
          <span class="heat-value" v-if="heritage.heatValue">热度: {{ heritage.heatValue }}</span>
        </div>
      </div>
    </div>
    <div class="card-content">
      <div class="card-header">
        <h3 class="card-title">{{ heritage.title }}</h3>
      </div>
      <div class="card-meta">
        <span class="card-category">{{ heritage.categoryName }}</span>
        <span class="card-region">{{ heritage.regionName }}</span>
        <span class="card-level">{{ heritage.level }}</span>
      </div>
      <p class="card-description">{{ truncateDescription(heritage.description) }}</p>
      <div class="card-actions">
        <router-link :to="`/heritage/${heritage.id}`" class="btn-detail">
          <span class="btn-text">查看详情</span>
          <i class="el-icon-arrow-right btn-icon"></i>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HeritageCard',
  props: {
    heritage: {
      type: Object,
      required: true
    }
  },
  methods: {
    truncateDescription(description) {
      if (!description) return '';
      return description.length > 100 ? description.substring(0, 100) + '...' : description;
    }
  }
}
</script>

<style scoped>
.heritage-card {
  background: var(--gradient-card);
  border-radius: 16px;
  box-shadow: var(--shadow-md);
  overflow: hidden;
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  display: flex;
  flex-direction: column;
  min-height: 440px;
  border: 1px solid var(--border-color);
  position: relative;
}

.heritage-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left var(--transition-slow);
  z-index: 1;
}

.heritage-card:hover::before {
  left: 100%;
}

.heritage-card:hover {
  transform: translateY(-10px) scale(1.02);
  box-shadow: var(--shadow-hover);
  border-color: var(--primary-color);
}

.card-img {
  height: 240px;
  overflow: hidden;
  position: relative;
  border-radius: 16px 16px 0 0;
}

.img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.6s cubic-bezier(0.4, 0, 0.2, 1);
}

.heritage-card:hover .img {
  transform: scale(1.15);
}

.card-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.8), transparent);
  opacity: 0;
  transition: opacity 0.4s ease;
  display: flex;
  align-items: flex-end;
  padding: 20px;
  border-radius: 16px 16px 0 0;
}

.heritage-card:hover .card-overlay {
  opacity: 1;
  animation: fadeIn 0.4s ease;
}

.overlay-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  animation: slideUp 0.5s ease;
}

.heat-value {
  background: rgba(255, 255, 255, 0.95);
  color: var(--primary-color);
  padding: 8px 16px;
  border-radius: 25px;
  font-size: 0.9rem;
  font-weight: 600;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  gap: 6px;
  animation: pulse 2s infinite;
}

.heat-value::before {
  content: '🔥';
  font-size: 1rem;
}

.card-content {
  padding: 24px;
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0;
  position: relative;
  z-index: 2;
}

.card-header {
  margin-bottom: 16px;
}

.card-title {
  font-size: 1.35rem;
  font-weight: 600;
  margin: 0;
  color: var(--text-color);
  line-height: 1.4;
  transition: all 0.3s ease;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.heritage-card:hover .card-title {
  color: var(--primary-color);
  transform: translateX(8px);
}

.card-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
  align-items: center;
}

.card-category {
  font-size: 0.85rem;
  color: var(--primary-color);
  font-weight: 500;
  background: rgba(157, 41, 50, 0.1);
  padding: 6px 12px;
  border-radius: 20px;
  border: 1px solid rgba(157, 41, 50, 0.2);
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 4px;
}

.card-category::before {
  content: '🎯';
  font-size: 0.8rem;
}

.heritage-card:hover .card-category {
  background: var(--primary-color);
  color: white;
  border-color: var(--primary-color);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(157, 41, 50, 0.3);
}

.card-region, .card-level {
  font-size: 0.85rem;
  color: var(--light-text);
  background: rgba(0, 0, 0, 0.05);
  padding: 6px 12px;
  border-radius: 20px;
  border: 1px solid var(--border-color);
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 4px;
}

.card-region::before {
  content: '📍';
  font-size: 0.8rem;
}

.card-level::before {
  content: '⭐';
  font-size: 0.8rem;
}

.heritage-card:hover .card-region,
.heritage-card:hover .card-level {
  border-color: var(--accent-color);
  color: var(--accent-color);
  background: rgba(0, 102, 204, 0.05);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 102, 204, 0.2);
}

.card-description {
  font-size: 0.95rem;
  color: var(--text-color);
  line-height: 1.6;
  margin: 0 0 24px 0;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  flex: 1;
  min-height: 80px;
  transition: color 0.3s ease;
  position: relative;
}

.card-description::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 20px;
  background: linear-gradient(to top, var(--card-background), transparent);
  pointer-events: none;
}

.heritage-card:hover .card-description {
  color: var(--text-color);
}

.card-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: auto;
}

.btn-detail {
  background: var(--gradient-primary);
  color: white;
  padding: 12px 24px;
  border-radius: 30px;
  text-decoration: none;
  font-size: 0.9rem;
  font-weight: 600;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
  box-shadow: 0 4px 12px rgba(157, 41, 50, 0.25);
  position: relative;
  overflow: hidden;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.btn-detail::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
  transition: left 0.5s ease;
}

.btn-detail:hover::before {
  left: 100%;
}

.btn-detail:hover {
  background: var(--gradient-primary);
  color: white;
  transform: translateY(-4px) scale(1.05);
  box-shadow: 0 8px 20px rgba(157, 41, 50, 0.4);
}

.btn-text {
  position: relative;
  z-index: 1;
}

.btn-icon {
  font-size: 16px;
  transition: all 0.3s ease;
  position: relative;
  z-index: 1;
}

.btn-detail:hover .btn-icon {
  transform: translateX(6px) rotate(15deg);
}

/* 动画效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes slideUp {
  from {
    transform: translateY(20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .heritage-card {
    min-height: 380px;
  }
  
  .card-img {
    height: 180px;
  }
  
  .card-content {
    padding: 16px;
  }
  
  .card-title {
    font-size: 1.1rem;
  }
  
  .card-meta {
    gap: 6px;
  }
  
  .card-category,
  .card-region,
  .card-level {
    font-size: 0.8rem;
    padding: 3px 8px;
  }
  
  .card-description {
    font-size: 0.85rem;
    margin-bottom: 16px;
  }
  
  .btn-detail {
    padding: 8px 16px;
    font-size: 0.8rem;
  }
}

@media (max-width: 576px) {
  .heritage-card {
    min-height: 360px;
  }
  
  .card-img {
    height: 160px;
  }
  
  .card-content {
    padding: 14px;
  }
  
  .card-title {
    font-size: 1rem;
  }
  
  .card-description {
    -webkit-line-clamp: 2;
    min-height: 45px;
  }
}
</style>