package com.example.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) {
        final Properties props = new Properties();
        props.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "java-producer");
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        try (KafkaProducer producer = new KafkaProducer<String, String>(props)) {
            for (int i = 0; i < 1; i++) {
                final ProducerRecord message = new ProducerRecord<>(
                        "new-kafka-topic",     //topic name
                        "key-" + i,            // key
                        "message: " + i        // value
                );
                Future result= producer.send(message);
                while(!result.isDone()){
                    sleep(3);
                }
                System.out.println("OK:"+result.isDone());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        SpringApplication.run(Demo1Application.class, args);
    }

}
