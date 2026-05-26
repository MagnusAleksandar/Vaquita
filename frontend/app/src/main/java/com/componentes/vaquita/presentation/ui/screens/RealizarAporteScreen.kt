package com.componentes.vaquita.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.componentes.vaquita.domain.model.Goal
import com.componentes.vaquita.domain.model.Person
import com.componentes.vaquita.presentation.ui.viewmodel.AporteViewModel
import com.componentes.vaquita.presentation.ui.states.AporteUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealizarAporteScreen(
    viewModel: AporteViewModel,
    onBack: () -> Unit,
    onConfirmar: () -> Unit,
    onInicio: () -> Unit,
    onAportes: () -> Unit,
    onMiembros: () -> Unit,
    onPerfil: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val members by viewModel.members.collectAsState()
    val goals by viewModel.goals.collectAsState()

    var expandedMiembro by remember { mutableStateOf(false) }
    var expandedMeta by remember { mutableStateOf(false) }

    var miembroSeleccionado by remember { mutableStateOf<Person?>(null) }
    var metaSeleccionada by remember { mutableStateOf<Goal?>(null) }
    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // Manejo de éxito
    LaunchedEffect(uiState) {
        if (uiState is AporteUiState.Success) {
            onConfirmar()
            viewModel.resetState()
        }
    }

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
        if (uiState is AporteUiState.Loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
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

                // Error message
                if (uiState is AporteUiState.Error) {
                    Text(
                        text = (uiState as AporteUiState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // =========================
                // LISTA MIEMBROS
                // =========================
                ExposedDropdownMenuBox(
                    expanded = expandedMiembro,
                    onExpandedChange = { expandedMiembro = !expandedMiembro }
                ) {
                    OutlinedTextField(
                        value = miembroSeleccionado?.persName ?: "Seleccionar miembro",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Miembro") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMiembro) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedMiembro,
                        onDismissRequest = { expandedMiembro = false }
                    ) {
                        members.forEach { miembro ->
                            DropdownMenuItem(
                                text = { Text(miembro.persName ?: "Sin nombre") },
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
                    onExpandedChange = { expandedMeta = !expandedMeta }
                ) {
                    OutlinedTextField(
                        value = metaSeleccionada?.name ?: "Seleccionar meta",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Meta") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMeta) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedMeta,
                        onDismissRequest = { expandedMeta = false }
                    ) {
                        goals.forEach { meta ->
                            DropdownMenuItem(
                                text = { Text(meta.name ?: "Sin nombre") },
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
                    value = monto,
                    onValueChange = { monto = it },
                    label = { Text("Monto") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // =========================
                // DESCRIPCION
                // =========================
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        miembroSeleccionado?.let { person ->
                            metaSeleccionada?._id?.let { goalId ->
                                viewModel.registrarAporte(goalId, person, monto)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text("Confirmar aporte")
                }
            }
        }
    }
}
