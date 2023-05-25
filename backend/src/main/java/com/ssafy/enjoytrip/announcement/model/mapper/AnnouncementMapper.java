package com.ssafy.enjoytrip.announcement.model.mapper;

import com.ssafy.enjoytrip.board.model.Board;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface AnnouncementMapper {

    List<Board> selectAnnouncement(Map<String, Object> paramMap) throws SQLException;

    int selectAnnouncementCount(Map<String, Object> paramMap) throws SQLException;

    void insertAnnouncement(Board board) throws SQLException;

    void updateAnnouncement(Board board) throws SQLException;

    void deleteAnnouncement(int announcementId) throws SQLException;

    Board selectAnnouncementByAnnouncementId(int announcementId) throws SQLException;

    void updateHits(int announcementId) throws SQLException;
}