package com.unisales.sistemaDeReservas.service

import com.unisales.sistemaDeReservas.model.ReservRoom
import com.unisales.sistemaDeReservas.model.Room
import com.unisales.sistemaDeReservas.repository.ReservRepository
import com.unisales.sistemaDeReservas.repository.RoomRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ReservService(
    private val reservaRepository: ReservRepository,
    private val roomRepository: RoomRepository
) {

    /* Cria reserva atraves do ID da sala*/
    fun criarReserva(reserva: ReservRoom): Mono<ReservRoom> {
        return roomRepository.findById(reserva.salaId)
            .switchIfEmpty(Mono.error(IllegalArgumentException("Sala não encontrada")))
            .flatMap { sala ->
                if (sala.status != Room.StatusDaSala.ATIVA) {
                    return@flatMap Mono.error<ReservRoom>(IllegalArgumentException("Sala está INATIVA e não pode ser reservada"))
                }
                reservaRepository.findBySalaIdAndInicioBeforeAndFimAfter(reserva.salaId, reserva.fim, reserva.inicio)
                    .collectList()
                    .flatMap { reservasExistentes ->
                        if (reservasExistentes.isNotEmpty()) {
                            Mono.error<ReservRoom>(IllegalStateException("Conflito de horário detectado"))
                        } else {
                            reservaRepository.save(reserva)
                        }
                    }
            }
    }

    /* Cancela uma reserva com base no ID */
    fun cancelarReserva(id: String): Mono<Void> {
        return reservaRepository.deleteById(id)
    }

    /* Busca todas reservas cadastradas */
    fun filtrarReservas(): Flux<ReservRoom> {
        return reservaRepository.findAll()
    }

    /* Altera reserva cadastrada */
    fun alterarHorarioReserva(id: String, novaReserva: ReservRoom): Mono<ReservRoom> {
        return reservaRepository.findById(id)
            .flatMap { reservaExistente ->
                reservaRepository.findBySalaIdAndInicioBeforeAndFimAfter(
                    novaReserva.salaId, novaReserva.fim, novaReserva.inicio
                ).collectList().flatMap { conflitos ->
                    if (conflitos.isNotEmpty()) {
                        Mono.error(IllegalArgumentException("Conflito de horário detectado"))
                    } else {
                        reservaRepository.save(novaReserva.copy(idReserv = id))
                    }
                }
            }
    }

}