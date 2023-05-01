package com.ssafy.enjoytrip.board.model.mapper;

import com.ssafy.enjoytrip.board.model.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentMapper {
    List<Comment> selectComment(int boardId) throws SQLException;

    void insertComment(Comment comment) throws SQLException;

    void updateComment(Comment comment) throws SQLException;

    void deleteComment(int commentId) throws SQLException;

    void cascadeDeleteComment(int boardId) throws SQLException;
}
