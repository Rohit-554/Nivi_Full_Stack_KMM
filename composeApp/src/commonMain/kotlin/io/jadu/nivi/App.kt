package io.jadu.nivi

/**
 * Created by Rohit (Jadu)
 * Jan 6 2026
*/

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.jadu.nivi.presentation.screens.LoginScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        LoginScreen()
    }
}