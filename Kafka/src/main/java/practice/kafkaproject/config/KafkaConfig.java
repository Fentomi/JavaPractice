package practice.kafkaproject.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

  @Bean
  public NewTopic createdTopic() {
    return new NewTopic("orders.created", 1, (short) 1);
  }

  @Bean
  public NewTopic processedTopic() {
    return new NewTopic("orders.processed", 1, (short) 1);
  }
}
