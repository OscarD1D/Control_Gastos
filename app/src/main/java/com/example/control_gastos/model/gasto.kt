package com.example.control_gastos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_gastos")
data class Gasto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ingresos: Float? = null,
    val egresos: Float? = null,
    val disponible: Float,
    val acumulado: Float,
    val fecha: String

)
