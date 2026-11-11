package com.example.control_gastos.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.control_gastos.ViewModel.GastoViewModel

sealed class Pantalla(val ruta: String) {
    object Principal : Pantalla("principal")
    object VerMes : Pantalla("ver_mes")
    object IngresoGastos : Pantalla("ingreso_gastos")
}

@Composable
fun Navegacion(viewModel: GastoViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Pantalla.Principal.ruta) {
        composable(Pantalla.Principal.ruta) {
            PantallaPrincipal(
                viewModel = viewModel,
                onNavegarAVerMes = { navController.navigate(Pantalla.VerMes.ruta) },
                onNavegarAIngresar = {
                    viewModel.seleccionarGastoParaEditar(null)
                    navController.navigate(Pantalla.IngresoGastos.ruta)
                }
            )
        }
        composable(Pantalla.VerMes.ruta) {
            PantallaVerMes(
                viewModel = viewModel,
                onVolver = { navController.popBackStack() },
                onEditarGasto = { gasto ->
                    viewModel.seleccionarGastoParaEditar(gasto)
                    navController.navigate(Pantalla.IngresoGastos.ruta)
                }
            )
        }
        composable(Pantalla.IngresoGastos.ruta) {
            PantallaIngresoGastos(
                viewModel = viewModel,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}