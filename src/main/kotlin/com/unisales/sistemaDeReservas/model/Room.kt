package com.unisales.sistemaDeReservas.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/* Representa uma sala de reunião */
@Document(collection = "salas")
/* a anotação @Entity para indicar que essa classe representa uma entidade do JPA.*/
data class Room(
    @Id
    val idRoom: String? = null,            /* ID único da salq pode ser nulo ao criar porque usa o "?" */
    val nome: String,                      /* Nome da sala*/
    val capacidade: Int,                   /* Quantidade de pessoas que a sala suporta*/
    val recursos: String,                  /* Lista de recursos (ex: projetor, quadro)*/
    val status: StatusDaSala               /* Status da sala: ATIVA ou INATIVA*/
) {

    // Enum que define os possíveis status das salas
    enum class StatusDaSala {
        ATIVA,     /* Sala disponível para reserva */
        INATIVA    /* Sala fora de operação */
    }
}