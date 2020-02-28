package com.imooc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:/resource/resource.properties")
public class ResourceConfig {

    @Value("${fileSpace}")
    private String fileSpace;

    public String getFileSpace() {
        return fileSpace;
    }

    public void setFileSpace(String fileSpace) {
        this.fileSpace = fileSpace;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
