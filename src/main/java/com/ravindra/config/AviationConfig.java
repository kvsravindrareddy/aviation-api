package com.ravindra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aviation")
@Setter
@Getter
public class AviationConfig {
    private String baseUrl;
}
