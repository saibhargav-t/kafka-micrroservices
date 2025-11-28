# Apache Kafka Microservices Tutorial

A comprehensive hands-on project demonstrating **Apache Kafka** integration with **Spring Boot microservices**. This repository uses **Wikimedia's real-time event stream** to simulate live message flow between producer and consumer services.

> **Note:** This is a learning project designed to help developers understand Kafka fundamentals and microservices communication patterns. It is not production-ready code.

---

## 📋 Table of Contents

- [Apache Kafka Microservices Tutorial](#apache-kafka-microservices-tutorial)
  - [📋 Table of Contents](#-table-of-contents)
  - [🎯 Overview](#-overview)
  - [🏗️ Architecture](#️-architecture)
  - [✨ Features](#-features)
  - [📦 Prerequisites](#-prerequisites)
  - [📁 Project Structure](#-project-structure)
    - [📂 Module Descriptions](#-module-descriptions)
      - [1. **Parent Project** (`kafka/`)](#1-parent-project-kafka)
      - [2. **Kafka Producer** (`kafka-producer/`)](#2-kafka-producer-kafka-producer)
      - [3. **Kafka Consumer** (`kafka-consumer/`)](#3-kafka-consumer-kafka-consumer)
  - [🛠️ Technology Stack](#️-technology-stack)
    - [Core Technologies](#core-technologies)
    - [Key Libraries](#key-libraries)
  - [🚀 Getting Started](#-getting-started)
    - [Installing Kafka](#installing-kafka)
    - [Cloning the Repository](#cloning-the-repository)
    - [Building the Project](#building-the-project)
    - [Running the Application](#running-the-application)
      - [Option 1: Run from IDE (Recommended for Development)](#option-1-run-from-ide-recommended-for-development)
      - [Option 2: Run from Command Line](#option-2-run-from-command-line)
      - [Option 3: Run Packaged JARs](#option-3-run-packaged-jars)
  - [⚙️ Configuration](#️-configuration)
    - [Kafka Producer Configuration](#kafka-producer-configuration)
    - [Kafka Consumer Configuration](#kafka-consumer-configuration)
  - [🔍 How It Works](#-how-it-works)
    - [Producer Flow](#producer-flow)
    - [Consumer Flow](#consumer-flow)
  - [💡 Key Concepts](#-key-concepts)
    - [1. **Kafka Topics**](#1-kafka-topics)
    - [2. **Producer Pattern**](#2-producer-pattern)
    - [3. **Consumer Pattern**](#3-consumer-pattern)
    - [4. **Event Source (SSE)**](#4-event-source-sse)
    - [5. **Multi-Module Maven**](#5-multi-module-maven)
  - [🐛 Troubleshooting](#-troubleshooting)
    - [Common Issues](#common-issues)
      - [1. **Kafka Connection Refused**](#1-kafka-connection-refused)
      - [2. **Topic Not Found**](#2-topic-not-found)
      - [3. **Database Connection Error**](#3-database-connection-error)
      - [4. **Port Already in Use**](#4-port-already-in-use)
      - [5. **Wikimedia Stream Connection Timeout**](#5-wikimedia-stream-connection-timeout)
  - [🤝 Contributing](#-contributing)
    - [Reporting Issues](#reporting-issues)
  - [📚 Learning Resources](#-learning-resources)
  - [🙏 Acknowledgments](#-acknowledgments)

---

## 🎯 Overview

This project demonstrates:

- **Real-time data streaming** using Wikimedia's recent changes API
- **Producer-Consumer pattern** with Apache Kafka
- **Microservices architecture** using Spring Boot
- **Event-driven communication** between services
- **Data persistence** with MySQL database

The application streams live Wikipedia edit events, processes them through Kafka, and stores them in a database for further analysis.

---

## 🏗️ Architecture

``` text
┌─────────────────────┐
│  Wikimedia Stream   │
│  (Event Source)     │
└──────────┬──────────┘
           │ SSE (Server-Sent Events)
           ▼
┌─────────────────────┐
│  Kafka Producer     │
│  (Spring Boot)      │
└──────────┬──────────┘
           │ Publishes to Topic
           ▼
┌─────────────────────┐
│   Kafka Broker      │
│   (Topic: wikimedia)│
└──────────┬──────────┘
           │ Consumes from Topic
           ▼
┌─────────────────────┐
│  Kafka Consumer     │
│  (Spring Boot)      │
└──────────┬──────────┘
           │ Persists Data
           ▼
┌─────────────────────┐
│   MySQL Database    │
└─────────────────────┘
```

---

## ✨ Features

- ✅ Real-time event streaming from Wikimedia
- ✅ Asynchronous message processing with Kafka
- ✅ Multi-module Maven project structure
- ✅ Spring Boot integration with Kafka
- ✅ Database persistence with JPA/Hibernate
- ✅ Lombok for reduced boilerplate code
- ✅ SLF4J logging for debugging
- ✅ KRaft mode (no Zookeeper dependency)

---

## 📦 Prerequisites

Before running this project, ensure you have the following installed:

| Tool             | Version | Purpose                 |
| ---------------- | ------- | ----------------------- |
| **Java**         | 17+     | Runtime environment     |
| **Maven**        | 3.6+    | Build tool              |
| **Apache Kafka** | 3.0+    | Message broker          |
| **MySQL**        | 8.0+    | Database (for consumer) |

> **Important:** This project uses **Kafka KRaft mode** (Kafka Raft metadata mode), which eliminates the need for Apache Zookeeper. Kafka now manages its own metadata internally.

---

## 📁 Project Structure

``` text
kafka-microservices/
│
├── kafka/                          # Parent project directory
│   ├── pom.xml                     # Parent POM (packaging: pom)
│   │
│   ├── kafka-producer/             # Producer microservice
│   │   ├── src/
│   │   │   └── main/
│   │   │       ├── java/
│   │   │       │   └── com/kafka/
│   │   │       │       ├── SpringBootProducerApplication.java
│   │   │       │       ├── config/
│   │   │       │       │   └── TopicsConfig.java
│   │   │       │       ├── handler/
│   │   │       │       │   └── WikimediaEventHandler.java
│   │   │       │       └── producer/
│   │   │       │           └── WikimediaChangesProducer.java
│   │   │       └── resources/
│   │   │           └── application.properties
│   │   └── pom.xml                 # Producer POM (packaging: jar)
│   │
│   └── kafka-consumer/             # Consumer microservice
│       ├── src/
│       │   └── main/
│       │       ├── java/
│       │       │   └── com/kafka/
│       │       │       ├── SpringConsumerApplication.java
│       │       │       ├── consumer/
│       │       │       │   └── KafkaConsumerConfig.java
│       │       │       ├── dao/
│       │       │       │   ├── DAO.java
│       │       │       │   └── impl/
│       │       │       │       └── DaoImpl.java
│       │       │       └── entity/
│       │       │           └── WikimediaEntity.java
│       │       └── resources/
│       │           └── application.properties
│       └── pom.xml                 # Consumer POM (packaging: jar)
│
└── Readme.md                       # This file
```

### 📂 Module Descriptions

#### 1. **Parent Project** (`kafka/`)

- **Purpose:** Manages common dependencies and configurations
- **Packaging:** `pom`
- **Key Dependencies:**
  - Spring Boot Starter Kafka
  - Spring Boot Starter Web MVC
  - Lombok
  - Jackson (JSON processing)

#### 2. **Kafka Producer** (`kafka-producer/`)

- **Purpose:** Connects to Wikimedia stream and publishes events to Kafka
- **Packaging:** `jar`
- **Key Components:**
  - `WikimediaChangesProducer`: Main producer service
  - `WikimediaEventHandler`: Handles incoming SSE events
  - `TopicsConfig`: Kafka topic configuration
- **Additional Dependencies:**
  - OkHttp EventSource (for SSE)
  - OkHttp (HTTP client)

#### 3. **Kafka Consumer** (`kafka-consumer/`)

- **Purpose:** Consumes messages from Kafka and persists to database
- **Packaging:** `jar`
- **Key Components:**
  - `KafkaConsumerConfig`: Kafka listener configuration
  - `WikimediaEntity`: JPA entity for database
  - `DAO` & `DaoImpl`: Data access layer
- **Additional Dependencies:**
  - Spring Data JPA
  - MySQL Connector

---

## 🛠️ Technology Stack

### Core Technologies

- **Java 25** (compatible with Java 17+)
- **Spring Boot 4.0.0**
- **Apache Kafka** (with KRaft mode)
- **Maven** (Multi-module project)

### Key Libraries

| Library                        | Purpose                   |
| ------------------------------ | ------------------------- |
| `spring-boot-starter-kafka`    | Kafka integration         |
| `spring-boot-starter-web`      | REST API support          |
| `spring-boot-starter-data-jpa` | Database ORM              |
| `mysql-connector-j`            | MySQL driver              |
| `lombok`                       | Reduce boilerplate code   |
| `okhttp-eventsource`           | Server-Sent Events client |
| `jackson-databind`             | JSON serialization        |

---

## 🚀 Getting Started

### Installing Kafka

1. **Download Kafka:**
   - Visit [Apache Kafka Downloads](https://kafka.apache.org/downloads)
   - Download the latest stable release (binary download)

2. **Extract the Archive:**

   ```bash
   tar -xzf kafka_2.13-3.x.x.tgz
   cd kafka_2.13-3.x.x
   ```

3. **Start Kafka Server (KRaft Mode):**

   **On Linux/Mac:**

   ```bash
   # Generate a cluster UUID
   KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
   
   # Format the storage directory
   bin/kafka-storage.sh format -t $KAFKA_CLUSTER_ID -c config/kraft/server.properties
   
   # Start Kafka server
   bin/kafka-server-start.sh config/kraft/server.properties
   ```

   **On Windows:**

   ```cmd
   # Generate a cluster UUID
   bin\windows\kafka-storage.bat random-uuid
   
   # Format the storage directory (replace <UUID> with generated UUID)
   bin\windows\kafka-storage.bat format -t <UUID> -c config\kraft\server.properties
   
   # Start Kafka server
   bin\windows\kafka-server-start.bat config\kraft\server.properties
   ```

4. **Verify Kafka is Running:**
   - Kafka should be running on `localhost:9092`
   - Check logs for any errors

> **Note:** With KRaft mode, you don't need to start Zookeeper separately!

---

### Cloning the Repository

```bash
git clone https://github.com/yourusername/kafka-microservices.git
cd kafka-microservices
```

---

### Building the Project

Navigate to the parent project directory and build all modules:

```bash
cd kafka
mvn clean install
```

This will:

- Compile all source code
- Run tests
- Package both microservices as JAR files
- Install artifacts to local Maven repository

---

### Running the Application

#### Option 1: Run from IDE (Recommended for Development)

1. **Import the project** into your IDE (IntelliJ IDEA, Eclipse, VS Code)
2. **Run Kafka Producer:**
   - Navigate to `kafka-producer/src/main/java/com/kafka/SpringBootProducerApplication.java`
   - Run as Java Application
3. **Run Kafka Consumer:**
   - Navigate to `kafka-consumer/src/main/java/com/kafka/SpringConsumerApplication.java`
   - Run as Java Application

#### Option 2: Run from Command Line

**Terminal 1 - Start Producer:**

```bash
cd kafka/kafka-producer
mvn spring-boot:run
```

**Terminal 2 - Start Consumer:**

```bash
cd kafka/kafka-consumer
mvn spring-boot:run
```

#### Option 3: Run Packaged JARs

```bash
# Build the project first
cd kafka
mvn clean package

# Run producer
java -jar kafka-producer/target/kafka-producer-1.0.0.jar

# Run consumer (in another terminal)
java -jar kafka-consumer/target/kafka-consumer-1.0.0.jar
```

---

## ⚙️ Configuration

### Kafka Producer Configuration

**File:** `kafka-producer/src/main/resources/application.properties`

```properties
# Server Configuration
server.port=8080

# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer

# Topic Configuration
kafka.topic.name=wikimedia
```

### Kafka Consumer Configuration

**File:** `kafka-consumer/src/main/resources/application.properties`

```properties
# Server Configuration
server.port=8081

# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=myGroup
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/wikimedia_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> **Important:** Update database credentials before running the consumer!

---

## 🔍 How It Works

### Producer Flow

1. **Application Starts:**
   - `SpringBootProducerApplication` initializes Spring context
   - `TopicsConfig` creates the `wikimedia` topic if it doesn't exist

2. **Connect to Wikimedia Stream:**
   - `WikimediaChangesProducer.sendMessage()` is called
   - Establishes SSE connection to `https://stream.wikimedia.org/v2/stream/recentchange`
   - Uses `BackgroundEventSource` for async event handling

3. **Process Events:**
   - `WikimediaEventHandler` receives each event
   - Extracts event data and sends to Kafka topic using `KafkaTemplate`

4. **Continuous Streaming:**
   - Runs for 10 minutes (configurable)
   - Handles reconnection automatically

### Consumer Flow

1. **Application Starts:**
   - `SpringConsumerApplication` initializes Spring context
   - `KafkaConsumerConfig` registers listener for `wikimedia` topic

2. **Listen for Messages:**
   - `@KafkaListener` annotation enables automatic message consumption
   - Consumer group `myGroup` ensures message distribution

3. **Process Messages:**
   - `consumeMessage()` method is invoked for each message
   - Creates `WikimediaEntity` object
   - Persists to MySQL database via `DAO`

4. **Logging:**
   - All consumed messages are logged for debugging

---

## 💡 Key Concepts

### 1. **Kafka Topics**

- **Topic Name:** `wikimedia`
- **Purpose:** Channel for message communication
- **Partitions:** Default (can be configured)
- **Replication Factor:** 1 (single broker setup)

### 2. **Producer Pattern**

- **Asynchronous Publishing:** Non-blocking message sending
- **Serialization:** String-based key-value pairs
- **Idempotence:** Can be enabled for exactly-once semantics

### 3. **Consumer Pattern**

- **Consumer Group:** `myGroup` - enables load balancing
- **Auto-offset Reset:** `earliest` - reads from beginning on first run
- **Deserialization:** String-based key-value pairs

### 4. **Event Source (SSE)**

- **Protocol:** Server-Sent Events over HTTP
- **Library:** OkHttp EventSource
- **Reconnection:** Automatic retry on connection loss

### 5. **Multi-Module Maven**

- **Parent POM:** Manages versions and common dependencies
- **Child Modules:** Inherit from parent, add specific dependencies
- **Packaging:** Parent uses `pom`, children use `jar`

---

## 🐛 Troubleshooting

### Common Issues

#### 1. **Kafka Connection Refused**

``` text
Error: Connection to node -1 (localhost/127.0.0.1:9092) could not be established
```

**Solution:**

- Ensure Kafka server is running
- Check `bootstrap-servers` configuration
- Verify port 9092 is not blocked by firewall

#### 2. **Topic Not Found**

``` text
Error: Topic 'wikimedia' not found
```

**Solution:**

- Enable auto-topic creation in Kafka config
- Or manually create topic:

  ```bash
  bin/kafka-topics.sh --create --topic wikimedia --bootstrap-server localhost:9092
  ```

#### 3. **Database Connection Error**

``` text
Error: Access denied for user 'root'@'localhost'
```

**Solution:**

- Verify MySQL is running
- Check database credentials in `application.properties`
- Create database: `CREATE DATABASE wikimedia_db;`

#### 4. **Port Already in Use**

``` text
Error: Port 8080 is already in use
```

**Solution:**

- Change port in `application.properties`:

  ```properties
  server.port=8082
  ```

#### 5. **Wikimedia Stream Connection Timeout**

``` text
Error: Connect timeout
```

**Solution:**

- Check internet connection
- Verify Wikimedia API is accessible
- Increase timeout in `WikimediaChangesProducer`:

  ```java
  .connectTimeout(5, TimeUnit.MINUTES)
  ```

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the repository**
2. **Create a feature branch:**

   ```bash
   git checkout -b feature/amazing-feature
   ```

3. **Commit your changes:**

   ```bash
   git commit -m 'Add some amazing feature'
   ```

4. **Push to the branch:**

   ```bash
   git push origin feature/amazing-feature
   ```

5. **Open a Pull Request**

### Reporting Issues

Found a bug or have a suggestion? Please [open an issue](https://github.com/yourusername/kafka-microservices/issues) with:

- Clear description of the problem
- Steps to reproduce
- Expected vs actual behavior
- Environment details (OS, Java version, Kafka version)

---

## 📚 Learning Resources

- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Spring for Apache Kafka](https://spring.io/projects/spring-kafka)
- [Wikimedia Event Streams](https://wikitech.wikimedia.org/wiki/Event_Platform/EventStreams)
- [Server-Sent Events (SSE)](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events)

---

## 🙏 Acknowledgments

- Apache Kafka community for excellent documentation
- Wikimedia Foundation for providing the event stream API
- Spring Boot team for seamless Kafka integration
- All contributors who help improve this project

- **Happy Learning! 🚀**
- If you found this project helpful, please consider giving it a ⭐ on GitHub!

---
