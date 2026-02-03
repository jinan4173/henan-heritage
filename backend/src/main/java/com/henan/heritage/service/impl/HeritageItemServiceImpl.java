package com.henan.heritage.service.impl;

import com.henan.heritage.entity.HeritageItem;
import com.henan.heritage.entity.HeritageMedia;
import com.henan.heritage.mapper.HeritageItemMapper;
import com.henan.heritage.service.HeritageItemService;
import com.henan.heritage.service.HeritageMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Service
public class HeritageItemServiceImpl implements HeritageItemService {

    @Autowired
    private HeritageItemMapper heritageItemMapper;

    @Autowired
    private HeritageMediaService heritageMediaService;

    @Override
    public List<HeritageItem> listAll(Integer status) {
        System.out.println("从数据库获取非遗项目列表，状态: " + status);
        try {
            // 当 status 为 null 时，获取所有状态的项目
            List<HeritageItem> items = heritageItemMapper.selectWithDetails(status);
            System.out.println("从数据库获取非遗项目数量: " + items.size());
            
            // 为每个项目加载媒体资源列表
            for (HeritageItem item : items) {
                if (item != null && item.getId() != null) {
                    try {
                        List<HeritageMedia> mediaList = heritageMediaService.getByHeritageItemId(item.getId());
                        item.setMediaList(mediaList);
                        System.out.println("为项目 " + item.getId() + " 加载了 " + (mediaList != null ? mediaList.size() : 0) + " 个媒体资源");
                    } catch (Exception e) {
                        System.out.println("加载项目 " + item.getId() + " 的媒体资源失败: " + e.getMessage());
                    }
                }
            }
            
            return items;
        } catch (Exception e) {
            System.out.println("获取非遗项目列表失败: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public HeritageItem getById(Long id) {
        System.out.println("获取非遗项目详情，ID: " + id);
        try {
            // 从数据库获取
            HeritageItem item = heritageItemMapper.selectByIdWithDetails(id);
            if (item != null) {
                // 加载媒体资源列表（添加异常处理，确保即使媒体资源加载失败也能返回项目信息）
                try {
                    List<HeritageMedia> mediaList = heritageMediaService.getByHeritageItemId(id);
                    item.setMediaList(mediaList);
                    System.out.println("加载了 " + (mediaList != null ? mediaList.size() : 0) + " 个媒体资源");
                } catch (Exception e) {
                    System.out.println("加载媒体资源失败: " + e.getMessage());
                    // 即使媒体资源加载失败，也返回项目基本信息
                    item.setMediaList(new ArrayList<>());
                }
                return item;
            }
        } catch (Exception e) {
            System.out.println("获取非遗项目详情失败: " + e.getMessage());
            // 即使获取失败，也返回 null，让控制器处理
        }
        return null;
    }

    @Override
    public List<HeritageItem> listByCategoryAndRegion(Long categoryId, Long regionId, Integer status) {
        // 从数据库获取
        List<HeritageItem> items = heritageItemMapper.selectByCategoryAndRegion(categoryId, regionId, status);
        if (!items.isEmpty()) {
            // 检查数据是否有乱码（如果标题包含问号，说明有乱码）
            if (items.get(0).getTitle() != null && !items.get(0).getTitle().contains("?")) {
                return items;
            }
        }
        return items;
    }

    @Override
    public void save(HeritageItem item) {
        System.out.println("保存非遗项目: " + item);
        heritageItemMapper.insert(item);
        System.out.println("保存非遗项目成功");
    }

    @Override
    public void update(HeritageItem item) {
        System.out.println("更新非遗项目: " + item);
        heritageItemMapper.update(item);
        System.out.println("更新非遗项目成功");
    }

    @Override
    public void delete(Long id) {
        System.out.println("删除非遗项目: " + id);
        // 先删除关联的媒体资源
        heritageMediaService.deleteByHeritageItemId(id);
        // 再删除非遗项目
        heritageItemMapper.delete(id);
        System.out.println("删除非遗项目成功");
    }

    @Override
    public long count() {
        // 直接从数据库获取计数
        System.out.println("从数据库获取非遗项目数量");
        long count = heritageItemMapper.count();
        System.out.println("数据库中非遗项目数量: " + count);
        return count;
    }

    @Override
    public List<Map<String, Object>> getCityStatistics() {
        System.out.println("获取地市非遗项目统计");
        List<Map<String, Object>> statistics = heritageItemMapper.selectCityStatistics();
        System.out.println("获取到 " + statistics.size() + " 个地市的统计数据");
        return statistics;
    }

    @Override
    public void saveMedia(HeritageMedia media) {
        System.out.println("保存媒体资源: " + media);
        heritageMediaService.save(media);
        System.out.println("保存媒体资源成功");
    }

    @Override
    public void deleteMediaByHeritageId(Long heritageItemId) {
        System.out.println("根据非遗项目ID删除媒体资源: " + heritageItemId);
        heritageMediaService.deleteByHeritageItemId(heritageItemId);
        System.out.println("删除媒体资源成功");
    }
}