package com.example.pushnotificationsapp.appointment.presentation

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pushnotificationsapp.appointment.data.models.AppointmentState
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    idJob: Int,
    onNavigateToReservation: (serviceId: Int) -> Unit
) {
    val context = LocalContext.current
    val viewModel: AppointmentViewModel = viewModel(
        factory = AppointViewModelFactory(context.applicationContext as Application, idJob)
    )
    val appointmentState by viewModel.appointmentState.observeAsState(AppointmentState.Idle)

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Historial de citas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToReservation(idJob) },
                containerColor = Color(0xFF3ECF72)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Reservar cita"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (appointmentState) {
                is AppointmentState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is AppointmentState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = (appointmentState as AppointmentState.Error).message)
                    }
                }
                is AppointmentState.Success -> {
                    val appointments = (appointmentState as AppointmentState.Success).appointments
                    if (appointments.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay citas reservadas",
                                color = Color(0xFF555555),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(appointments) { appointment ->
                                ElevatedCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    elevation = CardDefaults.elevatedCardElevation(4.dp),
                                    colors = CardDefaults.elevatedCardColors(
                                        containerColor = Color.White
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        val formattedDate = try {
                                            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                                            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                                            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                            val parsedDate = inputFormat.parse(appointment.date_appoint)
                                            outputFormat.format(parsedDate)
                                        } catch (e: Exception) {
                                            appointment.date_appoint
                                        }

                                        Text(
                                            text = "Fecha: $formattedDate",
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(text = "Observaciones: ${appointment.observations}")
                                        Spacer(modifier = Modifier.height(6.dp))

                                        val statusText = when (appointment.status) {
                                            0 -> "Pendiente"
                                            1 -> "Confirmado"
                                            else -> "Desconocido"
                                        }
                                        val statusColor = when (appointment.status) {
                                            0 -> Color(0xFFFFC107) // Amarillo
                                            1 -> Color(0xFF4CAF50) // Verde
                                            else -> Color.Gray
                                        }
                                        Text(
                                            text = "Estatus: $statusText",
                                            color = statusColor,
                                            fontWeight = FontWeight.Bold
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