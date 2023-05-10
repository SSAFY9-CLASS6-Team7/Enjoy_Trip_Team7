package com.ssafy.enjoytrip.board.controller;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.service.BoardService;
import com.ssafy.enjoytrip.image.model.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @GetMapping
    public List<Board> getBoardList(@RequestParam Map<String, Object> paramMap) throws SQLException {
        return boardService.getBoardList(paramMap);
    }

    @GetMapping("/{boardId}")
    public Map<String, Object> getBoard(@PathVariable int boardId) throws SQLException{
        return boardService.getBoard(boardId);
    }

    @PutMapping("/{boardId}")
    public void updateBoard(Board board, @PathVariable int boardId, List<MultipartFile> files) throws SQLException, IOException {
        board.setBoardId(boardId);
        boardService.updateBoard(board, files);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable int boardId) throws SQLException {
        boardService.deleteBoard(boardId);
    }

    // File 업로드 포함
    @PostMapping
    public void createBoard(Board board, List<MultipartFile> files) throws SQLException, IOException {
        boardService.createBoard(board, files);
    }

//    @PostMapping
//    public void createBoard(@RequestBody Board board) throws SQLException, IOException {
//        boardService.createBoard(board, null);
//    }

    @PutMapping("/{boardId}/heart")
    public void updateHeart(@RequestBody Map<String, Object> paramMap) throws SQLException {
        boardService.updateHeart(paramMap);
    }
}
