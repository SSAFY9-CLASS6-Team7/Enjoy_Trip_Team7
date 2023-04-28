package com.ssafy.enjoytrip.board.controller;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.service.BoardService;
import com.ssafy.enjoytrip.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public List<Board> getBoardList(@RequestParam Map<String, String> paramMap) throws SQLException {
        log.info("HEART = {}" , paramMap.get("condition"));
        return boardService.getBoardList(paramMap);
    }

    @PostMapping
    public void createBoard(HttpSession session, @RequestBody Board board) throws SQLException {
        /*
        TODO 로그인 유무 구분을 위한 필터 추가 필요
        */

        User loginUser = (User) session.getAttribute("loginUser");
        boardService.createBoard(board);
    }

}
