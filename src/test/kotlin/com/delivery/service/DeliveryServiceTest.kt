package com.delivery.service

import com.delivery.repository.DeliveryRepository
import com.delivery.util.TestUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
internal class DeliveryServiceTest(@Mock val deliveryRepository: DeliveryRepository) {

    @InjectMocks
    lateinit var deliveryService: DeliveryService

    @Test
    @DisplayName("When there is a delivery specified id then should return the entity")
    fun findByIdWhenThereIsADelivery() {
        // Given
        val delivery = TestUtil.createDelivery("apple", true)
        Mockito.`when`(deliveryRepository.findById(anyInt())).thenReturn(Mono.just(delivery))

        // When
        val actualValue = deliveryService.findById(1)

        // Then
        StepVerifier.create(actualValue)
            .consumeNextWith {
                assertEquals(delivery.deliveryID, it.deliveryID)
                assertEquals(delivery.product, it.product)
                assertEquals(delivery.supplier,it.supplier)
                assertEquals(delivery.quantity, it.quantity)
                assertEquals(delivery.received,it.received)
             }
            .verifyComplete()
        Mockito.verify(deliveryRepository,times(1)).findById(anyInt())
    }

    @Test
    @DisplayName("When there is a delivery specified id then should return empty Mono")
    fun findByIdWhenThereIsNoDelivery() {
        // Given
        Mockito.`when`(deliveryRepository.findById(anyInt())).thenReturn(Mono.empty())

        // When
        val actualValue = deliveryService.findById(1)

        // Then
        StepVerifier.create(actualValue)
            .expectNextCount(0)
            .verifyComplete()
        Mockito.verify(deliveryRepository,times(1)).findById(anyInt())
    }

    @Test
    @DisplayName("When there is received deliveries then should return them")
    fun findAllReceivedDeliveries() {
        // Given
        val appleDelivery = TestUtil.createDelivery("apple", true)
        val bananaDelivery = TestUtil.createDelivery("banana", true)
        Mockito.`when`(deliveryRepository.findAllByReceivedIsTrue()).thenReturn(Flux.just(appleDelivery,bananaDelivery))

        // When
        val actualValue = deliveryService.findAllReceivedDeliveries()

        // Then
        StepVerifier.create(actualValue)
            .expectNextCount(2)
            .verifyComplete()
        val receivedDeliveries = actualValue.collectList().block()
        assertEquals(appleDelivery.product,receivedDeliveries?.get(0)?.product)
        assertEquals(bananaDelivery.product,receivedDeliveries?.get(1)?.product)
        Mockito.verify(deliveryRepository,times(1)).findAllByReceivedIsTrue()

    }

    @Test
    @DisplayName("When there is no received deliveries then should return empty flux")
    fun findAllUnreceivedDeliveries() {
        // Given
        Mockito.`when`(deliveryRepository.findAllByReceivedIsFalse()).thenReturn(Flux.empty())

        // When
        val actualValue = deliveryService.findAllUnreceivedDeliveries()

        // Then
        StepVerifier.create(actualValue)
            .expectNextCount(0)
            .verifyComplete()
        Mockito.verify(deliveryRepository,times(1)).findAllByReceivedIsFalse()
    }

    @Test
    @DisplayName("When delivery is saved then should return saved delivery")
    fun save() {
        // Given
        val appleDelivery = TestUtil.createDelivery("apple", false)
        Mockito.`when`(deliveryRepository.save(any())).thenReturn(Mono.just(appleDelivery))

        // When
        val actualValue = deliveryService.save(appleDelivery)

        // Then
        StepVerifier
            .create(actualValue)
            .consumeNextWith {
                assertEquals(appleDelivery.deliveryID, it.deliveryID)
                assertEquals(appleDelivery.product, it.product)
                assertEquals(appleDelivery.supplier,it.supplier)
                assertEquals(appleDelivery.quantity, it.quantity)
                assertEquals(appleDelivery.received,it.received)
            }
            .verifyComplete()
        Mockito.verify(deliveryRepository,times(1)).save(any())
    }

}