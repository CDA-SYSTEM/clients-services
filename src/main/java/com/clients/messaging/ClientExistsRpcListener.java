package com.clients.messaging;

import com.clients.clients.repository.ClientRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.nio.charset.StandardCharsets;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * RPC compatible with NestJS {@code ClientProxy.send} / {@code @MessagePattern} over RabbitMQ:
 * request body {@code { "pattern", "data", "id" }}, reply {@code { "id", "err", "response" }}.
 */
@Component
public class ClientExistsRpcListener {

	public static final String PATTERN_CLIENT_EXISTS = "cda.client.exists";

	private final RabbitTemplate rabbitTemplate;
	private final ClientRepository clientRepository;
	private final ObjectMapper objectMapper = new ObjectMapper();

	public ClientExistsRpcListener(RabbitTemplate rabbitTemplate, ClientRepository clientRepository) {
		this.rabbitTemplate = rabbitTemplate;
		this.clientRepository = clientRepository;
	}

	@RabbitListener(queues = "${app.messaging.client-queue}", concurrency = "1-4")
	public void onMessage(Message message) throws Exception {
		var props = message.getMessageProperties();
		String replyTo = props.getReplyTo();
		if (replyTo == null || replyTo.isBlank()) {
			return;
		}

		String body = new String(message.getBody(), StandardCharsets.UTF_8);
		JsonNode root = objectMapper.readTree(body);
		String nestCorrelationId = root.path("id").asText(null);
		if (nestCorrelationId == null || nestCorrelationId.isBlank()) {
			nestCorrelationId = props.getCorrelationId();
		}

		String pattern = root.path("pattern").asText("");
		ObjectNode envelope = objectMapper.createObjectNode();
		envelope.put("id", nestCorrelationId != null ? nestCorrelationId : "");

		if (!PATTERN_CLIENT_EXISTS.equals(pattern)) {
			envelope.putNull("response");
			envelope.put("err", "UNKNOWN_PATTERN: " + pattern);
		} else {
			boolean exists = resolveExists(root.path("data"));
			ObjectNode response = objectMapper.createObjectNode();
			response.put("exists", exists);
			envelope.set("response", response);
			envelope.putNull("err");
		}

		byte[] out = objectMapper.writeValueAsBytes(envelope);
		rabbitTemplate.send(
				"",
				replyTo,
				MessageBuilder.withBody(out)
						.setContentType("application/json")
						.setCorrelationId(props.getCorrelationId())
						.build());
	}

	private boolean resolveExists(JsonNode data) {
		if (data == null || data.isNull()) {
			return false;
		}
		JsonNode idNode = data.get("id");
		if (idNode == null || idNode.isNull()) {
			return false;
		}
		String idStr = idNode.isNumber() ? idNode.asText() : idNode.asText("");
		if (idStr.isBlank()) {
			return false;
		}
		try {
			long id = Long.parseLong(idStr.trim());
			return clientRepository.existsById(id);
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
