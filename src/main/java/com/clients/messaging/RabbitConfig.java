package com.clients.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	public static final String TRACKER_EXCHANGE = "cda.domain.events";

	@Bean
	public Queue clientServiceQueue(@Value("${app.messaging.client-queue}") String queueName) {
		return QueueBuilder.durable(queueName).build();
	}

	@Bean
	public TopicExchange trackerExchange() {
		return ExchangeBuilder.topicExchange(TRACKER_EXCHANGE).durable(true).build();
	}

	@Bean
	public Queue trackerServiceQueue(@Value("${app.messaging.tracker-queue}") String queueName) {
		return QueueBuilder.durable(queueName).build();
	}

	@Bean
	public Binding trackerBinding(TopicExchange trackerExchange, Queue trackerServiceQueue) {
		return BindingBuilder.bind(trackerServiceQueue)
				.to(trackerExchange)
				.with("cliente.registro.creado");
	}
}
