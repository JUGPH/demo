# Kafka Consumer Demo

A Spring Boot application that demonstrates consuming Avro-serialized messages from an Apache Kafka topic using Confluent Cloud. This consumer works in conjunction with the Kafka Producer Demo application.

## Features

- Consumes messages from a Kafka topic using Spring for Apache Kafka
- Uses Avro for message deserialization with Confluent Schema Registry
- Secure connection to Confluent Cloud with SASL/SSL authentication
- Automatic offset management with consumer groups
- Logs consumed messages to the console
- Auto-registers Avro schemas with Confluent Schema Registry

## Prerequisites

- Java 21 or later
- Gradle 8.0 or later (included in the Gradle Wrapper)
- Confluent Cloud account with an active cluster
- Maven (for dependency resolution)

## Configuration

Before running the application, ensure the following configuration in `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: kafka-meetup-consumer-demo

  kafka:
    bootstrap-servers: <bootstrap servers>:9092
    consumer:
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: io.confluent.kafka.serializers.KafkaAvroDeserializer
      properties:
        specific.avro.reader: true
        schema:
          registry:
            url: <schema registry url>
        basic:
          auth:
            credentials:
              source: USER_INFO
            user:
              info: <api key>:<api secret>
        sasl:
          mechanism: PLAIN
          jaas:
            config: org.apache.kafka.common.security.plain.PlainLoginModule required username='<api key>' password='<api secret>';
        security:
          protocol: SASL_SSL
      client-id: <kafka client id>
      group-id: jugph-consumer-group-1
      auto-offset-reset: earliest

server:
  port: 8081

jugph:
  topic:
    name: <topic name>
```

## Building and Running the Project

To build the project, run:

```bash
./gradlew build
```

To run the application:

```bash
./gradlew bootRun
```

The application will start on port 8081 by default and begin consuming messages from the configured Kafka topic.

## Message Processing

The consumer listens to the configured Kafka topic and processes messages with the following Avro schema:

```json
{
  "type": "record",
  "name": "Message",
  "namespace": "com.rjtmahinay.kafka.avro",
  "fields": [
    {"name": "content", "type": "string"},
    {"name": "timestamp", "type": "long", "logicalType": "timestamp-millis"}
  ]
}
```

## Producer Integration

This consumer is designed to work with the Kafka Producer Demo application, which sends messages to the same topic. The consumer will automatically process any messages sent to the configured topic.

## Consumer Group Behavior

- The consumer is configured with `group-id: jugph-consumer-group-1`
- `auto-offset-reset: earliest` ensures the consumer reads all available messages from the beginning when joining the group
- The consumer automatically commits offsets after processing messages

## Security Note

For production use, consider the following security best practices:
1. Store sensitive credentials in environment variables or a secure secrets manager
2. Use proper access controls and IAM roles
3. Rotate API keys and credentials regularly
4. Use proper network security controls (VPC peering, private endpoints, etc.)

## Monitoring and Logging

- The application logs consumed messages at INFO level
- Spring Boot Actuator endpoints are available for health checks and metrics
- Consumer lag and other metrics can be monitored through Confluent Control Center or other monitoring tools

## Troubleshooting

- Ensure the Confluent Cloud cluster is running and accessible
- Verify the API keys and secrets are correct and have the necessary permissions
- Check the application logs for any error messages
- Ensure the consumer group is not blocked by other consumers with the same group ID

## License

This project is for demonstration purposes only. Please ensure you have the proper licenses and permissions when using this code in production.