package com.ssafy.enjoytrip.history.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.history.model.History;
import com.ssafy.enjoytrip.history.model.service.HistoryService;
import com.ssafy.enjoytrip.user.model.User;

@RestController
@RequestMapping("/history")
public class HistoryContoller {
private HistoryService historyService;
	
	public HistoryContoller(HistoryService historyService) {
		this.historyService = historyService;
	}
	
	private User getLoginUser(HttpSession session) {
		// ----- DUMMY USER FOR TEST -----
		User dummyUser = new User();
		dummyUser.setUserId("ssafy");
		session.setAttribute("loginUser", dummyUser);
		// --------------------------------
		return (User) session.getAttribute("loginUser");
	}
	@GetMapping
	public List<History> getHistoryList(HttpSession session) throws SQLException {
		return historyService.getHistoryList(getLoginUser(session).getUserId());
	}

	@GetMapping("{historyId}")
	public History getHistory(@PathVariable int historyId) throws SQLException {
		return historyService.getHistory(historyId);
	}

	@PostMapping
	public List<History> createHistory(@RequestBody History history, HttpSession session) throws SQLException {
		history.setUserId(getLoginUser(session).getUserId());
		historyService.createHistory(history);
		return historyService.getHistoryList(getLoginUser(session).getUserId());
	}
	
	@PostMapping("{historyId}")
	public List<History> updatehistory(@RequestBody History history, @PathVariable int historyId, HttpSession session) throws SQLException {
		history.setHistoryId(historyId);
		historyService.updateHistory(history);
		return historyService.getHistoryList(getLoginUser(session).getUserId());
	}
	
	@DeleteMapping("{historyId}")
	public List<History> deleteHistory(@PathVariable int historyId, HttpSession session) throws SQLException {
		historyService.deleteHistory(historyId);
		return historyService.getHistoryList(getLoginUser(session).getUserId());
	}
}
