package com.example.pushnotificationsapp.appointment.presentation

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pushnotificationsapp.appointment.data.models.AppointmentState
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun HistoryScreen(idJob: Int) {
    val context = LocalContext.current
    val viewModel: AppointmentViewModel = viewModel(
        factory = AppointViewModelFactory(context.applicationContext as Application, idJob)
    )
    val appointmentState by viewModel.appointmentsState.observeAsState(AppointmentState.Idle)

    when (appointmentState) {
        is AppointmentState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is AppointmentState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = (appointmentState as AppointmentState.Error).message)
            }
        }
        is AppointmentState.Success -> {
            val appointments = (appointmentState as AppointmentState.Success).appointments
            if (appointments.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No hay citas reservadas")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text(
                            text = "Historial de citas",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(24.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(appointments) { appointment ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = 4.dp
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

                                Text(text = "Fecha: $formattedDate", style = MaterialTheme.typography.subtitle1)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = "Observaciones: ${appointment.observations}")
                                Spacer(modifier = Modifier.height(6.dp))
                                val statusText = when (appointment.status) {
                                    0 -> "Pendiente"
                                    1 -> "Confirmado"
                                    else -> "Desconocido"
                                }
                                val statusColor = when (appointment.status) {
                                    0 -> Color(0xFFFFC107)
                                    1 -> Color(0xFF4CAF50)
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