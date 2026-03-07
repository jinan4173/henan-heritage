package com.henan.heritage.mapper;

import com.henan.heritage.entity.HeritageComment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface CommentMapper {
    @Select("SELECT c.*, u.username FROM heritage_comment c LEFT JOIN user u ON c.user_id = u.id WHERE c.heritage_id = #{heritageId} ORDER BY c.create_time DESC")
    List<HeritageComment> selectByHeritageId(Long heritageId);

    @Select("SELECT c.*, u.username FROM heritage_comment c LEFT JOIN user u ON c.user_id = u.id WHERE c.media_id = #{mediaId} ORDER BY c.create_time DESC")
    List<HeritageComment> selectByMediaId(Long mediaId);

    @Select("SELECT c.*, u.username FROM heritage_comment c LEFT JOIN user u ON c.user_id = u.id WHERE c.user_id = #{userId} ORDER BY c.create_time DESC")
    List<HeritageComment> selectByUserId(Long userId);

    @Select("SELECT c.*, u.username FROM heritage_comment c LEFT JOIN user u ON c.user_id = u.id ORDER BY c.create_time DESC")
    List<HeritageComment> selectAll();

    @Insert("INSERT INTO heritage_comment (user_id, heritage_id, media_id, content, create_time) VALUES (#{userId}, #{heritageId}, #{mediaId}, #{content}, NOW())")
    void insert(HeritageComment comment);

    @Delete("DELETE FROM heritage_comment WHERE id = #{id}")
    void delete(Long id);
}
