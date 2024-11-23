package com.unisales.sistemaDeReservas.controller

import com.unisales.sistemaDeReservas.model.Room
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import service.RoomService

@RestController
@RequestMapping("/rooms")
class RoomController(private val roomService: RoomService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criarRoom(@RequestBody room: Room): Mono<Room> {
        return roomService.criarRoom(room)
    }

    @GetMapping("/{idRoom}")
    fun buscarRoom(@PathVariable idRoom: String): Mono<Room> {
        return roomService.buscarRoomsAtivas()
            .filter { it.idRoom == idRoom }
            .next()
            .switchIfEmpty(Mono.error(IllegalArgumentException("Room não encontrada")))
    }

    @PutMapping("/{idRoom}")
    fun atualizarRoom(@PathVariable idRoom: String, @RequestBody room: Room): Mono<Room> {
        return roomService.atualizarRoom(idRoom, room)
            .switchIfEmpty(Mono.error(IllegalArgumentException("Room não encontrada")))
    }

    @GetMapping
    fun listarRooms(): Flux<Room> {
        return roomService.buscarRoomsAtivas()
    }
}