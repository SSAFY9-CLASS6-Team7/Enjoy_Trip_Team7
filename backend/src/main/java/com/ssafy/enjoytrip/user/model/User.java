package com.ssafy.enjoytrip.user.model;

import lombok.Data;

@Data
public class User {
    private String userId;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private String gender;
    private String birth;
    private String profilePicPath;
    private String mainpageCustom;
}