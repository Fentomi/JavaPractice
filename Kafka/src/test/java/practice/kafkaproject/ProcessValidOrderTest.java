package practice.kafkaproject;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import practice.kafkaproject.dto.OrderResult;
import practice.kafkaproject.producer.OrderProducer;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
public class ProcessValidOrderTest extends BaseTest {

  @Autowired
  private OrderProducer orderProducer;

  @ParameterizedTest
  @CsvSource({"1, 1", "2, 228", "3, 1337"})
  void shouldProcessValidOrder(String orderId, Integer amount) {
    orderProducer.send(createOrder(orderId, amount));

    OrderResult result = returnOrderResult(orderId);

    assertThat(result.getStatus()).isEqualTo("ACCEPTED");
    assertThat(result.getOrderId()).isEqualTo(orderId);
  }
}
