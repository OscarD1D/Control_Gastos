package com.example.control_gastos.Data

import com.example.control_gastos.model.Gasto
import kotlinx.coroutines.flow.Flow

class GastoRepository(private val gastoDao: GastoDao) {
    val todosLosGastos: Flow<List<Gasto>> = gastoDao.obtenerTodosLosGastos()

    suspend fun insertar(gasto: Gasto) {
        gastoDao.insertarGasto(gasto)
    }

    suspend fun actualizar(gasto: Gasto) {
        gastoDao.actualizarGasto(gasto)
    }

    suspend fun eliminar(gasto: Gasto) {
        gastoDao.eliminarGasto(gasto)
    }


}