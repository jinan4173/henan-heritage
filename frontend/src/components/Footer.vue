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
  background: #C41E3A;
  color: #FFFFFF;
  padding: 20px 0;
  margin: 0;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

.footer-section h3 {
  font-size: 1.2rem;
  margin-bottom: 20px;
  color: #FFFFFF;
}

.footer-section p {
  font-size: 0.9rem;
  line-height: 1.6;
  color: rgba(255, 255, 255, 0.8);
  margin-bottom: 10px;
}

.footer-links {
  list-style: none;
}

.footer-links li {
  margin-bottom: 10px;
}

.footer-links a {
  color: rgba(255, 255, 255, 0.8);
  text-decoration: none;
  font-size: 0.9rem;
  transition: color 0.3s ease;
}

.footer-links a:hover {
  color: #FFFFFF;
}

.footer-bottom {
  border-top: 1px solid rgba(255, 255, 255, 0.2);
  margin-top: 20px;
  padding-top: 15px;
  text-align: center;
  font-size: 0.8rem;
  color: rgba(255, 255, 255, 0.6);
}

@media (max-width: 768px) {
  .footer-container {
    grid-template-columns: 1fr;
    gap: 20px;
  }

  .footer-section {
    text-align: center;
  }
}
</style>