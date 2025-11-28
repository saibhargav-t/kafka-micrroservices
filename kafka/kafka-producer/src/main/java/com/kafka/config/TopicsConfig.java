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

/**
 * TopicsConfig is a configuration class that defines a Kafka topic for
 * Wikimedia changes.
 * It uses the NewTopic bean to create a topic named "wikimedia" with 3
 * partitions.
 */
