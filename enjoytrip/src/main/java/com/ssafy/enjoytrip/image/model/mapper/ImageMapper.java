package com.ssafy.enjoytrip.image.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.image.model.Image;

@Mapper
public interface ImageMapper {
	void registerFile(Object dto) throws Exception;
	void deleteFile(Object dto) throws Exception;
	List<Image> fileInfoList(Object dto) throws Exception;
}
