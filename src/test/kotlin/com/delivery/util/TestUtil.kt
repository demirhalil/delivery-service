package com.delivery.util

import com.delivery.entity.Delivery
import java.util.Random

object TestUtil {
    fun createDelivery(product: String, received: Boolean): Delivery {
        val generator = Random()
        val id = generator.nextInt(100 - 1) + 1
        val quantity = generator.nextInt(100 - 1) + 1
        val expectedDate = "2019-10-10T09:08:11.098Z"
        return Delivery(id, product, "$product-supplier", quantity, expectedDate, "warehouse", received)
    }
}