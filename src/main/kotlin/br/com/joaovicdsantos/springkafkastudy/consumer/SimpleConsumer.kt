package br.com.joaovicdsantos.springkafkastudy.consumer

import br.com.joaovicdsantos.springkafkastudy.event.SimpleEvent
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.retrytopic.FixedDelayStrategy
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component

@Component
class SimpleConsumer : ConsumerBase<SimpleEvent>(jacksonObjectMapper()) {

    override val topic = "simple-consumer-topic"

    @RetryableTopic(
        attempts = "3",
        backoff = Backoff(delayExpression = "30000"),
        fixedDelayTopicStrategy = FixedDelayStrategy.SINGLE_TOPIC
    )
    @KafkaListener(topics = ["simple-consumer-topic"])
    override fun onMessage(message: ConsumerRecord<String, String>) = super.onMessage(message)

    override fun consume(eventBody: SimpleEvent) {
        println(eventBody)
    }

}