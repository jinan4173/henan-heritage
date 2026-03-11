package com.henan.heritage.mapper;

import com.henan.heritage.entity.HeritageFavorite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface FavoriteMapper {
    @Select("SELECT * FROM heritage_favorite WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<HeritageFavorite> selectByUserId(Long userId);

    @Select("SELECT * FROM heritage_favorite ORDER BY create_time DESC")
    List<HeritageFavorite> selectAll();

    @Select("SELECT * FROM heritage_favorite WHERE user_id = #{userId} AND heritage_id = #{heritageId}")
    HeritageFavorite selectByUserAndHeritage(Long userId, Long heritageId);

    @Select("SELECT * FROM heritage_favorite WHERE user_id = #{userId} AND media_id = #{mediaId}")
    HeritageFavorite selectByUserAndMedia(Long userId, Long mediaId);

    @Insert("INSERT INTO heritage_favorite (user_id, heritage_id, media_id, create_time) VALUES (#{userId}, #{heritageId}, #{mediaId}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(HeritageFavorite favorite);

    @Delete("DELETE FROM heritage_favorite WHERE id = #{id}")
    void delete(Long id);
    
    @Select("SELECT * FROM heritage_favorite WHERE id = #{id}")
    HeritageFavorite selectById(Long id);
}
