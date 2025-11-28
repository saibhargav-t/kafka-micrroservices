package com.kafka.dao.impl;

import org.springframework.stereotype.Repository;

import com.kafka.dao.DAO;
import com.kafka.entity.WikimediaEntity;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class DaoImpl implements DAO{

	private final EntityManager entityManager;
	
	@Override
	@Transactional
	public void saveMessage(WikimediaEntity wikimediaEntity) {
		entityManager.persist(wikimediaEntity);
	}

}
