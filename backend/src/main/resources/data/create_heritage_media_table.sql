CREATE TABLE IF NOT EXISTS heritage_media (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  heritage_item_id BIGINT NOT NULL COMMENT '非遗项目ID',
  media_type VARCHAR(20) NOT NULL COMMENT 'image / video',
  media_url VARCHAR(255) NOT NULL COMMENT '图片或视频URL',
  source VARCHAR(50) COMMENT '官方 / AI / 公开视频',
  description VARCHAR(255) COMMENT '资源说明',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (heritage_item_id) REFERENCES heritage_item(id) ON DELETE CASCADE
);

-- 插入一些测试数据
INSERT INTO heritage_media (heritage_item_id, media_type, media_url, source, description) VALUES
(1, 'image', 'https://example.com/yuju1.jpg', '官方', '豫剧表演剧照1'),
(1, 'image', 'https://example.com/yuju2.jpg', '官方', '豫剧表演剧照2'),
(1, 'video', 'https://example.com/yuju.mp4', '公开视频', '豫剧经典选段'),
(2, 'image', 'https://example.com/shaolin1.jpg', '官方', '少林功夫表演1'),
(2, 'video', 'https://example.com/shaolin.mp4', '公开视频', '少林功夫演示');
