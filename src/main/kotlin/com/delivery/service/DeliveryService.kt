package com.delivery.service

import com.delivery.entity.Delivery
import com.delivery.repository.DeliveryRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class DeliveryService(private val deliveryRepository: DeliveryRepository) {
    fun findById(id: Int): Mono<Delivery> {
        return deliveryRepository.findById(id)
    }

    fun findAllReceivedDeliveries(): Flux<Delivery> {
        return deliveryRepository.findAllByReceivedIsTrue()
    }

    fun findAllUnreceivedDeliveries(): Flux<Delivery> {
        return deliveryRepository.findAllByReceivedIsFalse()
    }

    fun save(delivery: Delivery): Mono<Delivery> {
        return deliveryRepository.save(delivery)
    }
}