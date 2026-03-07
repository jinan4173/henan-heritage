<template>
  <div class="inheritor-card">
    <div class="card-img">
      <img :src="inheritor.photo || 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=chinese%20elder%20master%20traditional%20craftsman%20portrait&image_size=square'" :alt="inheritor.name" class="img">
    </div>
    <div class="card-content">
      <h3 class="card-name">{{ inheritor.name }}</h3>
      <p class="card-gender">{{ inheritor.gender }}</p>
      <p class="card-region">{{ inheritor.region }}</p>
      <p class="card-level">{{ inheritor.level }}</p>
      <p class="card-heritage">{{ inheritor.heritageItem }}</p>
      <p class="card-biography">{{ truncateBiography(inheritor.biography) }}</p>
      <div class="card-actions">
        <router-link :to="`/inheritors/${inheritor.id}`" class="btn-detail">查看详情</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'InheritorCard',
  props: {
    inheritor: {
      type: Object,
      required: true
    }
  },
  methods: {
    truncateBiography(biography) {
      if (!biography) return '';
      return biography.length > 100 ? biography.substring(0, 100) + '...' : biography;
    }
  }
}
</script>

<style scoped>
.inheritor-card {
  background: #FFFFFF;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: all 0.3s ease;
}

.inheritor-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.card-img {
  height: 200px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f5f5;
}

.img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.inheritor-card:hover .img {
  transform: scale(1.05);
}

.card-content {
  padding: 16px;
}

.card-name {
  font-size: 1.2rem;
  font-weight: 600;
  margin: 0 0 8px 0;
  color: #1F3D3A;
  line-height: 1.4;
}

.card-gender {
  font-size: 0.85rem;
  color: #C41E3A;
  margin: 0 0 4px 0;
  font-weight: 500;
}

.card-region {
  font-size: 0.85rem;
  color: #666;
  margin: 0 0 4px 0;
}

.card-level {
  font-size: 0.85rem;
  color: #666;
  margin: 0 0 4px 0;
}

.card-heritage {
  font-size: 0.85rem;
  color: #666;
  margin: 0 0 12px 0;
}

.card-biography {
  font-size: 0.9rem;
  color: #333;
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
  background: #C41E3A;
  color: #FFFFFF;
  padding: 8px 16px;
  border-radius: 4px;
  text-decoration: none;
  font-size: 0.85rem;
  font-weight: 500;
  transition: background 0.3s ease;
}

.btn-detail:hover {
  background: #A81A32;
  color: #FFFFFF;
}
</style>