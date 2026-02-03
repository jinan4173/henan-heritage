-- 非遗项目数据插入脚本（批量）
-- 插入用户提供的第31-125项非遗项目数据

-- 设置字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 插入非遗项目数据
INSERT INTO heritage_item (id, title, heritage_name, description, category, category_id, region_id, cover_image, video_url, status, create_time, update_time, project_serial, item_number, announcement_time, heritage_type, declaration_region, protection_unit) VALUES 
-- 1. 梁祝传说
(1, '梁祝传说', '梁祝传说', '序号: 1\n项目序号: 7\n编号: Ⅰ-7\n类别: 民间文学\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省汝南县\n保护单位: 汝南县文化馆', '民间文学', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Butterfly%20Lovers%20legend%20traditional%20Chinese%20folklore&image_size=landscape_16_9', '', 1, NOW(), NOW(), 7, 'Ⅰ-7', '2006 (第一批)', '新增项目', '河南省汝南县', '汝南县文化馆'),
-- 2. 董永传说
(2, '董永传说', '董永传说', '序号: 2\n项目序号: 9\n编号: Ⅰ-9\n类别: 民间文学\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省武陟县\n保护单位: 武陟县董永文化服务中心', '民间文学', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dong%20Yong%20legend%20traditional%20Chinese%20folklore&image_size=landscape_16_9', '', 1, NOW(), NOW(), 9, 'Ⅰ-9', '2006 (第一批)', '新增项目', '河南省武陟县', '武陟县董永文化服务中心'),
-- 3. 木兰传说
(3, '木兰传说', '木兰传说', '序号: 3\n项目序号: 537\n编号: Ⅰ-50\n类别: 民间文学\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省虞城县\n保护单位: 虞城县文化馆', '民间文学', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Mulan%20legend%20traditional%20Chinese%20folklore&image_size=landscape_16_9', '', 1, NOW(), NOW(), 537, 'Ⅰ-50', '2008 (第二批)', '新增项目', '河南省虞城县', '虞城县文化馆'),
-- 4. 盘古神话
(4, '盘古神话', '盘古神话', '序号: 4\n项目序号: 544\n编号: Ⅰ-57\n类别: 民间文学\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省桐柏县\n保护单位: 桐柏县人民文化馆', '民间文学', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Pangu%20mythology%20traditional%20Chinese%20creation%20story&image_size=landscape_16_9', '', 1, NOW(), NOW(), 544, 'Ⅰ-57', '2008 (第二批)', '新增项目', '河南省桐柏县', '桐柏县人民文化馆'),
-- 5. 盘古神话
(5, '盘古神话', '盘古神话', '序号: 5\n项目序号: 544\n编号: Ⅰ-57\n类别: 民间文学\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省泌阳县\n保护单位: 泌阳县文化馆', '民间文学', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Pangu%20mythology%20traditional%20Chinese%20creation%20story&image_size=landscape_16_9', '', 1, NOW(), NOW(), 544, 'Ⅰ-57', '2008 (第二批)', '新增项目', '河南省泌阳县', '泌阳县文化馆'),
-- 6. 邵原神话群
(6, '邵原神话群', '邵原神话群', '序号: 6\n项目序号: 545\n编号: Ⅰ-58\n类别: 民间文学\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省济源市\n保护单位: 济源市邵原镇女娲文化研究基地', '民间文学', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Shaoyuan%20mythology%20group%20traditional%20Chinese%20folklore&image_size=landscape_16_9', '', 1, NOW(), NOW(), 545, 'Ⅰ-58', '2008 (第二批)', '新增项目', '河南省济源市', '济源市邵原镇女娲文化研究基地'),
-- 7. 老子传说
(7, '老子传说', '老子传说', '序号: 7\n项目序号: 1225\n编号: Ⅰ-131\n类别: 民间文学\n公布时间: 2014 (第四批)\n类型: 新增项目\n申报地区或单位: 河南省灵宝市\n保护单位: 灵宝市文化馆', '民间文学', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Laozi%20legend%20traditional%20Chinese%20philosophy&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1225, 'Ⅰ-131', '2014 (第四批)', '新增项目', '河南省灵宝市', '灵宝市文化馆'),
-- 8. 河图洛书传说
(8, '河图洛书传说', '河图洛书传说', '序号: 8\n项目序号: 1229\n编号: Ⅰ-135\n类别: 民间文学\n公布时间: 2014 (第四批)\n类型: 新增项目\n申报地区或单位: 河南省洛阳市\n保护单位: 洛阳市文化馆（洛阳市非物质文化遗产保护中心）', '民间文学', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Hetu%20Luoshu%20legend%20traditional%20Chinese%20mythology&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1229, 'Ⅰ-135', '2014 (第四批)', '新增项目', '河南省洛阳市', '洛阳市文化馆（洛阳市非物质文化遗产保护中心）'),
-- 9. 杞人忧天传说
(9, '杞人忧天传说', '杞人忧天传说', '序号: 9\n项目序号: 1230\n编号: Ⅰ-136\n类别: 民间文学\n公布时间: 2014 (第四批)\n类型: 新增项目\n申报地区或单位: 河南省杞县\n保护单位: 杞县人民文化馆（杞县送戏下乡艺术团）', '民间文学', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Qiren%20youtian%20legend%20traditional%20Chinese%20folklore&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1230, 'Ⅰ-136', '2014 (第四批)', '新增项目', '河南省杞县', '杞县人民文化馆（杞县送戏下乡艺术团）'),
-- 10. 玄奘传说
(10, '玄奘传说', '玄奘传说', '序号: 10\n项目序号: 1374\n编号: Ⅰ-157\n类别: 民间文学\n公布时间: 2021 (第五批)\n类型: 新增项目\n申报地区或单位: 河南省洛阳市偃师区\n保护单位: 洛阳市偃师区人民文化馆', '民间文学', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Xuanzang%20legend%20traditional%20Chinese%20Buddhist%20folklore&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1374, 'Ⅰ-157', '2021 (第五批)', '新增项目', '河南省洛阳市偃师区', '洛阳市偃师区人民文化馆'),
-- 11. 唢呐艺术
(11, '唢呐艺术', '唢呐艺术', '序号: 11\n项目序号: 68\n编号: Ⅱ-37\n类别: 传统音乐\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省沁阳市\n保护单位: 沁阳市文化馆（沁阳市非物质文化遗产保中心）', '传统音乐', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Suona%20art%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW(), 68, 'Ⅱ-37', '2006 (第一批)', '新增项目', '河南省沁阳市', '沁阳市文化馆（沁阳市非物质文化遗产保中心）'),
-- 12. 板头曲
(12, '板头曲', '板头曲', '序号: 12\n项目序号: 77\n编号: Ⅱ-46\n类别: 传统音乐\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省南阳市\n保护单位: 南阳市群众艺术馆（南阳市非物质文化遗产保护中心）', '传统音乐', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Bantou%20qu%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW(), 77, 'Ⅱ-46', '2006 (第一批)', '新增项目', '河南省南阳市', '南阳市群众艺术馆（南阳市非物质文化遗产保护中心）'),
-- 13. 信阳民歌
(13, '信阳民歌', '信阳民歌', '序号: 13\n项目序号: 578\n编号: Ⅱ-79\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省信阳市\n保护单位: 信阳市群众艺术馆（信阳市非物质文化遗产保护中心）', '传统音乐', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Xinyang%20folk%20songs%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW(), 578, 'Ⅱ-79', '2008 (第二批)', '新增项目', '河南省信阳市', '信阳市群众艺术馆（信阳市非物质文化遗产保护中心）'),
-- 14. 西坪民歌
(14, '西坪民歌', '西坪民歌', '序号: 14\n项目序号: 579\n编号: Ⅱ-80\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省西峡县\n保护单位: 西峡县人民文化馆', '传统音乐', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Xiping%20folk%20songs%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW(), 579, 'Ⅱ-80', '2008 (第二批)', '新增项目', '河南省西峡县', '西峡县人民文化馆'),
-- 15. 江河号子（黄河号子）
(15, '江河号子（黄河号子）', '江河号子（黄河号子）', '序号: 15\n项目序号: 597\n编号: Ⅱ-98\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 黄河水利委员会河南黄河河务局\n保护单位: 河南省非物质文化遗产保护和智慧化中心', '传统音乐', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Yellow%20River%20work%20songs%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW(), 597, 'Ⅱ-98', '2008 (第二批)', '新增项目', '黄河水利委员会河南黄河河务局', '河南省非物质文化遗产保护和智慧化中心'),
-- 16. 古筝艺术（中州筝派）
(16, '古筝艺术（中州筝派）', '古筝艺术（中州筝派）', '序号: 16\n项目序号: 619\n编号: Ⅱ-120\n类别: 传统音乐\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省\n保护单位: 河南省文化艺术研究院', '传统音乐', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Zheng%20art%20central%20plain%20school%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW(), 619, 'Ⅱ-120', '2014 (第四批)', '扩展项目', '河南省', '河南省文化艺术研究院'),
-- 17. 笙管乐（超化吹歌）
(17, '笙管乐（超化吹歌）', '笙管乐（超化吹歌）', '序号: 17\n项目序号: 620\n编号: Ⅱ-121\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省新密市\n保护单位: 新密市文化馆（新密市非物质文化遗产保护中心）', '传统音乐', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Chaohua%20wind%20and%20pipe%20music%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW(), 620, 'Ⅱ-121', '2008 (第二批)', '新增项目', '河南省新密市', '新密市文化馆（新密市非物质文化遗产保护中心）'),
-- 18. 锣鼓艺术（大铜器）
(18, '锣鼓艺术（大铜器）', '锣鼓艺术（大铜器）', '序号: 18\n项目序号: 622\n编号: Ⅱ-123\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省西平县\n保护单位: 西平县人民文化馆', '传统音乐', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Gong%20and%20drum%20art%20traditional%20Chinese%20percussion&image_size=landscape_16_9', '', 1, NOW(), NOW(), 622, 'Ⅱ-123', '2008 (第二批)', '新增项目', '河南省西平县', '西平县人民文化馆'),
-- 19. 锣鼓艺术（大铜器）
(19, '锣鼓艺术（大铜器）', '锣鼓艺术（大铜器）', '序号: 19\n项目序号: 622\n编号: Ⅱ-123\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省郏县\n保护单位: 郏县文化馆', '传统音乐', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Gong%20and%20drum%20art%20traditional%20Chinese%20percussion&image_size=landscape_16_9', '', 1, NOW(), NOW(), 622, 'Ⅱ-123', '2008 (第二批)', '新增项目', '河南省郏县', '郏县文化馆'),
-- 20. 锣鼓艺术（开封盘鼓）
(20, '锣鼓艺术（开封盘鼓）', '锣鼓艺术（开封盘鼓）', '序号: 20\n项目序号: 622\n编号: Ⅱ-123\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省开封市\n保护单位: 开封市文化馆', '传统音乐', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Kaifeng%20drum%20art%20traditional%20Chinese%20percussion&image_size=landscape_16_9', '', 1, NOW(), NOW(), 622, 'Ⅱ-123', '2008 (第二批)', '新增项目', '河南省开封市', '开封市文化馆'),
-- 21. 锣鼓艺术（中州大鼓）
(21, '锣鼓艺术（中州大鼓）', '锣鼓艺术（中州大鼓）', '序号: 21\n项目序号: 622\n编号: Ⅱ-123\n类别: 传统音乐\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省新乡县\n保护单位: 新乡县文化馆', '传统音乐', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Zhongzhou%20drum%20art%20traditional%20Chinese%20percussion&image_size=landscape_16_9', '', 1, NOW(), NOW(), 622, 'Ⅱ-123', '2011 (第三批)', '扩展项目', '河南省新乡县', '新乡县文化馆'),
-- 22. 锣鼓艺术（大铜器）
(22, '锣鼓艺术（大铜器）', '锣鼓艺术（大铜器）', '序号: 22\n项目序号: 622\n编号: Ⅱ-123\n类别: 传统音乐\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省遂平县\n保护单位: 遂平县文化馆', '传统音乐', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Gong%20and%20drum%20art%20traditional%20Chinese%20percussion&image_size=landscape_16_9', '', 1, NOW(), NOW(), 622, 'Ⅱ-123', '2014 (第四批)', '扩展项目', '河南省遂平县', '遂平县文化馆'),
-- 23. 佛教音乐（大相国寺梵乐）
(23, '佛教音乐（大相国寺梵乐）', '佛教音乐（大相国寺梵乐）', '序号: 23\n项目序号: 637\n编号: Ⅱ-138\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省开封市\n保护单位: 开封大相国寺', '传统音乐', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Buddhist%20music%20Daxiangguo%20Temple%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW(), 637, 'Ⅱ-138', '2008 (第二批)', '新增项目', '河南省开封市', '开封大相国寺'),
-- 24. 龙舞（火龙舞）
(24, '龙舞（火龙舞）', '龙舞（火龙舞）', '序号: 24\n项目序号: 107\n编号: Ⅲ-4\n类别: 传统舞蹈\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省孟州市\n保护单位: 孟州市文化馆（孟州市非物质文化遗产保护中心）', '传统舞蹈', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Fire%20dragon%20dance%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW(), 107, 'Ⅲ-4', '2011 (第三批)', '扩展项目', '河南省孟州市', '孟州市文化馆（孟州市非物质文化遗产保护中心）'),
-- 25. 狮舞（小相狮舞）
(25, '狮舞（小相狮舞）', '狮舞（小相狮舞）', '序号: 25\n项目序号: 108\n编号: Ⅲ-5\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省巩义市\n保护单位: 巩义市文化和体育事业发展中心（巩义市体育馆、巩义市人民文化馆、巩义市图书馆）', '传统舞蹈', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Xiao%20Xiang%20lion%20dance%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW(), 108, 'Ⅲ-5', '2008 (第二批)', '扩展项目', '河南省巩义市', '巩义市文化和体育事业发展中心（巩义市体育馆、巩义市人民文化馆、巩义市图书馆）'),
-- 26. 狮舞（槐店文狮子）
(26, '狮舞（槐店文狮子）', '狮舞（槐店文狮子）', '序号: 26\n项目序号: 108\n编号: Ⅲ-5\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省沈丘县\n保护单位: 沈丘县回族文狮子文化协会', '传统舞蹈', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Huai%20Dian%20lion%20dance%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW(), 108, 'Ⅲ-5', '2008 (第二批)', '扩展项目', '河南省沈丘县', '沈丘县回族文狮子文化协会'),
-- 27. 高跷（高抬火轿）
(27, '高跷（高抬火轿）', '高跷（高抬火轿）', '序号: 27\n项目序号: 112\n编号: Ⅲ-9\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省沁阳市\n保护单位: 沁阳市文化馆（沁阳市非物质文化遗产保护中心）', '传统舞蹈', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Stilt%20walking%20fire%20palanquin%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW(), 112, 'Ⅲ-9', '2008 (第二批)', '扩展项目', '河南省沁阳市', '沁阳市文化馆（沁阳市非物质文化遗产保护中心）'),
-- 28. 麒麟舞
(28, '麒麟舞', '麒麟舞', '序号: 28\n项目序号: 640\n编号: Ⅲ-43\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省兰考县\n保护单位: 兰考县人民文化馆', '传统舞蹈', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Kylin%20dance%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW(), 640, 'Ⅲ-43', '2008 (第二批)', '新增项目', '河南省兰考县', '兰考县人民文化馆'),
-- 29. 麒麟舞（睢县麒麟舞）
(29, '麒麟舞（睢县麒麟舞）', '麒麟舞（睢县麒麟舞）', '序号: 29\n项目序号: 640\n编号: Ⅲ-43\n类别: 传统舞蹈\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省睢县\n保护单位: 睢县人民文化馆（睢县非物质文化遗产保护中心）', '传统舞蹈', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Suixian%20kylin%20dance%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW(), 640, 'Ⅲ-43', '2011 (第三批)', '扩展项目', '河南省睢县', '睢县人民文化馆（睢县非物质文化遗产保护中心）'),
-- 30. 灯舞（苏家作龙凤灯舞）
(30, '灯舞（苏家作龙凤灯舞）', '灯舞（苏家作龙凤灯舞）', '序号: 30\n项目序号: 642\n编号: Ⅲ-45\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省博爱县\n保护单位: 焦作市苏家作龙凤灯舞保护协会', '传统舞蹈', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dragon%20and%20phoenix%20lantern%20dance%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW(), 642, 'Ⅲ-45', '2008 (第二批)', '新增项目', '河南省博爱县', '焦作市苏家作龙凤灯舞保护协会'),
-- 31. 跑帷子
(31, '跑帷子', '跑帷子', '序号: 31\n项目序号: 647\n编号: Ⅲ-50\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省汤阴县\n保护单位: 汤阴县文化馆', '传统舞蹈', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Pao%20weizi%20traditional%20Chinese%20folk%20dance&image_size=landscape_16_9', '', 1, NOW(), NOW(), 647, 'Ⅲ-50', '2008 (第二批)', '新增项目', '河南省汤阴县', '汤阴县文化馆'),
-- 32. 官会响锣
(32, '官会响锣', '官会响锣', '序号: 32\n项目序号: 648\n编号: Ⅲ-51\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省项城市\n保护单位: 项城市文化馆（项城市非物质文化遗产保护中心）', '传统舞蹈', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Guanhui%20gong%20and%20drum%20traditional%20Chinese%20percussion&image_size=landscape_16_9', '', 1, NOW(), NOW(), 648, 'Ⅲ-51', '2008 (第二批)', '新增项目', '河南省项城市', '项城市文化馆（项城市非物质文化遗产保护中心）'),
-- 33. 耍老虎
(33, '耍老虎', '耍老虎', '序号: 33\n项目序号: 1271\n编号: Ⅲ-118\n类别: 传统舞蹈\n公布时间: 2014 (第四批)\n类型: 新增项目\n申报地区或单位: 河南省焦作市\n保护单位: 焦作市文化馆（焦作市非物质文化遗产保护中心）', '传统舞蹈', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Tiger%20play%20traditional%20Chinese%20folk%20dance&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1271, 'Ⅲ-118', '2014 (第四批)', '新增项目', '河南省焦作市', '焦作市文化馆（焦作市非物质文化遗产保护中心）'),
-- 34. 豫剧
(34, '豫剧', '豫剧', '序号: 34\n项目序号: 167\n编号: Ⅳ-23\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省\n保护单位: 河南省非物质文化遗产保护和智慧化中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Yu%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 167, 'Ⅳ-23', '2006 (第一批)', '新增项目', '河南省', '河南省非物质文化遗产保护和智慧化中心'),
-- 35. 宛梆
(35, '宛梆', '宛梆', '序号: 35\n项目序号: 168\n编号: Ⅳ-24\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省内乡县\n保护单位: 内乡县宛梆艺术传承保护中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Wanbang%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW(), 168, 'Ⅳ-24', '2006 (第一批)', '新增项目', '河南省内乡县', '内乡县宛梆艺术传承保护中心'),
-- 36. 怀梆
(36, '怀梆', '怀梆', '序号: 36\n项目序号: 169\n编号: Ⅳ-25\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省沁阳市\n保护单位: 沁阳市怀梆艺术保护传承中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Huai%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 169, 'Ⅳ-25', '2006 (第一批)', '新增项目', '河南省沁阳市', '沁阳市怀梆艺术保护传承中心'),
-- 37. 大平调
(37, '大平调', '大平调', '序号: 37\n项目序号: 170\n编号: Ⅳ-26\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省濮阳县\n保护单位: 濮阳县大平调艺术保护传承中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dapingdiao%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW(), 170, 'Ⅳ-26', '2006 (第一批)', '新增项目', '河南省濮阳县', '濮阳县大平调艺术保护传承中心'),
-- 38. 大平调
(38, '大平调', '大平调', '序号: 38\n项目序号: 170\n编号: Ⅳ-26\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省滑县\n保护单位: 滑县大平调剧团', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dapingdiao%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW(), 170, 'Ⅳ-26', '2006 (第一批)', '新增项目', '河南省滑县', '滑县大平调剧团'),
-- 39. 大平调
(39, '大平调', '大平调', '序号: 39\n项目序号: 170\n编号: Ⅳ-26\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省延津县\n保护单位: 延津县大平调艺术传承保护中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dapingdiao%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW(), 170, 'Ⅳ-26', '2006 (第一批)', '新增项目', '河南省延津县', '延津县大平调艺术传承保护中心'),
-- 40. 大平调
(40, '大平调', '大平调', '序号: 40\n项目序号: 170\n编号: Ⅳ-26\n类别: 传统戏剧\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省浚县\n保护单位: 浚县大平调传承保护中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dapingdiao%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW(), 170, 'Ⅳ-26', '2011 (第三批)', '扩展项目', '河南省浚县', '浚县大平调传承保护中心'),
-- 41. 越调
(41, '越调', '越调', '序号: 41\n项目序号: 171\n编号: Ⅳ-27\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省周口市\n保护单位: 河南省越调艺术保护传承中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Yue%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 171, 'Ⅳ-27', '2006 (第一批)', '新增项目', '河南省周口市', '河南省越调艺术保护传承中心'),
-- 42. 越调
(42, '越调', '越调', '序号: 42\n项目序号: 171\n编号: Ⅳ-27\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省许昌市\n保护单位: 许昌市戏曲艺术发展中心（许昌市越调艺术保护传承中心）', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Yue%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 171, 'Ⅳ-27', '2008 (第二批)', '扩展项目', '河南省许昌市', '许昌市戏曲艺术发展中心（许昌市越调艺术保护传承中心）'),
-- 43. 越调
(43, '越调', '越调', '序号: 43\n项目序号: 171\n编号: Ⅳ-27\n类别: 传统戏剧\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省邓州市\n保护单位: 邓州市越调剧团', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Yue%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 171, 'Ⅳ-27', '2011 (第三批)', '扩展项目', '河南省邓州市', '邓州市越调剧团'),
-- 44. 柳子戏
(44, '柳子戏', '柳子戏', '序号: 44\n项目序号: 187\n编号: Ⅳ-43\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省清丰县\n保护单位: 清丰县柳子戏艺术传承中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Liuzi%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 187, 'Ⅳ-43', '2008 (第二批)', '扩展项目', '河南省清丰县', '清丰县柳子戏艺术传承中心'),
-- 45. 大弦戏
(45, '大弦戏', '大弦戏', '序号: 45\n项目序号: 188\n编号: Ⅳ-44\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省滑县\n保护单位: 滑县大弦戏剧团', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Daxian%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 188, 'Ⅳ-44', '2006 (第一批)', '新增项目', '河南省滑县', '滑县大弦戏剧团'),
-- 46. 大弦戏
(46, '大弦戏', '大弦戏', '序号: 46\n项目序号: 188\n编号: Ⅳ-44\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省濮阳县\n保护单位: 濮阳县大弦戏艺术保护传承中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Daxian%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 188, 'Ⅳ-44', '2006 (第一批)', '新增项目', '河南省濮阳县', '濮阳县大弦戏艺术保护传承中心'),
-- 47. 四平调
(47, '四平调', '四平调', '序号: 47\n项目序号: 194\n编号: Ⅳ-50\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省商丘市\n保护单位: 商丘市梁园区四平调艺术研究中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Sipingdiao%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW(), 194, 'Ⅳ-50', '2006 (第一批)', '新增项目', '河南省商丘市', '商丘市梁园区四平调艺术研究中心'),
-- 48. 四平调
(48, '四平调', '四平调', '序号: 48\n项目序号: 194\n编号: Ⅳ-50\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省濮阳市\n保护单位: 范县四平调艺术传播研究中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Sipingdiao%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW(), 194, 'Ⅳ-50', '2006 (第一批)', '新增项目', '河南省濮阳市', '范县四平调艺术传播研究中心'),
-- 49. 曲剧
(49, '曲剧', '曲剧', '序号: 49\n项目序号: 212\n编号: Ⅳ-68\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省\n保护单位: 河南省曲剧艺术保护传承中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Qu%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 212, 'Ⅳ-68', '2006 (第一批)', '新增项目', '河南省', '河南省曲剧艺术保护传承中心'),
-- 50. 道情戏（太康道情戏）
(50, '道情戏（太康道情戏）', '道情戏（太康道情戏）', '序号: 50\n项目序号: 215\n编号: Ⅳ-71\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省太康县\n保护单位: 太康县道情艺术保护传承中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Daokqing%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 215, 'Ⅳ-71', '2006 (第一批)', '新增项目', '河南省太康县', '太康县道情艺术保护传承中心'),
-- 51. 目连戏（南乐目连戏）
(51, '目连戏（南乐目连戏）', '目连戏（南乐目连戏）', '序号: 51\n项目序号: 231\n编号: Ⅳ-87\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省南乐县\n保护单位: 南乐县非物质文化遗产保护中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Mulian%20Opera%20traditional%20Chinese%20Buddhist%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 231, 'Ⅳ-87', '2006 (第一批)', '新增项目', '河南省南乐县', '南乐县非物质文化遗产保护中心'),
-- 52. 皮影戏（罗山皮影戏）
(52, '皮影戏（罗山皮影戏）', '皮影戏（罗山皮影戏）', '序号: 52\n项目序号: 235\n编号: Ⅳ-91\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省罗山县\n保护单位: 罗山县文化馆', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luoshan%20shadow%20puppet%20theater%20traditional%20art&image_size=landscape_16_9', '', 1, NOW(), NOW(), 235, 'Ⅳ-91', '2008 (第二批)', '扩展项目', '河南省罗山县', '罗山县文化馆'),
-- 53. 皮影戏（桐柏皮影戏）
(53, '皮影戏（桐柏皮影戏）', '皮影戏（桐柏皮影戏）', '序号: 53\n项目序号: 235\n编号: Ⅳ-91\n类别: 传统戏剧\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省桐柏县\n保护单位: 桐柏县人民文化馆', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Tongbai%20shadow%20puppet%20theater%20traditional%20art&image_size=landscape_16_9', '', 1, NOW(), NOW(), 235, 'Ⅳ-91', '2011 (第三批)', '扩展项目', '河南省桐柏县', '桐柏县人民文化馆'),
-- 54. 花鼓戏（光山花鼓戏）
(54, '花鼓戏（光山花鼓戏）', '花鼓戏（光山花鼓戏）', '序号: 54\n项目序号: 713\n编号: Ⅳ-112\n类别: 传统戏剧\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省光山县\n保护单位: 光山县非物质文化遗产保护中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Guangshan%20flower%20drum%20opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 713, 'Ⅳ-112', '2014 (第四批)', '扩展项目', '河南省光山县', '光山县非物质文化遗产保护中心'),
-- 55. 二夹弦
(55, '二夹弦', '二夹弦', '序号: 55\n项目序号: 714\n编号: Ⅳ-113\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省开封市\n保护单位: 开封市田爱云二夹弦剧团', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Erjiaxian%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 714, 'Ⅳ-113', '2008 (第二批)', '新增项目', '河南省开封市', '开封市田爱云二夹弦剧团'),
-- 56. 二夹弦
(56, '二夹弦', '二夹弦', '序号: 56\n项目序号: 714\n编号: Ⅳ-113\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省滑县\n保护单位: 滑县二夹弦剧团', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Erjiaxian%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 714, 'Ⅳ-113', '2008 (第二批)', '新增项目', '河南省滑县', '滑县二夹弦剧团'),
-- 57. 罗卷戏
(57, '罗卷戏', '罗卷戏', '序号: 57\n项目序号: 724\n编号: Ⅳ-123\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省汝南县\n保护单位: 汝南县罗卷戏艺术传承保护中心', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luojuan%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 724, 'Ⅳ-123', '2008 (第二批)', '新增项目', '河南省汝南县', '汝南县罗卷戏艺术传承保护中心'),
-- 58. 罗卷戏
(58, '罗卷戏', '罗卷戏', '序号: 58\n项目序号: 724\n编号: Ⅳ-123\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省范县\n保护单位: 范县罗卷戏剧团', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luojuan%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 724, 'Ⅳ-123', '2008 (第二批)', '新增项目', '河南省范县', '范县罗卷戏剧团'),
-- 59. 罗卷戏
(59, '罗卷戏', '罗卷戏', '序号: 59\n项目序号: 724\n编号: Ⅳ-123\n类别: 传统戏剧\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省邓州市\n保护单位: 邓州市孔庄罗卷戏剧团有限公司', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luojuan%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 724, 'Ⅳ-123', '2014 (第四批)', '扩展项目', '河南省邓州市', '邓州市孔庄罗卷戏剧团有限公司'),
-- 60. 二股弦
(60, '二股弦', '二股弦', '序号: 60\n项目序号: 725\n编号: Ⅳ-124\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省武陟县\n保护单位: 武陟县大司马二股弦剧团', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Erguxian%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 725, 'Ⅳ-124', '2008 (第二批)', '新增项目', '河南省武陟县', '武陟县大司马二股弦剧团'),
-- 61. 淮调
(61, '淮调', '淮调', '序号: 61\n项目序号: 1109\n编号: Ⅳ-147\n类别: 传统戏剧\n公布时间: 2011 (第三批)\n类型: 新增项目\n申报地区或单位: 河南省安阳县\n保护单位: 安阳县邺祥淮调艺术有限公司', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Huai%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1109, 'Ⅳ-147', '2011 (第三批)', '新增项目', '河南省安阳县', '安阳县邺祥淮调艺术有限公司'),
-- 62. 落腔
(62, '落腔', '落腔', '序号: 62\n项目序号: 1110\n编号: Ⅳ-148\n类别: 传统戏剧\n公布时间: 2011 (第三批)\n类型: 新增项目\n申报地区或单位: 河南省内黄县\n保护单位: 内黄县文化馆', '传统戏剧', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luqiang%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1110, 'Ⅳ-148', '2011 (第三批)', '新增项目', '河南省内黄县', '内黄县文化馆'),
-- 63. 河洛大鼓
(63, '河洛大鼓', '河洛大鼓', '序号: 63\n项目序号: 248\n编号: Ⅴ-12\n类别: 曲艺\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省洛阳市\n保护单位: 洛阳市文化馆（洛阳市非物质文化遗产保护中心）', '曲艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Heluo%20dagu%20traditional%20Chinese%20storytelling&image_size=landscape_16_9', '', 1, NOW(), NOW(), 248, 'Ⅴ-12', '2006 (第一批)', '新增项目', '河南省洛阳市', '洛阳市文化馆（洛阳市非物质文化遗产保护中心）'),
-- 64. 河南坠子
(64, '河南坠子', '河南坠子', '序号: 64\n项目序号: 256\n编号: Ⅴ-20\n类别: 曲艺\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省\n保护单位: 河南省非物质文化遗产保护和智慧化中心', '曲艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Henan%20zhuizi%20traditional%20Chinese%20storytelling&image_size=landscape_16_9', '', 1, NOW(), NOW(), 256, 'Ⅴ-20', '2006 (第一批)', '新增项目', '河南省', '河南省非物质文化遗产保护和智慧化中心'),
-- 65. 三弦书（南阳三弦书）
(65, '三弦书（南阳三弦书）', '三弦书（南阳三弦书）', '序号: 65\n项目序号: 757\n编号: Ⅴ-64\n类别: 曲艺\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省南阳市\n保护单位: 南阳市说唱团演艺有限公司', '曲艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Sanxian%20storytelling%20traditional%20Chinese%20folk%20art&image_size=landscape_16_9', '', 1, NOW(), NOW(), 757, 'Ⅴ-64', '2008 (第二批)', '新增项目', '河南省南阳市', '南阳市说唱团演艺有限公司'),
-- 66. 大调曲子
(66, '大调曲子', '大调曲子', '序号: 66\n项目序号: 762\n编号: Ⅴ-69\n类别: 曲艺\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省南阳市\n保护单位: 南阳曲剧艺术中心', '曲艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dadiao%20quzi%20traditional%20Chinese%20folk%20music&image_size=landscape_16_9', '', 1, NOW(), NOW(), 762, 'Ⅴ-69', '2008 (第二批)', '新增项目', '河南省南阳市', '南阳曲剧艺术中心'),
-- 67. 陕州锣鼓书
(67, '陕州锣鼓书', '陕州锣鼓书', '序号: 67\n项目序号: 1434\n编号: Ⅴ-136\n类别: 曲艺\n公布时间: 2021 (第五批)\n类型: 新增项目\n申报地区或单位: 河南省三门峡市\n保护单位: 三门峡市群众艺术馆', '曲艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Shanzhou%20gong%20and%20drum%20storytelling%20traditional%20Chinese%20folk%20art&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1434, 'Ⅴ-136', '2021 (第五批)', '新增项目', '河南省三门峡市', '三门峡市群众艺术馆'),
-- 68. 少林功夫
(68, '少林功夫', '少林功夫', '序号: 68\n项目序号: 289\n编号: Ⅵ-7\n类别: 传统体育、游艺与杂技\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省登封市\n保护单位: 中国嵩山少林寺', '传统体育、游艺与杂技', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Shaolin%20Kung%20Fu%20traditional%20Chinese%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW(), 289, 'Ⅵ-7', '2006 (第一批)', '新增项目', '河南省登封市', '中国嵩山少林寺'),
-- 69. 太极拳（陈氏太极拳）
(69, '太极拳（陈氏太极拳）', '太极拳（陈氏太极拳）', '序号: 69\n项目序号: 293\n编号: Ⅵ-11\n类别: 传统体育、游艺与杂技\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省焦作市\n保护单位: 焦作市文化馆（焦作市非物质文化遗产保护中心）', '传统体育、游艺与杂技', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Tai%20Chi%20Chen%20style%20traditional%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW(), 293, 'Ⅵ-11', '2006 (第一批)', '新增项目', '河南省焦作市', '焦作市文化馆（焦作市非物质文化遗产保护中心）'),
-- 70. 太极拳（和氏太极拳）
(70, '太极拳（和氏太极拳）', '太极拳（和氏太极拳）', '序号: 70\n项目序号: 293\n编号: Ⅵ-11\n类别: 传统体育、游艺与杂技\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省温县\n保护单位: 温县和式太极拳学会', '传统体育、游艺与杂技', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=He%20style%20Tai%20Chi%20traditional%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW(), 293, 'Ⅵ-11', '2014 (第四批)', '扩展项目', '河南省温县', '温县和式太极拳学会'),
-- 71. 八极拳（月山八极拳）
(71, '八极拳（月山八极拳）', '八极拳（月山八极拳）', '序号: 71\n项目序号: 800\n编号: Ⅵ-28\n类别: 传统体育、游艺与杂技\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省博爱县\n保护单位: 博爱县八极文武学校', '传统体育、游艺与杂技', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Bajiquan%20traditional%20Chinese%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW(), 800, 'Ⅵ-28', '2008 (第二批)', '新增项目', '河南省博爱县', '博爱县八极文武学校'),
-- 72. 心意六合拳
(72, '心意六合拳', '心意六合拳', '序号: 72\n项目序号: 802\n编号: Ⅵ-30\n类别: 传统体育、游艺与杂技\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省漯河市\n保护单位: 漯河市心意六和拳研究会', '传统体育、游艺与杂技', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Xinyi%20Liuhe%20quan%20traditional%20Chinese%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW(), 802, 'Ⅵ-30', '2008 (第二批)', '新增项目', '河南省漯河市', '漯河市心意六和拳研究会'),
-- 73. 心意六合拳
(73, '心意六合拳', '心意六合拳', '序号: 73\n项目序号: 802\n编号: Ⅵ-30\n类别: 传统体育、游艺与杂技\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省周口市\n保护单位: 周口市心意六合拳协会', '传统体育、游艺与杂技', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Xinyi%20Liuhe%20quan%20traditional%20Chinese%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW(), 802, 'Ⅵ-30', '2008 (第二批)', '新增项目', '河南省周口市', '周口市心意六合拳协会'),
-- 74. 苌家拳
(74, '苌家拳', '苌家拳', '序号: 74\n项目序号: 806\n编号: Ⅵ-34\n类别: 传统体育、游艺与杂技\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省荥阳市\n保护单位: 荥阳苌家拳研究会', '传统体育、游艺与杂技', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Chang%20family%20martial%20arts%20traditional%20Chinese%20kung%20fu&image_size=landscape_16_9', '', 1, NOW(), NOW(), 806, 'Ⅵ-34', '2008 (第二批)', '新增项目', '河南省荥阳市', '荥阳苌家拳研究会'),
-- 75. 东北庄杂技
(75, '东北庄杂技', '东北庄杂技', '序号: 75\n项目序号: 821\n编号: Ⅵ-49\n类别: 传统体育、游艺与杂技\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省濮阳市\n保护单位: 濮阳市华龙区东北庄杂技艺术学校', '传统体育、游艺与杂技', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Northeast%20Village%20acrobatic%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW(), 821, 'Ⅵ-49', '2008 (第二批)', '新增项目', '河南省濮阳市', '濮阳市华龙区东北庄杂技艺术学校'),
-- 76. 撂石锁
(76, '撂石锁', '撂石锁', '序号: 76\n项目序号: 1147\n编号: Ⅵ-64\n类别: 传统体育、游艺与杂技\n公布时间: 2011 (第三批)\n类型: 新增项目\n申报地区或单位: 河南省开封市\n保护单位: 开封市文化馆', '传统体育、游艺与杂技', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Stone%20lock%20throwing%20traditional%20Chinese%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1147, 'Ⅵ-64', '2011 (第三批)', '新增项目', '河南省开封市', '开封市文化馆'),
-- 77. 幻术（宝丰魔术）
(77, '幻术（宝丰魔术）', '幻术（宝丰魔术）', '序号: 77\n项目序号: 1313\n编号: Ⅵ-82\n类别: 传统体育、游艺与杂技\n公布时间: 2021 (第五批)\n类型: 扩展项目\n申报地区或单位: 河南省平顶山市宝丰县\n保护单位: 宝丰县人民文化馆', '传统体育、游艺与杂技', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Baofeng%20magic%20tricks%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1313, 'Ⅵ-82', '2021 (第五批)', '扩展项目', '河南省平顶山市宝丰县', '宝丰县人民文化馆'),
-- 78. 朱仙镇木版年画
(78, '朱仙镇木版年画', '朱仙镇木版年画', '序号: 78\n项目序号: 306\n编号: Ⅶ-7\n类别: 传统美术\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省开封市\n保护单位: 开封市文化馆', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Zhuxianzhen%20woodblock%20new%20year%20paintings%20traditional%20Chinese%20art&image_size=landscape_16_9', '', 1, NOW(), NOW(), 306, 'Ⅶ-7', '2006 (第一批)', '新增项目', '河南省开封市', '开封市文化馆'),
-- 79. 剪纸（灵宝剪纸）
(79, '剪纸（灵宝剪纸）', '剪纸（灵宝剪纸）', '序号: 79\n项目序号: 315\n编号: Ⅶ-16\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省灵宝市\n保护单位: 灵宝市文化馆', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Lingbao%20paper%20cutting%20traditional%20Chinese%20art&image_size=landscape_16_9', '', 1, NOW(), NOW(), 315, 'Ⅶ-16', '2008 (第二批)', '扩展项目', '河南省灵宝市', '灵宝市文化馆'),
-- 80. 剪纸（卢氏剪纸）
(80, '剪纸（卢氏剪纸）', '剪纸（卢氏剪纸）', '序号: 80\n项目序号: 315\n编号: Ⅶ-16\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省卢氏县\n保护单位: 卢氏县人民文化馆', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Lushi%20paper%20cutting%20traditional%20Chinese%20art&image_size=landscape_16_9', '', 1, NOW(), NOW(), 315, 'Ⅶ-16', '2008 (第二批)', '扩展项目', '河南省卢氏县', '卢氏县人民文化馆'),
-- 81. 剪纸（辉县剪纸）
(81, '剪纸（辉县剪纸）', '剪纸（辉县剪纸）', '序号: 81\n项目序号: 315\n编号: Ⅶ-16\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省辉县市\n保护单位: 辉县市文化馆', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Huixian%20paper%20cutting%20traditional%20Chinese%20art&image_size=landscape_16_9', '', 1, NOW(), NOW(), 315, 'Ⅶ-16', '2008 (第二批)', '扩展项目', '河南省辉县市', '辉县市文化馆'),
-- 82. 泥塑（浚县泥咕咕）
(82, '泥塑（浚县泥咕咕）', '泥塑（浚县泥咕咕）', '序号: 82\n项目序号: 346\n编号: Ⅶ-47\n类别: 传统美术\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省浚县\n保护单位: 浚县人民文化馆', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Junxian%20clay%20figurines%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW(), 346, 'Ⅶ-47', '2006 (第一批)', '新增项目', '河南省浚县', '浚县人民文化馆'),
-- 83. 泥塑（淮阳泥泥狗）
(83, '泥塑（淮阳泥泥狗）', '泥塑（淮阳泥泥狗）', '序号: 83\n项目序号: 346\n编号: Ⅶ-47\n类别: 传统美术\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省淮阳县\n保护单位: 淮阳县文化馆', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Huaiyang%20clay%20dogs%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW(), 346, 'Ⅶ-47', '2014 (第四批)', '扩展项目', '河南省淮阳县', '淮阳县文化馆'),
-- 84. 灯彩（洛阳宫灯）
(84, '灯彩（洛阳宫灯）', '灯彩（洛阳宫灯）', '序号: 84\n项目序号: 349\n编号: Ⅶ-50\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省洛阳市\n保护单位: 洛阳市文化馆（洛阳市非物质文化遗产保护中心）', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luoyang%20palace%20lanterns%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW(), 349, 'Ⅶ-50', '2008 (第二批)', '扩展项目', '河南省洛阳市', '洛阳市文化馆（洛阳市非物质文化遗产保护中心）'),
-- 85. 灯彩（汴京灯笼张）
(85, '灯彩（汴京灯笼张）', '灯彩（汴京灯笼张）', '序号: 85\n项目序号: 349\n编号: Ⅶ-50\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省开封市\n保护单位: 开封市彩灯艺术研究会', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Bianjing%20Lantern%20Zhang%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW(), 349, 'Ⅶ-50', '2008 (第二批)', '扩展项目', '河南省开封市', '开封市彩灯艺术研究会'),
-- 86. 石雕（方城石猴）
(86, '石雕（方城石猴）', '石雕（方城石猴）', '序号: 86\n项目序号: 832\n编号: Ⅶ-56\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省方城县\n保护单位: 方城县文化馆', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Fangcheng%20stone%20monkeys%20traditional%20Chinese%20stone%20carving&image_size=landscape_16_9', '', 1, NOW(), NOW(), 832, 'Ⅶ-56', '2008 (第二批)', '新增项目', '河南省方城县', '方城县文化馆'),
-- 87. 玉雕（镇平玉雕）
(87, '玉雕（镇平玉雕）', '玉雕（镇平玉雕）', '序号: 87\n项目序号: 833\n编号: Ⅶ-57\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省镇平县\n保护单位: 镇平县石佛寺珠宝玉雕有限公司', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Zhenping%20jade%20carving%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW(), 833, 'Ⅶ-57', '2008 (第二批)', '新增项目', '河南省镇平县', '镇平县石佛寺珠宝玉雕有限公司'),
-- 88. 木版年画（滑县木版年画）
(88, '木版年画（滑县木版年画）', '木版年画（滑县木版年画）', '序号: 88\n项目序号: 841\n编号: Ⅶ-65\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省滑县\n保护单位: 滑县人民文化馆', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Huaxian%20woodblock%20new%20year%20paintings%20traditional%20Chinese%20art&image_size=landscape_16_9', '', 1, NOW(), NOW(), 841, 'Ⅶ-65', '2008 (第二批)', '新增项目', '河南省滑县', '滑县人民文化馆'),
-- 89. 麦秆剪贴
(89, '麦秆剪贴', '麦秆剪贴', '序号: 89\n项目序号: 845\n编号: Ⅶ-69\n类别: 传统美术\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省清丰县\n保护单位: 清丰县非物质文化遗产保护中心', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Wheat%20straw%20paste%20art%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW(), 845, 'Ⅶ-69', '2014 (第四批)', '扩展项目', '河南省清丰县', '清丰县非物质文化遗产保护中心'),
-- 90. 汴绣
(90, '汴绣', '汴绣', '序号: 90\n项目序号: 850\n编号: Ⅶ-74\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省开封市\n保护单位: 开封汴绣厂', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Kaifeng%20embroidery%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW(), 850, 'Ⅶ-74', '2008 (第二批)', '新增项目', '河南省开封市', '开封汴绣厂'),
-- 91. 烙画（南阳烙画）
(91, '烙画（南阳烙画）', '烙画（南阳烙画）', '序号: 91\n项目序号: 1473\n编号: Ⅶ-125\n类别: 传统美术\n公布时间: 2021 (第五批)\n类型: 新增项目\n申报地区或单位: 河南省南阳市卧龙区\n保护单位: 南阳市烙画厂', '传统美术', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Nanyang%20pyrography%20traditional%20Chinese%20burning%20painting&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1473, 'Ⅶ-125', '2021 (第五批)', '新增项目', '河南省南阳市卧龙区', '南阳市烙画厂'),
-- 92. 宝剑锻制技艺（棠溪宝剑锻制技艺）
(92, '宝剑锻制技艺（棠溪宝剑锻制技艺）', '宝剑锻制技艺（棠溪宝剑锻制技艺）', '序号: 92\n项目序号: 387\n编号: Ⅷ-37\n类别: 传统技艺\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省西平县\n保护单位: 河南棠溪宝剑集团有限公司', '传统技艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Tangxi%20sword%20forging%20technique%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW(), 387, 'Ⅷ-37', '2014 (第四批)', '扩展项目', '河南省西平县', '河南棠溪宝剑集团有限公司'),
-- 93. 钧瓷烧制技艺
(93, '钧瓷烧制技艺', '钧瓷烧制技艺', '序号: 93\n项目序号: 876\n编号: Ⅷ-93\n类别: 传统技艺\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省禹州市\n保护单位: 禹州市杨志钧窑有限公司', '传统技艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Jun%20porcelain%20firing%20technique%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW(), 876, 'Ⅷ-93', '2008 (第二批)', '新增项目', '河南省禹州市', '禹州市杨志钧窑有限公司'),
-- 94. 唐三彩烧制技艺
(94, '唐三彩烧制技艺', '唐三彩烧制技艺', '序号: 94\n项目序号: 877\n编号: Ⅷ-94\n类别: 传统技艺\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省洛阳市\n保护单位: 洛阳九朝文物复制品有限公司', '传统技艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Tri-colored%20Tang%20pottery%20firing%20technique%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW(), 877, 'Ⅷ-94', '2008 (第二批)', '新增项目', '河南省洛阳市', '洛阳九朝文物复制品有限公司'),
-- 95. 蒸馏酒传统酿造技艺（宝丰酒传统酿造技艺）
(95, '蒸馏酒传统酿造技艺（宝丰酒传统酿造技艺）', '蒸馏酒传统酿造技艺（宝丰酒传统酿造技艺）', '序号: 95\n项目序号: 927\n编号: Ⅷ-144\n类别: 传统技艺\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省宝丰县\n保护单位: 宝丰酒业有限公司', '传统技艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Baofeng%20liquor%20brewing%20technique%20traditional%20Chinese%20alcohol&image_size=landscape_16_9', '', 1, NOW(), NOW(), 927, 'Ⅷ-144', '2008 (第二批)', '新增项目', '河南省宝丰县', '宝丰酒业有限公司'),
-- 96. 绿茶制作技艺（信阳毛尖茶制作技艺）
(96, '绿茶制作技艺（信阳毛尖茶制作技艺）', '绿茶制作技艺（信阳毛尖茶制作技艺）', '序号: 96\n项目序号: 931\n编号: Ⅷ-148\n类别: 传统技艺\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省信阳市\n保护单位: 信阳市茶叶商会', '传统技艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Xinyang%20maojian%20tea%20making%20technique%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW(), 931, 'Ⅷ-148', '2014 (第四批)', '扩展项目', '河南省信阳市', '信阳市茶叶商会'),
-- 97. 真不同洛阳水席制作技艺
(97, '真不同洛阳水席制作技艺', '真不同洛阳水席制作技艺', '序号: 97\n项目序号: 956\n编号: Ⅷ-173\n类别: 传统技艺\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省洛阳市\n保护单位: 洛阳酒家有限责任公司', '传统技艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luoyang%20water%20banquet%20cooking%20technique%20traditional%20Chinese%20cuisine&image_size=landscape_16_9', '', 1, NOW(), NOW(), 956, 'Ⅷ-173', '2008 (第二批)', '新增项目', '河南省洛阳市', '洛阳酒家有限责任公司'),
-- 98. 窑洞营造技艺（地坑院营造技艺）
(98, '窑洞营造技艺（地坑院营造技艺）', '窑洞营造技艺（地坑院营造技艺）', '序号: 98\n项目序号: 963\n编号: Ⅷ-180\n类别: 传统技艺\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省陕县\n保护单位: 三门峡市陕州区文化馆', '传统技艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Yao%20cave%20dwellings%20traditional%20Chinese%20architecture&image_size=landscape_16_9', '', 1, NOW(), NOW(), 963, 'Ⅷ-180', '2011 (第三批)', '扩展项目', '河南省陕县', '三门峡市陕州区文化馆'),
-- 99. 汝瓷烧制技艺
(99, '汝瓷烧制技艺', '汝瓷烧制技艺', '序号: 99\n项目序号: 1169\n编号: Ⅷ-189\n类别: 传统技艺\n公布时间: 2011 (第三批)\n类型: 新增项目\n申报地区或单位: 河南省汝州市\n保护单位: 汝州市非物质文化遗产保护中心', '传统技艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Ru%20porcelain%20firing%20technique%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1169, 'Ⅷ-189', '2011 (第三批)', '新增项目', '河南省汝州市', '汝州市非物质文化遗产保护中心'),
-- 100. 汝瓷烧制技艺
(100, '汝瓷烧制技艺', '汝瓷烧制技艺', '序号: 100\n项目序号: 1169\n编号: Ⅷ-189\n类别: 传统技艺\n公布时间: 2011 (第三批)\n类型: 新增项目\n申报地区或单位: 河南省宝丰县\n保护单位: 宝丰县人民文化馆', '传统技艺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Ru%20porcelain%20firing%20technique%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1169, 'Ⅷ-189', '2011 (第三批)', '新增项目', '河南省宝丰县', '宝丰县人民文化馆'),
-- 101. 中医正骨疗法（平乐郭氏正骨法）
(101, '中医正骨疗法（平乐郭氏正骨法）', '中医正骨疗法（平乐郭氏正骨法）', '序号: 101\n项目序号: 445\n编号: Ⅸ-6\n类别: 传统医药\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省洛阳市\n保护单位: 河南省洛阳正骨医院（河南省骨科医院）', '传统医药', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Traditional%20Chinese%20bone%20setting%20therapy&image_size=landscape_16_9', '', 1, NOW(), NOW(), 445, 'Ⅸ-6', '2008 (第二批)', '扩展项目', '河南省洛阳市', '河南省洛阳正骨医院（河南省骨科医院）'),
-- 102. 重阳节（上蔡重阳习俗）
(102, '重阳节（上蔡重阳习俗）', '重阳节（上蔡重阳习俗）', '序号: 102\n项目序号: 454\n编号: Ⅹ-6\n类别: 民俗\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省上蔡县\n保护单位: 上蔡县人民文化馆', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Double%20Ninth%20Festival%20traditional%20Chinese%20holiday&image_size=landscape_16_9', '', 1, NOW(), NOW(), 454, 'Ⅹ-6', '2011 (第三批)', '扩展项目', '河南省上蔡县', '上蔡县人民文化馆'),
-- 103. 黄帝祭典（新郑黄帝拜祖祭典）
(103, '黄帝祭典（新郑黄帝拜祖祭典）', '黄帝祭典（新郑黄帝拜祖祭典）', '序号: 103\n项目序号: 480\n编号: Ⅹ-32\n类别: 民俗\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省新郑市\n保护单位: 新郑市文化馆', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Huangdi%20sacrifice%20ceremony%20traditional%20Chinese%20ritual&image_size=landscape_16_9', '', 1, NOW(), NOW(), 480, 'Ⅹ-32', '2008 (第二批)', '扩展项目', '河南省新郑市', '新郑市文化馆'),
-- 104. 太昊伏羲祭典
(104, '太昊伏羲祭典', '太昊伏羲祭典', '序号: 104\n项目序号: 485\n编号: Ⅹ-37\n类别: 民俗\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省淮阳县\n保护单位: 淮阳县文化馆', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Fuxi%20sacrifice%20ceremony%20traditional%20Chinese%20ritual&image_size=landscape_16_9', '', 1, NOW(), NOW(), 485, 'Ⅹ-37', '2006 (第一批)', '新增项目', '河南省淮阳县', '淮阳县文化馆'),
-- 105. 民间社火（浚县民间社火）
(105, '民间社火（浚县民间社火）', '民间社火（浚县民间社火）', '序号: 105\n项目序号: 502\n编号: Ⅹ-54\n类别: 民俗\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省浚县\n保护单位: 浚县人民文化馆', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Junxian%20folk%20social%20fire%20traditional%20Chinese%20festival&image_size=landscape_16_9', '', 1, NOW(), NOW(), 502, 'Ⅹ-54', '2008 (第二批)', '扩展项目', '河南省浚县', '浚县人民文化馆'),
-- 106. 马街书会
(106, '马街书会', '马街书会', '序号: 106\n项目序号: 506\n编号: Ⅹ-58\n类别: 民俗\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省宝丰县\n保护单位: 宝丰县人民文化馆', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Majie%20storytelling%20festival%20traditional%20Chinese%20folk%20art&image_size=landscape_16_9', '', 1, NOW(), NOW(), 506, 'Ⅹ-58', '2006 (第一批)', '新增项目', '河南省宝丰县', '宝丰县人民文化馆'),
-- 107. 药市习俗（百泉药会）
(107, '药市习俗（百泉药会）', '药市习俗（百泉药会）', '序号: 107\n项目序号: 508\n编号: Ⅹ-60\n类别: 民俗\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省辉县市\n保护单位: 辉县市文化馆', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Baiquan%20medicine%20market%20traditional%20Chinese%20festival&image_size=landscape_16_9', '', 1, NOW(), NOW(), 508, 'Ⅹ-60', '2008 (第二批)', '扩展项目', '河南省辉县市', '辉县市文化馆'),
-- 108. 药市习俗（禹州药会）
(108, '药市习俗（禹州药会）', '药市习俗（禹州药会）', '序号: 108\n项目序号: 508\n编号: Ⅹ-60\n类别: 民俗\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省禹州市\n保护单位: 禹州市非物质文化遗产保护中心', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Yuzhou%20medicine%20market%20custom%20traditional%20Chinese%20festival&image_size=landscape_16_9', '', 1, NOW(), NOW(), 508, 'Ⅹ-60', '2008 (第二批)', '扩展项目', '河南省禹州市', '禹州市非物质文化遗产保护中心'),
-- 109. 农历二十四节气（内乡打春牛习俗）
(109, '农历二十四节气（内乡打春牛习俗）', '农历二十四节气（内乡打春牛习俗）', '序号: 109\n项目序号: 516\n编号: Ⅹ-68\n类别: 民俗\n公布时间: 2021 (第五批)\n类型: 扩展项目\n申报地区或单位: 河南省南阳市内乡县\n保护单位: 内乡县县衙博物馆', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Chinese%2024%20solar%20terms%20traditional%20calendar%20system&image_size=landscape_16_9', '', 1, NOW(), NOW(), 516, 'Ⅹ-68', '2021 (第五批)', '扩展项目', '河南省南阳市内乡县', '内乡县县衙博物馆'),
-- 110. 庙会（浚县正月古庙会）
(110, '庙会（浚县正月古庙会）', '庙会（浚县正月古庙会）', '序号: 110\n项目序号: 991\n编号: Ⅹ-84\n类别: 民俗\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省浚县\n保护单位: 浚县人民文化馆', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Junxian%20January%20ancient%20temple%20fair%20traditional%20Chinese%20festival&image_size=landscape_16_9', '', 1, NOW(), NOW(), 991, 'Ⅹ-84', '2014 (第四批)', '扩展项目', '河南省浚县', '浚县人民文化馆'),
-- 111. 庙会（商丘火神台庙会）
(111, '庙会（商丘火神台庙会）', '庙会（商丘火神台庙会）', '序号: 111\n项目序号: 991\n编号: Ⅹ-84\n类别: 民俗\n公布时间: 2021 (第五批)\n类型: 扩展项目\n申报地区或单位: 河南省商丘市睢阳区\n保护单位: 商丘市睢阳区文化馆', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Shangqiu%20Fire%20God%20Temple%20fair%20traditional%20Chinese%20festival&image_size=landscape_16_9', '', 1, NOW(), NOW(), 991, 'Ⅹ-84', '2021 (第五批)', '扩展项目', '河南省商丘市睢阳区', '商丘市睢阳区文化馆'),
-- 112. 民间信俗（关公信俗）
(112, '民间信俗（关公信俗）', '民间信俗（关公信俗）', '序号: 112\n项目序号: 992\n编号: Ⅹ-85\n类别: 民俗\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省洛阳市\n保护单位: 洛阳关林管理处', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Guan%20Gong%20folk%20belief%20traditional%20Chinese%20custom&image_size=landscape_16_9', '', 1, NOW(), NOW(), 992, 'Ⅹ-85', '2008 (第二批)', '新增项目', '河南省洛阳市', '洛阳关林管理处'),
-- 113. 打铁花
(113, '打铁花', '打铁花', '序号: 113\n项目序号: 995\n编号: Ⅹ-88\n类别: 民俗\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省确山县\n保护单位: 确山县文化馆', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Iron%20flower%20beating%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW(), 995, 'Ⅹ-88', '2008 (第二批)', '新增项目', '河南省确山县', '确山县文化馆'),
-- 114. 洛阳牡丹花会
(114, '洛阳牡丹花会', '洛阳牡丹花会', '序号: 114\n项目序号: 1010\n编号: Ⅹ-103\n类别: 民俗\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省洛阳市\n保护单位: 洛阳市文化馆（洛阳市非物质文化遗产保护中心）', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luoyang%20peony%20flower%20festival%20traditional%20Chinese%20celebration&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1010, 'Ⅹ-103', '2008 (第二批)', '新增项目', '河南省洛阳市', '洛阳市文化馆（洛阳市非物质文化遗产保护中心）'),
-- 115. 祭典（老子祭典）
(115, '祭典（老子祭典）', '祭典（老子祭典）', '序号: 115\n项目序号: 1543\n编号: Ⅹ-169\n类别: 民俗\n公布时间: 2021 (第五批)\n类型: 新增项目\n申报地区或单位: 河南省鹿邑县\n保护单位: 鹿邑县非物质文化遗产保护中心', '民俗', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Laozi%20sacrifice%20ceremony%20traditional%20Chinese%20ritual&image_size=landscape_16_9', '', 1, NOW(), NOW(), 1543, 'Ⅹ-169', '2021 (第五批)', '新增项目', '河南省鹿邑县', '鹿邑县非物质文化遗产保护中心')

ON DUPLICATE KEY UPDATE 
title = VALUES(title), 
description = VALUES(description), 
category_id = VALUES(category_id), 
region_id = VALUES(region_id), 
cover_image = VALUES(cover_image), 
video_url = VALUES(video_url), 
status = VALUES(status), 
update_time = NOW(),
project_serial = VALUES(project_serial),
item_number = VALUES(item_number),
heritage_name = VALUES(heritage_name),
category = VALUES(category),
announcement_time = VALUES(announcement_time),
heritage_type = VALUES(heritage_type),
declaration_region = VALUES(declaration_region),
protection_unit = VALUES(protection_unit);

SET FOREIGN_KEY_CHECKS = 1;