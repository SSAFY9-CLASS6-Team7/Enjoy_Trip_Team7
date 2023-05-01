package com.ssafy.enjoytrip.history.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.history.model.History;
import com.ssafy.enjoytrip.history.model.mapper.HistoryMapper;
import com.ssafy.enjoytrip.image.model.Image;
import com.ssafy.enjoytrip.image.model.mapper.ImageMapper;

import lombok.RequiredArgsConstructor;
import static com.ssafy.enjoytrip.image.controller.ImageUploader.saveFile;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
	static final int TYPE = 200;

    @Value("${file.dir}")
    private String fileDir;
	
	private final HistoryMapper historyMapper;
	private final ImageMapper imageMapper;
	
//	public HistoryServiceImpl(String fileDir, HistoryMapper historyMapper, ImageMapper imageMapper) {
//		this.historyMapper = historyMapper;
//		this.imageMapper = imageMapper;
//	}


	@Override
	public List<History> getHistoryList(String userId) throws SQLException {
		return historyMapper.selectHistoryList(userId);
	}

	@Override
	public History getHistory(int historyId) throws SQLException {
		return historyMapper.selectHistoryById(historyId);
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

	@Override
	public void updateHistory(History history) throws SQLException {
		historyMapper.updateHistory(history);
	}

	@Override
	public void deleteHistory(int historyId) throws SQLException {
		historyMapper.deleteHistory(historyId);
	}
}	
