package com.unisales.sistemaDeReservas.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime


/*Representa uma reserva de sala*/
@Document(collection = "reservas")
data class ReservRoom(
    @Id
    val idReserv: String? = null, /* ID único da reserva, pode ser nulo ao criar porque usa o "?" */
    val salaId: String,    /* ID da sala que está sendo reservada*/
    val usuario: String,   /* Nome do usuário que fez a reserva*/
    val inicio: LocalDateTime, /* Data e hora de início da reserva*/
    val fim: LocalDateTime   /* Data e hora de término da reserva*/


)
