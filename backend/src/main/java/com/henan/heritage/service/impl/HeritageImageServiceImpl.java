package com.henan.heritage.service.impl;

import com.henan.heritage.entity.HeritageImage;
import com.henan.heritage.mapper.HeritageImageMapper;
import com.henan.heritage.service.HeritageImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HeritageImageServiceImpl implements HeritageImageService {

    @Autowired
    private HeritageImageMapper heritageImageMapper;

    @Override
    public List<HeritageImage> listByHeritageId(Long heritageId) {
        return heritageImageMapper.selectByHeritageId(heritageId);
    }

    @Override
    public void save(HeritageImage image) {
        heritageImageMapper.insert(image);
    }

    @Override
    public void delete(Long id) {
        heritageImageMapper.delete(id);
    }

    @Override
    public void deleteByHeritageId(Long heritageId) {
        // 这里可以使用MyBatis的条件删除
        // 为了简化，我们直接使用Mapper接口
        // 实际项目中应该在Mapper中添加相应的方法
        List<HeritageImage> images = heritageImageMapper.selectByHeritageId(heritageId);
        for (HeritageImage image : images) {
            heritageImageMapper.delete(image.getId());
        }
    }
}