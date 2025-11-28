package com.kafka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kafka.producer.WikimediaChangesProducer;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringBootProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProducerApplication.class);
	}

	private final WikimediaChangesProducer wikimediaChangesProducer;

	@Override
	public void run(String... args) throws Exception {
		wikimediaChangesProducer.sendMessage();
	}
}

/**
 * SpringBootProducerApplication is the main application class that runs the
 * producer application.
 * It implements CommandLineRunner to execute the producer logic when the
 * application starts.
 * - `wikimediaChangesProducer.sendMessage()`: Sends a message to the Wikimedia
 * stream.
 */
