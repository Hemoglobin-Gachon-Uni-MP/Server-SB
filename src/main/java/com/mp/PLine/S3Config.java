package com.mp.PLine;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class S3Config {
    private final Environment env;

    @Bean
    public AmazonS3 amazonS3(){
        String accessToken = env.getProperty("aws.s3.access-key");
        String refreshToken = env.getProperty("aws.s3.secret-key");

        assert accessToken != null;
        assert refreshToken != null;

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(accessToken, refreshToken)))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }
}
