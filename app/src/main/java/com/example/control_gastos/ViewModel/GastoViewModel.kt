package com.example.control_gastos.ViewModel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.control_gastos.Data.AppDatabase
import com.example.control_gastos.Data.GastoRepository
import com.example.control_gastos.model.Gasto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GastoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GastoRepository
    val listaGastos: StateFlow<List<Gasto>>

    // Estado para manejar el gasto que se va a editar
    var gastoEnEdicion by mutableStateOf<Gasto?>(null)
        private set

    init {
        val dao = AppDatabase.obtenerBaseDeDatos(application).gastoDao()
        repository = GastoRepository(dao)
        listaGastos = repository.todosLosGastos.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun seleccionarGastoParaEditar(gasto: Gasto?) {
        gastoEnEdicion = gasto
    }

    fun agregarGasto(ingresos: Float, egresos: Float, fecha: String, acumuladoAnterior: Float) {
        viewModelScope.launch {
            val disponible = ingresos - egresos
            val acumuladoTotal = acumuladoAnterior + disponible
            val nuevoGasto = Gasto(
                ingresos = ingresos,
                egresos = egresos,
                disponible = disponible,
                acumulado = acumuladoTotal,
                fecha = fecha
            )
            repository.insertar(nuevoGasto)
        }
    }

    fun actualizarGasto(gasto: Gasto, nuevosIngresos: Float, nuevosEgresos: Float, nuevaFecha: String, acumuladoAnterior: Float) {
        viewModelScope.launch {
            val nuevoDisponible = nuevosIngresos - nuevosEgresos
            val nuevoAcumulado = acumuladoAnterior + nuevoDisponible

            val gastoActualizado = gasto.copy(
                ingresos = nuevosIngresos,
                egresos = nuevosEgresos,
                disponible = nuevoDisponible,
                acumulado = nuevoAcumulado,
                fecha = nuevaFecha
            )
            repository.actualizar(gastoActualizado)
        }
    }



    fun eliminarGasto(gasto: Gasto) {
        viewModelScope.launch {
            repository.eliminar(gasto)
        }
    }
}