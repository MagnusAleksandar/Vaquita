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
fun ModificarMetaScreen(
    onGuardar: () -> Unit,
    onEliminar: () -> Unit,
    onInicio: () -> Unit,
    onAportes: () -> Unit,
    onMiembros: () -> Unit,
    onPerfil: () -> Unit
) {

    Scaffold(

        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = true,
                    onClick = onInicio,
                    icon = {
                        Text("🏠")
                    },
                    label = {
                        Text("Inicio")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onAportes,
                    icon = {
                        Text("💰")
                    },
                    label = {
                        Text("Aportes")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onMiembros,
                    icon = {
                        Text("👨")
                    },
                    label = {
                        Text("Miembros")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onPerfil,
                    icon = {
                        Text("👤")
                    },
                    label = {
                        Text("Perfil")
                    }
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
                text = "Modificar meta",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(
                value = "Televisor 55”",
                onValueChange = {},

                label = {
                    Text("Nombre de la meta")
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "3000000",
                onValueChange = {},

                label = {
                    Text("Valor objetivo")
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "Compra de televisor familiar",
                onValueChange = {},

                label = {
                    Text("Descripción")
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
                    text = "Guardar cambios",
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick = onEliminar,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Red
                )
            ) {

                Text(
                    text = "Eliminar meta",
                    fontSize = 16.sp
                )
            }
        }
    }
}

