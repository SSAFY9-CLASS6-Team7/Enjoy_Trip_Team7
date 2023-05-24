package com.ssafy.enjoytrip.attraction.model.mapper;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface AttractionMapper {
    List<Attraction> selectAttraction(Map<String, Object> paramMap) throws SQLException;
    int selectAttractionCount(Map<String, Object> paramMap) throws SQLException;
    Attraction selectAttractionByAttractionId(int attractionId) throws SQLException;
}
