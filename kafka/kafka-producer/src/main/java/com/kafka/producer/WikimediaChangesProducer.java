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

/**
 * WikimediaChangesProducer is a service class that sends Wikimedia changes to a
 * Kafka topic.
 * It uses the KafkaTemplate to send messages to the topic.
 * - `sendMessage()`: Sends a message to the Wikimedia stream.
 * - `ConnectStrategy`: Defines how the event source connects to the remote
 * stream. Here, it uses `http()` to specify the HTTP endpoint, `header()` to
 * add a User-Agent, and `connectTimeout()` to set a connection timeout.
 * - `EventSource.Builder`: A builder class used to configure and create an
 * `EventSource` instance. It takes the `ConnectStrategy` to establish the
 * connection.
 * - `BackgroundEventSource`: An asynchronous event source that runs event
 * processing in a background thread, preventing blocking of the main thread.
 * Its `Builder` takes a `BackgroundEventHandler` to process events and an
 * `EventSource.Builder` for connection details.
 * - `TimeUnit`: An enum representing time durations. `MINUTES.sleep(10)` pauses
 * the current thread for 10 minutes.
 * - `start()`: Initiates the `BackgroundEventSource` to begin listening for and
 * processing events.
 */
