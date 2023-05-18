package com.ssafy.enjoytrip.history.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.history.model.History;

public interface HistoryService {

	Map<String, Object> getHistoryList(Map<String, Object> map) throws SQLException;
	Map<String, Object> getHistory(int historyId) throws SQLException;
	void createHistory(History history, List<MultipartFile> files) throws SQLException, IOException;
	void updateHistory(History history, List<MultipartFile> files) throws SQLException, IOException;
	void deleteHistory(int historyId) throws SQLException;
	
}
