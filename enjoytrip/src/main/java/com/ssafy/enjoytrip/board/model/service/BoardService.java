package com.ssafy.enjoytrip.board.model.service;

import com.ssafy.enjoytrip.board.model.Board;

import java.sql.SQLException;
import java.util.List;

public interface BoardService {
    List<Board> getBoardList() throws SQLException;

    void createBoard(Board board) throws SQLException;
}
