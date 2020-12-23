package com.micrommer.filing.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * Ashiyane: filing (com.micrommer.filing.security)
 * @author : imanbhlool
 * @since : Dec/23/2020 - 4:33 PM, Wednesday
 */
@Component
class SecurityContextChain(private val authenticationManager: ReactiveAuthenticationManager) : ServerSecurityContextRepository {
    /**
     * Saves the SecurityContext
     * @param exchange the exchange to associate to the SecurityContext
     * @param context the SecurityContext to save
     * @return a completion notification (success or error)
     */
    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        throw UnsupportedOperationException("Not Supported yet ...")
    }

    /**
     * Loads the SecurityContext associated with the [ServerWebExchange]
     * @param exchange the exchange to look up the [SecurityContext]
     * @return the [SecurityContext] to lookup or empty if not found. Never null
     */
    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        val request = exchange.request
        val authHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        return if (authHeader != null && authHeader.length > 6 && authHeader.startsWith("Basic ")) {
            // split token part
            val token = authHeader.substring(6)
            // decode the token
            val bytes = Base64.getDecoder().decode(token)
            // convert decoded token to Sting
            val credentials = String(bytes, StandardCharsets.UTF_8)
            // split username and password
            val values = credentials.split(Regex(":"), 2)

            val auth = UsernamePasswordAuthenticationToken(values[0],values[1])

            authenticationManager.authenticate(auth).map { authentication ->
                SecurityContextImpl(authentication)
            }
        } else {
            Mono.empty()
        }
    }
}