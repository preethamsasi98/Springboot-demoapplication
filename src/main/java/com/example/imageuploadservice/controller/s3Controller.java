package com.example.imageuploadservice.controller;

import com.example.imageuploadservice.service.s3service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;

@RestController
public class s3Controller {
    @Autowired
    private s3service s3service;
    @GetMapping("name")
    public String View(){
        return "Sasi";
    }
    @PostMapping("upload")
    public String upload(@RequestParam("image")MultipartFile image){
       return s3service.Upload(image);
    }

    @GetMapping("download/{filename}")
    public ResponseEntity<byte[]> fetch(@PathVariable("filename")String filename){
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-type", MediaType.ALL_VALUE);
        headers.add("Content-Disposition", "attachment; filename="+filename);
        byte[] bytes = s3service.Fetch(filename);
        return  ResponseEntity.status(HTTP_OK).headers(headers).body(bytes);
    }

    @DeleteMapping("{filename}")
    public String delete(@PathVariable("filename")String filename){
        return s3service.Delete(filename);
    }

    @PostMapping("Update/{filename}")
    public String update(@RequestParam("image")MultipartFile image,@PathVariable("filename") String filename){
        return s3service.Update(filename,image);
    }
    @GetMapping("list")
    public List<String> getallimages(){
        return s3service.ListallImages();
    }
}
