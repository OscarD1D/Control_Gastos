package com.example.control_gastos.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.control_gastos.ViewModel.GastoViewModel

@Composable
fun PantallaPrincipal(
    viewModel: GastoViewModel,
    onNavegarAVerMes: () -> Unit,
    onNavegarAIngresar: () -> Unit
) {
    val gastos by viewModel.listaGastos.collectAsState()
    // Suma del sueldo disponible de todos los meses
    val saldoAcumulado = gastos.sumOf { it.disponible.toDouble() }.toFloat()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Control de Gastos Mensual",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNavegarAVerMes,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(text = "Ver Mes")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavegarAIngresar,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(text = "Ingresar Gastos")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Saldo acumulado actual")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$ $saldoAcumulado",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}