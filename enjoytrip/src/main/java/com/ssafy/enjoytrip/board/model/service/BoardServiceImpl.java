package com.ssafy.enjoytrip.board.model.service;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;
import com.ssafy.enjoytrip.board.model.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final CommentMapper commentMapper;

    @Override
    public List<Board> getBoardList(Map<String, String> paramMap) throws SQLException {
        return boardMapper.selectBoard(paramMap);
    }

    @Override
    public Board getBoard(int boardId) throws SQLException {
        return boardMapper.selectBoardByBoardId(boardId);
    }

    @Override
    public void createBoard(Board board) throws SQLException {
        boardMapper.insertBoard(board);
    }

    @Override
    public void updateBoard(Board board) throws SQLException {
        boardMapper.updateBoard(board);
    }

    @Transactional
    @Override
    public void deleteBoard(int boardId) throws SQLException {
        boardMapper.deleteBoard(boardId);
        commentMapper.cascadeDeleteComment(boardId);
    }
}
