package com.ssafy.enjoytrip.history.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.history.model.History;

@Mapper
public interface HistoryMapper {
	List<History> selectHistoryList(Map<String, Object> param) throws SQLException;
	int selectHistoryCount(Map<String, Object> map) throws SQLException;
	History selectHistoryById(int historyId) throws SQLException;
	void insertHistory(History history) throws SQLException;
	void updateHistory(History history) throws SQLException;
	void deleteHistory(int historyId) throws SQLException;
}
