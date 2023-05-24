package com.ssafy.enjoytrip.user.model.mapper;

import com.ssafy.enjoytrip.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface UserMapper {
    User login(User inputUser) throws SQLException;

    User selectUserByUserId(String userId) throws SQLException;

    void insertUser(User user) throws SQLException;

    void deleteUser(String userId) throws SQLException;

    void updateUser(User user) throws SQLException;

}
