package com.ssafy.enjoytrip.util.controller;

import com.ssafy.enjoytrip.util.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ImageUtils {

    public static String saveFile(MultipartFile file, String fileDir) throws IOException {
        String path = UUID.randomUUID() + ".png";
        file.transferTo(new File(fileDir + path));
        return path;
    }

    public static void deleteImageFile(List<Image> imagesForDelete) {
        for (Image image : imagesForDelete) {
            File file = new File("C:/enjoytrip_image/images/" + image.getImagePath());

            if (file.exists()) file.delete();
        }
    }
}


