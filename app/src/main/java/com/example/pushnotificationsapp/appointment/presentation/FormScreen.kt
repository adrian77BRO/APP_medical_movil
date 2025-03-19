package com.example.pushnotificationsapp.appointment.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.DateRange
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

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    idJob: Int,
    onReservationSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: AppointmentViewModel = viewModel(
        factory = AppointViewModelFactory(context.applicationContext as Application, idJob)
    )
    val appointmentState by viewModel.appointmentState.observeAsState(AppointmentState.Idle)

    var dateAppoint by remember { mutableStateOf("") }
    var observations by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    val customGreen = Color(0xFF3ECF72)

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            dateAppoint = String.format(
                "%02d/%02d/%04d",
                selectedDayOfMonth,
                selectedMonth + 1,
                selectedYear
            )
            error = ""
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    LaunchedEffect(key1 = appointmentState) {
        if (appointmentState is AppointmentState.SuccessForm) {
            onReservationSuccess()
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Reservar cita") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Reservar cita",
                style = MaterialTheme.typography.headlineLarge,
                color = customGreen,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = dateAppoint,
                onValueChange = {},
                label = { Text("Seleccionar fecha") },
                leadingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(
                            imageVector = Icons.Rounded.DateRange,
                            contentDescription = "Seleccionar fecha",
                            tint = customGreen
                        )
                    }
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = customGreen,
                    unfocusedBorderColor = customGreen,
                    focusedLabelColor = customGreen,
                    unfocusedLabelColor = customGreen,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = observations,
                onValueChange = { observations = it },
                label = { Text("Observaciones") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                maxLines = 5,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = customGreen,
                    unfocusedBorderColor = customGreen,
                    focusedLabelColor = customGreen,
                    unfocusedLabelColor = customGreen,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (dateAppoint.isBlank() || observations.isBlank()) {
                        error = "Todos los campos son requeridos"
                    } else {
                        val dateForRequest = try {
                            val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val parsedDate = inputFormat.parse(dateAppoint)
                            outputFormat.format(parsedDate)
                        } catch (e: Exception) { "" }

                        viewModel.reserve(dateForRequest, observations)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = customGreen
                )
            ) {
                Text(
                    "Reservar cita",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (error.isNotEmpty()) {
                Text(
                    text = error,
                    color = Color(0xFFFF0000),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            when (appointmentState) {
                is AppointmentState.Loading -> {
                    CircularProgressIndicator(color = customGreen)
                }
                is AppointmentState.SuccessForm -> {
                    Text(
                        text = (appointmentState as AppointmentState.SuccessForm).message,
                        color = customGreen,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                is AppointmentState.Error -> {
                    Text(
                        text = (appointmentState as AppointmentState.Error).message,
                        color = Color(0xFFFF0000),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                else -> {}
            }
        }
    }
}

/*import android.annotation.SuppressLint
import android.app.Application
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pushnotificationsapp.appointment.data.models.AppointmentState
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("DefaultLocale")
@Composable
fun FormScreen(
    idJob: Int,
    onReservationSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: AppointmentViewModel = viewModel(
        factory = AppointViewModelFactory(context.applicationContext as Application, idJob)
    )
    val appointmentState by viewModel.appointmentState.observeAsState(AppointmentState.Idle)

    var dateAppoint by remember { mutableStateOf("") }
    var observations by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            dateAppoint = String.format("%02d/%02d/%04d", selectedDayOfMonth, selectedMonth + 1, selectedYear)
            error = ""
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    LaunchedEffect(key1 = appointmentState) {
        if (appointmentState is AppointmentState.SuccessForm) {
            onReservationSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reservar Cita") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = dateAppoint,
                onValueChange = {},
                label = { Text("Seleccionar fecha") },
                modifier = Modifier
                    .fillMaxWidth(),
                readOnly = true,
                isError = error.isNotEmpty(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            datePickerDialog.show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.DateRange,
                            contentDescription = "Seleccionar fecha"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = observations,
                onValueChange = { observations = it },
                label = { Text("Observaciones") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp), maxLines = 5,
                isError = error.isNotEmpty()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (dateAppoint.isBlank() || observations.isBlank()) {
                        error = "Todos los campos son requeridos"
                    } else {
                        val dateForRequest = try {
                            val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val parsedDate = inputFormat.parse(dateAppoint)
                            outputFormat.format(parsedDate)
                        } catch (e: Exception) { "" }
                        viewModel.reserve(dateForRequest, observations)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reservar Cita")
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (error.isNotEmpty()) {
                Text(
                    text = error,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption
                )
            }
            when (appointmentState) {
                is AppointmentState.Loading -> {
                    CircularProgressIndicator()
                }
                is AppointmentState.SuccessForm -> {
                    Text(
                        text = (appointmentState as AppointmentState.SuccessForm).message,
                        color = MaterialTheme.colors.primary
                    )
                }
                is AppointmentState.Error -> {
                    Text(
                        text = (appointmentState as AppointmentState.Error).message,
                        color = MaterialTheme.colors.error
                    )
                }
                else -> {}
            }
        }
    }
}*/