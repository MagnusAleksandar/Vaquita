package com.componentes.vaquita.presentation.ui.navigation

import android.app.Application
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.componentes.vaquita.data.services.ContributionApi
import com.componentes.vaquita.data.services.GoalApi
import com.componentes.vaquita.data.services.PersonApi
import com.componentes.vaquita.data.services.RetrofitClient
import com.componentes.vaquita.data.services.repository.GoalRepository
import com.componentes.vaquita.data.services.repository.PersonRepository
import com.componentes.vaquita.presentation.ui.screens.*
import com.componentes.vaquita.presentation.ui.viewmodel.*

sealed class Screen {
    object Metas : Screen()
    object CrearMeta : Screen()
    object RealizarAporte : Screen()
    object Miembros : Screen()
    object Perfil : Screen()
    object Aportes : Screen()
    object EditarAporte : Screen()
    object AgregarMiembro : Screen()
    object ModificarMeta : Screen()
    data class DetalleMeta(val goalId: String) : Screen()
}

@Composable
fun AppRoutes() {
    val context = LocalContext.current.applicationContext as Application

    // APIs
    val goalApi = RetrofitClient.create(GoalApi::class.java)
    val personApi = RetrofitClient.create(PersonApi::class.java)
    val contributionApi = RetrofitClient.create(ContributionApi::class.java)

    // Repositories
    val goalRepository = GoalRepository(goalApi, contributionApi)
    val personRepository = PersonRepository(personApi)

    // Factories
    val metasFactory = MetasViewModelFactory(context, goalRepository)
    val detalleMetaFactory = DetalleMetaViewModelFactory(context, goalRepository)
    val aporteFactory = AporteViewModelFactory(context, goalRepository, personRepository)
    val personFactory = PersonViewModelFactory(context, personRepository)

    // ViewModels
    val metasViewModel: MetasViewModel = viewModel(factory = metasFactory)
    val detalleMetaViewModel: DetalleMetaViewModel = viewModel(factory = detalleMetaFactory)
    val aporteViewModel: AporteViewModel = viewModel(factory = aporteFactory)
    val personViewModel: PersonViewModel = viewModel(factory = personFactory)

    var currentScreen by remember {
        mutableStateOf<Screen>(Screen.Metas)
    }

    when (val screen = currentScreen) {
        is Screen.Metas -> MetasScreen(
            viewModel = metasViewModel,
            onNuevaMeta = { currentScreen = Screen.CrearMeta },
            onVerMeta = { id -> currentScreen = Screen.DetalleMeta(id)
            },
            onVerMiembros = { currentScreen = Screen.Miembros },
            onVerPerfil = { currentScreen = Screen.Perfil },
            onVerAportes = { currentScreen = Screen.Aportes }
        )

        is Screen.CrearMeta -> CrearMetaScreen(
            viewModel = metasViewModel,
            onBack = { currentScreen = Screen.Metas },
            onCrear = { currentScreen = Screen.Metas },
            onInicio = { currentScreen = Screen.Metas },
            onAportes = { currentScreen = Screen.Aportes },
            onMiembros = { currentScreen = Screen.Miembros },
            onPerfil = { currentScreen = Screen.Perfil }
        )

        is Screen.DetalleMeta -> DetalleMetaScreen(
            goalId = screen.goalId,
            viewModel = detalleMetaViewModel,
            onBack = { currentScreen = Screen.Metas },
            onRealizarAporte = { 
                // Al hacer clic en realizar aporte desde el detalle, 
                // podríamos querer pasar el ID de la meta actual.
                // Por ahora, simplemente navegamos a la pantalla de aportes.
                currentScreen = Screen.RealizarAporte 
            },
            onInicio = { currentScreen = Screen.Metas },
            onAportes = { currentScreen = Screen.Aportes },
            onMiembros = { currentScreen = Screen.Miembros },
            onPerfil = { currentScreen = Screen.Perfil },
            onModificarMeta = { currentScreen = Screen.ModificarMeta }
        )

        is Screen.RealizarAporte -> RealizarAporteScreen(
            viewModel = aporteViewModel,
            onBack = { currentScreen = Screen.Metas },
            onConfirmar = { currentScreen = Screen.Aportes },
            onInicio = { currentScreen = Screen.Metas },
            onAportes = { currentScreen = Screen.Aportes },
            onMiembros = { currentScreen = Screen.Miembros },
            onPerfil = { currentScreen = Screen.Perfil }
        )

        is Screen.Aportes -> AportesScreen(
            viewModel = metasViewModel,
            onEditarAporte = { contribution, goalId, goalName ->
                aporteViewModel.seleccionarAporte(contribution, goalId, goalName)
                currentScreen = Screen.EditarAporte
            },
            onInicio = { currentScreen = Screen.Metas },
            onMiembros = { currentScreen = Screen.Miembros },
            onPerfil = { currentScreen = Screen.Perfil },
            onRealizarAportes = { currentScreen = Screen.RealizarAporte }
        )

        is Screen.EditarAporte -> EditarAporteScreen(
            viewModel = aporteViewModel,
            onGuardar = { currentScreen = Screen.Aportes },
            onCancelar = { currentScreen = Screen.Aportes },
            onInicio = { currentScreen = Screen.Metas },
            onMiembros = { currentScreen = Screen.Miembros },
            onPerfil = { currentScreen = Screen.Perfil }
        )

        is Screen.Perfil -> PerfilScreen(
            onBack = { currentScreen = Screen.Metas },
            onInicio = { currentScreen = Screen.Metas },
            onAportes = { currentScreen = Screen.Aportes },
            onMiembros = { currentScreen = Screen.Miembros }
        )

        is Screen.Miembros -> MiembrosScreen(
            viewModel = personViewModel,
            onBack = { currentScreen = Screen.Metas },
            onInicio = { currentScreen = Screen.Metas },
            onAportes = { currentScreen = Screen.Aportes },
            onPerfil = { currentScreen = Screen.Perfil },
            onAgregarMiembro = { currentScreen = Screen.AgregarMiembro }
        )

        is Screen.AgregarMiembro -> AgregarMiembroScreen(
            viewModel = personViewModel,
            onGuardar = { currentScreen = Screen.Miembros },
            onInicio = { currentScreen = Screen.Metas },
            onAportes = { currentScreen = Screen.Aportes },
            onMiembros = { currentScreen = Screen.Miembros },
            onPerfil = { currentScreen = Screen.Perfil }
        )

        is Screen.ModificarMeta -> ModificarMetaScreen(
            onGuardar = { currentScreen = Screen.Metas },
            onEliminar = { currentScreen = Screen.Metas },
            onInicio = { currentScreen = Screen.Metas },
            onAportes = { currentScreen = Screen.Aportes },
            onMiembros = { currentScreen = Screen.Miembros },
            onPerfil = { currentScreen = Screen.Perfil }
        )
    }
}
