package com.ssafy.enjoytrip.comment;

import lombok.Data;

@Data
public class Comment {
    private String commentId;
    private String boardId;
    private String userId;
    private String commentContent;
    private String anonymous;
}
