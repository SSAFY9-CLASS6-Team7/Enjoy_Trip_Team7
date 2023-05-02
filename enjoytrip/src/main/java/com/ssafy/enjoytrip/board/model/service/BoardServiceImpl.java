package com.ssafy.enjoytrip.board.model.service;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.Comment;
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
public class BoardServiceImpl implements BoardService {
    static final int TYPE = 100;
    // 페이지 사이즈
    private final int LIST_SIZE = 10;
    @Value("${file.dir}")
    private String fileDir;

    private final BoardMapper boardMapper;
    private final CommentMapper commentMapper;
    private final ImageMapper imageMapper;

    @Override
    public List<Board> getBoardList(Map<String, Object> paramMap) throws SQLException {
        int pageNo;
        if ("".equals((String) paramMap.get("pageNo"))) pageNo = 1;
        else pageNo = Integer.parseInt((String) paramMap.get("pageNo"));
        log.info("START INT = {}", pageNo);
        paramMap.put("start", pageNo * LIST_SIZE - LIST_SIZE);
        paramMap.put("size", LIST_SIZE);
        return boardMapper.selectBoard(paramMap);
    }

    @Transactional
    @Override
    public Map<String, Object> getBoard(int boardId) throws SQLException {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("type", TYPE);
        paramMap.put("dataId", boardId);

        // 조회수 증가
        boardMapper.updateHits(boardId);

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
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("type", TYPE);
        paramMap.put("dataId", boardId);

        List<Image> images = imageMapper.selectImage(paramMap);
        deleteImageFile(images);

        // 게시글 삭제시 자동으로 댓글도 삭제되도록
        List<Comment> list = commentMapper.selectComment(boardId);
        for (Comment comment : list) {
            commentMapper.cascadeDeleteHeart(comment.getCommentId());
        }

        commentMapper.cascadeDeleteComment(boardId);
        imageMapper.cascadeDeleteImage(paramMap);
        boardMapper.cascadeDeleteHeart(boardId);
        boardMapper.deleteBoard(boardId);
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

    @Transactional
    @Override
    public void updateHeart(Map<String, Object> paramMap) throws SQLException {
        int heartCount = (int) paramMap.get("heart");
        if (boardMapper.selectHeartFlag(paramMap)) {
            heartCount -= 1;
            boardMapper.deleteHeartFlag(paramMap);
        } else {
            heartCount += 1;
            boardMapper.insertHeartFlag(paramMap);
        }
        Board board = new Board();
        board.setBoardId((int) paramMap.get("boardId"));
        board.setHeart(heartCount);
        boardMapper.updateBoard(board);
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
