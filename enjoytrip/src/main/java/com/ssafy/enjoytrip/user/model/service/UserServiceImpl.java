package com.ssafy.enjoytrip.user.model.service;

import com.ssafy.enjoytrip.user.model.User;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;

    @Override
    public User login(User inputUser) throws SQLException {
        return userMapper.login(inputUser);
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
    public void updateUser(User user) throws SQLException {
       userMapper.updateUser(user);
    }
}
