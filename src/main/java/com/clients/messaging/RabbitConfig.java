package com.clients.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Bean
	public Queue clientServiceQueue(@Value("${app.messaging.client-queue}") String queueName) {
		return QueueBuilder.durable(queueName).build();
	}

	@Bean
	public TopicExchange trackerExchange(@Value("${app.messaging.tracker-exchange}") String exchangeName) {
		return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
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

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
