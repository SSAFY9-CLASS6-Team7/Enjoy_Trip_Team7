package com.ssafy.enjoytrip.user.controller;

import com.ssafy.enjoytrip.exception.UnAuthorizedException;
import com.ssafy.enjoytrip.user.model.User;
import com.ssafy.enjoytrip.user.model.service.UserService;
import com.ssafy.enjoytrip.util.controller.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Value("${profile.dir}")
    private String fileDir;

    private final UserService userService;
    private final JWTUtils jwtUtils;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User inputUser) throws SQLException {
        Map<String, Object> result = new HashMap<>();

        User loginUser = userService.login(inputUser);
        if (loginUser != null) {
            String accessToken = jwtUtils.createAccessToken(loginUser.getUserId());
            String refreshToken = jwtUtils.createRefreshToken(loginUser.getUserId());
            result.put("message", "success");
            result.put("access-token", accessToken);
            result.put("refresh-token", refreshToken);
        }
        return result;
    }

    @PostMapping("/refresh")
    public Map<String, String> refreshAccessToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("refresh-token");
        if (!jwtUtils.checkToken(refreshToken)) throw new UnAuthorizedException("세션이 만료되었습니다.");

        Map<String, String> result = new HashMap<>();
        String userId = jwtUtils.getUserId(refreshToken);
        String newAccessToken = jwtUtils.createAccessToken(userId);
        String newRefreshToken = jwtUtils.createRefreshToken(userId);
        result.put("access-token", newAccessToken);
        result.put("refresh-token", newRefreshToken);
        return result;
    }

    @PostMapping
    public void createUser(@RequestBody User user) throws SQLException {
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

    @GetMapping
    public Map<String, Object> getUser(@PathVariable String userId) throws SQLException {
        Map<String, Object> result = new HashMap<>();
        User findUser = userService.getUserInfo(userId);
        if (findUser != null) {
            result.put("message", "success");
            result.put("userInfo", findUser);
        } else {
            result.put("message", "fail");
        }
        return result;
    }
}


