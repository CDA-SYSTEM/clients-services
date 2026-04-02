package com.clients.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Bean
	public Queue clientServiceQueue(@Value("${app.messaging.client-queue}") String queueName) {
		return QueueBuilder.durable(queueName).build();
	}
}
