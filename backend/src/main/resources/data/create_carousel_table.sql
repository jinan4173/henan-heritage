-- 创建轮播图表
CREATE TABLE IF NOT EXISTS carousel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    order_num INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 插入一些示例数据
INSERT INTO carousel (title, image_url, order_num, status) VALUES
('豫剧表演', 'https://www.ihchina.cn/Uploads/Picture/2025/03/18/s67d8d37822d64.jpg', 1, 1),
('少林功夫', 'https://www.ihchina.cn/Uploads/Picture/2025/03/18/s67d8d37822d64.jpg', 2, 1),
('洛阳牡丹', 'https://www.ihchina.cn/Uploads/Picture/2025/03/18/s67d8d37822d64.jpg', 3, 1);