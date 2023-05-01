package com.ssafy.enjoytrip.board.controller;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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
        return boardService.getBoardList(paramMap);
    }

    @GetMapping("/{boardId}")
    public Board getBoard(@PathVariable int boardId) throws SQLException{
        return boardService.getBoard(boardId);
    }

    @PostMapping
    public void createBoard(@RequestBody Board board) throws SQLException {
        /*
        TODO 로그인 유무 구분을 위한 필터 추가 필요
        비동기 처리시 JSSESSION ID 사용 불가!!
        로그인 처리 필요
        */
        boardService.createBoard(board);
    }

    @PutMapping("/{boardId}")
    public void updateBoard(@RequestBody Board board, @PathVariable int boardId) throws SQLException {
        board.setBoardId(boardId);
        boardService.updateBoard(board);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable int boardId) throws SQLException {
        boardService.deleteBoard(boardId);
    }

}
