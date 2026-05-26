package com.componentes.vaquita.presentacion.ui.screens

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
import com.componentes.vaquita.presentacion.ui.states.AporteUiState
import com.componentes.vaquita.presentacion.ui.viewmodel.AporteViewModel

@Composable
fun EditarAporteScreen(
    viewModel: AporteViewModel,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit,
    onInicio: () -> Unit,
    onMiembros: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedContribution by viewModel.selectedContribution.collectAsState()
    val selectedGoalName by viewModel.selectedGoalName.collectAsState()

    var monto by remember { mutableStateOf("") }

    // Cargar monto inicial cuando se selecciona un aporte
    LaunchedEffect(selectedContribution) {
        selectedContribution?.let {
            monto = it.amount?.toString() ?: ""
        }
    }

    // Manejo de éxito tras guardar
    LaunchedEffect(uiState) {
        if (uiState is AporteUiState.Success) {
            onGuardar()
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
                    onClick = onCancelar, // Volver a aportes
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
                    text = "Editar aporte",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(25.dp))

                if (uiState is AporteUiState.Error) {
                    Text(
                        text = (uiState as AporteUiState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                OutlinedTextField(
                    value = monto,
                    onValueChange = { monto = it },
                    label = { Text("Monto") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = selectedGoalName ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Meta") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = selectedContribution?.contributor?.persName ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Miembro") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = { viewModel.actualizarAporte(monto) },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text("Guardar cambios")
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedButton(
                    onClick = onCancelar,
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}
