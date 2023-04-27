package com.ssafy.enjoytrip.board.controller;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public List<Board> getBoardList(@RequestParam int pageNo, @RequestParam int code, @RequestParam int order) throws SQLException {
        // TODO Query String 조건 추가 필요
        return boardService.getBoardList();
    }

    @PostMapping
    public void createBoard(HttpSession session, @RequestBody Board board) throws SQLException {

        /*
        - 로그인 유무 구분을 위한 필터 추가 필요
        - User Dto 추가 필요
        User loginUser = (User) session.getAttribute("loginUser");
        board.setUserId(loginUser.getUserId());
        */

        boardService.createBoard(board);
    }

}
