package com.delivery.mutation

import com.delivery.entity.Delivery
import com.delivery.service.DeliveryService
import com.delivery.util.getLogger
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Component

@Component
final class DeliveryMutation(private val deliveryService: DeliveryService): Mutation {
    private val logger = getLogger(this)

    @GraphQLDescription("Mark a delivery as received to warehouse")
    suspend fun markDeliveryAsReceived(id: Int): Delivery {
        val delivery = deliveryService.findById(id).awaitSingle()
        if (delivery.received) {
            logger.info("The delivery is already received. Delivery id:  $id")
            return delivery
        }
        synchronized(this) {
            delivery.received = true
        }
        return deliveryService.save(delivery).awaitSingle()
    }
}