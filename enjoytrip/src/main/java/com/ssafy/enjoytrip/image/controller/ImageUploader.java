package com.ssafy.enjoytrip.image.controller;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ImageUploader {

    public static String saveFile(MultipartFile file, String fileDir) throws IOException {
        String fullPath = fileDir + UUID.randomUUID() + ".png";
        file.transferTo(new File(fullPath));
        return fullPath;
    }
}


