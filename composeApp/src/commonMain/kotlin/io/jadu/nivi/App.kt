package io.jadu.nivi

/**
 * Created by Rohit (Jadu)
 * Sun 1 FEB
*/

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.jadu.nivi.presentation.navigation.AppNavRoute
import io.jadu.nivi.presentation.navigation.AppRoute
import io.jadu.nivi.presentation.theme.Nivi

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun App(
    startRoute: AppRoute
) {
    Nivi {
        Scaffold { innerPadding ->
            AppNavRoute(
                startDestination = startRoute,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}