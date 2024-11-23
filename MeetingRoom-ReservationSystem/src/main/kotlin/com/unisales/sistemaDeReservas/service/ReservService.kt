package com.unisales.sistemaDeReservas.service

import com.unisales.sistemaDeReservas.model.ReservRoom
import com.unisales.sistemaDeReservas.repository.ReservRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ReservService(private val reservaRepository: ReservRepository) {

    /* cria uma nova reserva buscando no banco de dados reservas que possam gerar erro,
    pois podem estar agendadas no mesmo horario, se nao houver erro salva a nova no banco. */

    fun criarReserva(reserva: ReservRoom): Mono<ReservRoom> {
        return reservaRepository.findBySalaIdAndInicioBetween(reserva.salaId, reserva.inicio, reserva.fim)
            .collectList()
            .flatMap { reservasExistentes ->
                if (reservasExistentes.isNotEmpty()) {
                    Mono.error(IllegalStateException("Conflito de horário"))
                } else {
                    reservaRepository.save(reserva)
                }
            }
    }


    /* Cancelar uma reserva com base no ID chamando
    o repositorio para excluir de acordo com o ID */
    fun cancelarReserva(id: String): Mono<Void> {
        return reservaRepository.deleteById(id)
    }

    /* Busca todas reservas cadastradas, retornando do banco de dados*/
    fun buscarReservas(): Flux<ReservRoom> {
        return reservaRepository.findAll()
    }
}