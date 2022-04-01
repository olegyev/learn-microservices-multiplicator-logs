package learn.microservices.logs.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures RabbitMQ via AMQP abstraction.
 */
@Configuration
public class AmqpConfiguration {

    // Configure exchange.
    @Bean
    public TopicExchange logsTopicExchange(@Value("${amqp.exchange.logs}") final String exchangeName) {
        return ExchangeBuilder
                .topicExchange(exchangeName)
                .durable(true)
                .build();
    }

    // Configure queue.
    @Bean
    public Queue logsQueue(@Value("${amqp.queue.logs}") final String queueName) {
        return QueueBuilder
                // Don't remove queue on broker shutdown.
                .durable(queueName)
                .build();
    }

    // Configure binding key.
    @Bean
    public Binding logsBinding(final Queue logsQueue,
                               final TopicExchange logsExchange) {
        return BindingBuilder
                .bind(logsQueue)
                .to(logsExchange)
                // Get all.
                .with("#");
    }

}