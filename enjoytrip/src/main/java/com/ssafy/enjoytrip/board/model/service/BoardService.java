package com.ssafy.enjoytrip.board.model.service;

import com.ssafy.enjoytrip.board.model.Board;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BoardService {
    List<Board> getBoardList(Map<String, String> paramMap) throws SQLException;

    void createBoard(Board board) throws SQLException;
}
