package com.henan.heritage.service.impl;

import com.henan.heritage.entity.HeritageFavorite;
import com.henan.heritage.mapper.FavoriteMapper;
import com.henan.heritage.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public List<HeritageFavorite> listByUserId(Long userId) {
        return favoriteMapper.selectByUserId(userId);
    }

    @Override
    public Long add(HeritageFavorite favorite) {
        favoriteMapper.insert(favorite);
        return favorite.getId();
    }

    @Override
    public void delete(Long id) {
        favoriteMapper.delete(id);
    }

    @Override
    public boolean checkFavorite(Long userId, Long heritageId, Long mediaId) {
        if (heritageId != null) {
            HeritageFavorite favorite = favoriteMapper.selectByUserAndHeritage(userId, heritageId);
            return favorite != null;
        } else if (mediaId != null) {
            HeritageFavorite favorite = favoriteMapper.selectByUserAndMedia(userId, mediaId);
            return favorite != null;
        }
        return false;
    }

    @Override
    public List<HeritageFavorite> listAll() {
        return favoriteMapper.selectAll();
    }

    @Override
    public void batchDelete(List<Long> ids) {
        for (Long id : ids) {
            favoriteMapper.delete(id);
        }
    }
}
