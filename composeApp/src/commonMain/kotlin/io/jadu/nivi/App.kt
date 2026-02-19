package io.jadu.nivi

/**
 * Created by Rohit (Jadu)
 * Sun 1 FEB
*/

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.jadu.nivi.presentation.test.TestScreens
import io.jadu.nivi.presentation.theme.Nivi

@Composable
@Preview
fun App() {
    Nivi {
        Scaffold { innerPadding ->
            TestScreens()
//            AppNavRoute(
//                modifier = Modifier.padding(innerPadding)
//            )
        }
    }
}