package com.example.bank.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.bank.infra.AwsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private AwsConfig awsConfig;

    @GetMapping
    public ResponseEntity<List<S3ObjectSummary> > getMyTransactions() {
        AmazonS3 amazonS3Client = awsConfig.amazonS3();

        ObjectListing objectListing = amazonS3Client.listObjects("my-bucket");
        List<S3ObjectSummary> objectList = objectListing.getObjectSummaries();

        return new ResponseEntity<>(objectList, HttpStatus.OK);
    }
}
