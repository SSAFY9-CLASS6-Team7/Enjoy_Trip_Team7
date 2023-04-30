package com.ssafy.enjoytrip.attraction.model.service;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.attraction.model.mapper.AttractionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService{

    private final AttractionMapper attractionMapper;

    @Override
    public List<Attraction> getAttractionList(Map<String, Object> paramMap) throws SQLException {
        return attractionMapper.selectAttraction(paramMap);
    }

    @Override
    public Attraction getAttraction(int attractionId) throws SQLException {
        return attractionMapper.selectAttractionByAttractionId(attractionId);
    }
}
