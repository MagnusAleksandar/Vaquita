package com.example.appmetas.ui.screens

import androidx.compose.foundation.background
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
fun DetalleMetaScreen(
    onBack: () -> Unit,
    onRealizarAporte: () -> Unit,
    onModificarMeta: () -> Unit,
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
                        text = "Fondo para Televisor",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "$1.200.000 / $3.000.000",
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { 0.4f },
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "40% completado",
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

            Text("Juan - $300.000")
            Spacer(modifier = Modifier.height(8.dp))

            Text("Laura - $400.000")
            Spacer(modifier = Modifier.height(8.dp))

            Text("Carlos - $500.000")

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = onModificarMeta,
                modifier = Modifier.fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF16A34A)
                )
            ) {

                Text("Modificar meta")
            }
        }
    }
}