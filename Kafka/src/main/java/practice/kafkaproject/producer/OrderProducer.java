package practice.kafkaproject.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import practice.kafkaproject.dto.OrderEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {
  private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

  public void send(OrderEvent event) {
    log.info("Sending order: {}", event);

    kafkaTemplate.send("orders.created", event.getOrderId(), event).join();
  }
}