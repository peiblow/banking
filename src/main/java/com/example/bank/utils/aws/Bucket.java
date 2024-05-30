package com.example.bank.utils.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.bank.infra.AwsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class Bucket {
    @Autowired
    private AwsConfig awsConfig;

    public void updateObject(String bucketName, String objectName, File file) {
        AmazonS3 amazonS3 = awsConfig.amazonS3();
        amazonS3.putObject(bucketName, objectName, file);
    }
}
