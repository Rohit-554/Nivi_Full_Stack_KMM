package io.jadu.nivi.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import io.jadu.nivi.presentation.viewModel.AuthUiState
import io.jadu.nivi.presentation.viewModel.AuthViewModel
import org.koin.compose.koinInject

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = koinInject()
) {

    // Collect the StateFlow as Compose State
    val uiState by viewModel.uiState.collectAsState()

    // Local state for the input fields
    var isLoginMode by remember { mutableStateOf(true) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Helper boolean to disable inputs while loading
    val isLoading = uiState is AuthUiState.Loading

    // Optional: Handle Side Effects (like Navigation on Success)
    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {

            println("Auth Successful: ${(uiState as AuthUiState.Success).data}")
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = if (isLoginMode) "Welcome Back" else "Create an account",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            // -- NAME FIELD (only for signup) --
            if (!isLoginMode) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // -- EMAIL FIELD --
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading, // Disable input during loading
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // -- PASSWORD FIELD --
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // -- CONFIRM PASSWORD (only for signup) --
            if (!isLoginMode) {
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(24.dp))
            } else {
                Spacer(modifier = Modifier.height(24.dp))
            }

            // -- ERROR MESSAGE --
            // Only show if the state is Error
            if (uiState is AuthUiState.Error) {
                val errorMsg = (uiState as AuthUiState.Error).message ?: "Unknown Error"
                Text(
                    text = errorMsg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            } else if (uiState is AuthUiState.Success) {
                Text(
                    text = if (isLoginMode) "Login successful" else "Signup successful",
                    color = Color.Green,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Password mismatch message for signup
            if (!isLoginMode && confirmPassword.isNotBlank() && password != confirmPassword) {
                Text(
                    text = "Passwords do not match",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            // -- PRIMARY BUTTON --
            val enabledForAction = !isLoading && email.isNotBlank() && password.isNotBlank() && (isLoginMode || (name.isNotBlank() && confirmPassword.isNotBlank() && password == confirmPassword))

            Button(
                onClick = {
                    if (isLoginMode) {
                        viewModel.login(email, password)
                    } else {
                        // call register on the viewModel
                        viewModel.register(name, email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = enabledForAction
            ) {
                if (isLoading) {
                    // Show spinner inside button when loading
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(if (isLoginMode) "Login" else "Sign up")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // -- TOGGLE TEXT BELOW BUTTON --
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (isLoginMode) "Don't have an account? " else "Already have an account? ",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = if (isLoginMode) "Sign up" else "Log in",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        // simple if/else route toggle — flip the mode
                        isLoginMode = !isLoginMode

                        // reset fields when switching to avoid stale inputs
                        name = ""
                        email = ""
                        password = ""
                        confirmPassword = ""

                        // example: print the selected route (optional)
                        if (isLoginMode) println("Switched to Login") else println("Switched to Signup")
                    }
                )
            }
        }
    }
}