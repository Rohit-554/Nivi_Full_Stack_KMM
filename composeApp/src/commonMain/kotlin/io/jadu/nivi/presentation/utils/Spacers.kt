package io.jadu.nivi.presentation.utils

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jadu.nivi.presentation.theme.spacing

@Composable
fun HSpacer(width: Dp = MaterialTheme.spacing.s4) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun HSpacer(factor: Int) {
    Spacer(modifier = Modifier.width(MaterialTheme.spacing.s4 * factor))
}

@Composable
fun HSpacer(factor: Float) {
    Spacer(modifier =  Modifier.width(MaterialTheme.spacing.s4 * factor))
}

@Composable
fun RowScope.HSpacerAuto() {
    Spacer(modifier = Modifier.weight(1f))
}

/**
 * Vertical Spacer with default height of 16.dp
 */
@Composable
fun VSpacer(height: Dp = MaterialTheme.spacing.s4) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun VSpacer(factor: Float) {
    Spacer(modifier =  Modifier.height(MaterialTheme.spacing.s4 * factor))
}

@Composable
fun VSpacer(factor: Int) {
    Spacer(modifier =  Modifier.height(MaterialTheme.spacing.s4 * factor))
}

@Composable
fun ColumnScope.VSpacerAuto() {
    Spacer(modifier = Modifier.weight(1f))
}

@Composable
fun RowScope.VSpacerAuto() {
    Spacer(modifier = Modifier.weight(1f))
}


@Composable
fun HDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 0.5.dp,
        color = Color(0xffA9B0B8).copy(alpha = 0.7f)
    )
}

@Composable
fun VDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 0.5.dp,
    color: Color = Color(0xffA9B0B8).copy(alpha = 0.7f)
) {
    VerticalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}
