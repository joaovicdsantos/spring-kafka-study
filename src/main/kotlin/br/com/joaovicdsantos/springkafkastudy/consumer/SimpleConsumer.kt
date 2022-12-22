package br.com.joaovicdsantos.springkafkastudy.consumer

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class SimpleConsumer {

    @KafkaListener(topics = ["simple-consumer-topic"])
    fun onMessage(message: ConsumerRecord<String, String>) {
        println("Received message: ${message.value()} | ${message.headers()}")
    }

}