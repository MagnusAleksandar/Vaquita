package com.example.appmetas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealizarAporteScreen(
    onBack: () -> Unit,
    onConfirmar: () -> Unit,
    onInicio: () -> Unit,
    onAportes: () -> Unit,
    onMiembros: () -> Unit,
    onPerfil: () -> Unit
) {

    var expandedMiembro by remember {
        mutableStateOf(false)
    }

    var expandedMeta by remember {
        mutableStateOf(false)
    }

    var miembroSeleccionado by remember {
        mutableStateOf("Seleccionar miembro")
    }

    var metaSeleccionada by remember {
        mutableStateOf("Seleccionar meta")
    }

    val miembros = listOf(
        "Juan Pérez",
        "Laura Gómez",
        "Carlos Ruiz"
    )

    val metas = listOf(
        "Televisor 55”",
        "Fondo para Carro",
        "Viaje a Europa"
    )

    Scaffold(

        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = false,
                    onClick = onInicio,
                    icon = { Text("🏠") },
                    label = { Text("Inicio") }
                )

                NavigationBarItem(
                    selected = true,
                    onClick = onAportes,
                    icon = { Text("💰") },
                    label = { Text("Aportes") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onMiembros,
                    icon = { Text("👨") },
                    label = { Text("Miembros") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onPerfil,
                    icon = { Text("👤") },
                    label = { Text("Perfil") }
                )
            }
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Text(
                text = "Nuevo aporte",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(25.dp))

            // =========================
            // LISTA MIEMBROS
            // =========================

            ExposedDropdownMenuBox(
                expanded = expandedMiembro,
                onExpandedChange = {
                    expandedMiembro = !expandedMiembro
                }
            ) {

                OutlinedTextField(
                    value = miembroSeleccionado,
                    onValueChange = {},
                    readOnly = true,

                    label = {
                        Text("Miembro")
                    },

                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandedMiembro
                        )
                    },

                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp)
                )

                ExposedDropdownMenu(
                    expanded = expandedMiembro,
                    onDismissRequest = {
                        expandedMiembro = false
                    }
                ) {

                    miembros.forEach { miembro ->

                        DropdownMenuItem(
                            text = {
                                Text(miembro)
                            },

                            onClick = {
                                miembroSeleccionado = miembro
                                expandedMiembro = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // =========================
            // LISTA METAS
            // =========================

            ExposedDropdownMenuBox(
                expanded = expandedMeta,
                onExpandedChange = {
                    expandedMeta = !expandedMeta
                }
            ) {

                OutlinedTextField(
                    value = metaSeleccionada,
                    onValueChange = {},
                    readOnly = true,

                    label = {
                        Text("Meta")
                    },

                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandedMeta
                        )
                    },

                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp)
                )

                ExposedDropdownMenu(
                    expanded = expandedMeta,
                    onDismissRequest = {
                        expandedMeta = false
                    }
                ) {

                    metas.forEach { meta ->

                        DropdownMenuItem(
                            text = {
                                Text(meta)
                            },

                            onClick = {
                                metaSeleccionada = meta
                                expandedMeta = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // =========================
            // MONTO
            // =========================

            OutlinedTextField(
                value = "",
                onValueChange = {},

                label = {
                    Text("Monto")
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // =========================
            // DESCRIPCION
            // =========================

            OutlinedTextField(
                value = "",
                onValueChange = {},

                label = {
                    Text("Descripción")
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = onConfirmar,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF16A34A)
                ),

                shape = RoundedCornerShape(18.dp)
            ) {

                Text("Confirmar aporte")
            }
        }
    }
}