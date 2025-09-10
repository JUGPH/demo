# Kafka Producer Demo

A Spring Boot application that demonstrates producing Avro-serialized messages to an Apache Kafka topic using Confluent Cloud.

## Features

- Produces messages to a Kafka topic using Spring for Apache Kafka
- Uses Avro for message serialization with Confluent Schema Registry
- Secure connection to Confluent Cloud with SASL/SSL
- Simple REST API for sending messages
- Auto-generated Avro Java classes from schema

## Prerequisites

- Java 21 or later
- Gradle 8.0 or later
- Confluent Cloud account (or local Kafka cluster with Schema Registry)
- Maven (for dependency resolution)

## Configuration

Before running the application, you need to configure the following in `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: kafka-meetup-producer-demo

  kafka:
    bootstrap-servers: <bootstrap servers>:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: io.confluent.kafka.serializers.KafkaAvroSerializer
      properties:
        schema:
          registry:
            url: <schema registry url>
        basic:
          auth:
            credentials:
              source: USER_INFO
            user:
              info: <api key>:<api secret>
        specific.avro.reader: true
        sasl:
          mechanism: PLAIN
          jaas:
            config: org.apache.kafka.common.security.plain.PlainLoginModule required username='<api key>' password='<api secret>';
        security:
          protocol: SASL_SSL
      client-id: <kafka client id>


jugph:
  topic:
    name: <topic name>
```

## Building the Project

To build the project, run:

```bash
./gradlew build
```

This will:
1. Compile the Java code
2. Generate Avro classes from the schema
3. Run tests
4. Package the application

## Running the Application

To run the application locally:

```bash
./gradlew bootRun
```

The application will start on port 8080 by default.

## API Endpoints

### Send a Message

**Endpoint:** `POST /send`

Send a message to the configured Kafka topic.

**Request Body:**
```
String (plain text message)
```

**Example using curl:**
```bash
curl -X POST http://localhost:8080/send \
  -H "Content-Type: text/plain" \
  -d "Hello, Kafka!"
```

## Message Schema

Messages are serialized using the following Avro schema:

```json
{
  "namespace": "com.rjtmahinay.kafka.avro",
  "type": "record",
  "name": "Message",
  "fields": [
    {"name": "id", "type": "string"},
    {"name": "content", "type": "string"},
    {"name": "timestamp", "type": "long"}
  ]
}
```

## Dependencies

- Spring Boot 3.4.4
- Spring for Apache Kafka
- Apache Avro
- Confluent Kafka Avro Serializer
- Lombok

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.