package com.ravindra.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.ravindra.config.S3Config;
import com.ravindra.util.SFTPUtil;
import com.ravindra.util.WindowsSharedDriveUtil;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;

import net.schmizz.sshj.SSHClient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {S3Service.class, S3Config.class})
@ExtendWith(SpringExtension.class)
class S3ServiceTest {
    @MockBean
    private AmazonS3 amazonS3;

    @Autowired
    private S3Config s3Config;

    @Autowired
    private S3Service s3Service;

    @MockBean
    private SFTPUtil sFTPUtil;

    @MockBean
    private WindowsSharedDriveUtil windowsSharedDriveUtil;

    @Test
    void testListObjects() throws SdkClientException {
        ListObjectsV2Result listObjectsV2Result = new ListObjectsV2Result();
        listObjectsV2Result.setBucketName("bucket-name");
        listObjectsV2Result.setCommonPrefixes(new ArrayList<>());
        listObjectsV2Result.setContinuationToken("ABC123");
        listObjectsV2Result.setDelimiter("Delimiter");
        listObjectsV2Result.setEncodingType("UTF-8");
        listObjectsV2Result.setKeyCount(3);
        listObjectsV2Result.setMaxKeys(3);
        listObjectsV2Result.setNextContinuationToken("ABC123");
        listObjectsV2Result.setPrefix("Prefix");
        listObjectsV2Result.setStartAfter("Start After");
        listObjectsV2Result.setTruncated(true);
        when(this.amazonS3.listObjectsV2((String) any())).thenReturn(listObjectsV2Result);
        assertTrue(this.s3Service.listObjects().isEmpty());
        verify(this.amazonS3).listObjectsV2((String) any());
    }

    @Test
    void testSftpTest() {
        // TODO: This test is incomplete.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     S3Service.amazonS3Client
        //     S3Service.s3Config
        //     S3Service.sftpUtil
        //     S3Service.windowsSharedDriveUtil

        when(this.sFTPUtil.connectSFTP()).thenReturn(new SSHClient());
        this.s3Service.sftpTest();
    }

    @Test
    void testUploadObject() throws SdkClientException, UnsupportedEncodingException {
        PutObjectResult putObjectResult = new PutObjectResult();
        putObjectResult.setBucketKeyEnabled(true);
        putObjectResult.setContentMd5("MjdjN2NmNDAwMjI5MTAzZTAwYzZkODgzMDAyOWUyOWI=");
        putObjectResult.setETag("E Tag");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        putObjectResult.setExpirationTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        putObjectResult.setExpirationTimeRuleId("42");
        putObjectResult.setMetadata(new ObjectMetadata());
        putObjectResult.setRequesterCharged(true);
        putObjectResult.setSSEAlgorithm("Algorithm");
        putObjectResult.setSSECustomerAlgorithm("Algorithm");
        putObjectResult.setSSECustomerKeyMd5("27c7cf400229103e00c6d8830029e29b");
        putObjectResult.setVersionId("42");
        when(this.amazonS3.putObject((com.amazonaws.services.s3.model.PutObjectRequest) any())).thenReturn(putObjectResult);
        this.s3Service.uploadObject(new MockMultipartFile("Name", "AAAAAAAA".getBytes("UTF-8")));
        verify(this.amazonS3).putObject((com.amazonaws.services.s3.model.PutObjectRequest) any());
    }
}

