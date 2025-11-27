package com.kafka.handler;

import org.springframework.kafka.core.KafkaTemplate;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class WikimediaEventHandler implements BackgroundEventHandler {

	private KafkaTemplate<String, String> kafkaTemplate;
	private String topic;



	@Override
	public void onMessage(String event, MessageEvent messageEvent) throws Exception {
		kafkaTemplate.send(topic, messageEvent.getData());
		log.info("Event data -> {}", messageEvent.getData());
	}
	@Override
    public void onError(Throwable t) {
        log.error("Streaming error in WikimediaEventHandler", t);
    }

    @Override
    public void onOpen() throws Exception {
        log.info("Wikimedia stream opened");
    }

    @Override
    public void onClosed() throws Exception {
        log.info("Wikimedia stream closed");
    }

    @Override
    public void onComment(String comment) throws Exception {
        // Optional, but can help debug:
        log.debug("Comment from stream: {}", comment);
    }

}
