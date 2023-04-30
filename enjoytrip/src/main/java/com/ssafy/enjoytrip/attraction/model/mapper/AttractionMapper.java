package com.ssafy.enjoytrip.attraction.model.mapper;

import com.ssafy.enjoytrip.attraction.model.Attraction;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AttractionMapper {
    List<Attraction> selectAttraction(Map<String, Object> paramMap) throws SQLException;
}
