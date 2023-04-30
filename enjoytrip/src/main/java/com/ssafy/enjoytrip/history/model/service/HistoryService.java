package com.ssafy.enjoytrip.history.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.history.model.History;

public interface HistoryService {

	List<History> getHistoryList(String userId) throws SQLException;
	History getHistory(int historyId) throws SQLException;
	void createHistory(History history) throws SQLException;
	void updateHistory(History history) throws SQLException;
	void deleteHistory(int historyId) throws SQLException;

}
