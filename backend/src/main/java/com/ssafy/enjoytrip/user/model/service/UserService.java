package com.ssafy.enjoytrip.user.model.service;

import com.ssafy.enjoytrip.user.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface UserService {
    User login(User inputUser) throws SQLException;

    User getUserInfo(String userId) throws SQLException;

    void createUser(User user) throws SQLException;

    void deleteUser(String userId) throws SQLException;

    void updateUser(User user, MultipartFile file) throws SQLException, IOException;
}
