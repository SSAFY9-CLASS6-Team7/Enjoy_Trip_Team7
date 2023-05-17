package com.ssafy.enjoytrip.util.model.mapper;

import com.ssafy.enjoytrip.util.model.Image;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface ImageMapper {
	List<Image> selectImage(Map<String, Integer> paramMap) throws SQLException;

	void insertImage(Image image) throws SQLException;

	void deleteImage(int imageId) throws SQLException;

	void cascadeDeleteImage(Map<String, Integer> paramMap) throws SQLException;

}
