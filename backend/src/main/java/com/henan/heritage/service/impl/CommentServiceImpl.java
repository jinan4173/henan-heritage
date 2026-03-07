package com.henan.heritage.service.impl;

import com.henan.heritage.entity.HeritageComment;
import com.henan.heritage.mapper.CommentMapper;
import com.henan.heritage.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<HeritageComment> listByHeritageId(Long heritageId) {
        return commentMapper.selectByHeritageId(heritageId);
    }

    @Override
    public List<HeritageComment> listByMediaId(Long mediaId) {
        return commentMapper.selectByMediaId(mediaId);
    }

    @Override
    public List<HeritageComment> listByUserId(Long userId) {
        return commentMapper.selectByUserId(userId);
    }

    @Override
    public Long add(HeritageComment comment) {
        commentMapper.insert(comment);
        return comment.getId();
    }

    @Override
    public void delete(Long id) {
        commentMapper.delete(id);
    }

    @Override
    public List<HeritageComment> listAll() {
        return commentMapper.selectAll();
    }

    @Override
    public void batchDelete(List<Long> ids) {
        for (Long id : ids) {
            commentMapper.delete(id);
        }
    }
}
