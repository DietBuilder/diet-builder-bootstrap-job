package com.dietbuilder.boot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "init-data")
public class InitializationProperties {

    private String filePath;
}
