package com.ssafy.enjoytrip.board.controller;

import com.ssafy.enjoytrip.board.model.Comment;
import com.ssafy.enjoytrip.board.model.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/board/{boardId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<Comment> getCommentList(@PathVariable int boardId) throws SQLException {
        return commentService.getCommentList(boardId);
    }
}
