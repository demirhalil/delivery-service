package com.delivery.mutation

import com.delivery.service.DeliveryService
import com.delivery.util.TestUtil
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono

@ExtendWith(MockitoExtension::class)
internal class DeliveryMutationTest(
    @Mock private val deliveryService: DeliveryService) {

    @InjectMocks
    private lateinit var deliveryMutation: DeliveryMutation

    @Test
    @DisplayName("When delivery is marked as received then should return it as received")
    fun markDeliveryAsReceived() = runBlocking {
        // Given
        val unreceivedDelivery = TestUtil.createDelivery("apple", false)
        Mockito.`when`(deliveryService.findById(unreceivedDelivery.deliveryID)).thenReturn(Mono.just(unreceivedDelivery))
        Mockito.`when`(deliveryService.save(unreceivedDelivery)).thenReturn(Mono.just(unreceivedDelivery))

        // When
        val response = deliveryMutation.markDeliveryAsReceived(unreceivedDelivery.deliveryID)

        // Then
        Mockito.verify(deliveryService, times(1)).findById(unreceivedDelivery.deliveryID)
        Mockito.verify(deliveryService, times(1)).save(unreceivedDelivery)
        Assertions.assertEquals(true, response.received)
    }
}