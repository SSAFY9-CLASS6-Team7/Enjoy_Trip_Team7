package com.ssafy.enjoytrip.board.model.service;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;
import com.ssafy.enjoytrip.board.model.mapper.CommentMapper;
import com.ssafy.enjoytrip.image.model.Image;
import com.ssafy.enjoytrip.image.model.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ssafy.enjoytrip.image.controller.ImageUtils.deleteImageFile;
import static com.ssafy.enjoytrip.image.controller.ImageUtils.saveFile;

@Slf4j
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
    public Map<String, Object> getBoard(int boardId) throws SQLException {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("type", TYPE);
        paramMap.put("dataId", boardId);

        Map<String, Object> result = new HashMap<>();

        result.put("board", boardMapper.selectBoardByBoardId(boardId));
        result.put("images", imageMapper.selectImage(paramMap));
        return result;
    }

    @Transactional
    @Override
    public void updateBoard(Board board, List<MultipartFile> files) throws SQLException, IOException {
        Map<String, Integer> paramMap = new HashMap<>();
        int dataId = board.getBoardId();
        paramMap.put("type", TYPE);
        paramMap.put("dataId", dataId);

        List<Image> images = imageMapper.selectImage(paramMap);
        deleteImageFile(images);

        imageMapper.cascadeDeleteImage(paramMap);
        boardMapper.updateBoard(board);
        if (files != null) {
            insertImages(dataId, files);
        }
    }

    @Transactional
    @Override
    public void deleteBoard(int boardId) throws SQLException {
        boardMapper.deleteBoard(boardId);
        commentMapper.cascadeDeleteComment(boardId);

        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("type", TYPE);
        paramMap.put("dataId", boardId);

        List<Image> images = imageMapper.selectImage(paramMap);
        deleteImageFile(images);

        imageMapper.cascadeDeleteImage(paramMap);
    }

    // 사진 포함
    @Transactional
    @Override
    public void createBoard(Board board, List<MultipartFile> files) throws SQLException, IOException {
        boardMapper.insertBoard(board);
        int dataId = board.getBoardId();
        if (files != null) {
            insertImages(dataId, files);
        }
    }

    public void insertImages(int boardId, List<MultipartFile> files) throws IOException, SQLException {
        for (int i = 0; i < files.size(); i++) {
            String imagePath = saveFile(files.get(i), fileDir);

            Image image = new Image();
            image.setDataId(boardId);
            image.setImagePath(imagePath);
            image.setType(TYPE);

            imageMapper.insertImage(image);
        }
    }


}
