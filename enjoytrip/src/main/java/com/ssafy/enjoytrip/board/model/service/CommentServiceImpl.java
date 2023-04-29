package com.ssafy.enjoytrip.board.model.service;

import com.ssafy.enjoytrip.board.model.Comment;
import com.ssafy.enjoytrip.board.model.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

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
}
