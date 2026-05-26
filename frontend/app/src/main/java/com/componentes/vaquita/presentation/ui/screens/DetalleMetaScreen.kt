package com.componentes.vaquita.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.componentes.vaquita.presentation.ui.states.DetalleMetaUiState
import com.componentes.vaquita.presentation.ui.viewmodel.DetalleMetaViewModel

@Composable
fun DetalleMetaScreen(
    goalId: String,
    viewModel: DetalleMetaViewModel,
    onBack: () -> Unit,
    onRealizarAporte: () -> Unit,
    onModificarMeta: () -> Unit,
    onInicio: () -> Unit,
    onAportes: () -> Unit,
    onMiembros: () -> Unit,
    onPerfil: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(goalId) {
        viewModel.getGoalById(goalId)
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
                    selected = false,
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
        when (val state = uiState) {
            is DetalleMetaUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is DetalleMetaUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${state.message}", color = Color.Red)
                }
            }
            is DetalleMetaUiState.Success -> {
                val meta = state.goal
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Detalle meta",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF0E9F4B)
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "Fondo para ${meta.name ?: "Sin nombre"}",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            val totalAportado = meta.contributions?.sumOf { it.amount ?: 0 } ?: 0
                            val goalAmount = meta.amount ?: 0
                            val progress = if (goalAmount > 0) totalAportado.toFloat() / goalAmount else 0f
                            val percentage = (progress * 100).toInt()
                            
                            Text(
                                text = "$$totalAportado / $$goalAmount",
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.White,
                                trackColor = Color.White.copy(alpha = 0.3f)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "$percentage% completado",
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    Text(
                        text = "Miembros",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    meta.contributions?.forEach { contribution ->
                        Text("${contribution.contributor?.persName ?: "Desconocido"} - $${contribution.amount ?: 0}")
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = onRealizarAporte,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF16A34A)
                        )
                    ) {
                        Text("Añadir aporte")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onModificarMeta,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF16A34A).copy(alpha = 0.7f)
                        )
                    ) {
                        Text("Modificar meta")
                    }
                }
            }
            else -> {}
        }
    }
}
