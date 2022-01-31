package com.ravindra.controller;

import com.ravindra.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/s3")
public class S3RestController {

    @Autowired
    private S3Service s3Service;

    @GetMapping("/s3object")
    public String downloadS3Object(@RequestParam("filename")String fileName)
    {
        return s3Service.s3ObjectDownload(fileName);
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
}