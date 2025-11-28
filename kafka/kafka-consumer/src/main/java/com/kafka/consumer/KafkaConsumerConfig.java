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
	@KafkaListener(topics = "wikimedia", groupId="myGroup")
	void consumeMessage(String message) {
		log.info("Saving messages to database...");
		log.info("Consumed message: {}", message);
		WikimediaEntity wikimediaEntity = new WikimediaEntity();
		wikimediaEntity.setMessage(message);
		dao.saveMessage(wikimediaEntity);
		
		
	}

}
