package com.componentes.vaquita.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.componentes.vaquita.presentation.ui.states.MetasUiState
import com.componentes.vaquita.presentation.ui.viewmodel.MetasViewModel

@Composable
fun MetasScreen(
    viewModel: MetasViewModel,
    onNuevaMeta: () -> Unit,
    onVerMeta: (String) -> Unit,
    onVerMiembros: () -> Unit,
    onVerPerfil: () -> Unit,
    onVerAportes: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getMetas()
    }

    LaunchedEffect(uiState) {
        if (uiState is MetasUiState.Success) {
            Toast.makeText(context, "Conectado al servidor con éxito", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Text("🏠") },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onVerAportes,
                    icon = { Text("💰") },
                    label = { Text("Aportes") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onVerMiembros,
                    icon = { Text("👨") },
                    label = { Text("Miembros") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onVerPerfil,
                    icon = { Text("👤") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { padding ->
        when (val state = uiState) {
            is MetasUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is MetasUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${state.message}", color = Color.Red)
                }
            }
            is MetasUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Mis metas",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                            FloatingActionButton(
                                onClick = onNuevaMeta,
                                modifier = Modifier.size(50.dp),
                                containerColor = Color(0xFF16A34A),
                                shape = CircleShape
                            ) {
                                Text(
                                    text = "+",
                                    color = Color.White,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }

                    if (state.goals.isNotEmpty()) {
                        val principalGoal = state.goals.first()
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onVerMeta(principalGoal._id ?: "")
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF0E9F4B)
                                ),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "Fondo para",
                                                color = Color.White.copy(alpha = 0.9f),
                                                fontSize = 16.sp
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = principalGoal.name ?: "Sin nombre",
                                                color = Color.White,
                                                fontSize = 26.sp,
                                                fontWeight = FontWeight.Bold,
                                                lineHeight = 30.sp
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Box(
                                            modifier = Modifier
                                                .size(85.dp)
                                                .background(
                                                    Color.Black,
                                                    RoundedCornerShape(12.dp)
                                                )
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(18.dp))

                                    val totalAportado = principalGoal.contributions?.sumOf { it.amount ?: 0 } ?: 0
                                    val goalAmount = principalGoal.amount ?: 0
                                    val progress = if (goalAmount > 0) totalAportado.toFloat() / goalAmount else 0f
                                    val percentage = (progress * 100).toInt()

                                    Text(
                                        text = "Meta",
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontSize = 13.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "$$goalAmount",
                                        color = Color.White,
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Text(
                                        text = "$$totalAportado / $$goalAmount",
                                        color = Color.White.copy(alpha = 0.9f),
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    LinearProgressIndicator(
                                        progress = { progress },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp),
                                        color = Color.White,
                                        trackColor = Color.White.copy(alpha = 0.3f)
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "$percentage% completado",
                                        color = Color.White,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }

                        if (state.goals.size > 1) {
                            item {
                                Text(
                                    text = "Otras metas",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            items(state.goals.drop(1)) { meta ->
                                val totalAportadoOther = meta.contributions?.sumOf { it.amount ?: 0 } ?: 0
                                val metaAmount = meta.amount ?: 0
                                val progressOther = if (metaAmount > 0) totalAportadoOther.toFloat() / metaAmount else 0f
                                val percentageOther = (progressOther * 100).toInt()

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onVerMeta(meta._id ?: "") },
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(55.dp)
                                                .background(Color.LightGray, RoundedCornerShape(12.dp))
                                        )
                                        Spacer(modifier = Modifier.width(14.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(text = meta.name ?: "Sin nombre", fontWeight = FontWeight.Bold)
                                            Spacer(modifier = Modifier.height(5.dp))
                                            Text(text = "Ver detalles de la meta", fontSize = 12.sp)
                                            Spacer(modifier = Modifier.height(8.dp))
                                            LinearProgressIndicator(
                                                progress = { progressOther },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(6.dp),
                                                color = Color(0xFF16A34A),
                                                trackColor = Color.LightGray
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = "$percentageOther%",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        item {
                            Text("No tienes metas creadas todavía.", color = Color.Gray)
                        }
                    }
                    item { Spacer(modifier = Modifier.height(20.dp)) }
                }
            }
            else -> {}
        }
    }
}
