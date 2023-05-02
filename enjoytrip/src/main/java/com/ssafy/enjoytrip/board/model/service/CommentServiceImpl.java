package com.ssafy.enjoytrip.board.model.service;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.Comment;
import com.ssafy.enjoytrip.board.model.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    static final int TYPE = 300;

    private final CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentList(int boardId) throws SQLException {
        return commentMapper.selectComment(boardId);
    }

    @Override
    public void createComment(Comment comment) throws SQLException {
        commentMapper.insertComment(comment);
    }

    @Override
    public void updateComment(Comment comment) throws SQLException {
        commentMapper.updateComment(comment);
    }

    @Override
    public void deleteComment(int commentId) throws SQLException {
        commentMapper.deleteComment(commentId);
    }

    @Transactional
    @Override
    public void updateHeart(Map<String, Object> paramMap) throws SQLException {
        int heartCount = (int) paramMap.get("heart");
        if (commentMapper.selectHeartFlag(paramMap)) {
            heartCount -= 1;
            commentMapper.deleteHeartFlag(paramMap);
        }
        else {
            heartCount += 1;
            commentMapper.insertHeartFlag(paramMap);
        }
        Comment comment = new Comment();
        comment.setCommentId((int) paramMap.get("commentId"));
        comment.setHeart(heartCount);
        commentMapper.updateComment(comment);
    }
}
