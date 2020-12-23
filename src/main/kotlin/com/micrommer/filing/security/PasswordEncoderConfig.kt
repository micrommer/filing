package com.micrommer.filing.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * Ashiyane: filing (com.micrommer.filing.security)
 * @author : imanbhlool
 * @since : Dec/23/2020 - 3:57 PM, Wednesday
 */
@Configuration
class PasswordEncoderConfig {

    @Bean
    fun getEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}