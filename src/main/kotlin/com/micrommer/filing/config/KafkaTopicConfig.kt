package com.micrommer.filing.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

/**
 * Project : filing
 * Ashiyane: com.micrommer.filing.config
 * @author : iman
 * @since : December/26/2020 22:47
 */
@Configuration
class KafkaTopicConfig {
    @Value("\${kafka.address.host}")
    private lateinit var kafkaHost: String

    @Value("\${kafka.address.port}")
    private lateinit var kafkaPort: String

    @Value("\${kafka.topic.log}")
    private lateinit var topic: String

    private val kafkaAddress by lazy {
        "$kafkaHost:$kafkaPort"
    }

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs: HashMap<String, Any> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaAddress
        return KafkaAdmin(configs)
    }

    @Bean
    fun createTopic(): NewTopic {
        return NewTopic(topic,1,1)
    }
}