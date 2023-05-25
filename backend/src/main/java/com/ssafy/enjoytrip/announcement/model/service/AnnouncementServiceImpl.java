package com.ssafy.enjoytrip.announcement.model.service;

import com.ssafy.enjoytrip.announcement.model.mapper.AnnouncementMapper;
import com.ssafy.enjoytrip.announcement.model.service.AnnouncementService;
import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.util.model.Image;
import com.ssafy.enjoytrip.util.model.Page;
import com.ssafy.enjoytrip.util.model.PageResult;
import com.ssafy.enjoytrip.util.model.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ssafy.enjoytrip.util.controller.ImageUtils.deleteImageFile;
import static com.ssafy.enjoytrip.util.controller.ImageUtils.saveFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    static final int TYPE = 300;
    // 페이지 사이즈
    private final int LIST_SIZE = 10;

    @Value("${file.dir}")
    private String fileDir;

    private final AnnouncementMapper announcementMapper;
    private final ImageMapper imageMapper;

    @Override
    public Map<String, Object> getAnnouncementList(Map<String, Object> paramMap) throws SQLException {
        log.info("Controller ==== {}", paramMap.toString());
        Page page = new Page();
        int pageNo = Integer.parseInt(String.valueOf(paramMap.get("pageNo")));
        page.setPageNo(pageNo);
        paramMap.put("page", page);

        Map<String, Object> result = new HashMap<>();

        int totalCount = announcementMapper.selectAnnouncementCount(paramMap);
        PageResult pageResult = new PageResult(pageNo, totalCount);
        result.put("boards",  announcementMapper.selectAnnouncement(paramMap));
        result.put("pageResult", pageResult);
        return result;
    }

    @Transactional
    @Override
    public Map<String, Object> getAnnouncement(int announcementId) throws SQLException {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("type", TYPE);
        paramMap.put("dataId", announcementId);

        // 조회수 증가
        announcementMapper.updateHits(announcementId);

        Map<String, Object> result = new HashMap<>();

        result.put("board", announcementMapper.selectAnnouncementByAnnouncementId(announcementId));
        result.put("images", imageMapper.selectImage(paramMap));

        return result;
    }

    @Transactional
    @Override
    public void updateAnnouncement(Board board, List<MultipartFile> files) throws SQLException, IOException {
        Map<String, Integer> paramMap = new HashMap<>();
        int dataId = board.getBoardId();
        paramMap.put("type", TYPE);
        paramMap.put("dataId", dataId);

        List<Image> images = imageMapper.selectImage(paramMap);
        deleteImageFile(images);

        imageMapper.cascadeDeleteImage(paramMap);
        announcementMapper.updateAnnouncement(board);
        if (files != null) {
            insertImages(dataId, files);
        }
    }

    @Transactional
    @Override
    public void deleteAnnouncement(int announcementId) throws SQLException {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("type", TYPE);
        paramMap.put("dataId", announcementId);

        List<Image> images = imageMapper.selectImage(paramMap);
        deleteImageFile(images);


        imageMapper.cascadeDeleteImage(paramMap);
        announcementMapper.deleteAnnouncement(announcementId);
    }

    // 사진 포함
    @Transactional
    @Override
    public void createAnnouncement(Board board, List<MultipartFile> files) throws SQLException, IOException {
        // for Test
        if (board.getCode() == 0) board.setCode(100);

        announcementMapper.insertAnnouncement(board);

        int dataId = board.getBoardId();
        log.info("files {}", files);
        if (files != null) {
            insertImages(dataId, files);
        }
    }

    public void insertImages(int boardId, List<MultipartFile> files) throws IOException, SQLException {
        for (int i = 0; i < files.size(); i++) {
            String imagePath = saveFile(files.get(i), fileDir);
            Image image = new Image();
            image.setDataId(boardId);
            image.setImagePath(imagePath);
            image.setType(TYPE);

            imageMapper.insertImage(image);
        }
    }

    @Override
    public Map<String, Object> getIsImage(int boardId) throws SQLException {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("type", TYPE);
        paramMap.put("dataId", boardId);
        Map<String, Object> result = new HashMap<>();
        result.put("images", imageMapper.selectImage(paramMap));
        return result;
    }

}
