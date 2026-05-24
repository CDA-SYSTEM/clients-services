package com.clients.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(ClientEventPublisher.class);

    public static final String ROUTING_KEY_CLIENTE_REGISTRADO = "cliente.registro.creado";

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;

    public ClientEventPublisher(RabbitTemplate rabbitTemplate,
                                @Value("${app.messaging.tracker-exchange}") String exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void publishClientCreated(ClientCreatedEvent event) {
        log.info("Publicando evento cliente.registro.creado para cliente id={}", event.getId());
        rabbitTemplate.convertAndSend(exchange, ROUTING_KEY_CLIENTE_REGISTRADO, event);
    }
}
