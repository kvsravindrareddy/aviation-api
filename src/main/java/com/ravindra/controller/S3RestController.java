package com.ravindra.controller;

import com.ravindra.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/s3")
public class S3RestController {

    @Autowired
    private S3Service s3Service;

    @GetMapping("/s3object")
    public String downloadS3Object(@RequestParam("path")String path)
    {
        return s3Service.s3ObjectDownload(path);
    }

    @GetMapping("/listobjects")
    public List<String> listObjects()
    {
        return s3Service.listObjects();
    }

    @GetMapping("/sftpconnectioncheck")
    public void sftpConnectionCheck()
    {
        s3Service.sftpTest();
    }

    @PostMapping("/uploadobject")
    public void uploadObject(@RequestPart("file") MultipartFile file)
    {
        s3Service.uploadObject(file);
    }
}