package service

import repository.ReservRepository
import com.unisales.sistemaDeReservas.model.ReservRoom
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ReservaService(private val reservaRepository: ReservRepository) {

    // Cria uma nova reserva, verificando existe um conflito de horário
    fun criarReserva(reserva: ReservRoom): Mono<ReservRoom> {
        // Verifica se ja existe alguma reserva para a sala e horário solicitado,


        return reservaRepository.findBySalaIdAndInicioBetween(reserva.salaId, reserva.inicio, reserva.fim)
            .collectList() // Coleta todas as reservas encontradas em uma lista
            .flatMap { reservasExistentes ->
                if (reservasExistentes.isNotEmpty()) {  // Se houver alguma reserva no mesmo horário
                    Mono.error(IllegalStateException("Conflito de horário")) // Retorna um erro
                } else {
                    reservaRepository.save(reserva)  // Se não houver conflito, salva a reserva
                }
            }
    }



    fun buscarReservas(): Flux<ReservRoom> = reservaRepository.findAll()
}// Busca todas as reservas ja feitas