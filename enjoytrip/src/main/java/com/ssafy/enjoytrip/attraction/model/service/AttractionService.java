package com.ssafy.enjoytrip.attraction.model.service;

import com.ssafy.enjoytrip.attraction.model.Attraction;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AttractionService {
    List<Attraction> getAttractionList(Map<String, Object> paramMap) throws SQLException;
}
