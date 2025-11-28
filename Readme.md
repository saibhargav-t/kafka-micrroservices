# Apache Kafka Tutorial - Microservices

This repository contains the source code and configuration files for a Kafka-based microservices project. I used Wikimedia for live streaming of messages to replicate live service for continuous flow of messages. This repository demonstrates the use of Kafka for inter-microservices communication. This repository contains 2 micoservices

- Kafka Producer - This microservice produces messages and send them to Kafka topic in Broker.
- Kafka Consumer - This microservice consumes the messages from the Kafka topic and process them.

Note: This repository is created for the purpose of learning Kafka and microservices. It is not a production-ready code.

---

## Prerequisites

- Java 17+
- Maven
- Kafka

_Note_: Kafka introduced Kraft and we do not need to use Zookeeper for Kafka. Kafka became independent of Zookeeper.

---

## Installing Kafka

Install kafka from this  [Kafka Download](https://kafka.apache.org/quickstart). Follow the steps mentioned there.

---

## Running the Application

- Clone the repository
- Navigate to the root directory of the project
- Run the following command to build the project

```bash
mvn clean install
```

- Run the following command to run the application

```bash
mvn spring-boot:run
```

---

## Repository Structure

1. ParentProject
    - This is the parent project for the microservices. It contains the common dependencies and configurations for the microservices.
    - It also contains the Kafka configuration files.
2. KafkaProducer
    - This is the Kafka producer microservice. It produces messages and sends them to the Kafka topic in the broker.
3. KafkaConsumer
    - This is the Kafka consumer microservice. It consumes messages from the Kafka topic and processes them.

In the parent project, create those two modules. Most Ide's auto add the dependencies in the parent project.
The Packaging of parent project should be `pom` and the packaging of child projects should be `jar`.

---

## Important Annotations

1. @SpringBootApplication
    - This annotation is used to mark the main class of the application.
    - It is a convenience annotation that adds all of the following:
        - @Configuration: Tags the class as a source of bean definitions for the application context.
        - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
        - @ComponentScan: Tells Spring to look for other components, configurations, and services in the package, allowing it to find the controllers.
2. @KafkaListener
    - This annotation is used to mark the method that will be called when a message is received.
    - It is a convenience annotation that adds all of the following:
        - @MessageMapping: Maps the method to the message.
        - @SendTo: Sends the message to the specified destination.
3. NewTopic
    - This annotation is used to create a new topic in the Kafka broker.
    - It is a convenience annotation that adds all of the following: Topic name, partitions, and replication factor.
4. KafkaTemplate
    - This annotation is used to send messages to the Kafka topic.
