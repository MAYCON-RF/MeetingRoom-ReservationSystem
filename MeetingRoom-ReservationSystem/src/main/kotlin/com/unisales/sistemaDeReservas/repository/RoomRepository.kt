package com.unisales.sistemaDeReservas.repository

import com.unisales.sistemaDeReservas.model.Room
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface RoomRepository : ReactiveCrudRepository<Room, String> {

    fun findByStatus(status: String): Flux<Room>

}