package com.unibite.unibit_backend.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderConsumer {
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "order-status",groupId="order-group")
    public void consume(String message){
        System.out.println("Kafka recieved: "+message);
        messagingTemplate.convertAndSend("/topic/orders",message);
    }
}
