package com.unisales.sistemaDeReservas.service

import com.unisales.sistemaDeReservas.model.Room
import com.unisales.sistemaDeReservas.repository.RoomRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class RoomService(private val RoomRepository: RoomRepository) {

    /* Cria uma nova sala e a salva no banco de dados */
    fun criarRoom(room: Room): Mono<Room> = RoomRepository.save(room)


    /*  Busca todas as salas com status "ATIVA"  */
    fun buscarRoomsAtivas(): Flux<Room> = RoomRepository.findByStatus(Room.StatusDaSala.ATIVA.name)

    /* Atualiza uma sala existente */
    fun atualizarRoom(roomId: String, roomAtualizada: Room): Mono<Room> {
     return RoomRepository.findById(roomId)
         /*  Busca a sala no banco pelo ID, se a sala existir, cria uma cópia da sala com os dados atualizados */
         .flatMap { roomExistente ->
             val novaRoom = roomExistente.copy(
                 nome = roomAtualizada.nome,
                 capacidade = roomAtualizada.capacidade,
                 recursos = roomAtualizada.recursos,
                 status = roomAtualizada.status
             )
             /* Salva a sala atualizada  e atualiza no banco*/
             RoomRepository.save(novaRoom)
         }
 }
}