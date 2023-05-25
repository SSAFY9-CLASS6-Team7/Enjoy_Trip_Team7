package com.ssafy.enjoytrip.announcement.model.service;

import com.ssafy.enjoytrip.board.model.Board;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AnnouncementService {
    Map<String, Object> getAnnouncementList(Map<String, Object> paramMap) throws SQLException;

    Map<String, Object> getAnnouncement(int announcementId) throws SQLException;

    void createAnnouncement(Board board, List<MultipartFile> files) throws SQLException, IOException;

    void updateAnnouncement(Board board, List<MultipartFile> files) throws SQLException, IOException;

    void deleteAnnouncement(int announcementId) throws SQLException;

    Map<String, Object> getIsImage(int announcementId) throws SQLException;
}
