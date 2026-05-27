package com.componentes.vaquita.presentacion.ui.screens

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
import com.componentes.vaquita.dominio.models.Contribution
import com.componentes.vaquita.dominio.states.UiState
import com.componentes.vaquita.presentacion.viewmodels.MetasViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AportesScreen(
    viewModel: MetasViewModel,
    onRealizarAportes: () -> Unit,
    onInicio: () -> Unit,
    onMiembros: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val formato = NumberFormat.getCurrencyInstance(Locale("es", "CO"))

    LaunchedEffect(Unit) {
        viewModel.getMetas()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onRealizarAportes,
                containerColor = Color(0xFF16A34A),
                shape = CircleShape
            ) {
                Text(
                    text = "+",
                    color = Color.White,
                    fontSize = 24.sp
                )
            }
        },
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
                    onClick = {},
                    icon = { Text("💰") },
                    label = { Text("Aportes") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onMiembros,
                    icon = { Text("👨") },
                    label = { Text("Miembros") }
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
                text = "Aportes",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            when (val state = uiState) {
                is UiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Error -> {
                    Text("Error: ${state.message}", color = Color.Red)
                }
                is UiState.Success -> {
                    val goals = state.data
                    val allContributions = goals.flatMap { goal ->
                        goal.contributions?.map { Triple(it, goal._id ?: "", goal.name ?: "Meta desconocida") } ?: emptyList()
                    }.sortedByDescending { it.first.createdAt }

                    if (allContributions.isEmpty()) {
                        Text("No hay aportes registrados")
                    } else {
                        LazyColumn {
                            items(allContributions) { (contribution, goalId, goalName) ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Column(modifier = Modifier.padding(18.dp)) {
                                        Text(
                                            text = "Aporte: $goalName",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        
                                        if (!contribution.description.isNullOrBlank()) {
                                            Text(
                                                text = contribution.description,
                                                color = Color.Gray,
                                                fontSize = 14.sp,
                                                modifier = Modifier.padding(bottom = 6.dp)
                                            )
                                        }

                                        Text(
                                            text = formato.format(contribution.amount ?: 0),
                                            color = Color(0xFF16A34A),
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(contribution.contributor?.persName ?: "Anónimo")
                                        Spacer(modifier = Modifier.height(6.dp))
                                        
                                        val dateStr = contribution.createdAt?.let {
                                            try {
                                                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
                                                inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                                                val date = inputFormat.parse(it)
                                                val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                                                outputFormat.format(date!!)
                                            } catch (e: Exception) {
                                                it
                                            }
                                        } ?: "Fecha desconocida"
                                        
                                        Text(
                                            text = dateStr,
                                            color = Color.Gray,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }
}
