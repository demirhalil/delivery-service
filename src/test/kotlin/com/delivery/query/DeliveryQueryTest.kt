package com.delivery.query

import com.delivery.service.DeliveryService
import com.delivery.util.TestUtil
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@ExtendWith(MockitoExtension::class)
internal class DeliveryQueryTest(
    @Mock private  val deliveryService: DeliveryService) {

    @InjectMocks
    private lateinit var deliverQuery: DeliveryQuery

    @Test
    @DisplayName("When there is a delivery for specified id then should return it")
    fun deliveryByIdWhenThereIsADeliveryForSpecifiedId() = runBlocking{
        // Given
        val delivery = TestUtil.createDelivery("apple", true)
        Mockito.`when`(deliveryService.findById(anyInt())).thenReturn(Mono.just(delivery))

        // When
        val response = deliverQuery.deliveryById(1)

        // Then
        Mockito.verify(deliveryService, times(1)).findById(anyInt())
        Assertions.assertEquals(delivery.deliveryID,response.deliveryID)
        Assertions.assertEquals(delivery.product,response.product)
        Assertions.assertEquals(delivery.supplier,response.supplier)
        Assertions.assertEquals(delivery.quantity,response.quantity)
        Assertions.assertEquals(delivery.expectedDate,response.expectedDate)
        Assertions.assertEquals(delivery.received,response.received)
        Assertions.assertEquals(delivery.expectedWarehouse,response.expectedWarehouse)
    }

    @Test
    @DisplayName("When there is only one received delivery then should return it")
    fun receivedDeliveries() = runBlocking{
        // Given
        val delivery = TestUtil.createDelivery("apple", true)
        Mockito.`when`(deliveryService.findAllReceivedDeliveries()).thenReturn(Flux.just(delivery))

        // When
        val response = deliverQuery.receivedDeliveries()

        // Then
        Mockito.verify(deliveryService, times(1)).findAllReceivedDeliveries()
        Assertions.assertEquals(1,response.size)
        Assertions.assertEquals(delivery.deliveryID, response[0].deliveryID)
        Assertions.assertEquals(delivery.product, response[0].product)
        Assertions.assertEquals(delivery.supplier, response[0].supplier)
        Assertions.assertEquals(delivery.quantity, response[0].quantity)
        Assertions.assertEquals(delivery.expectedDate, response[0].expectedDate)
        Assertions.assertEquals(delivery.received, response[0].received)
        Assertions.assertEquals(delivery.expectedWarehouse, response[0].expectedWarehouse)
    }

    @Test
    @DisplayName("When ther is no received delivery then should return empty list")
    fun unreceivedDeliveries() = runBlocking{
        // Given
        Mockito.`when`(deliveryService.findAllUnreceivedDeliveries()).thenReturn(Flux.empty())

        // When
        val response = deliverQuery.unreceivedDeliveries()

        // Then
        Mockito.verify(deliveryService, times(1)).findAllUnreceivedDeliveries()
        Assertions.assertEquals(0,response.size)
    }
}