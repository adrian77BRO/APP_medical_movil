package com.example.pushnotificationsapp.stepcounter.presentation

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pushnotificationsapp.core.service.StepService
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepCountScreen(viewModel: StepViewModel = viewModel()) {
    val context = LocalContext.current
    val journeys by viewModel.steps.observeAsState(emptyList())

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Contador de Pasos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Botones para iniciar y terminar viaje
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = {
                        // Iniciar viaje: arrancar el servicio
                        val intent = Intent(context, StepService::class.java)
                        ContextCompat.startForegroundService(context, intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Iniciar viaje", color = MaterialTheme.colorScheme.onPrimary)
                }

                Button(
                    onClick = {
                        // Terminar viaje: parar el servicio
                        val intent = Intent(context, StepService::class.java)
                        context.stopService(intent)
                        // Cuando se detenga, onDestroy guardará la diferencia en la BD
                        // Actualizamos la lista en la UI
                        viewModel.loadJourneys()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text("Terminar viaje", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Historial de viajes:",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar los journeys guardados
            LazyColumn {
                items(journeys) { journey ->
                    val start = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                        .format(Date(journey.startTime))
                    val end = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                        .format(Date(journey.endTime))
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Inicio: $start",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Fin: $end",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Pasos: ${journey.totalSteps}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}