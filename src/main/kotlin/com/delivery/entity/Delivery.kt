package com.delivery.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("deliveries")
class Delivery(
    @Id
    val deliveryID: Int = -1,
    val product: String = "",
    val supplier: String = "",
    val quantity: Int = 0,
    val expectedDate: String = "",
    val expectedWarehouse: String = "",
    var received: Boolean = false
)
