<template>
  <div class="footer">
    <div class="footer-container">
      <div class="footer-section">
        <h3>关于我们</h3>
        <p>河南非物质文化遗产数字化展示系统致力于传承和弘扬河南优秀的非物质文化遗产，通过数字化手段让更多人了解和认识河南的文化瑰宝。</p>
      </div>
      <div class="footer-section">
        <h3>快速链接</h3>
        <ul class="footer-links">
          <li><router-link to="/">首页</router-link></li>
          <li><router-link to="/heritage">非遗项目</router-link></li>
          <li><router-link to="/inheritors">传承人</router-link></li>
          <li><router-link to="/news">文化资讯</router-link></li>
          <li><router-link to="/activity">非遗活动</router-link></li>
          <li><router-link to="/notice">公告</router-link></li>
        </ul>
      </div>
      <div class="footer-section">
        <h3>联系我们</h3>
        <p>地址：河南省郑州市文化和旅游厅</p>
        <p>电话：0371-12345678</p>
        <p>邮箱：heritage@henan.gov.cn</p>
      </div>
    </div>
    <div class="footer-bottom">
      <p>&copy; 2024 河南非物质文化遗产数字化展示系统 版权所有</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Footer'
}
</script>

<style scoped>
.footer {
  background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
  color: white;
  padding: 60px 0 30px;
  margin: 0;
  position: relative;
  overflow: hidden;
}

.footer::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url('https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=chinese%20traditional%20pattern%20background%20subtle&image_size=square') center/cover;
  opacity: 0.1;
  z-index: 1;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 40px;
  position: relative;
  z-index: 2;
}

.footer-section {
  animation: fadeInUp 1s ease;
}

.footer-section:nth-child(2) {
  animation-delay: 0.2s;
  animation-fill-mode: both;
}

.footer-section:nth-child(3) {
  animation-delay: 0.4s;
  animation-fill-mode: both;
}

.footer-section h3 {
  font-size: 1.2rem;
  margin-bottom: 20px;
  color: white;
  font-weight: 600;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
  position: relative;
  display: inline-block;
}

.footer-section h3::after {
  content: '';
  position: absolute;
  bottom: -8px;
  left: 0;
  width: 40px;
  height: 2px;
  background: var(--secondary-color);
  border-radius: 1px;
}

.footer-section p {
  font-size: 0.9rem;
  line-height: 1.6;
  color: rgba(255, 255, 255, 0.8);
  margin-bottom: 12px;
}

.footer-links {
  list-style: none;
}

.footer-links li {
  margin-bottom: 12px;
  position: relative;
  padding-left: 20px;
}

.footer-links li::before {
  content: '→';
  position: absolute;
  left: 0;
  top: 0;
  color: var(--secondary-color);
  transition: transform var(--transition-normal);
}

.footer-links li:hover::before {
  transform: translateX(4px);
  color: white;
}

.footer-links a {
  color: rgba(255, 255, 255, 0.8);
  text-decoration: none;
  font-size: 0.9rem;
  transition: all var(--transition-normal);
  text-shadow: none;
  position: relative;
}

.footer-links a::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 100%;
  height: 1px;
  background-color: var(--secondary-color);
  transform: scaleX(0);
  transition: transform var(--transition-normal);
  transform-origin: right;
}

.footer-links a:hover {
  color: white;
  padding-left: 8px;
}

.footer-links a:hover::after {
  transform: scaleX(1);
  transform-origin: left;
}

.footer-bottom {
  border-top: 1px solid rgba(255, 255, 255, 0.2);
  margin-top: 40px;
  padding-top: 20px;
  text-align: center;
  font-size: 0.85rem;
  color: rgba(255, 255, 255, 0.7);
  position: relative;
  z-index: 2;
  animation: fadeInUp 1s ease 0.6s both;
}

/* 动画效果 */
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

/* 响应式设计 */
@media (max-width: 768px) {
  .footer {
    padding: 40px 0 20px;
  }
  
  .footer-container {
    grid-template-columns: 1fr;
    gap: 30px;
  }

  .footer-section {
    text-align: center;
  }
  
  .footer-section h3::after {
    left: 50%;
    transform: translateX(-50%);
  }
  
  .footer-links li {
    padding-left: 0;
  }
  
  .footer-links li::before {
    display: none;
  }
  
  .footer-links a:hover {
    padding-left: 0;
  }
}

@media (max-width: 576px) {
  .footer {
    padding: 30px 0 15px;
  }
  
  .footer-container {
    gap: 20px;
  }
  
  .footer-section h3 {
    font-size: 1.1rem;
  }
  
  .footer-section p {
    font-size: 0.85rem;
  }
  
  .footer-links a {
    font-size: 0.85rem;
  }
  
  .footer-bottom {
    font-size: 0.75rem;
  }
}
</style>