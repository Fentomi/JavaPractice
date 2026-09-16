package practice.kafkaproject.service;

import org.springframework.stereotype.Service;
import practice.kafkaproject.dto.OrderEvent;
import practice.kafkaproject.dto.OrderResult;

@Service
public class OrderService {
  public OrderResult process(OrderEvent event) {
    String status = event.getAmount() > 0 ? "ACCEPTED" : "REJECTED";
    return OrderResult.builder()
        .orderId(event.getOrderId())
        .status(status)
        .build();
  }
}
