package com.example.appmetas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MetasScreen(
    onNuevaMeta: () -> Unit,
    onVerMeta: () -> Unit,
    onVerMiembros: () -> Unit,
    onVerPerfil: () -> Unit,
    onVerAportes: () -> Unit
) {

    Scaffold(

        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Text("🏠")
                    },
                    label = {
                        Text("Inicio")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onVerAportes,
                    icon = {
                        Text("💰")
                    },
                    label = {
                        Text("Aportes")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onVerMiembros,
                    icon = {
                        Text("👨")
                    },
                    label = {
                        Text("Miembros")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onVerPerfil,
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

            item {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onVerMeta()
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

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = "Fondo para",
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 16.sp
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = "Televisor 55”",
                                    color = Color.White,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 30.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            //AQUI IRIA LA IMAGEN EL CUBO NEGRO ES SOLO PARA DAR UN EJEMPLO
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

                        Text(
                            text = "Meta",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "$3.000.000",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "$1.200.000 / $3.000.000",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LinearProgressIndicator(
                            progress = { 0.4f },

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),

                            color = Color.White,

                            trackColor = Color.White.copy(alpha = 0.3f)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "40% completado",
                            color = Color.White,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            item {

                Text(
                    text = "Otras metas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(10) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onVerMeta()
                        },

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
                                .background(
                                    Color.LightGray,
                                    RoundedCornerShape(12.dp)
                                )
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = "Meta ${it + 1}",
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = "$ 2.000.000 / $ 10.000.000",
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            LinearProgressIndicator(
                                progress = { 0.2f },

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp),

                                color = Color(0xFF16A34A),

                                trackColor = Color.LightGray
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "20%",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            item {

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}