package com.ssafy.enjoytrip.history.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	public Map<String, Object> getHistory(@PathVariable int historyId) throws SQLException {
		return historyService.getHistory(historyId);
	}

	@PostMapping
	public List<History> createHistory(@RequestPart History history, @RequestPart(required = false) List<MultipartFile> files, HttpSession session) throws SQLException, IOException {
		history.setUserId(getLoginUser(session).getUserId());
		historyService.createHistory(history, files);
		return historyService.getHistoryList(getLoginUser(session).getUserId());
	}
	
	@PutMapping("{historyId}")
	public List<History> updatehistory(@RequestPart History history, @PathVariable int historyId, HttpSession session, @RequestPart(required = false) List<MultipartFile> files) throws SQLException, IOException {
		history.setHistoryId(historyId);
		historyService.updateHistory(history, files);
		return historyService.getHistoryList(getLoginUser(session).getUserId());
	}
	
	@DeleteMapping("{historyId}")
	public List<History> deleteHistory(@PathVariable int historyId, HttpSession session) throws SQLException {
		historyService.deleteHistory(historyId);
		return historyService.getHistoryList(getLoginUser(session).getUserId());
	}
}
