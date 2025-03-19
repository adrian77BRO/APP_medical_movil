package com.example.pushnotificationsapp.register.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import com.example.pushnotificationsapp.register.data.models.RegisterState

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val customGreen = Color(0xFF3ECF72)

    val fname by viewModel.fname.observeAsState("")
    val lname by viewModel.lname.observeAsState("")
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val registerState by viewModel.registerState.observeAsState(RegisterState.Idle)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = fname,
            onValueChange = { viewModel.onFnameChange(it) },
            label = { Text("Nombre(s)") },
            modifier = Modifier.fillMaxWidth(),
            colors = outlinedTextFieldColors(
                focusedBorderColor = customGreen,
                unfocusedBorderColor = customGreen,
                focusedLabelColor = customGreen
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = lname,
            onValueChange = { viewModel.onLnameChange(it) },
            label = { Text("Apellidos") },
            modifier = Modifier.fillMaxWidth(),
            colors = outlinedTextFieldColors(
                focusedBorderColor = customGreen,
                unfocusedBorderColor = customGreen,
                focusedLabelColor = customGreen
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            colors = outlinedTextFieldColors(
                focusedBorderColor = customGreen,
                unfocusedBorderColor = customGreen,
                focusedLabelColor = customGreen
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = outlinedTextFieldColors(
                focusedBorderColor = customGreen,
                unfocusedBorderColor = customGreen,
                focusedLabelColor = customGreen
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.register() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = customGreen
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onNavigateBack) {
            Text("¿Ya tienes cuenta? Inicia sesión", color = customGreen)
        }
        Spacer(modifier = Modifier.height(8.dp))
        when (registerState) {
            is RegisterState.Loading -> {
                CircularProgressIndicator(color = customGreen)
            }
            is RegisterState.Error -> {
                Text(
                    text = (registerState as RegisterState.Error).message,
                    color = MaterialTheme.colors.error
                )
            }
            is RegisterState.Success -> {
                Text(
                    text = (registerState as RegisterState.Success).message,
                    color = customGreen
                )
            }
            else -> {}
        }
    }
}