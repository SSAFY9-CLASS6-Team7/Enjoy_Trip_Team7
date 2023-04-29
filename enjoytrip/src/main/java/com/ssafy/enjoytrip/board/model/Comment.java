package com.ssafy.enjoytrip.board.model;

import lombok.Data;

@Data
public class Comment {
    private int commentId;
    private int boardId;
    private String userId;
    private String commentContent;
    private String createTime;
    private int heart;
    private String anonymous;
}
