package com.ssafy.enjoytrip.announcement.controller;

import com.ssafy.enjoytrip.announcement.model.service.AnnouncementService;
import com.ssafy.enjoytrip.board.model.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;


    @GetMapping
    public Map<String, Object> getBoardList(@RequestParam Map<String, Object> paramMap) throws SQLException {
        return announcementService.getBoardList(paramMap);
    }

    @GetMapping("/{boardId}")
    public Map<String, Object> getBoard(@PathVariable int boardId) throws SQLException {
        return announcementService.getBoard(boardId);
    }

    @GetMapping("/{boardId}/image")
    public Map<String, Object> getIsImage(@PathVariable int boardId) throws SQLException{
        return announcementService.getIsImage(boardId);
    }

    @PutMapping("/{boardId}")
    public void updateBoard(Board board, @PathVariable int boardId, List<MultipartFile> files) throws SQLException, IOException {
        board.setBoardId(boardId);
        log.info("board == {}", board.toString());
        announcementService.updateBoard(board, files);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable int boardId) throws SQLException {
        announcementService.deleteBoard(boardId);
    }

    // File 업로드 포함
    @PostMapping
    public void createBoard(Board board, List<MultipartFile> files) throws SQLException, IOException {
        announcementService.createBoard(board, files);
    }
}
