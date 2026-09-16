package practice.kafkaproject.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import practice.kafkaproject.dto.OrderEvent;
import practice.kafkaproject.dto.OrderResult;
import practice.kafkaproject.service.OrderService;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {
  private final OrderService orderService;
  private final KafkaTemplate<String, OrderResult> kafkaTemplate;

  @KafkaListener(topics = "orders.created", groupId = "order-group")
  public void consume(OrderEvent event) {
    log.info("Received order event {}", event);

    OrderResult result = orderService.process(event);

    log.info("Processed result {}", result);

    kafkaTemplate.send("orders.processed", result.getOrderId(), result).join();
  }
}
