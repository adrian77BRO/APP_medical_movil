package com.example.pushnotificationsapp.job.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pushnotificationsapp.job.data.models.JobState

@Composable
fun ListJobsScreen(
    onNavigateToHistory: (idJob: Int) -> Unit,
    viewModel: JobsViewModel = viewModel()
) {
    val jobsState by viewModel.jobsState.observeAsState(JobState.Idle)

    when (jobsState) {
        is JobState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is JobState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = (jobsState as JobState.Error).message)
            }
        }
        is JobState.Success -> {
            val jobs = (jobsState as JobState.Success).jobs
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = "Servicios médicos",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(24.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                items(jobs) { job ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = job.title, style = MaterialTheme.typography.h6)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = job.description)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Precio: \$${job.cost}")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Médico: ${job.doctor}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { onNavigateToHistory(job.id_job) },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFF3ECF72)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Reservar cita", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
        else -> {}
    }
}