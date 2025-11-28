package com.kafka.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wikimedia")
@Data
@NoArgsConstructor
public class WikimediaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Lob
	@Column(name = "message", columnDefinition = "LONGTEXT")
	private String message;
}

/**
 * WikimediaEntity is an entity class that represents a Wikimedia article.
 * It has an ID, a message, and a timestamp.
 */
