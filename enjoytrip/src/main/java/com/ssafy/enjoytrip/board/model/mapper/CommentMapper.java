package com.ssafy.enjoytrip.board.model.mapper;

import com.ssafy.enjoytrip.board.model.Comment;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CommentMapper {
    List<Comment> selectComment(int boardId) throws SQLException;

    void insertComment(Comment comment) throws SQLException;

    void updateComment(Comment comment) throws SQLException;

    void deleteComment(int commentId) throws SQLException;

    void cascadeDeleteComment(int boardId) throws SQLException;

    boolean selectHeartFlag(Map<String, Object> paramMap) throws SQLException;

    void insertHeartFlag(Map<String, Object> paramMap) throws SQLException;

    void deleteHeartFlag(Map<String, Object> paramMap) throws SQLException;
}
