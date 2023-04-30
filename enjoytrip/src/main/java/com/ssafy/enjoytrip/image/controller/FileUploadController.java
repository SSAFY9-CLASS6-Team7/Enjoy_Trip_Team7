package com.ssafy.enjoytrip.image.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.history.model.History;
import com.ssafy.enjoytrip.image.model.Image;

public class FileUploadController {
	@Value("${file.path}")
	private String uploadPath;
	
	public List<Image> fileUpload(MultipartFile[] files, Object dto) throws IllegalStateException, IOException {
		int type = 0; //초기 값
		int dataId = 0;
		if(dto.getClass() == Board.class) {
			type = 1; 
			Board board = (Board) dto;
			dataId = board.getBoardId();
		}
		else if(dto.getClass() == History.class) {
			type = 2;
			History history = (History) dto;
			dataId = history.getHistoryId();
		}
		
		String today = new SimpleDateFormat("yyMMdd").format(new Date());
		String saveFolder = uploadPath + File.separator + today;
		File folder = new File(saveFolder);
		if (!folder.exists())
			folder.mkdirs();
		List<Image> images = new ArrayList<Image>();
		for (MultipartFile mfile : files) {
			Image image = new Image();
			String originalFileName = mfile.getOriginalFilename();
			if (!originalFileName.isEmpty()) {
				String saveFileName = UUID.randomUUID().toString()
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				image.setSaveFolder(today);
				image.setOriginalFile(originalFileName);
				image.setSaveFile(saveFileName);
				
				image.setType(type);
				image.setDataId(dataId);
				
				mfile.transferTo(new File(folder, saveFileName));
			}
			images.add(image);
		}
		return images;
	}
}
