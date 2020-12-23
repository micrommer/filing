package com.micrommer.filing.extra

import com.micrommer.filing.model.User
import com.micrommer.filing.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.kotlin.core.publisher.switchIfEmpty

/**
 * Ashiyane: filing (com.micrommer.filing)
 * @author : imanbhlool
 * @since : Dec/23/2020 - 5:24 PM, Wednesday
 */

@Component
class InitBean(private val userRepository: UserRepository,
               private val passwordEncoder: PasswordEncoder) : CommandLineRunner {
    /**
     * Callback used to run the bean.
     * @param args incoming main method arguments
     * @throws Exception on error
     */

    private val userName = "Bob"
    private val password = "BobLikesBobWithDoubleO"

    override fun run(vararg args: String?) {
        userRepository.findByName(userName).switchIfEmpty {
            val user = User(ObjectId.get(), userName, passwordEncoder.encode(password),"USER")
            userRepository.save(user)
        }.subscribe()
    }

}