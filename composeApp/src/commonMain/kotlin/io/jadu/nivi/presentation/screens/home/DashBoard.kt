package io.jadu.nivi.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.jadu.nivi.presentation.theme.bodyXXLarge
import io.jadu.nivi.presentation.theme.h1TextStyle
import io.jadu.nivi.presentation.utils.VSpacer

@Composable
fun DashBoard() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "PROJECT NIVI",
                style = h1TextStyle()
            )
            VSpacer()
            Text(
                text = "Welcomes You!",
                style = bodyXXLarge()
            )
        }
    }
}