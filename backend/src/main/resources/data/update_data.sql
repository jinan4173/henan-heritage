-- 数据库更新脚本
-- 为河南非遗系统更新类别和地区数据

-- 设置字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 1. 更新分类表(heritage_category)数据
-- 按照用户数据的类别ID重新组织
INSERT INTO heritage_category (id, name, description, create_time, update_time) VALUES (1, '民俗', '河南地区的传统民俗活动和习俗', NOW(), NOW()) ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description), update_time = NOW();
INSERT INTO heritage_category (id, name, description, create_time, update_time) VALUES (2, '传统体育、游艺与杂技', '河南地区的传统体育项目，如少林功夫等', NOW(), NOW()) ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description), update_time = NOW();
INSERT INTO heritage_category (id, name, description, create_time, update_time) VALUES (3, '民间文学', '河南地区的民间文学作品和传说', NOW(), NOW()) ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description), update_time = NOW();
INSERT INTO heritage_category (id, name, description, create_time, update_time) VALUES (4, '传统医药', '河南地区的传统医药知识和技能', NOW(), NOW()) ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description), update_time = NOW();
INSERT INTO heritage_category (id, name, description, create_time, update_time) VALUES (5, '曲艺', '河南地区的传统曲艺形式', NOW(), NOW()) ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description), update_time = NOW();
INSERT INTO heritage_category (id, name, description, create_time, update_time) VALUES (6, '传统戏剧', '河南地区的传统戏剧形式，如豫剧、曲剧等', NOW(), NOW()) ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description), update_time = NOW();
INSERT INTO heritage_category (id, name, description, create_time, update_time) VALUES (7, '传统技艺', '河南地区的传统工艺技艺，如汴绣、唐三彩等', NOW(), NOW()) ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description), update_time = NOW();
INSERT INTO heritage_category (id, name, description, create_time, update_time) VALUES (8, '传统美术', '河南地区的传统美术形式', NOW(), NOW()) ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description), update_time = NOW();
INSERT INTO heritage_category (id, name, description, create_time, update_time) VALUES (9, '传统音乐', '河南地区的传统音乐形式', NOW(), NOW()) ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description), update_time = NOW();
INSERT INTO heritage_category (id, name, description, create_time, update_time) VALUES (10, '传统舞蹈', '河南地区的传统舞蹈形式', NOW(), NOW()) ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description), update_time = NOW();

-- 2. 更新地区表(region)数据
-- 添加缺失的地区
INSERT INTO region (id, name, create_time, update_time) VALUES (98, '济源市', NOW(), NOW()) ON DUPLICATE KEY UPDATE name = VALUES(name), update_time = NOW();
INSERT INTO region (id, name, create_time, update_time) VALUES (97, '企业申报', NOW(), NOW()) ON DUPLICATE KEY UPDATE name = VALUES(name), update_time = NOW();
INSERT INTO region (id, name, create_time, update_time) VALUES (99, '省级', NOW(), NOW()) ON DUPLICATE KEY UPDATE name = VALUES(name), update_time = NOW();

SET FOREIGN_KEY_CHECKS = 1;