package io.jadu.nivi

/**
 * Created by Rohit (Jadu)
 * Sun 1 FEB
*/

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.jadu.nivi.presentation.navigation.AppNavRoute
import io.jadu.nivi.presentation.theme.Nivi

@Composable
@Preview
fun App() {
    Nivi {
        Scaffold { innerPadding ->
            AppNavRoute(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}