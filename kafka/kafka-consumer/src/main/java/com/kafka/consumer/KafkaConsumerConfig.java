package com.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kafka.dao.DAO;
import com.kafka.entity.WikimediaEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerConfig {

	private final DAO dao;

	@KafkaListener(topics = "wikimedia", groupId = "myGroup")
	void consumeMessage(String message) {
		log.info("Saving messages to database...");
		log.info("Consumed message: {}", message);
		WikimediaEntity wikimediaEntity = new WikimediaEntity();
		wikimediaEntity.setMessage(message);
		dao.saveMessage(wikimediaEntity);

	}

}
/**
 * KafkaConsumerConfig is a configuration class that defines a Kafka listener to
 * consume messages from a topic.
 * It uses the `@KafkaListener` annotation to specify the topic and group ID.
 *
 * - `@KafkaListener`: A listener that consumes messages from a Kafka topic.
 * - `topics`: The topic from which messages are consumed.
 * - `groupId`: The group ID of the listener.
 *
 * The `consumeMessage()` method is called when a message is received.
 * It logs the message and saves it to the database using the DAO.
 */