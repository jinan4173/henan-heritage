# 河南非物质文化遗产数字化展示系统 - 数据库修改说明

## 1. 修改内容

### 1.1 数据类型统一
- **修改前**：不同实体类使用不同的日期类型
  - HeritageItem、HeritageMedia、User 使用 Date 类型
  - HeritageCategory、Region 使用 LocalDateTime 类型
- **修改后**：所有实体类统一使用 LocalDateTime 类型存储日期时间
  - User 实体：将 createdAt 和 updatedAt 改为 LocalDateTime 类型
  - HeritageItem 实体：将 createTime 和 updateTime 改为 LocalDateTime 类型
  - HeritageMedia 实体：将 createTime 改为 LocalDateTime 类型

### 1.2 消除数据冗余
- **修改前**：HeritageItem 中同时存储了 regionId 和 regionName，categoryId 和 categoryName
- **修改后**：移除 HeritageItem 中的 regionName 和 categoryName 字段，通过关联查询获取地区和类别的名称

### 1.3 代码优化
- **修改前**：HeritageItem 中的 mediaList 使用完整类名 java.util.List
- **修改后**：添加 import java.util.List; 并使用 List 简化代码

## 2. 修改文件列表

1. **User.java**
   - 修改日期类型：Date → LocalDateTime
   - 导入包：import java.time.LocalDateTime;

2. **HeritageItem.java**
   - 修改日期类型：Date → LocalDateTime
   - 移除冗余字段：regionName、categoryName
   - 导入包：import java.time.LocalDateTime;、import java.util.List;

3. **HeritageMedia.java**
   - 修改日期类型：Date → LocalDateTime
   - 导入包：import java.time.LocalDateTime;

## 3. 数据库表结构修改建议

### 3.1 表结构调整
1. **user 表**
   - 修改字段类型：createdAt、updatedAt 改为 DATETIME

2. **heritage_item 表**
   - 修改字段类型：createTime、updateTime 改为 DATETIME
   - 删除字段：regionName、categoryName

3. **heritage_media 表**
   - 修改字段类型：createTime 改为 DATETIME

### 3.2 索引添加建议
1. **heritage_item 表**
   - 添加索引：regionId、categoryId、status

2. **heritage_media 表**
   - 添加索引：heritageItemId、mediaType

3. **user 表**
   - 添加索引：username、email

### 3.3 外键约束添加建议
1. **heritage_item 表**
   - 添加外键：regionId 关联 region.id
   - 添加外键：categoryId 关联 heritage_category.id

2. **heritage_media 表**
   - 添加外键：heritageItemId 关联 heritage_item.id

## 4. 实施步骤

1. **备份现有数据**：在进行任何修改前，备份现有数据库

2. **修改实体类**：已完成，修改了 User、HeritageItem、HeritageMedia 实体类

3. **更新数据库表结构**：
   - 修改日期字段类型
   - 删除冗余字段
   - 添加索引和外键约束

4. **数据迁移**：将现有数据迁移到新的表结构

5. **测试**：确保所有功能正常工作

## 5. 预期效果

1. **数据一致性**：统一日期类型，消除数据冗余，提高数据一致性

2. **查询性能**：添加适当的索引，提高查询性能

3. **代码质量**：简化代码，提高代码可读性和可维护性

4. **系统稳定性**：添加外键约束，确保数据完整性

## 6. 注意事项

1. **数据备份**：修改前一定要备份现有数据，防止数据丢失

2. **兼容性**：确保修改后的数据库结构与现有代码兼容

3. **性能影响**：添加索引可能会影响插入和更新性能，需要权衡

4. **测试**：修改后需要进行全面测试，确保所有功能正常工作

通过以上修改，数据库结构将更加合理、高效，为系统的稳定运行和未来扩展打下良好基础。