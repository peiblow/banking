package com.example.bank.utils.aws;

import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.bank.infra.AwsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SQS {
    @Value("${sqs.queue.url}")
    private String sqsQueueUrl;

    @Autowired
    private AwsConfig awsConfig;

    public void sendMessage(String messageBody) {
        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(sqsQueueUrl)
                .withMessageBody(messageBody);

        awsConfig.amazonSQS().sendMessage(request);
    }
}

