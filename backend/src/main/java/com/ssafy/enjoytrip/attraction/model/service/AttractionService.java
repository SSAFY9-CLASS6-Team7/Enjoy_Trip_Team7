package com.ssafy.enjoytrip.attraction.model.service;

import com.ssafy.enjoytrip.attraction.model.Attraction;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AttractionService {
    Map<String, Object> getAttractionList(Map<String, Object> paramMap) throws SQLException;

    Attraction getAttraction(int attractionId) throws SQLException;
}
