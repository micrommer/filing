package com.micrommer.filing.service

import com.micrommer.filing.domain.LogDetail
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*

/**
 * Project : filing
 * Ashiyane: com.micrommer.filing.service
 * @author : iman
 * @since : December/26/2020 22:03
 */
@Component
class LogService(private val kafkaTemplate: KafkaTemplate<String, LogDetail>) : WebFilter {

    @Value("\${kafka.topic.log}")
    private lateinit var topic: String

    /**
     * Process the Web request and (optionally) delegate to the next
     * `WebFilter` through the given [WebFilterChain].
     * @param exchange the current server exchange
     * @param chain provides a way to delegate to the next filter
     * @return `Mono<Void>` to indicate when request processing is complete
     */
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return exchange.getPrincipal<UsernamePasswordAuthenticationToken>().flatMap {
            val username = it.principal.toString()
            val requestPath = exchange.request.path.toString()
            val requestMethod = exchange.request.methodValue
            val requestDate = Date(System.currentTimeMillis())

            val logDetail = LogDetail(username, requestPath, requestMethod, requestDate)
            kafkaTemplate.send(topic, logDetail)
            chain.filter(exchange)
        }
    }

}