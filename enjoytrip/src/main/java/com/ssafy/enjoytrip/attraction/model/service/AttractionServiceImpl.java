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
    // 페이지 사이즈
    private final int LIST_SIZE = 10;

    private final AttractionMapper attractionMapper;

    @Override
    public List<Attraction> getAttractionList(Map<String, Object> paramMap) throws SQLException {
        int pageNo;
        if ("".equals((String) paramMap.get("pageNo"))) pageNo = 1;
        else pageNo = Integer.parseInt((String) paramMap.get("pageNo"));
        paramMap.put("start", pageNo * LIST_SIZE - LIST_SIZE);
        paramMap.put("size", LIST_SIZE);
        return attractionMapper.selectAttraction(paramMap);
    }

    @Override
    public Attraction getAttraction(int attractionId) throws SQLException {
        return attractionMapper.selectAttractionByAttractionId(attractionId);
    }
}
