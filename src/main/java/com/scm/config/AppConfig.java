package com.scm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfig {

    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    @Value("${cloudanary.cloud.api.key}")
    private String apikey;
    @Value("${cloudanary.cloud.api.secret}")
    private String apiSecret;

    @Bean
public Cloudinary cloudinary(){


    return new Cloudinary(
        ObjectUtils.asMap(
            "cloud_name",cloudName,
            "api_key",apikey,
            "api_secret",apiSecret)
    );
}

}
