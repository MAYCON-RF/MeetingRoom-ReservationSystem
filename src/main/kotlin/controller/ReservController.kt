package com.unisales.sistemaDeReservas.controller

import com.unisales.sistemaDeReservas.model.ReservRoom
import com.unisales.sistemaDeReservas.service.ReservService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/reservas")
class ReservController(private val reservaService: ReservService) {

    /* Cria uma nova reserva, mapeia as requisições POST para "/reservas".
     * Retorna o status HTTP 201 (CREATED) em caso de sucesso.*/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criarReserva(@RequestBody reserva: ReservRoom): Mono<ReservRoom> {/* Chama a classe ReservRoom da pasta Service para criar uma reserva e trata possíveis erros caso haja*/

        return reservaService.criarReserva(reserva).onErrorResume { e ->
            if (e is IllegalStateException) {
                Mono.error(IllegalArgumentException("Erro ao criar reserva: ${e.message}"))
            } else {
                Mono.error(e)
            }
        }
    }

    /* Lista todas as reservas, mapeia as requisições GET para "/reservas".e retorna as reservas encontradas. */
    @GetMapping("/disponiveis")
    fun listarReservasDisponiveis(): Flux<ReservRoom> {
        return reservaService.filtrarReservas()
    }

    /*Cancela uma reserva com base no ID fornecido, mapeia requisições DELETE para "/reservas/{id} e
    retorna o status HTTP 204 em caso de sucesso.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun cancelarReserva(@PathVariable id: String): Mono<Void> {
        return reservaService.cancelarReserva(id)
    }

    @PutMapping("/{id}")
    fun alterarHorarioReserva(
        @PathVariable id: String, @RequestBody novaReserva: ReservRoom
    ): Mono<ReservRoom> {
        return reservaService.alterarHorarioReserva(id, novaReserva).onErrorResume { e ->
            Mono.error(IllegalArgumentException("Erro ao alterar horário: ${e.message}"))
        }
    }

}

