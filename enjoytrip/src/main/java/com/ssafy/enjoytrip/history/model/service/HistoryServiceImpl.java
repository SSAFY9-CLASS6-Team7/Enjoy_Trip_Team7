package com.ssafy.enjoytrip.history.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.history.model.History;
import com.ssafy.enjoytrip.history.model.mapper.HistoryMapper;
import com.ssafy.enjoytrip.image.model.Image;
import com.ssafy.enjoytrip.image.model.mapper.ImageMapper;

import lombok.RequiredArgsConstructor;

import static com.ssafy.enjoytrip.image.controller.ImageUtils.deleteImageFile;
import static com.ssafy.enjoytrip.image.controller.ImageUtils.saveFile;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
	static final int TYPE = 200;

    @Value("${file.dir}")
    private String fileDir;
	
	private final HistoryMapper historyMapper;
	private final ImageMapper imageMapper;


	@Override
	public List<History> getHistoryList(String userId) throws SQLException {
		return historyMapper.selectHistoryList(userId);
	}

	@Override
	public Map<String, Object> getHistory(int historyId) throws SQLException {
		Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("type", TYPE);
        paramMap.put("dataId", historyId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("board", historyMapper.selectHistoryById(historyId));
        result.put("images", imageMapper.selectImage(paramMap));
        return result;
	}

	@Transactional
	@Override
	public void createHistory(History history, List<MultipartFile> files) throws SQLException, IOException {
		historyMapper.insertHistory(history);
		int dataId = history.getHistoryId();
		for (int i = 0; i < files.size(); i++) {
            String imagePath = saveFile(files.get(i), fileDir);

            Image image = new Image();
            image.setDataId(dataId);
            image.setImagePath(imagePath);
            image.setType(TYPE);

            imageMapper.insertImage(image);
        }
	}

	@Transactional
	@Override
	public void updateHistory(History history, List<MultipartFile> files) throws SQLException, IOException {
		Map<String, Integer> paramMap = new HashMap<>();
        int dataId = history.getHistoryId();
        paramMap.put("type", TYPE);
        paramMap.put("dataId", dataId);

        List<Image> images = imageMapper.selectImage(paramMap);
        deleteImageFile(images);

        imageMapper.cascadeDeleteImage(paramMap);
        historyMapper.updateHistory(history);
        if (files != null) {
            insertImages(dataId, files);
        }
	}

	@Override
	public void deleteHistory(int historyId) throws SQLException {
		historyMapper.deleteHistory(historyId);
		
		Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("type", TYPE);
        paramMap.put("dataId", historyId);

        List<Image> images = imageMapper.selectImage(paramMap);
        deleteImageFile(images);

        imageMapper.cascadeDeleteImage(paramMap);
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
}	
