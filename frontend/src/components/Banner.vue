<template>
  <div class="banner-container">
    <div class="banner">
      <div class="banner-content">
        <p>{{ welcomeMessage }}</p>
        <div class="banner-actions">
          <router-link to="/heritage" class="hero-btn hero-btn-primary">探索非遗项目</router-link>
          <router-link to="/news" class="hero-btn hero-btn-secondary">了解文化资讯</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Banner',
  props: {
    welcomeMessage: {
      type: String,
      default: '传承文化根脉，守护精神家园'
    }
  }
}
</script>

<style scoped>
.banner-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  box-sizing: border-box;
  width: 100%;
}

.banner {
  background: var(--gradient-primary);
  color: white;
  text-align: center;
  padding: 140px 0;
  position: relative;
  overflow: hidden;
  width: 100%;
  box-sizing: border-box;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
  border-radius: 16px;
  margin-bottom: 60px;
}

.banner::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url('https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=henan%20chinese%20traditional%20pattern%20background%20subtle%20cultural%20heritage&image_size=landscape_16_9') center/cover;
  opacity: 0.12;
  z-index: 1;
  animation: backgroundMove 25s ease-in-out infinite;
}

/* 装饰元素 */
.banner::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: 
    radial-gradient(circle at 10% 20%, rgba(255, 255, 255, 0.15) 0%, transparent 20%),
    radial-gradient(circle at 90% 80%, rgba(255, 255, 255, 0.15) 0%, transparent 20%),
    radial-gradient(circle at 50% 50%, rgba(255, 255, 255, 0.1) 0%, transparent 30%),
    linear-gradient(135deg, rgba(255, 255, 255, 0.05) 0%, transparent 100%);
  z-index: 1;
}

.banner-content {
  max-width: 850px;
  margin: 0 auto;
  text-align: center;
  padding: 0 20px;
  position: relative;
  z-index: 2;
  box-sizing: border-box;
}

.banner-content p {
  font-size: 1.4rem;
  opacity: 0.95;
  color: white;
  margin-bottom: 40px;
  animation: fadeInUp 1.2s ease 0.3s both;
  position: relative;
  padding: 0 60px;
  line-height: 1.6;
  font-weight: 500;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.banner-content p::before,
.banner-content p::after {
  content: '';
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 40px;
  height: 2px;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.8), transparent);
  border-radius: 1px;
}

.banner-content p::before {
  left: 0;
}

.banner-content p::after {
  right: 0;
}

.banner-actions {
  display: flex;
  gap: 24px;
  justify-content: center;
  animation: fadeInUp 1.2s ease 0.5s both;
  flex-wrap: wrap;
  margin-top: 50px;
}

.hero-btn {
  padding: 16px 36px;
  border-radius: 50px;
  font-size: 1.2rem;
  font-weight: 600;
  text-decoration: none;
  transition: all var(--transition-normal);
  position: relative;
  overflow: hidden;
  z-index: 1;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.25);
  text-transform: uppercase;
  letter-spacing: 1px;
  min-width: 200px;
  text-align: center;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.hero-btn-primary {
  background: white;
  color: var(--primary-color);
}

.hero-btn-secondary {
  background: transparent;
  color: white;
  border: 3px solid white;
}

.hero-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.4), transparent);
  transition: left var(--transition-slow);
  z-index: -1;
}

.hero-btn:hover::before {
  left: 100%;
}

.hero-btn:hover {
  transform: translateY(-6px) scale(1.05);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.35);
}

.hero-btn-primary:hover {
  background: #f8f9fa;
  color: var(--primary-color);
  box-shadow: 0 12px 30px rgba(157, 41, 50, 0.4);
}

.hero-btn-secondary:hover {
  background: white;
  color: var(--primary-color);
  box-shadow: 0 12px 30px rgba(255, 255, 255, 0.3);
}

.hero-btn:active {
  transform: translateY(-2px) scale(0.98);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

/* 装饰性元素 */
.banner-decoration {
  position: absolute;
  z-index: 1;
  opacity: 0.6;
}

.decoration-1 {
  top: 20%;
  left: 10%;
  width: 80px;
  height: 80px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  animation: pulse 4s infinite;
}

.decoration-2 {
  bottom: 20%;
  right: 10%;
  width: 120px;
  height: 120px;
  border: 2px solid rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  animation: pulse 6s infinite;
}

.decoration-3 {
  top: 50%;
  right: 5%;
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  animation: float 3s ease-in-out infinite;
}

.decoration-4 {
  bottom: 10%;
  left: 5%;
  width: 60px;
  height: 60px;
  background: rgba(255, 255, 255, 0.15);
  border-radius: 50%;
  animation: float 4s ease-in-out infinite;
}

/* 动画效果 */
@keyframes backgroundMove {
  0%, 100% {
    transform: scale(1) translate(0, 0);
  }
  50% {
    transform: scale(1.05) translate(0, 5px);
  }
}

@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-15px);
  }
}

@keyframes pulse {
  0%, 100% {
    transform: translateY(-50%) scale(1);
    opacity: 0.8;
  }
  50% {
    transform: translateY(-50%) scale(1.2);
    opacity: 1;
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 视差效果 */
.banner {
  transition: transform 0.1s ease-out;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .banner-container {
    padding: 0 15px;
  }
}

@media (max-width: 768px) {
  .banner {
    padding: 80px 0;
  }
  
  .banner-container {
    padding: 0 15px;
  }
  
  .banner-content p {
    font-size: 1.1rem;
    padding: 0 20px;
  }
  
  .banner-actions {
    flex-direction: column;
    align-items: center;
  }
  
  .hero-btn {
    width: 200px;
    text-align: center;
  }
}

@media (max-width: 576px) {
  .banner {
    padding: 60px 0;
  }
  
  .banner-container {
    padding: 0 10px;
  }
  
  .banner-content p {
    font-size: 1rem;
    padding: 0 10px;
  }
  
  .banner-content p::before,
  .banner-content p::after {
    width: 20px;
  }
  
  .banner-actions {
    gap: 15px;
  }
  
  .hero-btn {
    padding: 10px 24px;
    font-size: 1rem;
  }
}
</style>