package com.micrommer.filing.config

import com.micrommer.filing.domain.LogDetail
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate

/**
 * Project : filing
 * Ashiyane: com.micrommer.filing.config
 * @author : iman
 * @since : December/26/2020 23:00
 */
@Configuration
class KafkaProducerConfig {
    @Value("\${kafka.address.host}")
    private lateinit var kafkaHost: String

    @Value("\${kafka.address.port}")
    private lateinit var kafkaPort: String

    private val kafkaAddress by lazy {
        "$kafkaHost:$kafkaPort"
    }

    @Bean
    fun createKafkaTemplate(): KafkaTemplate<String, LogDetail> {
        val config: MutableMap<String, Any> = HashMap()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaAddress
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        val producer = DefaultKafkaProducerFactory<String, LogDetail>(config)
        return KafkaTemplate(producer)
    }

}