package com.ssafy.enjoytrip.user.model.service;

import com.ssafy.enjoytrip.user.model.User;

import java.sql.SQLException;

public interface UserService {
    User login(User inputUser) throws SQLException;

    void createUser(User user) throws SQLException;

    void deleteUser(String userId) throws SQLException;

    void updateUser(User user) throws SQLException;
}
