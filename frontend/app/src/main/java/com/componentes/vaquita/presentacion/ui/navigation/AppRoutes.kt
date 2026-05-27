package com.componentes.vaquita.presentacion.ui.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.componentes.vaquita.presentacion.ui.screens.*
import com.componentes.vaquita.presentacion.viewmodels.*

sealed class Screen {
    object Metas : Screen()
    object CrearMeta : Screen()
    object RealizarAporte : Screen()
    object Miembros : Screen()
    object Aportes : Screen()
    object AgregarMiembro : Screen()
    data class DetalleMeta(val goalId: String) : Screen()
}

@Composable
fun AppRoutes() {
    val metasViewModel: MetasViewModel = viewModel()
    val detalleMetaViewModel: DetalleMetaViewModel = viewModel()
    val aporteViewModel: AporteViewModel = viewModel()
    val personViewModel: PersonViewModel = viewModel()

    var currentScreen by remember {
        mutableStateOf<Screen>(Screen.Metas)
    }

    when (val screen = currentScreen) {
        is Screen.Metas -> MetasScreen(
            viewModel = metasViewModel,
            onNuevaMeta = { currentScreen = Screen.CrearMeta },
            onVerMeta = { id ->
                currentScreen = Screen.DetalleMeta(id)
            },
            onVerMiembros = { currentScreen = Screen.Miembros },
            onVerAportes = { currentScreen = Screen.Aportes }
        )

        is Screen.CrearMeta -> CrearMetaScreen(
            viewModel = metasViewModel,
            onBack = { currentScreen = Screen.Metas },
            onCrear = { currentScreen = Screen.Metas },
            onInicio = { currentScreen = Screen.Metas },
            onAportes = { currentScreen = Screen.Aportes },
            onMiembros = { currentScreen = Screen.Miembros }
        )

        is Screen.DetalleMeta -> DetalleMetaScreen(
            goalId = screen.goalId,
            viewModel = detalleMetaViewModel,
            onBack = { currentScreen = Screen.Metas },
            onRealizarAporte = {
                currentScreen = Screen.RealizarAporte
            },
            onInicio = { currentScreen = Screen.Metas },
            onAportes = { currentScreen = Screen.Aportes },
            onMiembros = { currentScreen = Screen.Miembros }
        )

        is Screen.RealizarAporte -> RealizarAporteScreen(
            viewModel = aporteViewModel,
            onBack = { currentScreen = Screen.Metas },
            onConfirmar = { currentScreen = Screen.Aportes },
            onInicio = { currentScreen = Screen.Metas },
            onAportes = { currentScreen = Screen.Aportes },
            onMiembros = { currentScreen = Screen.Miembros }
        )

        is Screen.Aportes -> AportesScreen(
            viewModel = metasViewModel,
            onInicio = { currentScreen = Screen.Metas },
            onMiembros = { currentScreen = Screen.Miembros },
            onRealizarAportes = { currentScreen = Screen.RealizarAporte }
        )

        is Screen.Miembros -> MiembrosScreen(
            viewModel = personViewModel,
            onBack = { currentScreen = Screen.Metas },
            onInicio = { currentScreen = Screen.Metas },
            onAportes = { currentScreen = Screen.Aportes },
            onAgregarMiembro = { currentScreen = Screen.AgregarMiembro }
        )

        is Screen.AgregarMiembro -> AgregarMiembroScreen(
            viewModel = personViewModel,
            onGuardar = { currentScreen = Screen.Miembros },
            onInicio = { currentScreen = Screen.Metas },
            onAportes = { currentScreen = Screen.Aportes },
            onMiembros = { currentScreen = Screen.Miembros }
        )
    }
}
