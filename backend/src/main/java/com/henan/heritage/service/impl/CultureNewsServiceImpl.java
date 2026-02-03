package com.henan.heritage.service.impl;

import com.henan.heritage.entity.CultureNews;
import com.henan.heritage.mapper.CultureNewsMapper;
import com.henan.heritage.service.CultureNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CultureNewsServiceImpl implements CultureNewsService {

    @Autowired
    private CultureNewsMapper cultureNewsMapper;

    @Override
    public List<CultureNews> listAll(Integer type, Integer status) {
        System.out.println("从数据库获取文化资讯列表，类型: " + type + "，状态: " + status);
        List<CultureNews> items = cultureNewsMapper.selectList(type, status);
        System.out.println("从数据库获取文化资讯数量: " + items.size());
        return items;
    }

    @Override
    public CultureNews getById(Long id) {
        System.out.println("获取文化资讯详情，ID: " + id);
        return cultureNewsMapper.selectById(id);
    }

    @Override
    public void save(CultureNews cultureNews) {
        System.out.println("保存文化资讯: " + cultureNews);
        cultureNewsMapper.insert(cultureNews);
        System.out.println("保存文化资讯成功");
    }

    @Override
    public void update(CultureNews cultureNews) {
        System.out.println("更新文化资讯: " + cultureNews);
        cultureNewsMapper.update(cultureNews);
        System.out.println("更新文化资讯成功");
    }

    @Override
    public void delete(Long id) {
        System.out.println("删除文化资讯: " + id);
        cultureNewsMapper.delete(id);
        System.out.println("删除文化资讯成功");
    }

    @Override
    public long count(Integer type, Integer status) {
        System.out.println("从数据库获取文化资讯数量，类型: " + type + "，状态: " + status);
        long count = cultureNewsMapper.count(type, status);
        System.out.println("数据库中文化资讯数量: " + count);
        return count;
    }
}
