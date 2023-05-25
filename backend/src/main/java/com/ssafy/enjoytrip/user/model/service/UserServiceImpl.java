package com.ssafy.enjoytrip.user.model.service;

import com.ssafy.enjoytrip.user.model.User;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

import static com.ssafy.enjoytrip.util.controller.ImageUtils.saveFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Value("${profile.dir}")
    private String fileDir;

    private final UserMapper userMapper;

    @Override
    public User login(User inputUser) throws SQLException {
        return userMapper.login(inputUser);
    }

    @Override
    public User getUserInfo(String userId) throws SQLException {
        return userMapper.selectUserByUserId(userId);
    }

    @Override
    public void createUser(User user) throws SQLException {
        userMapper.insertUser(user);
    }

    @Override
    public void deleteUser(String userId) throws SQLException {
        userMapper.deleteUser(userId);
    }

    @Override
    public void updateUser(User user, MultipartFile file) throws SQLException, IOException {
        if (file != null){
            String profilePicPath = saveFile(file, fileDir);
            user.setProfilePicPath(profilePicPath);
        }
        userMapper.updateUser(user);
    }
}
