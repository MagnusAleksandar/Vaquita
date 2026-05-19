package com.example.appmetas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.appmetas.ui.screens.*


sealed class Screen {

    object Metas : Screen()

    object CrearMeta : Screen()

    object DetalleMeta : Screen()

    object RealizarAporte : Screen()

    object Miembros : Screen()

    object Perfil : Screen()
}

@Composable
fun AppRoutes() {

    var currentScreen by remember {
        mutableStateOf<Screen>(Screen.Metas)
    }

    when(currentScreen){

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
            }
        )

        is Screen.CrearMeta -> CrearMetaScreen(
            onBack = {
                currentScreen = Screen.Metas
            },
            onCrear = {
                currentScreen = Screen.DetalleMeta
            }
        )

        is Screen.DetalleMeta -> DetalleMetaScreen(
            onBack = {
                currentScreen = Screen.Metas
            },
            onRealizarAporte = {
                currentScreen = Screen.RealizarAporte
            }
        )

        is Screen.RealizarAporte -> RealizarAporteScreen(
            onBack = {
                currentScreen = Screen.DetalleMeta
            },
            onConfirmar = {
                currentScreen = Screen.DetalleMeta
            }
        )

        is Screen.Miembros -> MiembrosScreen(
            onBack = {
                currentScreen = Screen.Metas
            }
        )

        is Screen.Perfil -> PerfilScreen(
            onBack = {
                currentScreen = Screen.Metas
            }
        )
    }
}