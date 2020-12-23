package com.micrommer.filing.security

import com.micrommer.filing.repository.UserRepository
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

/**
 * Ashiyane: filing (com.micrommer.filing.security)
 * @author : imanbhlool
 * @since : Dec/23/2020 - 3:19 PM, Wednesday
 */
@Component
class AuthenticationManager(private val encoder: PasswordEncoder,
                            private val userRepository: UserRepository) : ReactiveAuthenticationManager {
    /**
     * Attempts to authenticate the provided [Authentication]
     * @param authentication the [Authentication] to test
     * @return if authentication is successful an [Authentication] is returned. If
     * authentication cannot be determined, an empty Mono is returned. If authentication
     * fails, a Mono error is returned.
     */
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val pass = authentication.credentials.toString()
        val name = authentication.principal.toString()

        return userRepository.findByName(name).filter { user ->
            encoder.matches(pass, user.password)
        }.map { user ->
            UsernamePasswordAuthenticationToken(user.name, user.pass, user.authorities)
        }
    }
}