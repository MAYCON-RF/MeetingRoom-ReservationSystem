package com.unisales.sistemaDeReservas.repository

import com.unisales.sistemaDeReservas.model.ReservRoom
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import java.time.LocalDateTime

interface ReservRepository : ReactiveMongoRepository<ReservRoom, String> {

    // Verifica se existem conflitos de horário não aceitando dois horarios na mesma sala
    fun findBySalaIdAndInicioBeforeAndFimAfter(
        salaId: String,
        fim: LocalDateTime,
        inicio: LocalDateTime
    ): Flux<ReservRoom>

}