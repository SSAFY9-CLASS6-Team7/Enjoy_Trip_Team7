package com.ssafy.enjoytrip.history.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.history.model.History;
import com.ssafy.enjoytrip.history.model.mapper.HistoryMapper;

@Service
public class HistoryServiceImpl implements HistoryService {
	private HistoryMapper historyMapper;
	
	public HistoryServiceImpl(HistoryMapper historyMapper) {
		this.historyMapper = historyMapper;
	}

	@Override
	public List<History> getHistoryList(String userId) throws SQLException {
		return historyMapper.selectHistoryList(userId);
	}

	@Override
	public History getHistory(int historyId) throws SQLException {
		return historyMapper.selectHistoryById(historyId);
	}

	@Override
	public void createHistory(History history) throws SQLException {
		historyMapper.insertHistory(history);
	}

	@Override
	public void updateHistory(History history) throws SQLException {
		historyMapper.updateHistory(history);
	}

	@Override
	public void deleteHistory(int historyId) throws SQLException {
		historyMapper.deleteHistory(historyId);
	}
}	
