package com.delivery.repository

import com.delivery.entity.Delivery
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface DeliveryRepository: ReactiveCrudRepository<Delivery, Int> {
    fun findAllByReceivedIsTrue(): Flux<Delivery>
    fun findAllByReceivedIsFalse(): Flux<Delivery>
}