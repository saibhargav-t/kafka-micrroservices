package com.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class TopicsConfig {

	@Bean
	NewTopic kafkaProducer() {
		log.info("Creating Kafka Topic Wikimedia producer...");
		return TopicBuilder.name("wikimedia").partitions(3).build();
	}
}
