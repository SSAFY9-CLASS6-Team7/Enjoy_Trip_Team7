package com.ssafy.enjoytrip.board.model.service;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;
import com.ssafy.enjoytrip.board.model.mapper.CommentMapper;
import com.ssafy.enjoytrip.image.model.Image;
import com.ssafy.enjoytrip.image.model.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.ssafy.enjoytrip.image.controller.ImageUploader.saveFile;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    static final int TYPE = 100;

    @Value("${file.dir}")
    private String fileDir;

    private final BoardMapper boardMapper;
    private final CommentMapper commentMapper;
    private final ImageMapper imageMapper;

    @Override
    public List<Board> getBoardList(Map<String, String> paramMap) throws SQLException {
        return boardMapper.selectBoard(paramMap);
    }

    @Override
    public Board getBoard(int boardId) throws SQLException {
        return boardMapper.selectBoardByBoardId(boardId);
    }

    /*@Override
    public void createBoard(Board board) throws SQLException {
        boardMapper.insertBoard(board);
    }*/

    @Override
    public void updateBoard(Board board) throws SQLException {
        boardMapper.updateBoard(board);
    }

    @Transactional
    @Override
    public void deleteBoard(int boardId) throws SQLException {
        boardMapper.deleteBoard(boardId);
        commentMapper.cascadeDeleteComment(boardId);
    }

    // 사진 포함
    @Transactional
    @Override
    public void createBoard(Board board, List<MultipartFile> files) throws SQLException, IOException {
        boardMapper.insertBoard(board);
        int dataId = board.getBoardId();
        for (int i = 0; i < files.size(); i++) {
            String imagePath = saveFile(files.get(i), fileDir);

            Image image = new Image();
            image.setDataId(dataId);
            image.setImagePath(imagePath);
            image.setType(TYPE);

            imageMapper.insertImage(image);
        }
    }
}
