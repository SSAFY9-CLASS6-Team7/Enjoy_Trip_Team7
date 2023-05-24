package com.ssafy.enjoytrip.announcement.model.mapper;

import com.ssafy.enjoytrip.board.model.Board;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface AnnouncementMapper {

    List<Board> selectBoard(Map<String, Object> paramMap) throws SQLException;

    int selectBoardCount(Map<String, Object> paramMap) throws SQLException;

    void insertBoard(Board board) throws SQLException;

    void updateBoard(Board board) throws SQLException;

    void deleteBoard(int boardId) throws SQLException;

    Board selectBoardByBoardId(int boardId) throws SQLException;

    void updateHits(int boardId) throws SQLException;
}