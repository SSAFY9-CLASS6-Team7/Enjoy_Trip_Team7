package com.ssafy.enjoytrip.board.model.service;

import com.ssafy.enjoytrip.board.model.Comment;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CommentService {

    List<Comment> getCommentList(int boardId) throws SQLException;

    void createComment(Comment comment) throws SQLException;

    void updateComment(Comment comment) throws SQLException;

    void deleteComment(int commentId) throws SQLException;

    void updateHeart(Map<String, Object> paramMap) throws SQLException;
}
