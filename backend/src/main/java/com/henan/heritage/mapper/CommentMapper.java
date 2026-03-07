package com.henan.heritage.mapper;

import com.henan.heritage.entity.HeritageComment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface CommentMapper {
    @Select("SELECT * FROM heritage_comment WHERE heritage_id = #{heritageId} ORDER BY create_time DESC")
    List<HeritageComment> selectByHeritageId(Long heritageId);

    @Select("SELECT * FROM heritage_comment WHERE media_id = #{mediaId} ORDER BY create_time DESC")
    List<HeritageComment> selectByMediaId(Long mediaId);

    @Select("SELECT * FROM heritage_comment WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<HeritageComment> selectByUserId(Long userId);

    @Select("SELECT * FROM heritage_comment ORDER BY create_time DESC")
    List<HeritageComment> selectAll();

    @Insert("INSERT INTO heritage_comment (user_id, heritage_id, media_id, content, create_time) VALUES (#{userId}, #{heritageId}, #{mediaId}, #{content}, NOW())")
    void insert(HeritageComment comment);

    @Delete("DELETE FROM heritage_comment WHERE id = #{id}")
    void delete(Long id);
}
