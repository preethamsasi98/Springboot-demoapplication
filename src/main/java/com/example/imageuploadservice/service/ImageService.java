package com.example.imageuploadservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    String Upload(MultipartFile image);

    byte[] Fetch(String imagename);
    String Delete(String imagename);
    String Update(String imagename,MultipartFile image);

    List<String> ListallImages();

}
