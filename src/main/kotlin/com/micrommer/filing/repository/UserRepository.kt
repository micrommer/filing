package com.micrommer.filing.repository

import com.micrommer.filing.model.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

/**
 * Ashiyane: filing (com.micrommer.filing.repository)
 * @author : imanbhlool
 * @since : Dec/23/2020 - 4:12 PM, Wednesday
 */
interface UserRepository : ReactiveMongoRepository<User,ObjectId>{
    fun findByName(name: String) : Mono<User>
}