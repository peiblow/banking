package com.example.bank.services;

import com.example.bank.domain.user.User;
import com.example.bank.dtos.NotificationDTO;

import com.example.bank.utils.aws.SQS;
import lombok.extern.slf4j.Slf4j;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
public class NotificationService {
    @Autowired
    private SQS sqs;

    public void sendNotification (User user, String message) throws Exception {
        try {
            String email = user.getEmail();
            String sqsMessage = "{\"from\": \"example@example.com\", \"to\": \"%s\", \"subject\": \"Uma nova transação foi efetuada!\", \"content\": \"%s\"}";
            sqsMessage = String.format(sqsMessage, email, message);
            sqs.sendMessage(sqsMessage);
        } catch (Exception e) {
            log.error("Erro ao enviar email: " + e.getMessage());
            throw new Exception("Erro ao enviar email: " + e.getMessage());
        }
    }
}
