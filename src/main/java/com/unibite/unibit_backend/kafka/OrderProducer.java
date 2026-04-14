package com.unibite.unibit_backend.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;
    public void sendStatusUpdate(Long orderId,String status){
        String message=orderId+":" +status;
        kafkaTemplate.send("order_status",message);
    }
}
