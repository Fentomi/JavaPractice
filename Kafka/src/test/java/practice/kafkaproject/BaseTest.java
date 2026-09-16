package practice.kafkaproject;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;
import practice.kafkaproject.dto.OrderEvent;
import practice.kafkaproject.dto.OrderResult;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.awaitility.Awaitility.await;

@Slf4j
public abstract class BaseTest {
  @Container
  protected static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka:3.9.1"));

  @DynamicPropertySource
  protected static void configureKafka(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
  }

  protected Consumer<String, OrderResult> resultConsumer;

  @Autowired
  private KafkaProperties kafkaProperties;

  @BeforeEach
  protected void setUp() {
    Map<String, Object> props = kafkaProperties.buildConsumerProperties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group" + UUID.randomUUID());
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

    DefaultKafkaConsumerFactory<String, OrderResult> factory = new DefaultKafkaConsumerFactory<>(props,
        new StringDeserializer(), new JsonDeserializer<>(OrderResult.class));

    resultConsumer = factory.createConsumer();
    resultConsumer.subscribe(List.of("orders.processed"));
    resultConsumer.poll(Duration.ofSeconds(1));
  }

  @AfterEach
  protected void tearDown() {
    resultConsumer.close();
  }

  protected OrderEvent createOrder(String orderId, Integer amount) {
    return OrderEvent.builder()
        .orderId(orderId)
        .customer("testCustomer")
        .amount(amount)
        .build();
  }

  protected OrderResult returnOrderResult(String orderId) {
    return await()
        .atMost(Duration.ofSeconds(5))
        .until(() -> {
          ConsumerRecords<String, OrderResult> records =
              KafkaTestUtils.getRecords(resultConsumer);

          return Objects.requireNonNull(StreamSupport.stream(records.spliterator(), false)
              .map(ConsumerRecord::value)
              .filter(r -> r.getOrderId().equals(orderId))
              .peek(r -> log.info(
                  "Received result: orderId={}, status={}",
                  r.getOrderId(),
                  r.getStatus()))
              .findFirst()
              .orElse(null));
        }, obj -> true);
  }
}
