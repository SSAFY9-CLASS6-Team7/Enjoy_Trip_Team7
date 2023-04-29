package com.ssafy.enjoytrip.board.model.service;

import com.ssafy.enjoytrip.board.model.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentService {

    List<Comment> getCommentList(int boardId) throws SQLException;

    void createComment(Comment comment) throws SQLException;
}
