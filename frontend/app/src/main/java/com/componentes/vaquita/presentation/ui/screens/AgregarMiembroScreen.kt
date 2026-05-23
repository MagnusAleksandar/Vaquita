package com.componentes.vaquita.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AgregarMiembroScreen(
    onGuardar: () -> Unit,
    onInicio: () -> Unit,
    onAportes: () -> Unit,
    onMiembros: () -> Unit,
    onPerfil: () -> Unit
) {

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
                    selected = true,
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
                text = "Agregar miembro",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Nombre completo")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Correo")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Teléfono")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = onGuardar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF16A34A)
                ),

                shape = RoundedCornerShape(18.dp)
            ) {

                Text(
                    text = "Guardar miembro",
                    fontSize = 16.sp
                )
            }
        }
    }
}