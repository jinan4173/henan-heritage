-- 数据库表结构创建脚本
-- 为河南非遗系统创建所有必要的表

-- 设置字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 1. 地区表(region)
CREATE TABLE IF NOT EXISTS region (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '地区ID',
    name VARCHAR(50) NOT NULL COMMENT '地区名称',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. 分类表(heritage_category)
CREATE TABLE IF NOT EXISTS heritage_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    description VARCHAR(255) COMMENT '分类描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. 用户表(user)
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    email VARCHAR(100) COMMENT '邮箱',
    is_admin TINYINT DEFAULT 0 COMMENT '管理员标志: 0-普通用户, 1-管理员',
    status TINYINT DEFAULT 1 COMMENT '状态: 1-启用, 0-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. 非遗项目表(heritage_item)
CREATE TABLE IF NOT EXISTS heritage_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '项目ID',
    serial_number INT COMMENT '序号',
    project_serial_number VARCHAR(50) COMMENT '项目序号',
    project_code VARCHAR(50) COMMENT '编号',
    title VARCHAR(100) NOT NULL COMMENT '名称',
    description TEXT COMMENT '项目描述',
    category_name VARCHAR(100) COMMENT '类别',
    announcement_time VARCHAR(50) COMMENT '公布时间',
    project_type VARCHAR(50) COMMENT '类型',
    declaration_region VARCHAR(255) COMMENT '申报地区或单位',
    protection_unit VARCHAR(255) COMMENT '保护单位',
    category_id BIGINT COMMENT '分类ID',
    region_id BIGINT COMMENT '地区ID',
    cover_image VARCHAR(255) COMMENT '封面图片URL',
    video_url VARCHAR(255) COMMENT '视频URL',
    status TINYINT DEFAULT 1 COMMENT '状态: 1-启用, 0-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (category_id) REFERENCES heritage_category(id),
    FOREIGN KEY (region_id) REFERENCES region(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. 文化资讯表(culture_news)
CREATE TABLE IF NOT EXISTS culture_news (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '资讯ID',
    title VARCHAR(100) NOT NULL COMMENT '资讯标题',
    content TEXT COMMENT '资讯内容',
    cover_image VARCHAR(255) COMMENT '封面图片URL',
    date VARCHAR(50) COMMENT '资讯日期',
    type TINYINT DEFAULT 1 COMMENT '资讯类型: 1-新闻, 2-活动, 3-公告',
    status TINYINT DEFAULT 1 COMMENT '状态: 1-启用, 0-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 6. 传承人表(inheritor)
CREATE TABLE IF NOT EXISTS inheritor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '传承人ID',
    name VARCHAR(50) NOT NULL COMMENT '传承人姓名',
    gender VARCHAR(10) COMMENT '性别',
    nation VARCHAR(20) COMMENT '民族',
    project VARCHAR(100) COMMENT '传承项目',
    category VARCHAR(50) COMMENT '项目类别',
    status TINYINT DEFAULT 1 COMMENT '状态: 1-启用, 0-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 7. 非遗图片表(heritage_image)
CREATE TABLE IF NOT EXISTS heritage_image (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '图片ID',
    heritage_id BIGINT COMMENT '非遗项目ID',
    image_url VARCHAR(255) COMMENT '图片URL',
    status TINYINT DEFAULT 1 COMMENT '状态: 1-启用, 0-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (heritage_id) REFERENCES heritage_item(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 8. 轮播图表(carousel)
CREATE TABLE IF NOT EXISTS carousel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '轮播图ID',
    title VARCHAR(100) NOT NULL COMMENT '轮播图标题',
    image_url VARCHAR(255) NOT NULL COMMENT '轮播图图片URL',
    order_num INT DEFAULT 0 COMMENT '排序序号',
    status TINYINT DEFAULT 1 COMMENT '状态: 1-启用, 0-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;