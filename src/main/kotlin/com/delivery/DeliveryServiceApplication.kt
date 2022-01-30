package com.delivery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class DeliveryServiceApplication

fun main(args: Array<String>) {
	runApplication<DeliveryServiceApplication>(*args)
}
