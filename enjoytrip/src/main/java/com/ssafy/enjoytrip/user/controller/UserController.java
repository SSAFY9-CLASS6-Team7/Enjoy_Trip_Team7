package com.ssafy.enjoytrip.user.controller;

import com.ssafy.enjoytrip.user.model.User;
import com.ssafy.enjoytrip.user.model.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Value("${profile.dir}")
    private String fileDir;

    private final UserService userService;

    // TODO 추후 JWT 방식으로 교체 예정
    @PostMapping("/login")
    public User login(HttpSession session, @RequestBody User inputUser) throws SQLException {
        User loginUser = userService.login(inputUser);
        if (loginUser != null) {
            session.setAttribute("loginUser", loginUser);
        }
        return loginUser;
    }

    // TODO 추후 JWT 방식으로 교체 예정
    @GetMapping("/logout")
    public void logout(HttpSession session){
        session.invalidate();
    }

    @PostMapping
    public void createUser(@RequestBody User user) throws SQLException{
        userService.createUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) throws SQLException {
        userService.deleteUser(userId);
    }

    @Transactional
    @PutMapping("/{userId}")
    public void updateUser(@PathVariable String userId, @RequestPart User user, @RequestPart MultipartFile file) throws SQLException, IOException {
        user.setUserId(userId);
        userService.updateUser(user, file);
    }
}


