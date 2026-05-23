package com.componentes.vaquita

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.componentes.vaquita.presentation.ui.screens.AgregarMiembroScreen
import com.componentes.vaquita.presentation.ui.screens.AportesScreen
import com.componentes.vaquita.presentation.ui.screens.EditarAporteScreen
import com.example.appmetas.ui.screens.*

sealed class Screen {

    object Metas : Screen()

    object CrearMeta : Screen()

    object DetalleMeta : Screen()

    object RealizarAporte : Screen()

    object Miembros : Screen()

    object Perfil : Screen()

    object Aportes : Screen()

    object EditarAporte : Screen()

    object AgregarMiembro : Screen()

    object ModificarMeta : Screen()
}

@Composable
fun AppRoutes() {

    var currentScreen by remember {
        mutableStateOf<Screen>(Screen.Metas)
    }

    when (currentScreen) {


        is Screen.Metas -> MetasScreen(

            onNuevaMeta = {
                currentScreen = Screen.CrearMeta
            },

            onVerMeta = {
                currentScreen = Screen.DetalleMeta
            },

            onVerMiembros = {
                currentScreen = Screen.Miembros
            },

            onVerPerfil = {
                currentScreen = Screen.Perfil
            },

            onVerAportes = {
                currentScreen = Screen.Aportes
            }
        )


        is Screen.CrearMeta -> CrearMetaScreen(

            onBack = {
                currentScreen = Screen.Metas
            },

            onCrear = {
                currentScreen = Screen.Metas
            },

            onInicio = {
                currentScreen = Screen.Metas
            },

            onAportes = {
                currentScreen = Screen.Aportes
            },

            onMiembros = {
                currentScreen = Screen.Miembros
            },

            onPerfil = {
                currentScreen = Screen.Perfil
            }
        )


        is Screen.DetalleMeta -> DetalleMetaScreen(

            onBack = {
                currentScreen = Screen.Metas
            },

            onRealizarAporte = {
                currentScreen = Screen.RealizarAporte
            },

            onInicio = {
                currentScreen = Screen.Metas
            },

            onAportes = {
                currentScreen = Screen.Aportes
            },

            onMiembros = {
                currentScreen = Screen.Miembros
            },

            onPerfil = {
                currentScreen = Screen.Perfil
            },

            onModificarMeta = {
                currentScreen = Screen.ModificarMeta
            }
        )


        is Screen.RealizarAporte -> RealizarAporteScreen(

            onBack = {
                currentScreen = Screen.DetalleMeta
            },

            onConfirmar = {
                currentScreen = Screen.Aportes
            },

            onInicio = {
                currentScreen = Screen.Metas
            },

            onAportes = {
                currentScreen = Screen.Aportes
            },

            onMiembros = {
                currentScreen = Screen.Miembros
            },

            onPerfil = {
                currentScreen = Screen.Perfil
            }
        )


        is Screen.Aportes -> AportesScreen(

            onEditarAporte = {
                currentScreen = Screen.EditarAporte
            },

            onInicio = {
                currentScreen = Screen.Metas
            },

            onMiembros = {
                currentScreen = Screen.Miembros
            },

            onPerfil = {
                currentScreen = Screen.Perfil
            },

            onRealizarAportes = {
                currentScreen = Screen.RealizarAporte
            }
        )


        is Screen.EditarAporte -> EditarAporteScreen(

            onGuardar = {
                currentScreen = Screen.Aportes
            },

            onCancelar = {
                currentScreen = Screen.Aportes
            },

            onInicio = {
                currentScreen = Screen.Metas
            },

            onMiembros = {
                currentScreen = Screen.Miembros
            },

            onPerfil = {
                currentScreen = Screen.Perfil
            }
        )

        is Screen.Perfil -> PerfilScreen(

            onBack = {
                currentScreen = Screen.Metas
            },

            onInicio = {
                currentScreen = Screen.Metas
            },

            onAportes = {
                currentScreen = Screen.Aportes
            },

            onMiembros = {
                currentScreen = Screen.Miembros
            }
        )

        is Screen.Miembros -> MiembrosScreen(

            onBack = {
                currentScreen = Screen.Metas
            },

            onInicio = {
                currentScreen = Screen.Metas
            },

            onAportes = {
                currentScreen = Screen.Aportes
            },

            onPerfil = {
                currentScreen = Screen.Perfil
            },

            onAgregarMiembro = {
                currentScreen = Screen.AgregarMiembro
            }
        )

        is Screen.AgregarMiembro -> AgregarMiembroScreen(

            onGuardar = {
                currentScreen = Screen.Miembros
            },

            onInicio = {
                currentScreen = Screen.Metas
            },

            onAportes = {
                currentScreen = Screen.Aportes
            },

            onMiembros = {
                currentScreen = Screen.Miembros
            },

            onPerfil = {
                currentScreen = Screen.Perfil
            }
        )

        is Screen.ModificarMeta -> ModificarMetaScreen(

            onGuardar = {
                currentScreen = Screen.Metas
            },

            onEliminar = {
                currentScreen = Screen.Metas
            },

            onInicio = {
                currentScreen = Screen.Metas
            },

            onAportes = {
                currentScreen = Screen.Aportes
            },

            onMiembros = {
                currentScreen = Screen.Miembros
            },

            onPerfil = {
                currentScreen = Screen.Perfil
            }
        )
    }
}