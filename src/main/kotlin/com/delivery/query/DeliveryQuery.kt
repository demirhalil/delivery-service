package com.delivery.query

import com.delivery.entity.Delivery
import com.delivery.service.DeliveryService
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Component

@Component
class DeliveryQuery(private val deliveryService: DeliveryService) : Query {
    @GraphQLDescription("Return a delivery by specified id")
    suspend fun deliveryById(id: Int): Delivery = deliveryService.findById(id).awaitSingle()

    @GraphQLDescription("Return a list of received deliveries")
    suspend fun receivedDeliveries(): List<Delivery> = deliveryService.findAllReceivedDeliveries().collectList().awaitFirst()

    @GraphQLDescription("Return a list of unreceived deliveries")
    suspend fun unreceivedDeliveries(): List<Delivery> = deliveryService.findAllUnreceivedDeliveries().collectList().awaitFirst()
}