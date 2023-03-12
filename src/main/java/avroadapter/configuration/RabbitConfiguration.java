package avroadapter.configuration;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Rabbit configuration and queues. */
@AllArgsConstructor
@Configuration
public class RabbitConfiguration {

  private final RabbitConfig rabbitConfig;

  @Bean
  public CachingConnectionFactory connectionFactory() {
    return new CachingConnectionFactory(rabbitConfig.getHost());
  }

  @Bean
  public RabbitAdmin amqpAdmin() {
    return new RabbitAdmin(connectionFactory());
  }

  @Bean
  public RabbitTemplate rabbitTemplate() {
    return new RabbitTemplate(connectionFactory());
  }

  @Bean
  public Queue adapterQueue() {
    return new Queue("supplierc-adapter");
  }

  @Bean
  Queue purchaseQueue() {
    return new Queue("adapter-purchase", true);
  }

  @Bean
  Queue supplierQueue() {
    return new Queue("adapter-supplierc", true);
  }
}
