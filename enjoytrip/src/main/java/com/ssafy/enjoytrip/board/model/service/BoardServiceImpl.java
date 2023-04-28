package com.ssafy.enjoytrip.board.model.service;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;

    @Override
    public List<Board> getBoardList(Map<String, String> paramMap) throws SQLException {
        return boardMapper.selectBoard(paramMap);
    }

    @Override
    public void createBoard(Board board) throws SQLException {
        boardMapper.insertBoard(board);
    }
}
