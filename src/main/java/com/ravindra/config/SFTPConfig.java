package com.ravindra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "sftp")
public class SFTPConfig {
    private String host;
    private String userName;
    private String password;
    private String serverPath;
}