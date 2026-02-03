package com.henan.heritage.service.impl;

import com.henan.heritage.entity.News;
import com.henan.heritage.mapper.NewsMapper;
import com.henan.heritage.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> listAll(Integer status) {
        // 从数据库获取数据
        System.out.println("从数据库获取新闻列表");
        List<News> newsList = newsMapper.selectList(status);
        System.out.println("从数据库获取新闻数量: " + newsList.size());
        return newsList;
    }

    @Override
    public News getById(Long id) {
        // 从数据库获取
        News news = newsMapper.selectById(id);
        if (news != null) {
            // 检查数据是否有乱码（如果标题包含问号，说明有乱码）
            if (news.getTitle() != null && !news.getTitle().contains("?")) {
                return news;
            }
        }
        return null;
    }

    @Override
    public void save(News news) {
        System.out.println("保存新闻: " + news);
        newsMapper.insert(news);
        System.out.println("保存新闻成功");
    }

    @Override
    public void update(News news) {
        System.out.println("更新新闻: " + news);
        newsMapper.update(news);
        System.out.println("更新新闻成功");
    }

    @Override
    public void delete(Long id) {
        System.out.println("删除新闻: " + id);
        newsMapper.delete(id);
        System.out.println("删除新闻成功");
    }

    @Override
    public long count() {
        // 从数据库获取
        return newsMapper.count();
    }
}
