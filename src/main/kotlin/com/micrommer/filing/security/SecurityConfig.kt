package com.micrommer.filing.security

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import reactor.core.publisher.Mono

/**
 * Ashiyane: filing (com.micrommer.filing.security)
 * @author : imanbhlool
 * @since : Dec/23/2020 - 3:12 PM, Wednesday
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(private val authenticationManager: ReactiveAuthenticationManager,
                     private val securityContextRepository: ServerSecurityContextRepository) {
    @Bean
    fun setSecurityConfigure(http : ServerHttpSecurity) : SecurityWebFilterChain{
        return http
                .formLogin()
                .disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .anyExchange()
                .authenticated()
                .and()
                .build()

    }
}