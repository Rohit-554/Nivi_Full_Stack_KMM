package io.jadu.nivi

/**
 * Created by Rohit (Jadu)
 * Jan 6 2026
*/

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import io.jadu.nivi.presentation.screens.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        LoginScreen()
    }
}