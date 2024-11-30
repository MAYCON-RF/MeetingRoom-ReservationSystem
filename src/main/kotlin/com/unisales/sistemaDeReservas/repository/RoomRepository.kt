package com.unisales.sistemaDeReservas.repository

import com.unisales.sistemaDeReservas.model.Room
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface RoomRepository : ReactiveMongoRepository<Room, String> {

    fun findByStatus(status: String): Flux<Room>

}