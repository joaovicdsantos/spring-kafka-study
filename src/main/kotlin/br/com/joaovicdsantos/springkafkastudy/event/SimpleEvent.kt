package br.com.joaovicdsantos.springkafkastudy.event

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class SimpleEvent(
    val id: Long,
    val name: String,
    val lastName: String,
    val message: String
)