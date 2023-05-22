package com.ssafy.enjoytrip.board.model.service;

import com.ssafy.enjoytrip.board.model.Board;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BoardService {
    Map<String, Object> getBoardList(Map<String, Object> paramMap) throws SQLException;

    Map<String, Object> getBoard(int boardId) throws SQLException;

    void createBoard(Board board, List<MultipartFile> files) throws SQLException, IOException;

    void updateBoard(Board board, List<MultipartFile> files) throws SQLException, IOException;

    void deleteBoard(int boardId) throws SQLException;

    void updateHeart(Map<String, Object> paramMap) throws SQLException;

    Map<String, Object> getIsImage(int boardId) throws SQLException;

    Map<String, Object> getHeart(String boardId, String userId) throws SQLException;
}
