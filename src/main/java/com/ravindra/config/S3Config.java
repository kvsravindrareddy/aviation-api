package com.ravindra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "s3")
public class S3Config {
    private String accessKey;
    private String secretKey;
    private String region;
    private String bucket;
}
