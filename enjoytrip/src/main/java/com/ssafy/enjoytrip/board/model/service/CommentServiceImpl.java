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
}
