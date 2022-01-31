package com.ravindra.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ravindra.SFTPUtil;
import com.ravindra.config.S3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    public String s3ObjectDownload(String s3FileName)
    {
        S3Object s3Object = amazonS3Client.getObject(s3Config.getBucket(),s3FileName+".txt");
        InputStream inputStream = s3Object.getObjectContent();
        //String s3FileName = "";
        try {
            File localFile = File.createTempFile(s3FileName,"");
            // write S3Object stream into a temp file
            Files.copy(inputStream, localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            sftpUtil.copyFileToSftp(localFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "File Downloaded";
    }

    public List<String> listObjects() {
      ListObjectsV2Result listObjectsV2Result = amazonS3Client.listObjectsV2(s3Config.getBucket());
      List<String> list = new ArrayList<>();
      for(S3ObjectSummary s3ObjectSummary : listObjectsV2Result.getObjectSummaries())
      {
          list.add(s3ObjectSummary.getKey());
      }
      return list;
    }

    public void sftpTest() {
        sftpUtil.connectSFTP();;
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
}