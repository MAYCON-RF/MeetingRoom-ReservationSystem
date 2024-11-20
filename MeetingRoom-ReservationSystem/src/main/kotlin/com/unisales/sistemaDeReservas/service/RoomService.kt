package service

import com.unisales.sistemaDeReservas.model.Room
import com.unisales.sistemaDeReservas.repository.RoomRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class RoomService(private val RoomRepository: RoomRepository) {

    /* Cria uma nova sala e a salva no banco de dados */

     fun criarSala(room: Room): Mono<Room> = RoomRepository.save(room)


    /*  Busca todas as salas com status ATIVA */
 fun buscarSalasAtivas(): Flux<Room> = RoomRepository.findByStatus(StatusDaSala.ATIVA.name)


    /* Atualiza uma sala existente */
 fun atualizarSala(roomId: String, roomAtualizada: Room): Mono<Room> {
     return RoomRepository.findById(roomId)  /*  Busca a sala pelo ID */
         .flatMap { roomExistente ->  /*  Se a sala existir */
             val novaRoom = roomExistente.copy(  /*  Cria uma cópia da sala com os dados atualizados */
                 nome = roomAtualizada.nome,
                 capacidade = roomAtualizada.capacidade,
                 recursos = roomAtualizada.recursos,
                 status = roomAtualizada.status
             )
             // Salva a sala atualizada
             RoomRepository.save(novaRoom)
         }
 }
}