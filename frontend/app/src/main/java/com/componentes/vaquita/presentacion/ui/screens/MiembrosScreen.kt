package com.componentes.vaquita.presentacion.ui.screens

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
import com.componentes.vaquita.presentacion.ui.states.MiembrosUiState
import com.componentes.vaquita.presentacion.ui.viewmodel.PersonViewModel

@Composable
fun MiembrosScreen(
    viewModel: PersonViewModel,
    onBack: () -> Unit,
    onInicio: () -> Unit,
    onAportes: () -> Unit,
    onAgregarMiembro: () -> Unit
) {
    val uiState by viewModel.miembrosState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPersons()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAgregarMiembro,
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
                    selected = false,
                    onClick = onAportes,
                    icon = { Text("💰") },
                    label = { Text("Aportes") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Text("👨") },
                    label = { Text("Miembros") }
                )
            }
        }
    ) { padding ->
        when (val state = uiState) {
            is MiembrosUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is MiembrosUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${state.message}", color = Color.Red)
                }
            }
            is MiembrosUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    item {
                        Text(
                            text = "Miembros",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    items(state.members) { person ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = person.persName ?: "Sin nombre",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "ID: ${person.idNum ?: "N/A"}",
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Tel: ${person.persPhone ?: "N/A"}",
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
            else -> {}
        }
    }
}
