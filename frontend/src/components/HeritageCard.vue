<template>
  <div class="heritage-card">
    <div class="card-img">
      <img :src="heritage.coverImage || 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=chinese%20intangible%20cultural%20heritage%20traditional%20art%20pattern&image_size=square'" :alt="heritage.title" class="img">
    </div>
    <div class="card-content">
      <h3 class="card-title">{{ heritage.title }}</h3>
      <p class="card-category">{{ heritage.categoryName }}</p>
      <p class="card-region">{{ heritage.regionName }}</p>
      <p class="card-level">{{ heritage.level }}</p>
      <p class="card-description">{{ truncateDescription(heritage.description) }}</p>
      <div class="card-actions">
        <router-link :to="`/heritage/${heritage.id}`" class="btn-detail">查看详情</router-link>
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
  background: var(--card-background);
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: all 0.3s ease;
}

.heritage-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.card-img {
  height: 200px;
  overflow: hidden;
}

.img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.heritage-card:hover .img {
  transform: scale(1.05);
}

.card-content {
  padding: 16px;
}

.card-title {
  font-size: 1.2rem;
  font-weight: 600;
  margin: 0 0 8px 0;
  color: var(--text-color);
  line-height: 1.4;
}

.card-category {
  font-size: 0.85rem;
  color: var(--primary-color);
  margin: 0 0 4px 0;
  font-weight: 500;
}

.card-region {
  font-size: 0.85rem;
  color: var(--light-text);
  margin: 0 0 4px 0;
}

.card-level {
  font-size: 0.85rem;
  color: var(--light-text);
  margin: 0 0 12px 0;
}

.card-description {
  font-size: 0.9rem;
  color: var(--text-color);
  line-height: 1.5;
  margin: 0 0 16px 0;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.card-actions {
  display: flex;
  justify-content: flex-end;
}

.btn-detail {
  background: var(--primary-color);
  color: #FFFFFF;
  padding: 8px 16px;
  border-radius: 4px;
  text-decoration: none;
  font-size: 0.85rem;
  font-weight: 500;
  transition: background 0.3s ease;
}

.btn-detail:hover {
  background: #C53030;
  color: #FFFFFF;
}
</style>