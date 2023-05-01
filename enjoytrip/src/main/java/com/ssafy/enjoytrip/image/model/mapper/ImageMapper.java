package com.ssafy.enjoytrip.image.model.mapper;

import com.ssafy.enjoytrip.image.model.Image;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface ImageMapper {
	void insertImage(Image image) throws SQLException;

}
