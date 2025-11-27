package com.kafka.producer;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.kafka.handler.WikimediaEventHandler;
import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class WikimediaChangesProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage() throws InterruptedException {
		String topic = "wikimedia";
		BackgroundEventHandler eventhandler = new WikimediaEventHandler(kafkaTemplate, topic);
		String url = "https://stream.wikimedia.org/v2/stream/recentchange";
		// Step 1: Create ConnectStrategy
		ConnectStrategy connectStrategy = ConnectStrategy
		        .http(URI.create(url))
		        .header("User-Agent", "MyKafkaWikimediaClient/1.0 (your-email@example.com)")
		        .connectTimeout(1, TimeUnit.MINUTES);
		// Step 2: Create EventSource.Builder (NO handler here)
		EventSource.Builder esBuilder = new EventSource.Builder(connectStrategy);

		// Step 3: Create BackgroundEventSource
		BackgroundEventSource bes = new BackgroundEventSource.Builder(eventhandler, esBuilder).build();

		// Step 4: Start listening
		bes.start();
		TimeUnit.MINUTES.sleep(10);
	}

}
