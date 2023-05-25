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
    public Map<String, Object> getAnnouncementList(@RequestParam Map<String, Object> paramMap) throws SQLException {
        log.info("Controller ==== {}", paramMap.toString());
        return announcementService.getAnnouncementList(paramMap);
    }

    @GetMapping("/{announcementId}")
    public Map<String, Object> getAnnouncement(@PathVariable int announcementId) throws SQLException {
        return announcementService.getAnnouncement(announcementId);
    }

    @GetMapping("/{announcementId}/image")
    public Map<String, Object> getIsImage(@PathVariable int announcementId) throws SQLException{
        return announcementService.getIsImage(announcementId);
    }

    @PutMapping("/{announcementId}")
    public void updateAnnouncement(Board board, @PathVariable int announcementId, List<MultipartFile> files) throws SQLException, IOException {
        board.setBoardId(announcementId);
        announcementService.updateAnnouncement(board, files);
    }

    @DeleteMapping("/{announcementId}")
    public void deleteAnnouncement(@PathVariable int announcementId) throws SQLException {
        announcementService.deleteAnnouncement(announcementId);
    }

    // File 업로드 포함
    @PostMapping
    public void createAnnouncement(Board board, List<MultipartFile> files) throws SQLException, IOException {
        announcementService.createAnnouncement(board, files);
    }
}
