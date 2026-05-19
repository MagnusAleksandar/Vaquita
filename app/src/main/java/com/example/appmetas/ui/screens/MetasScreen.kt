package com.example.appmetas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
    onVerPerfil: () -> Unit
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
                    onClick = {},
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

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

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

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF0E9F4B)
                ),

                shape = RoundedCornerShape(24.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {

                            Text(
                                text = "Fondo para",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "Televisor 55”",
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        //AQUI IRIA LA IMAGEN ES EL CUBO NEGRO ES SOLO PARA DAR UN EJEMPLO
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .background(
                                    Color.Black,
                                    RoundedCornerShape(10.dp)
                                )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Meta",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "$3.000.000",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "$ 1.200.000 / $ 3.000.000",
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

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Otras metas",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
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
                            text = "Fondo para Carro",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "$ 6.500.000 / $ 30.000.000",
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { 0.22f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp),

                            color = Color(0xFF16A34A),

                            trackColor = Color.LightGray
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "22%",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
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
                            text = "Viaje a Europa",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "$ 2.150.000 / $ 8.000.000",
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { 0.26f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp),

                            color = Color(0xFF16A34A),

                            trackColor = Color.LightGray
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "26%",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}