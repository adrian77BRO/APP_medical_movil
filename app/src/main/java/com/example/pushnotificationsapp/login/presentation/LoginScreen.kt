package com.example.pushnotificationsapp.login.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.pushnotificationsapp.login.data.models.LoginState

@Composable
fun LoginScreen(
    onNavigateToServices: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val customGreen = Color(0xFF3ECF72)

    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val loginState by viewModel.loginState.observeAsState(LoginState.Idle)

    if (loginState is LoginState.Success) {
        onNavigateToServices()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.login() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = customGreen
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes cuenta? Regístrate", color = customGreen)
        }
        Spacer(modifier = Modifier.height(8.dp))
        when (loginState) {
            is LoginState.Loading -> {
                CircularProgressIndicator(color = customGreen)
            }
            is LoginState.Error -> {
                Text(
                    text = (loginState as LoginState.Error).message,
                    color = MaterialTheme.colors.error
                )
            }
            is LoginState.Success -> {
                Text(
                    text = (loginState as LoginState.Success).message,
                    color = customGreen
                )
            }
            else -> {}
        }
    }
}