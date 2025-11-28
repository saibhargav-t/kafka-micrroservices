package com.kafka.dao;

import com.kafka.entity.WikimediaEntity;

public interface DAO {

	void saveMessage(WikimediaEntity wikimediaEntity);
}
/**
 * DAO is an interface that defines a contract for saving messages to the
 * database.
 * It has a method `saveMessage()` that takes a `WikimediaEntity` object as a
 * parameter.
 */