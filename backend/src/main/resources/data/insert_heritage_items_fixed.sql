-- 非遗项目数据插入脚本（批量）
-- 插入用户提供的第31-125项非遗项目数据

-- 设置字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 插入非遗项目数据
INSERT INTO heritage_item (id, serial_number, project_serial_number, project_code, title, description, category_name, announcement_time, project_type, declaration_region, protection_unit, category_id, region_id, cover_image, video_url, status, create_time, update_time) VALUES 
-- 1. 梁祝传说
(1, 1, '7', 'Ⅰ-7', '梁祝传说', '序号: 1\n项目序号: 7\n编号: Ⅰ-7\n类别: 民间文学\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省汝南县\n保护单位: 汝南县文化馆', '民间文学', '2006 (第一批)', '新增项目', '河南省汝南县', '汝南县文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Butterfly%20Lovers%20legend%20traditional%20Chinese%20folklore&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 2. 董永传说
(2, 2, '9', 'Ⅰ-9', '董永传说', '序号: 2\n项目序号: 9\n编号: Ⅰ-9\n类别: 民间文学\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省武陟县\n保护单位: 武陟县董永文化服务中心', '民间文学', '2006 (第一批)', '新增项目', '河南省武陟县', '武陟县董永文化服务中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dong%20Yong%20legend%20traditional%20Chinese%20folklore&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 3. 木兰传说
(3, 3, '537', 'Ⅰ-50', '木兰传说', '序号: 3\n项目序号: 537\n编号: Ⅰ-50\n类别: 民间文学\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省虞城县\n保护单位: 虞城县文化馆', '民间文学', '2008 (第二批)', '新增项目', '河南省虞城县', '虞城县文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Mulan%20legend%20traditional%20Chinese%20folklore&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 4. 盘古神话
(4, 4, '544', 'Ⅰ-57', '盘古神话', '序号: 4\n项目序号: 544\n编号: Ⅰ-57\n类别: 民间文学\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省桐柏县\n保护单位: 桐柏县人民文化馆', '民间文学', '2008 (第二批)', '新增项目', '河南省桐柏县', '桐柏县人民文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Pangu%20mythology%20traditional%20Chinese%20creation%20story&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 5. 盘古神话
(5, 5, '544', 'Ⅰ-57', '盘古神话', '序号: 5\n项目序号: 544\n编号: Ⅰ-57\n类别: 民间文学\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省泌阳县\n保护单位: 泌阳县文化馆', '民间文学', '2008 (第二批)', '新增项目', '河南省泌阳县', '泌阳县文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Pangu%20mythology%20traditional%20Chinese%20creation%20story&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 6. 邵原神话群
(6, 6, '545', 'Ⅰ-58', '邵原神话群', '序号: 6\n项目序号: 545\n编号: Ⅰ-58\n类别: 民间文学\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省济源市\n保护单位: 济源市邵原镇女娲文化研究基地', '民间文学', '2008 (第二批)', '新增项目', '河南省济源市', '济源市邵原镇女娲文化研究基地', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Shaoyuan%20mythology%20group%20traditional%20Chinese%20folklore&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 7. 老子传说
(7, 7, '1225', 'Ⅰ-131', '老子传说', '序号: 7\n项目序号: 1225\n编号: Ⅰ-131\n类别: 民间文学\n公布时间: 2014 (第四批)\n类型: 新增项目\n申报地区或单位: 河南省灵宝市\n保护单位: 灵宝市文化馆', '民间文学', '2014 (第四批)', '新增项目', '河南省灵宝市', '灵宝市文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Laozi%20legend%20traditional%20Chinese%20philosophy&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 8. 河图洛书传说
(8, 8, '1229', 'Ⅰ-135', '河图洛书传说', '序号: 8\n项目序号: 1229\n编号: Ⅰ-135\n类别: 民间文学\n公布时间: 2014 (第四批)\n类型: 新增项目\n申报地区或单位: 河南省洛阳市\n保护单位: 洛阳市文化馆（洛阳市非物质文化遗产保护中心）', '民间文学', '2014 (第四批)', '新增项目', '河南省洛阳市', '洛阳市文化馆（洛阳市非物质文化遗产保护中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Hetu%20Luoshu%20legend%20traditional%20Chinese%20mythology&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 9. 杞人忧天传说
(9, 9, '1230', 'Ⅰ-136', '杞人忧天传说', '序号: 9\n项目序号: 1230\n编号: Ⅰ-136\n类别: 民间文学\n公布时间: 2014 (第四批)\n类型: 新增项目\n申报地区或单位: 河南省杞县\n保护单位: 杞县人民文化馆（杞县送戏下乡艺术团）', '民间文学', '2014 (第四批)', '新增项目', '河南省杞县', '杞县人民文化馆（杞县送戏下乡艺术团）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Qiren%20youtian%20legend%20traditional%20Chinese%20folklore&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 10. 玄奘传说
(10, 10, '1374', 'Ⅰ-157', '玄奘传说', '序号: 10\n项目序号: 1374\n编号: Ⅰ-157\n类别: 民间文学\n公布时间: 2021 (第五批)\n类型: 新增项目\n申报地区或单位: 河南省洛阳市偃师区\n保护单位: 洛阳市偃师区人民文化馆', '民间文学', '2021 (第五批)', '新增项目', '河南省洛阳市偃师区', '洛阳市偃师区人民文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Xuanzang%20legend%20traditional%20Chinese%20Buddhist%20folklore&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 11. 唢呐艺术
(11, 11, '68', 'Ⅱ-37', '唢呐艺术', '序号: 11\n项目序号: 68\n编号: Ⅱ-37\n类别: 传统音乐\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省沁阳市\n保护单位: 沁阳市文化馆（沁阳市非物质文化遗产保中心）', '传统音乐', '2006 (第一批)', '新增项目', '河南省沁阳市', '沁阳市文化馆（沁阳市非物质文化遗产保中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Suona%20art%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 12. 板头曲
(12, 12, '77', 'Ⅱ-46', '板头曲', '序号: 12\n项目序号: 77\n编号: Ⅱ-46\n类别: 传统音乐\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省南阳市\n保护单位: 南阳市群众艺术馆（南阳市非物质文化遗产保护中心）', '传统音乐', '2006 (第一批)', '新增项目', '河南省南阳市', '南阳市群众艺术馆（南阳市非物质文化遗产保护中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Bantou%20qu%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 13. 信阳民歌
(13, 13, '578', 'Ⅱ-79', '信阳民歌', '序号: 13\n项目序号: 578\n编号: Ⅱ-79\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省信阳市\n保护单位: 信阳市群众艺术馆（信阳市非物质文化遗产保护中心）', '传统音乐', '2008 (第二批)', '新增项目', '河南省信阳市', '信阳市群众艺术馆（信阳市非物质文化遗产保护中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Xinyang%20folk%20songs%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 14. 西坪民歌
(14, 14, '579', 'Ⅱ-80', '西坪民歌', '序号: 14\n项目序号: 579\n编号: Ⅱ-80\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省西峡县\n保护单位: 西峡县人民文化馆', '传统音乐', '2008 (第二批)', '新增项目', '河南省西峡县', '西峡县人民文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Xiping%20folk%20songs%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 15. 江河号子（黄河号子）
(15, 15, '597', 'Ⅱ-98', '江河号子（黄河号子）', '序号: 15\n项目序号: 597\n编号: Ⅱ-98\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 黄河水利委员会河南黄河河务局\n保护单位: 河南省非物质文化遗产保护和智慧化中心', '传统音乐', '2008 (第二批)', '新增项目', '黄河水利委员会河南黄河河务局', '河南省非物质文化遗产保护和智慧化中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Yellow%20River%20work%20songs%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 16. 古筝艺术（中州筝派）
(16, 16, '619', 'Ⅱ-120', '古筝艺术（中州筝派）', '序号: 16\n项目序号: 619\n编号: Ⅱ-120\n类别: 传统音乐\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省\n保护单位: 河南省文化艺术研究院', '传统音乐', '2014 (第四批)', '扩展项目', '河南省', '河南省文化艺术研究院', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Zheng%20art%20central%20plain%20school%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 17. 笙管乐（超化吹歌）
(17, 17, '620', 'Ⅱ-121', '笙管乐（超化吹歌）', '序号: 17\n项目序号: 620\n编号: Ⅱ-121\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省新密市\n保护单位: 新密市文化馆（新密市非物质文化遗产保护中心）', '传统音乐', '2008 (第二批)', '新增项目', '河南省新密市', '新密市文化馆（新密市非物质文化遗产保护中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Chaohua%20wind%20and%20pipe%20music%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 18. 锣鼓艺术（大铜器）
(18, 18, '622', 'Ⅱ-123', '锣鼓艺术（大铜器）', '序号: 18\n项目序号: 622\n编号: Ⅱ-123\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省西平县\n保护单位: 西平县人民文化馆', '传统音乐', '2008 (第二批)', '新增项目', '河南省西平县', '西平县人民文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Gong%20and%20drum%20art%20traditional%20Chinese%20percussion&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 19. 锣鼓艺术（大铜器）
(19, 19, '622', 'Ⅱ-123', '锣鼓艺术（大铜器）', '序号: 19\n项目序号: 622\n编号: Ⅱ-123\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省郏县\n保护单位: 郏县文化馆', '传统音乐', '2008 (第二批)', '新增项目', '河南省郏县', '郏县文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Gong%20and%20drum%20art%20traditional%20Chinese%20percussion&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 20. 锣鼓艺术（开封盘鼓）
(20, 20, '622', 'Ⅱ-123', '锣鼓艺术（开封盘鼓）', '序号: 20\n项目序号: 622\n编号: Ⅱ-123\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省开封市\n保护单位: 开封市文化馆', '传统音乐', '2008 (第二批)', '新增项目', '河南省开封市', '开封市文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Kaifeng%20drum%20art%20traditional%20Chinese%20percussion&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 21. 锣鼓艺术（中州大鼓）
(21, 21, '622', 'Ⅱ-123', '锣鼓艺术（中州大鼓）', '序号: 21\n项目序号: 622\n编号: Ⅱ-123\n类别: 传统音乐\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省新乡县\n保护单位: 新乡县文化馆', '传统音乐', '2011 (第三批)', '扩展项目', '河南省新乡县', '新乡县文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Zhongzhou%20drum%20art%20traditional%20Chinese%20percussion&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 22. 锣鼓艺术（大铜器）
(22, 22, '622', 'Ⅱ-123', '锣鼓艺术（大铜器）', '序号: 22\n项目序号: 622\n编号: Ⅱ-123\n类别: 传统音乐\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省遂平县\n保护单位: 遂平县文化馆', '传统音乐', '2014 (第四批)', '扩展项目', '河南省遂平县', '遂平县文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Gong%20and%20drum%20art%20traditional%20Chinese%20percussion&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 23. 佛教音乐（大相国寺梵乐）
(23, 23, '637', 'Ⅱ-138', '佛教音乐（大相国寺梵乐）', '序号: 23\n项目序号: 637\n编号: Ⅱ-138\n类别: 传统音乐\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省开封市\n保护单位: 开封大相国寺', '传统音乐', '2008 (第二批)', '新增项目', '河南省开封市', '开封大相国寺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Buddhist%20music%20Daxiangguo%20Temple%20traditional%20Chinese%20music&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 24. 龙舞（火龙舞）
(24, 24, '107', 'Ⅲ-4', '龙舞（火龙舞）', '序号: 24\n项目序号: 107\n编号: Ⅲ-4\n类别: 传统舞蹈\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省孟州市\n保护单位: 孟州市文化馆（孟州市非物质文化遗产保护中心）', '传统舞蹈', '2011 (第三批)', '扩展项目', '河南省孟州市', '孟州市文化馆（孟州市非物质文化遗产保护中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Fire%20dragon%20dance%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 25. 狮舞（小相狮舞）
(25, 25, '108', 'Ⅲ-5', '狮舞（小相狮舞）', '序号: 25\n项目序号: 108\n编号: Ⅲ-5\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省巩义市\n保护单位: 巩义市文化和体育事业发展中心（巩义市体育馆、巩义市人民文化馆、巩义市图书馆）', '传统舞蹈', '2008 (第二批)', '扩展项目', '河南省巩义市', '巩义市文化和体育事业发展中心（巩义市体育馆、巩义市人民文化馆、巩义市图书馆）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Xiao%20Xiang%20lion%20dance%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 26. 狮舞（槐店文狮子）
(26, 26, '108', 'Ⅲ-5', '狮舞（槐店文狮子）', '序号: 26\n项目序号: 108\n编号: Ⅲ-5\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省沈丘县\n保护单位: 沈丘县回族文狮子文化协会', '传统舞蹈', '2008 (第二批)', '扩展项目', '河南省沈丘县', '沈丘县回族文狮子文化协会', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Huai%20Dian%20lion%20dance%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 27. 高跷（高抬火轿）
(27, 27, '112', 'Ⅲ-9', '高跷（高抬火轿）', '序号: 27\n项目序号: 112\n编号: Ⅲ-9\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省沁阳市\n保护单位: 沁阳市文化馆（沁阳市非物质文化遗产保护中心）', '传统舞蹈', '2008 (第二批)', '扩展项目', '河南省沁阳市', '沁阳市文化馆（沁阳市非物质文化遗产保护中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Stilt%20walking%20fire%20palanquin%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 28. 麒麟舞
(28, 28, '640', 'Ⅲ-43', '麒麟舞', '序号: 28\n项目序号: 640\n编号: Ⅲ-43\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省兰考县\n保护单位: 兰考县人民文化馆', '传统舞蹈', '2008 (第二批)', '新增项目', '河南省兰考县', '兰考县人民文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Kylin%20dance%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 29. 麒麟舞（睢县麒麟舞）
(29, 29, '640', 'Ⅲ-43', '麒麟舞（睢县麒麟舞）', '序号: 29\n项目序号: 640\n编号: Ⅲ-43\n类别: 传统舞蹈\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省睢县\n保护单位: 睢县人民文化馆（睢县非物质文化遗产保护中心）', '传统舞蹈', '2011 (第三批)', '扩展项目', '河南省睢县', '睢县人民文化馆（睢县非物质文化遗产保护中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Suixian%20kylin%20dance%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 30. 灯舞（苏家作龙凤灯舞）
(30, 30, '642', 'Ⅲ-45', '灯舞（苏家作龙凤灯舞）', '序号: 30\n项目序号: 642\n编号: Ⅲ-45\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省博爱县\n保护单位: 焦作市苏家作龙凤灯舞保护协会', '传统舞蹈', '2008 (第二批)', '新增项目', '河南省博爱县', '焦作市苏家作龙凤灯舞保护协会', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dragon%20and%20phoenix%20lantern%20dance%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 31. 跑帷子
(31, 31, '647', 'Ⅲ-50', '跑帷子', '序号: 31\n项目序号: 647\n编号: Ⅲ-50\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省汤阴县\n保护单位: 汤阴县文化馆', '传统舞蹈', '2008 (第二批)', '新增项目', '河南省汤阴县', '汤阴县文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Pao%20weizi%20traditional%20Chinese%20folk%20dance&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 32. 官会响锣
(32, 32, '648', 'Ⅲ-51', '官会响锣', '序号: 32\n项目序号: 648\n编号: Ⅲ-51\n类别: 传统舞蹈\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省项城市\n保护单位: 项城市文化馆（项城市非物质文化遗产保护中心）', '传统舞蹈', '2008 (第二批)', '新增项目', '河南省项城市', '项城市文化馆（项城市非物质文化遗产保护中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Guanhui%20gong%20and%20drum%20traditional%20Chinese%20percussion&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 33. 耍老虎
(33, 33, '1271', 'Ⅲ-118', '耍老虎', '序号: 33\n项目序号: 1271\n编号: Ⅲ-118\n类别: 传统舞蹈\n公布时间: 2014 (第四批)\n类型: 新增项目\n申报地区或单位: 河南省焦作市\n保护单位: 焦作市文化馆（焦作市非物质文化遗产保护中心）', '传统舞蹈', '2014 (第四批)', '新增项目', '河南省焦作市', '焦作市文化馆（焦作市非物质文化遗产保护中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Tiger%20play%20traditional%20Chinese%20folk%20dance&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 34. 豫剧
(34, 34, '167', 'Ⅳ-23', '豫剧', '序号: 34\n项目序号: 167\n编号: Ⅳ-23\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省\n保护单位: 河南省非物质文化遗产保护和智慧化中心', '传统戏剧', '2006 (第一批)', '新增项目', '河南省', '河南省非物质文化遗产保护和智慧化中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Yu%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 35. 宛梆
(35, 35, '168', 'Ⅳ-24', '宛梆', '序号: 35\n项目序号: 168\n编号: Ⅳ-24\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省内乡县\n保护单位: 内乡县宛梆艺术传承保护中心', '传统戏剧', '2006 (第一批)', '新增项目', '河南省内乡县', '内乡县宛梆艺术传承保护中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Wanbang%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 36. 怀梆
(36, 36, '169', 'Ⅳ-25', '怀梆', '序号: 36\n项目序号: 169\n编号: Ⅳ-25\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省沁阳市\n保护单位: 沁阳市怀梆艺术保护传承中心', '传统戏剧', '2006 (第一批)', '新增项目', '河南省沁阳市', '沁阳市怀梆艺术保护传承中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Huai%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 37. 大平调
(37, 37, '170', 'Ⅳ-26', '大平调', '序号: 37\n项目序号: 170\n编号: Ⅳ-26\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省濮阳县\n保护单位: 濮阳县大平调艺术保护传承中心', '传统戏剧', '2006 (第一批)', '新增项目', '河南省濮阳县', '濮阳县大平调艺术保护传承中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dapingdiao%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 38. 大平调
(38, 38, '170', 'Ⅳ-26', '大平调', '序号: 38\n项目序号: 170\n编号: Ⅳ-26\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省滑县\n保护单位: 滑县大平调剧团', '传统戏剧', '2006 (第一批)', '新增项目', '河南省滑县', '滑县大平调剧团', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dapingdiao%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 39. 大平调
(39, 39, '170', 'Ⅳ-26', '大平调', '序号: 39\n项目序号: 170\n编号: Ⅳ-26\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省延津县\n保护单位: 延津县大平调艺术传承保护中心', '传统戏剧', '2006 (第一批)', '新增项目', '河南省延津县', '延津县大平调艺术传承保护中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dapingdiao%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 40. 大平调
(40, 40, '170', 'Ⅳ-26', '大平调', '序号: 40\n项目序号: 170\n编号: Ⅳ-26\n类别: 传统戏剧\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省浚县\n保护单位: 浚县大平调传承保护中心', '传统戏剧', '2011 (第三批)', '扩展项目', '河南省浚县', '浚县大平调传承保护中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dapingdiao%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 41. 越调
(41, 41, '171', 'Ⅳ-27', '越调', '序号: 41\n项目序号: 171\n编号: Ⅳ-27\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省周口市\n保护单位: 河南省越调艺术保护传承中心', '传统戏剧', '2006 (第一批)', '新增项目', '河南省周口市', '河南省越调艺术保护传承中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Yue%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 42. 越调
(42, 42, '171', 'Ⅳ-27', '越调', '序号: 42\n项目序号: 171\n编号: Ⅳ-27\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省许昌市\n保护单位: 许昌市戏曲艺术发展中心（许昌市越调艺术保护传承中心）', '传统戏剧', '2008 (第二批)', '扩展项目', '河南省许昌市', '许昌市戏曲艺术发展中心（许昌市越调艺术保护传承中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Yue%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 43. 越调
(43, 43, '171', 'Ⅳ-27', '越调', '序号: 43\n项目序号: 171\n编号: Ⅳ-27\n类别: 传统戏剧\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省邓州市\n保护单位: 邓州市越调剧团', '传统戏剧', '2011 (第三批)', '扩展项目', '河南省邓州市', '邓州市越调剧团', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Yue%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 44. 柳子戏
(44, 44, '187', 'Ⅳ-43', '柳子戏', '序号: 44\n项目序号: 187\n编号: Ⅳ-43\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省清丰县\n保护单位: 清丰县柳子戏艺术传承中心', '传统戏剧', '2008 (第二批)', '扩展项目', '河南省清丰县', '清丰县柳子戏艺术传承中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Liuzi%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 45. 大弦戏
(45, 45, '188', 'Ⅳ-44', '大弦戏', '序号: 45\n项目序号: 188\n编号: Ⅳ-44\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省滑县\n保护单位: 滑县大弦戏剧团', '传统戏剧', '2006 (第一批)', '新增项目', '河南省滑县', '滑县大弦戏剧团', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Daxian%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 46. 大弦戏
(46, 46, '188', 'Ⅳ-44', '大弦戏', '序号: 46\n项目序号: 188\n编号: Ⅳ-44\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省濮阳县\n保护单位: 濮阳县大弦戏艺术保护传承中心', '传统戏剧', '2006 (第一批)', '新增项目', '河南省濮阳县', '濮阳县大弦戏艺术保护传承中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Daxian%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 47. 四平调
(47, 47, '194', 'Ⅳ-50', '四平调', '序号: 47\n项目序号: 194\n编号: Ⅳ-50\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省商丘市\n保护单位: 商丘市梁园区四平调艺术研究中心', '传统戏剧', '2006 (第一批)', '新增项目', '河南省商丘市', '商丘市梁园区四平调艺术研究中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Sipingdiao%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 48. 四平调
(48, 48, '194', 'Ⅳ-50', '四平调', '序号: 48\n项目序号: 194\n编号: Ⅳ-50\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省濮阳市\n保护单位: 范县四平调艺术传播研究中心', '传统戏剧', '2006 (第一批)', '新增项目', '河南省濮阳市', '范县四平调艺术传播研究中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Sipingdiao%20traditional%20Chinese%20opera&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 49. 曲剧
(49, 49, '212', 'Ⅳ-68', '曲剧', '序号: 49\n项目序号: 212\n编号: Ⅳ-68\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省\n保护单位: 河南省曲剧艺术保护传承中心', '传统戏剧', '2006 (第一批)', '新增项目', '河南省', '河南省曲剧艺术保护传承中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Qu%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 50. 道情戏（太康道情戏）
(50, 50, '215', 'Ⅳ-71', '道情戏（太康道情戏）', '序号: 50\n项目序号: 215\n编号: Ⅳ-71\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省太康县\n保护单位: 太康县道情艺术保护传承中心', '传统戏剧', '2006 (第一批)', '新增项目', '河南省太康县', '太康县道情艺术保护传承中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Daokqing%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 51. 目连戏（南乐目连戏）
(51, 51, '231', 'Ⅳ-87', '目连戏（南乐目连戏）', '序号: 51\n项目序号: 231\n编号: Ⅳ-87\n类别: 传统戏剧\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省南乐县\n保护单位: 南乐县非物质文化遗产保护中心', '传统戏剧', '2006 (第一批)', '新增项目', '河南省南乐县', '南乐县非物质文化遗产保护中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Mulian%20Opera%20traditional%20Chinese%20Buddhist%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 52. 皮影戏（罗山皮影戏）
(52, 52, '235', 'Ⅳ-91', '皮影戏（罗山皮影戏）', '序号: 52\n项目序号: 235\n编号: Ⅳ-91\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省罗山县\n保护单位: 罗山县文化馆', '传统戏剧', '2008 (第二批)', '扩展项目', '河南省罗山县', '罗山县文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luoshan%20shadow%20puppet%20theater%20traditional%20art&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 53. 皮影戏（桐柏皮影戏）
(53, 53, '235', 'Ⅳ-91', '皮影戏（桐柏皮影戏）', '序号: 53\n项目序号: 235\n编号: Ⅳ-91\n类别: 传统戏剧\n公布时间: 2011 (第三批)\n类型: 扩展项目\n申报地区或单位: 河南省桐柏县\n保护单位: 桐柏县人民文化馆', '传统戏剧', '2011 (第三批)', '扩展项目', '河南省桐柏县', '桐柏县人民文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Tongbai%20shadow%20puppet%20theater%20traditional%20art&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 54. 花鼓戏（光山花鼓戏）
(54, 54, '713', 'Ⅳ-112', '花鼓戏（光山花鼓戏）', '序号: 54\n项目序号: 713\n编号: Ⅳ-112\n类别: 传统戏剧\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省光山县\n保护单位: 光山县非物质文化遗产保护中心', '传统戏剧', '2014 (第四批)', '扩展项目', '河南省光山县', '光山县非物质文化遗产保护中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Guangshan%20flower%20drum%20opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 55. 二夹弦
(55, 55, '714', 'Ⅳ-113', '二夹弦', '序号: 55\n项目序号: 714\n编号: Ⅳ-113\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省开封市\n保护单位: 开封市田爱云二夹弦剧团', '传统戏剧', '2008 (第二批)', '新增项目', '河南省开封市', '开封市田爱云二夹弦剧团', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Erjiaxian%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 56. 二夹弦
(56, 56, '714', 'Ⅳ-113', '二夹弦', '序号: 56\n项目序号: 714\n编号: Ⅳ-113\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省滑县\n保护单位: 滑县二夹弦剧团', '传统戏剧', '2008 (第二批)', '新增项目', '河南省滑县', '滑县二夹弦剧团', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Erjiaxian%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 57. 罗卷戏
(57, 57, '724', 'Ⅳ-123', '罗卷戏', '序号: 57\n项目序号: 724\n编号: Ⅳ-123\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省汝南县\n保护单位: 汝南县罗卷戏艺术传承保护中心', '传统戏剧', '2008 (第二批)', '新增项目', '河南省汝南县', '汝南县罗卷戏艺术传承保护中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luojuan%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 58. 罗卷戏
(58, 58, '724', 'Ⅳ-123', '罗卷戏', '序号: 58\n项目序号: 724\n编号: Ⅳ-123\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省范县\n保护单位: 范县罗卷戏剧团', '传统戏剧', '2008 (第二批)', '新增项目', '河南省范县', '范县罗卷戏剧团', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luojuan%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 59. 罗卷戏
(59, 59, '724', 'Ⅳ-123', '罗卷戏', '序号: 59\n项目序号: 724\n编号: Ⅳ-123\n类别: 传统戏剧\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省邓州市\n保护单位: 邓州市孔庄罗卷戏剧团有限公司', '传统戏剧', '2014 (第四批)', '扩展项目', '河南省邓州市', '邓州市孔庄罗卷戏剧团有限公司', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luojuan%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 60. 二股弦
(60, 60, '725', 'Ⅳ-124', '二股弦', '序号: 60\n项目序号: 725\n编号: Ⅳ-124\n类别: 传统戏剧\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省武陟县\n保护单位: 武陟县大司马二股弦剧团', '传统戏剧', '2008 (第二批)', '新增项目', '河南省武陟县', '武陟县大司马二股弦剧团', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Erguxian%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 61. 淮调
(61, 61, '1109', 'Ⅳ-147', '淮调', '序号: 61\n项目序号: 1109\n编号: Ⅳ-147\n类别: 传统戏剧\n公布时间: 2011 (第三批)\n类型: 新增项目\n申报地区或单位: 河南省安阳县\n保护单位: 安阳县邺祥淮调艺术有限公司', '传统戏剧', '2011 (第三批)', '新增项目', '河南省安阳县', '安阳县邺祥淮调艺术有限公司', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Huai%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 62. 落腔
(62, 62, '1110', 'Ⅳ-148', '落腔', '序号: 62\n项目序号: 1110\n编号: Ⅳ-148\n类别: 传统戏剧\n公布时间: 2011 (第三批)\n类型: 新增项目\n申报地区或单位: 河南省内黄县\n保护单位: 内黄县文化馆', '传统戏剧', '2011 (第三批)', '新增项目', '河南省内黄县', '内黄县文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luqiang%20Opera%20traditional%20Chinese%20theater&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 63. 河洛大鼓
(63, 63, '248', 'Ⅴ-12', '河洛大鼓', '序号: 63\n项目序号: 248\n编号: Ⅴ-12\n类别: 曲艺\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省洛阳市\n保护单位: 洛阳市文化馆（洛阳市非物质文化遗产保护中心）', '曲艺', '2006 (第一批)', '新增项目', '河南省洛阳市', '洛阳市文化馆（洛阳市非物质文化遗产保护中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Heluo%20dagu%20traditional%20Chinese%20storytelling&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 64. 河南坠子
(64, 64, '256', 'Ⅴ-20', '河南坠子', '序号: 64\n项目序号: 256\n编号: Ⅴ-20\n类别: 曲艺\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省\n保护单位: 河南省非物质文化遗产保护和智慧化中心', '曲艺', '2006 (第一批)', '新增项目', '河南省', '河南省非物质文化遗产保护和智慧化中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Henan%20zhuizi%20traditional%20Chinese%20storytelling&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 65. 三弦书（南阳三弦书）
(65, 65, '757', 'Ⅴ-64', '三弦书（南阳三弦书）', '序号: 65\n项目序号: 757\n编号: Ⅴ-64\n类别: 曲艺\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省南阳市\n保护单位: 南阳市说唱团演艺有限公司', '曲艺', '2008 (第二批)', '新增项目', '河南省南阳市', '南阳市说唱团演艺有限公司', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Sanxian%20storytelling%20traditional%20Chinese%20folk%20art&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 66. 大调曲子
(66, 66, '762', 'Ⅴ-69', '大调曲子', '序号: 66\n项目序号: 762\n编号: Ⅴ-69\n类别: 曲艺\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省南阳市\n保护单位: 南阳曲剧艺术中心', '曲艺', '2008 (第二批)', '新增项目', '河南省南阳市', '南阳曲剧艺术中心', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Dadiao%20quzi%20traditional%20Chinese%20folk%20music&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 67. 陕州锣鼓书
(67, 67, '1434', 'Ⅴ-136', '陕州锣鼓书', '序号: 67\n项目序号: 1434\n编号: Ⅴ-136\n类别: 曲艺\n公布时间: 2021 (第五批)\n类型: 新增项目\n申报地区或单位: 河南省三门峡市\n保护单位: 三门峡市群众艺术馆', '曲艺', '2021 (第五批)', '新增项目', '河南省三门峡市', '三门峡市群众艺术馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Shanzhou%20gong%20and%20drum%20storytelling%20traditional%20Chinese%20folk%20art&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 68. 少林功夫
(68, 68, '289', 'Ⅵ-7', '少林功夫', '序号: 68\n项目序号: 289\n编号: Ⅵ-7\n类别: 传统体育、游艺与杂技\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省登封市\n保护单位: 中国嵩山少林寺', '传统体育、游艺与杂技', '2006 (第一批)', '新增项目', '河南省登封市', '中国嵩山少林寺', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Shaolin%20Kung%20Fu%20traditional%20Chinese%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 69. 太极拳（陈氏太极拳）
(69, 69, '293', 'Ⅵ-11', '太极拳（陈氏太极拳）', '序号: 69\n项目序号: 293\n编号: Ⅵ-11\n类别: 传统体育、游艺与杂技\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省焦作市\n保护单位: 焦作市文化馆（焦作市非物质文化遗产保护中心）', '传统体育、游艺与杂技', '2006 (第一批)', '新增项目', '河南省焦作市', '焦作市文化馆（焦作市非物质文化遗产保护中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Tai%20Chi%20Chen%20style%20traditional%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 70. 太极拳（和氏太极拳）
(70, 70, '293', 'Ⅵ-11', '太极拳（和氏太极拳）', '序号: 70\n项目序号: 293\n编号: Ⅵ-11\n类别: 传统体育、游艺与杂技\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省温县\n保护单位: 温县和式太极拳学会', '传统体育、游艺与杂技', '2014 (第四批)', '扩展项目', '河南省温县', '温县和式太极拳学会', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=He%20style%20Tai%20Chi%20traditional%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 71. 八极拳（月山八极拳）
(71, 71, '800', 'Ⅵ-28', '八极拳（月山八极拳）', '序号: 71\n项目序号: 800\n编号: Ⅵ-28\n类别: 传统体育、游艺与杂技\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省博爱县\n保护单位: 博爱县八极文武学校', '传统体育、游艺与杂技', '2008 (第二批)', '新增项目', '河南省博爱县', '博爱县八极文武学校', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Bajiquan%20traditional%20Chinese%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 72. 心意六合拳
(72, 72, '802', 'Ⅵ-30', '心意六合拳', '序号: 72\n项目序号: 802\n编号: Ⅵ-30\n类别: 传统体育、游艺与杂技\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省漯河市\n保护单位: 漯河市心意六和拳研究会', '传统体育、游艺与杂技', '2008 (第二批)', '新增项目', '河南省漯河市', '漯河市心意六和拳研究会', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Xinyi%20Liuhe%20quan%20traditional%20Chinese%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 73. 心意六合拳
(73, 73, '802', 'Ⅵ-30', '心意六合拳', '序号: 73\n项目序号: 802\n编号: Ⅵ-30\n类别: 传统体育、游艺与杂技\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省周口市\n保护单位: 周口市心意六合拳协会', '传统体育、游艺与杂技', '2008 (第二批)', '新增项目', '河南省周口市', '周口市心意六合拳协会', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Xinyi%20Liuhe%20quan%20traditional%20Chinese%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 74. 苌家拳
(74, 74, '806', 'Ⅵ-34', '苌家拳', '序号: 74\n项目序号: 806\n编号: Ⅵ-34\n类别: 传统体育、游艺与杂技\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省荥阳市\n保护单位: 荥阳苌家拳研究会', '传统体育、游艺与杂技', '2008 (第二批)', '新增项目', '河南省荥阳市', '荥阳苌家拳研究会', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Chang%20family%20martial%20arts%20traditional%20Chinese%20kung%20fu&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 75. 东北庄杂技
(75, 75, '821', 'Ⅵ-49', '东北庄杂技', '序号: 75\n项目序号: 821\n编号: Ⅵ-49\n类别: 传统体育、游艺与杂技\n公布时间: 2008 (第二批)\n类型: 新增项目\n申报地区或单位: 河南省濮阳市\n保护单位: 濮阳市华龙区东北庄杂技艺术学校', '传统体育、游艺与杂技', '2008 (第二批)', '新增项目', '河南省濮阳市', '濮阳市华龙区东北庄杂技艺术学校', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Northeast%20Village%20acrobatic%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 76. 撂石锁
(76, 76, '1147', 'Ⅵ-64', '撂石锁', '序号: 76\n项目序号: 1147\n编号: Ⅵ-64\n类别: 传统体育、游艺与杂技\n公布时间: 2011 (第三批)\n类型: 新增项目\n申报地区或单位: 河南省开封市\n保护单位: 开封市文化馆', '传统体育、游艺与杂技', '2011 (第三批)', '新增项目', '河南省开封市', '开封市文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Stone%20lock%20throwing%20traditional%20Chinese%20martial%20arts&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 77. 幻术（宝丰魔术）
(77, 77, '1313', 'Ⅵ-82', '幻术（宝丰魔术）', '序号: 77\n项目序号: 1313\n编号: Ⅵ-82\n类别: 传统体育、游艺与杂技\n公布时间: 2021 (第五批)\n类型: 扩展项目\n申报地区或单位: 河南省平顶山市宝丰县\n保护单位: 宝丰县人民文化馆', '传统体育、游艺与杂技', '2021 (第五批)', '扩展项目', '河南省平顶山市宝丰县', '宝丰县人民文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Baofeng%20magic%20tricks%20traditional%20Chinese%20performance&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 78. 朱仙镇木版年画
(78, 78, '306', 'Ⅶ-7', '朱仙镇木版年画', '序号: 78\n项目序号: 306\n编号: Ⅶ-7\n类别: 传统美术\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省开封市\n保护单位: 开封市文化馆', '传统美术', '2006 (第一批)', '新增项目', '河南省开封市', '开封市文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Zhuxianzhen%20woodblock%20new%20year%20paintings%20traditional%20Chinese%20art&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 79. 剪纸（灵宝剪纸）
(79, 79, '315', 'Ⅶ-16', '剪纸（灵宝剪纸）', '序号: 79\n项目序号: 315\n编号: Ⅶ-16\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省灵宝市\n保护单位: 灵宝市文化馆', '传统美术', '2008 (第二批)', '扩展项目', '河南省灵宝市', '灵宝市文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Lingbao%20paper%20cutting%20traditional%20Chinese%20art&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 80. 剪纸（卢氏剪纸）
(80, 80, '315', 'Ⅶ-16', '剪纸（卢氏剪纸）', '序号: 80\n项目序号: 315\n编号: Ⅶ-16\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省卢氏县\n保护单位: 卢氏县人民文化馆', '传统美术', '2008 (第二批)', '扩展项目', '河南省卢氏县', '卢氏县人民文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Lushi%20paper%20cutting%20traditional%20Chinese%20art&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 81. 剪纸（辉县剪纸）
(81, 81, '315', 'Ⅶ-16', '剪纸（辉县剪纸）', '序号: 81\n项目序号: 315\n编号: Ⅶ-16\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省辉县市\n保护单位: 辉县市文化馆', '传统美术', '2008 (第二批)', '扩展项目', '河南省辉县市', '辉县市文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Huixian%20paper%20cutting%20traditional%20Chinese%20art&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 82. 泥塑（浚县泥咕咕）
(82, 82, '346', 'Ⅶ-47', '泥塑（浚县泥咕咕）', '序号: 82\n项目序号: 346\n编号: Ⅶ-47\n类别: 传统美术\n公布时间: 2006 (第一批)\n类型: 新增项目\n申报地区或单位: 河南省浚县\n保护单位: 浚县人民文化馆', '传统美术', '2006 (第一批)', '新增项目', '河南省浚县', '浚县人民文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Junxian%20clay%20figurines%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 83. 泥塑（淮阳泥泥狗）
(83, 83, '346', 'Ⅶ-47', '泥塑（淮阳泥泥狗）', '序号: 83\n项目序号: 346\n编号: Ⅶ-47\n类别: 传统美术\n公布时间: 2014 (第四批)\n类型: 扩展项目\n申报地区或单位: 河南省淮阳县\n保护单位: 淮阳县文化馆', '传统美术', '2014 (第四批)', '扩展项目', '河南省淮阳县', '淮阳县文化馆', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Huaiyang%20clay%20dogs%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 84. 灯彩（洛阳宫灯）
(84, 84, '349', 'Ⅶ-50', '灯彩（洛阳宫灯）', '序号: 84\n项目序号: 349\n编号: Ⅶ-50\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省洛阳市\n保护单位: 洛阳市文化馆（洛阳市非物质文化遗产保护中心）', '传统美术', '2008 (第二批)', '扩展项目', '河南省洛阳市', '洛阳市文化馆（洛阳市非物质文化遗产保护中心）', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Luoyang%20palace%20lanterns%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW()),
-- 85. 灯彩（汴京灯笼张）
(85, 85, '349', 'Ⅶ-50', '灯彩（汴京灯笼张）', '序号: 85\n项目序号: 349\n编号: Ⅶ-50\n类别: 传统美术\n公布时间: 2008 (第二批)\n类型: 扩展项目\n申报地区或单位: 河南省开封市\n保护单位: 开封市彩灯艺术研究会', '传统美术', '2008 (第二批)', '扩展项目', '河南省开封市', '开封市彩灯艺术研究会', 4, 1, 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Bianjing%20Lantern%20Zhang%20traditional%20Chinese%20craft&image_size=landscape_16_9', '', 1, NOW(), NOW())
ON DUPLICATE KEY UPDATE
id = VALUES(id),
serial_number = VALUES(serial_number),
project_serial_number = VALUES(project_serial_number),
project_code = VALUES(project_code),
title = VALUES(title),
description = VALUES(description),
category_name = VALUES(category_name),
announcement_time = VALUES(announcement_time),
project_type = VALUES(project_type),
declaration_region = VALUES(declaration_region),
protection_unit = VALUES(protection_unit),
category_id = VALUES(category_id),
region_id = VALUES(region_id),
cover_image = VALUES(cover_image),
video_url = VALUES(video_url),
status = VALUES(status),
update_time = NOW();

SET FOREIGN_KEY_CHECKS = 1;\