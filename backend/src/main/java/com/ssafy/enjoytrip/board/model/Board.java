package com.ssafy.enjoytrip.board.model;

import lombok.Data;

@Data
public class Board {

    private int code;
    private String title;
    private String createTime;
    private String boardContent;
    private int heart;
    private int hits;
    private String anonymous;

    private int boardId;
    private String userId;
    private int attractionId;
}
