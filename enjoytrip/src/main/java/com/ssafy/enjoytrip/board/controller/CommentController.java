package com.ssafy.enjoytrip.board.controller;

import com.ssafy.enjoytrip.board.model.Comment;
import com.ssafy.enjoytrip.board.model.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public void createComment(@PathVariable int boardId, @RequestBody Comment comment) throws SQLException{
        comment.setBoardId(boardId);
        commentService.createComment(comment);
    }
}
