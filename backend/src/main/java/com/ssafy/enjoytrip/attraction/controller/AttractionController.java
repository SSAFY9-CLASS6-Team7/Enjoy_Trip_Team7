package com.ssafy.enjoytrip.attraction.controller;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.attraction.model.service.AttractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/attraction")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping
    public Map<String, Object> getAttractionList(@RequestParam Map<String, Object> paramMap) throws SQLException {
        return attractionService.getAttractionList(paramMap);
    }

    @GetMapping("/{attractionId}")
    public Attraction getAttraction(@PathVariable int attractionId) throws SQLException {
        System.out.println(attractionId);
        return attractionService.getAttraction(attractionId);
    }

}
