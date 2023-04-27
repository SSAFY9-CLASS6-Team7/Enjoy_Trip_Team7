package com.ssafy.enjoytrip.board.model.mapper;

import com.ssafy.enjoytrip.board.model.Board;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface BoardMapper {

    List<Board> selectBoard() throws SQLException;
}
