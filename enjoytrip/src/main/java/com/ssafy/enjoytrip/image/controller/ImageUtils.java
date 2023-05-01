package com.ssafy.enjoytrip.image.controller;

import com.ssafy.enjoytrip.image.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ImageUtils {

    public static String saveFile(MultipartFile file, String fileDir) throws IOException {
        String fullPath = fileDir + UUID.randomUUID() + ".png";
        file.transferTo(new File(fullPath));
        return fullPath;
    }

    public static void deleteImageFile(List<Image> imagesForDelete) {
        for (Image image : imagesForDelete) {
            File file = new File(image.getImagePath());

            if (file.exists()) file.delete();
        }
    }
}


