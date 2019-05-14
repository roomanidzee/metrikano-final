#!/bin/bash
set -m

echo Waiting for Kafka to be ready... && \
/etc/confluent/docker/configure && \
cub kafka-ready -b kafka-service:9092 1 60 --config /etc/kafka/kafka.properties && \
sleep 5 && \
kafka-topics --zookeeper zookeeper-service:2181 --topic influx-group-topic --create --replication-factor 1 --partitions 1 && \
kafka-topics --zookeeper zookeeper-service:2181 --topic influx-classify-topic --create --replication-factor 1 --partitions 1 && \
kafka-topics --zookeeper zookeeper-service:2181 --topic clickhouse-user-topic --create --replication-factor 1 --partitions 1 && \
kafka-topics --zookeeper zookeeper-service:2181 --topic clickhouse-classify-topic --create --replication-factor 1 --partitions 1 && \
kafka-topics --zookeeper zookeeper-service:2181 --topic user-record-topic --create --replication-factor 1 --partitions 1 && \
kafka-topics --zookeeper zookeeper-service:2181 --topic user-link-topic --create --replication-factor 1 --partitions 1