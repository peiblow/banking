package com.example.bank.infra;

import com.amazonaws.client.builder.AwsClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import com.amazonaws.regions.Regions;

@Configuration
public class AwsConfig {

    public AWSCredentials credentials() {
        AWSCredentials credentials = new BasicAWSCredentials(
                "test",
                "test"
        );
        return credentials;
    }

    @Bean
    public AmazonS3 amazonS3() {
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                    "http://localhost:4566",
                    "us-east-1"
                ))
                .withCredentials(new AWSStaticCredentialsProvider(credentials()))
                .enablePathStyleAccess()
                .build();

        return s3client;
    }
}
