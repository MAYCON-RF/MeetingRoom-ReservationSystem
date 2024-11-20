package repository

import com.unisales.sistemaDeReservas.model.ReservRoom
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import java.time.LocalDateTime

interface ReservRepository : ReactiveCrudRepository<ReservRoom, String> {

    fun findBySalaIdAndInicioBetween(salaId: String, inicio: LocalDateTime, fim: LocalDateTime): Flux<ReservRoom>

}