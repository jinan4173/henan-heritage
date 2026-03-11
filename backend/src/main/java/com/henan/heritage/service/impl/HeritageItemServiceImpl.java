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
import java.util.HashMap;

/**
 * 非遗项目服务实现类
 * 负责处理非遗项目的业务逻辑
 */
@Service
public class HeritageItemServiceImpl implements HeritageItemService {

    @Autowired
    private HeritageItemMapper heritageItemMapper;

    @Autowired
    private HeritageMediaService heritageMediaService;

    /**
     * 获取非遗项目列表
     * @param status 状态，为null时获取所有状态的项目
     * @return 非遗项目列表
     */
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

    /**
     * 根据ID获取非遗项目详情
     * @param id 项目ID
     * @return 非遗项目详情
     */
    @Override
    public HeritageItem getById(Long id) {
        System.out.println("获取非遗项目详情，ID: " + id);
        try {
            // 从数据库获取
            HeritageItem item = heritageItemMapper.selectByIdWithDetails(id);
            if (item != null) {
                System.out.println("获取到的项目信息: " + item);
                System.out.println("项目标题: " + item.getTitle());
                System.out.println("项目介绍: " + item.getDescription());
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
            e.printStackTrace();
            // 即使获取失败，也返回 null，让控制器处理
        }
        return null;
    }

    /**
     * 根据分类和地区筛选非遗项目
     * @param categoryId 分类ID
     * @param regionId 地区ID
     * @param status 状态
     * @return 筛选后的非遗项目列表
     */
    @Override
    public List<HeritageItem> listByCategoryAndRegion(Long categoryId, Long regionId, Integer status) {
        // 从数据库获取
        List<HeritageItem> items = heritageItemMapper.selectByCategoryAndRegion(categoryId, regionId, status);
        return items;
    }

    /**
     * 保存非遗项目
     * @param item 非遗项目信息
     */
    @Override
    public void save(HeritageItem item) {
        System.out.println("保存非遗项目: " + item);
        heritageItemMapper.insert(item);
        System.out.println("保存非遗项目成功");
    }

    /**
     * 更新非遗项目
     * @param item 非遗项目信息
     */
    @Override
    public void update(HeritageItem item) {
        System.out.println("更新非遗项目: " + item);
        heritageItemMapper.update(item);
        System.out.println("更新非遗项目成功");
    }

    /**
     * 删除非遗项目
     * @param id 项目ID
     */
    @Override
    public void delete(Long id) {
        System.out.println("删除非遗项目: " + id);
        // 先删除关联的媒体资源
        heritageMediaService.deleteByHeritageItemId(id);
        // 再删除非遗项目
        heritageItemMapper.delete(id);
        System.out.println("删除非遗项目成功");
    }

    /**
     * 获取非遗项目总数
     * @return 非遗项目总数
     */
    @Override
    public long count() {
        // 直接从数据库获取计数
        System.out.println("从数据库获取非遗项目数量");
        long count = heritageItemMapper.count();
        System.out.println("数据库中非遗项目数量: " + count);
        return count;
    }

    /**
     * 获取地市非遗项目统计
     * @return 地市统计数据列表
     */
    @Override
    public List<Map<String, Object>> getCityStatistics() {
        System.out.println("获取地市非遗项目统计");
        List<Map<String, Object>> statistics = heritageItemMapper.selectCityStatistics();
        System.out.println("获取到 " + statistics.size() + " 个地市的统计数据");
        return statistics;
    }

    /**
     * 获取分类非遗项目统计
     * @return 分类统计数据列表
     */
    @Override
    public List<Map<String, Object>> getCategoryStatistics() {
        System.out.println("获取分类非遗项目统计");
        List<Map<String, Object>> statistics = heritageItemMapper.selectCategoryStatistics();
        System.out.println("获取到 " + statistics.size() + " 个分类的统计数据");
        return statistics;
    }

    /**
     * 保存媒体资源
     * @param media 媒体资源信息
     */
    @Override
    public void saveMedia(HeritageMedia media) {
        System.out.println("保存媒体资源: " + media);
        heritageMediaService.save(media);
        System.out.println("保存媒体资源成功");
    }

    /**
     * 根据非遗项目ID删除媒体资源
     * @param heritageItemId 非遗项目ID
     */
    @Override
    public void deleteMediaByHeritageId(Long heritageItemId) {
        System.out.println("根据非遗项目ID删除媒体资源: " + heritageItemId);
        heritageMediaService.deleteByHeritageItemId(heritageItemId);
        System.out.println("删除媒体资源成功");
    }

    /**
     * 获取热门非遗项目排行
     * @param limit 限制返回数量
     * @return 热门非遗项目列表
     */
    @Override
    public List<Map<String, Object>> getPopularHeritage(int limit) {
        System.out.println("获取热门非遗项目排行，限制: " + limit);
        try {
            // 按照访问量和收藏数的综合得分来排序
            List<HeritageItem> items = heritageItemMapper.selectPopular(limit);
            List<Map<String, Object>> result = new ArrayList<>();
            
            for (HeritageItem item : items) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", item.getId());
                map.put("title", item.getTitle());
                map.put("categoryName", item.getCategoryName());
                map.put("regionName", item.getRegionName());
                map.put("description", item.getDescription());
                map.put("coverImage", item.getCoverImage());
                // 计算热度值（访问量 + 收藏数 * 2）
                int viewCount = item.getViewCount() != null ? item.getViewCount() : 0;
                int favoriteCount = item.getFavoriteCount() != null ? item.getFavoriteCount() : 0;
                int popularity = viewCount + favoriteCount * 2;
                map.put("popularity", popularity);
                result.add(map);
            }
            
            System.out.println("获取到 " + result.size() + " 个热门非遗项目");
            return result;
        } catch (Exception e) {
            System.out.println("获取热门非遗项目失败: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void incrementViewCount(Long id) {
        try {
            heritageItemMapper.incrementViewCount(id);
            System.out.println("增加非遗项目访问量，ID: " + id);
        } catch (Exception e) {
            System.out.println("增加访问量失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void incrementFavoriteCount(Long id) {
        try {
            heritageItemMapper.incrementFavoriteCount(id);
            System.out.println("增加非遗项目收藏数，ID: " + id);
        } catch (Exception e) {
            System.out.println("增加收藏数失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void decrementFavoriteCount(Long id) {
        try {
            heritageItemMapper.decrementFavoriteCount(id);
            System.out.println("减少非遗项目收藏数，ID: " + id);
        } catch (Exception e) {
            System.out.println("减少收藏数失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}