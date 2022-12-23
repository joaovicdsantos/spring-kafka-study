package br.com.joaovicdsantos.springkafkastudy.consumer

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import java.lang.reflect.ParameterizedType


abstract class ConsumerBase<T>(
   private val objectMapper: ObjectMapper,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    open fun onMessage(message: ConsumerRecord<String, String>) = handle(message, ::consume)

    private fun handle(
        message: ConsumerRecord<String, String>,
        consumeFunction: (T) -> Unit
    ) {
        try {
            val event = objectMapper.readValue<T>(message.value(), getTokenClass())
            consumeFunction(event)
        } catch (ex: Exception) {
            logger.warn("Error when processing message from topic [${message.topic()}]")
            throw ex
        }
    }

    private fun getTokenClass(): JavaType? {
        val type: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        val actualType = type.actualTypeArguments.first()

        return objectMapper.typeFactory.constructType(actualType)
    }

    abstract fun consume(eventBody: T)
    abstract val topic: String

}