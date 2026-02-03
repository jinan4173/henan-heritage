package com.henan.heritage.service.impl;

import com.henan.heritage.entity.HeritageMedia;
import com.henan.heritage.mapper.HeritageMediaMapper;
import com.henan.heritage.service.HeritageMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HeritageMediaServiceImpl implements HeritageMediaService {

    @Autowired
    private HeritageMediaMapper heritageMediaMapper;

    @Override
    public List<HeritageMedia> getByHeritageItemId(Long heritageItemId) {
        System.out.println("根据非遗项目ID查询媒体资源: " + heritageItemId);
        List<HeritageMedia> mediaList = heritageMediaMapper.selectByHeritageItemId(heritageItemId);
        System.out.println("查询结果数量: " + (mediaList != null ? mediaList.size() : 0));
        return mediaList;
    }

    @Override
    public HeritageMedia getById(Long id) {
        System.out.println("根据ID查询媒体资源: " + id);
        HeritageMedia media = heritageMediaMapper.selectById(id);
        System.out.println("查询结果: " + media);
        return media;
    }

    @Override
    public void save(HeritageMedia heritageMedia) {
        System.out.println("保存媒体资源: " + heritageMedia);
        heritageMediaMapper.insert(heritageMedia);
        System.out.println("保存成功");
    }

    @Override
    public void update(HeritageMedia heritageMedia) {
        System.out.println("更新媒体资源: " + heritageMedia);
        heritageMediaMapper.update(heritageMedia);
        System.out.println("更新成功");
    }

    @Override
    public void delete(Long id) {
        System.out.println("删除媒体资源: " + id);
        heritageMediaMapper.delete(id);
        System.out.println("删除成功");
    }

    @Override
    public void deleteByHeritageItemId(Long heritageItemId) {
        System.out.println("根据非遗项目ID删除媒体资源: " + heritageItemId);
        heritageMediaMapper.deleteByHeritageItemId(heritageItemId);
        System.out.println("删除成功");
    }
}
