package com.ssafy.enjoytrip.history.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.history.model.History;
import com.ssafy.enjoytrip.history.model.service.HistoryService;
import com.ssafy.enjoytrip.user.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/history")
public class HistoryContoller {
	private HistoryService historyService;
	
	public HistoryContoller(HistoryService historyService) {
		this.historyService = historyService;
	}
	
//	private User getLoginUser(HttpSession session) {
//		// ----- DUMMY USER FOR TEST -----
//		User dummyUser = new User();
//		dummyUser.setUserId("TestUser1");
//		session.setAttribute("loginUser", dummyUser);
//		// --------------------------------
//		return (User) session.getAttribute("loginUser");
//	}
	@GetMapping
	public Map<String, Object> getHistoryList(@RequestParam Map<String, Object> paramMap) throws SQLException {
		return historyService.getHistoryList(paramMap);
	}

	@GetMapping("{historyId}")
	public Map<String, Object> getHistory(@PathVariable int historyId) throws SQLException {
		return historyService.getHistory(historyId);
	}

	@PostMapping
	public void createHistory(History history, List<MultipartFile> files) throws SQLException, IOException {
		historyService.createHistory(history, files);
	}
	
	@PutMapping("{historyId}")
	public void updatehistory(History history, @PathVariable int historyId, List<MultipartFile> files) throws SQLException, IOException {
		history.setHistoryId(historyId);
		historyService.updateHistory(history, files);
	}
	
	@DeleteMapping("{historyId}")
	public void deleteHistory(@PathVariable int historyId) throws SQLException {
		historyService.deleteHistory(historyId);
	}
}
