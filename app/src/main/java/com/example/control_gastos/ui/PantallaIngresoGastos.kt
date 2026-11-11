package com.example.control_gastos.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.control_gastos.ViewModel.GastoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaIngresoGastos(
    viewModel: GastoViewModel,
    onVolver: () -> Unit
) {
    val gastoAEditar = viewModel.gastoEnEdicion

    var ingresosStr by remember { mutableStateOf(gastoAEditar?.ingresos?.toString() ?: "") }
    var egresosStr by remember { mutableStateOf(gastoAEditar?.egresos?.toString() ?: "") }
    var fecha by remember { mutableStateOf(gastoAEditar?.fecha ?: "2026-11-09") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (gastoAEditar == null) "Pantalla Ingreso Gastos" else "Editar Mes") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = ingresosStr,
                onValueChange = { ingresosStr = it },
                label = { Text("Ingresos (Sueldo, etc.)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = egresosStr,
                onValueChange = { egresosStr = it },
                label = { Text("Egresos (Alquiler, etc.)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha / Mes (Ej. 2026-11)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val ingresos = ingresosStr.toFloatOrNull() ?: 0f
                    val egresos = egresosStr.toFloatOrNull() ?: 0f


                    val listaActual = viewModel.listaGastos.value.sortedBy { it.id }
                    val acumuladoAnterior = if (gastoAEditar == null) {
                        listaActual.lastOrNull()?.acumulado ?: 0f
                    } else {
                        // Si estamos editando, tomamos el acumulado previo al elemento actual
                        val index = listaActual.indexOfFirst { it.id == gastoAEditar.id }
                        if (index > 0) listaActual[index - 1].acumulado else 0f
                    }

                    if (gastoAEditar == null) {
                        viewModel.agregarGasto(ingresos, egresos, fecha, acumuladoAnterior)
                    } else {
                        viewModel.actualizarGasto(gastoAEditar, ingresos, egresos, fecha, acumuladoAnterior)
                    }
                    onVolver()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(text = "REGISTRAR")
            }
        }
    }
}