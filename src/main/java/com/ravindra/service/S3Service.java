package com.ravindra.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.ravindra.util.SFTPUtil;
import com.ravindra.config.S3Config;
import com.ravindra.util.WindowsSharedDriveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3Client;

    @Autowired
    private S3Config s3Config;

    @Autowired
    private SFTPUtil sftpUtil;

    @Autowired
    private WindowsSharedDriveUtil windowsSharedDriveUtil;

    public String s3ObjectDownload(String path) {
        //String fileName = path.substring(path.lastIndexOf("/")+1);
        //String bucket = path.substring(0,path.indexOf("/"));
        String bucket = path.substring(0,path.indexOf("/"));
        System.out.println("bucket name : "+bucket);
        String fileName = path.substring(path.indexOf("/")+1);
        String fName = path.substring(path.lastIndexOf("/")+1);
        S3Object s3Object = amazonS3Client.getObject(bucket, fileName );
//        ObjectMetadata objectMetadata = amazonS3Client.getObjectMetadata(s3Config.getBucket(), fileName);
//        System.out.println("last modified.... " + objectMetadata.getLastModified());
        InputStream inputStream = s3Object.getObjectContent();
        //String s3FileName = "";
        try {
            File localFile = File.createTempFile(fName, "");
            // write S3Object stream into a temp file
            Files.copy(inputStream, localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            //Switch either windows or sftp based on the requirement
            sftpUtil.copyFileToSftp(localFile);
            //windowsSharedDriveUtil.copyFileToWindosSharedPath(localFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "File Downloaded";
    }

    public List<String> listObjects() {
        ListObjectsV2Result listObjectsV2Result = amazonS3Client.listObjectsV2(s3Config.getBucket());
        List<String> list = new ArrayList<>();
        for (S3ObjectSummary s3ObjectSummary : listObjectsV2Result.getObjectSummaries()) {
            list.add(s3ObjectSummary.getKey());
        }
        return list;
    }

    public void sftpTest() {
        sftpUtil.connectSFTP();
        ;
    }

    public void uploadObject(MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getBytes().length);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(file.getBytes());

            PutObjectRequest putObjectRequest = new PutObjectRequest(s3Config.getBucket(),
                    file.getOriginalFilename(), byteArrayInputStream, metadata);
            amazonS3Client.putObject(putObjectRequest);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
//
//    public boolean saveFilesToSFTP(Context context, File... files) {
//        // this is for test only - In real application, I would suggest that
//        // do NOT store these information in the code.
//        // You should use service like Secrets Manager or Parameter Store
//        final String sftpHostname = "==== SFTP Hostname ====";
//        final String sftpUsername = "==== SFTP Username ====";
//        final String sftpPassword = "==== SFTP Password ====";
//
//        String remoteFolderPath = "/root/S3Files/";
//
//        try {
//            SSHClient ssh = new SSHClient();
//            ssh.addHostKeyVerifier((hostname1, port, key) -> true);
//
//            ssh.connect(sftpHostname);
//            logger.log("SSHClient Connected!");
//
//            try {
//                ssh.authPassword(sftpUsername, sftpPassword);
//                logger.log("SSHClient Authenticated!");
//
//                try (SFTPClient sftp = ssh.newSFTPClient()) {
//                    for(File file : files) {
//                        sftp.put(new FileSystemFile(file), remoteFolderPath);
//                    }
//                } catch (Exception e) {
//                    logger.log("failed to get SFTPClient: " + e.toString());
//                    return false;
//                }
//            } finally {
//                ssh.disconnect();
//            }
//        } catch (Exception e) {
//            logger.log("SFTP connection failed: " + e.toString());
//            return false;
//        }
//
//        return true;
//    }

//    public static void main(String[] args) {
//        String str = "bucket/folder/ravi.txt";
//        String bucket = str.substring(0,str.indexOf("/"));
//        System.out.println("bucket name : "+bucket);
//        String remainPath = str.substring(str.indexOf("/"));
//        System.out.println(remainPath);
//    }
}