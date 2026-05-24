package com.clients.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClientEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(ClientEventPublisher.class);

    public static final String EXCHANGE = RabbitConfig.TRACKER_EXCHANGE;
    public static final String ROUTING_KEY_CLIENTE_REGISTRADO = "cliente.registro.creado";

    private final RabbitTemplate rabbitTemplate;

    public ClientEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishClientCreated(ClientCreatedEvent event) {
        log.info("Publicando evento cliente.registro.creado para cliente id={}", event.getId());
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_CLIENTE_REGISTRADO, event);
    }
}
