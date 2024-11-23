package com.unisales.sistemaDeReservas.service

import com.unisales.sistemaDeReservas.model.Room
import com.unisales.sistemaDeReservas.repository.RoomRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class RoomService(
    private val RoomRepository: RoomRepository,
    private val roomRepository: RoomRepository
) {

    /* Cria uma nova sala e a salva no banco de dados */

    fun criarRoom(room: Room): Mono<Room> {
        return roomRepository.save(room)
    }

    /*  Busca todas as salas com status "ATIVA"  */
    fun buscarRoomsAtivas(): Flux<Room> = RoomRepository.findByStatus(Room.StatusDaSala.ATIVA.name)

    /* Atualiza uma sala existente */
    fun atualizarRoom(roomId: String, roomAtualizada: Room): Mono<Room> {
        return roomRepository.findById(roomId)
            .flatMap { roomExistente ->
                val novaRoom = roomExistente.copy(
                    nome = roomAtualizada.nome,
                    capacidade = roomAtualizada.capacidade,
                    recursos = roomAtualizada.recursos,
                    status = roomAtualizada.status
                )
                roomRepository.save(novaRoom)
            }
    }
    fun DeletaRoom(id: String): Mono<Void> {
        return RoomRepository.deleteById(id)
    }
}