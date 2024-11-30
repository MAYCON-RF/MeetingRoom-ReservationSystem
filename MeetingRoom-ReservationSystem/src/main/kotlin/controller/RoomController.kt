package com.unisales.sistemaDeReservas.controller

import com.unisales.sistemaDeReservas.model.Room
import com.unisales.sistemaDeReservas.service.RoomService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/rooms")
class RoomController(private val roomService: RoomService) {

    /* Cria uma nova sala, define a rota POST para "/rooms".e retorna o código 201
     em caso de sucesso.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criarRoom(@RequestBody room: Room): Mono<Room> {
        return roomService.criarRoom(room)
    }

    /*busca uma sala específica pelo ID define a rota GET para "/rooms/{idRoom}",
     mapeia o valor da variável idRoom da URL para o parâmetro do método. e Retorna
     a sala encontrada ou um erro caso não seja encontrada.
     */
    @GetMapping("/{idRoom}")
    fun buscarRoom(@PathVariable idRoom: String): Mono<Room> {
        return roomService.buscarRoomsAtivas().filter { it.idRoom == idRoom }.next()
            .switchIfEmpty(Mono.error(IllegalArgumentException("Room não encontrada")))
    }

    /* Atualiza os dados de uma sala existente pelo ID, define a rota PUT para "/rooms/{idRoom}",
    mapeia o valor da variável idRoom da URL para o parâmetro do método e atualiza os
    dados da sala eretorna a sala atualizada ou um erro caso ela não seja encontrada.
        */
    @PutMapping("/{idRoom}")
    fun atualizarRoom(@PathVariable idRoom: String, @RequestBody room: Room): Mono<Room> {
        return roomService.atualizarRoom(idRoom, room)
            .switchIfEmpty(Mono.error(IllegalArgumentException("Room não encontrada")))
    }

    /* Lista todas as salas com status "ATIVA". Define a rota GET para "/rooms" e
    retorna todas as salas ativas disponíveis no sistema.
    */
    @GetMapping
    fun listarRooms(): Flux<Room> = roomService.buscarRoomsAtivas()

    /*deletar salas criadas*/
    @DeleteMapping("/{id}")
    fun deletarSala(@PathVariable id: String): Mono<ResponseEntity<Void>> {
        return roomService.DeletaRoom(id)
            .then(Mono.just(ResponseEntity<Void>(HttpStatus.NO_CONTENT))) // Retorna 204 se bem-sucedido
            .onErrorResume { e ->
                Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()) // Retorna 404 se não encontrado
            }
    }

}