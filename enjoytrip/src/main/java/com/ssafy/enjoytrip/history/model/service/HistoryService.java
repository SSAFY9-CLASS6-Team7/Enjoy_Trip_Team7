package com.ssafy.enjoytrip.history.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.history.model.History;

public interface HistoryService {

	List<History> getHistoryList(String userId) throws SQLException;
	History getHistory(int historyId) throws SQLException;
	void createHistory(History history, List<MultipartFile> files) throws SQLException, IOException;
	void updateHistory(History history) throws SQLException;
	void deleteHistory(int historyId) throws SQLException;
	
}
