package practice.kafkaproject;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.testcontainers.junit.jupiter.Testcontainers;
import practice.kafkaproject.dto.OrderResult;
import practice.kafkaproject.producer.OrderProducer;

import java.time.Duration;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
public class RejectNegativeAmountTest extends BaseTest {

  @Autowired
  private OrderProducer orderProducer;

  @ParameterizedTest
  @CsvSource({"4, 0", "5, -1", "6, -228", "8, -1337"})
  void shouldRejectOrderWithNegativeAmount(String orderId, Integer amount) {
    orderProducer.send(createOrder(orderId, amount));

    OrderResult result = returnOrderResult(orderId);

    assertThat(result.getStatus()).isEqualTo("REJECTED");
    assertThat(result.getOrderId()).isEqualTo(orderId);
  }
}
