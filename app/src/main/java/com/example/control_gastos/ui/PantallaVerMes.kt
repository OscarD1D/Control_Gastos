package com.example.control_gastos.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.control_gastos.ViewModel.GastoViewModel
import com.example.control_gastos.model.Gasto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaVerMes(
    viewModel: GastoViewModel,
    onVolver: () -> Unit,
    onEditarGasto: (Gasto) -> Unit
) {
    val gastos by viewModel.listaGastos.collectAsState()
    var gastoAEliminar by remember { mutableStateOf<Gasto?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pantalla Ver Mes") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (gastos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay meses registrados")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(gastos) { gasto ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Fecha / Mes: ${gasto.fecha}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Ingresos: $${gasto.ingresos}")
                                Text(text = "Egresos: $${gasto.egresos}")
                                Text(text = "Disponible: $${gasto.disponible}")

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    TextButton(onClick = { gastoAEliminar = gasto }) {
                                        Text("Eliminar", color = MaterialTheme.colorScheme.error)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(onClick = { onEditarGasto(gasto) }) {
                                        Text("Editar")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Mensaje de confirmacion para eliminar
            if (gastoAEliminar != null) {
                AlertDialog(
                    onDismissRequest = { gastoAEliminar = null },
                    title = { Text("Confirmar eliminación") },
                    text = { Text("¿Deseas eliminar este registro de mes?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                gastoAEliminar?.let { viewModel.eliminarGasto(it) }
                                gastoAEliminar = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Eliminar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { gastoAEliminar = null }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}