package com.ravindra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "windows")
public class WindowsConfig {
    private String url;
    private String userName;
    private String password;
}