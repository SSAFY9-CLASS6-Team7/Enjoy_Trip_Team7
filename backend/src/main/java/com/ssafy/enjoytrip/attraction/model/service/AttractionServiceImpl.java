package com.ssafy.enjoytrip.attraction.model.service;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.attraction.model.mapper.AttractionMapper;
import com.ssafy.enjoytrip.util.model.Page;
import com.ssafy.enjoytrip.util.model.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService{
    // 페이지 사이즈
    private final int LIST_SIZE = 10;

    private final AttractionMapper attractionMapper;

    @Override
    public Map<String, Object> getAttractionList(Map<String, Object> paramMap) throws SQLException {
        Map<String, Object> map = new HashMap<>();

        Page page = new Page();
        int pageNo = Integer.parseInt(String.valueOf(paramMap.get("pageNo")));
        page.setPageNo(pageNo);
        page.setListSize(LIST_SIZE);
        map.put("page", page);

        String[] codes = paramMap.get("code").toString().split(",");
        String[] sidos = paramMap.get("sido").toString().split(",");
        if (codes.length > 0 && !codes[0].isEmpty()) {
            map.put("codes", Arrays.asList(codes));
        } else {
            map.put("codes", Collections.emptyList());
        }
        if (sidos.length > 0 && !sidos[0].isEmpty()) {
            map.put("sidos", Arrays.asList(sidos));
        } else {
            map.put("sidos", Collections.emptyList());
        }map.put("keyword", paramMap.get("keyword"));
        log.info("map: {}", map);

        Map<String, Object> result = new HashMap<>();
        int totalCount = attractionMapper.selectAttractionCount();
        PageResult pageResult = new PageResult(pageNo, totalCount, LIST_SIZE, 5);
        result.put("attractions", attractionMapper.selectAttraction(map));
        result.put("pageResult", pageResult);
        return result;
    }

    @Override
    public Attraction getAttraction(int attractionId) throws SQLException {
        return attractionMapper.selectAttractionByAttractionId(attractionId);
    }
}
