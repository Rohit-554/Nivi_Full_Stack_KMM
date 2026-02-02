package io.jadu.nivi.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import nivi.composeapp.generated.resources.Res
import nivi.composeapp.generated.resources.google_sans_bold
import nivi.composeapp.generated.resources.google_sans_medium
import nivi.composeapp.generated.resources.google_sans_normal
import nivi.composeapp.generated.resources.google_sans_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun manropeFamily()  = FontFamily(
    Font(Res.font.google_sans_normal, FontWeight.Normal),
    Font(Res.font.google_sans_medium, FontWeight.Medium),
    Font(Res.font.google_sans_semibold, FontWeight.SemiBold),
    Font(Res.font.google_sans_bold, FontWeight.Bold),
    Font(Res.font.google_sans_bold, FontWeight.Black)
)

/**
 * Font size 32.sp
 */
@Composable
fun h1TextStyle() = TextStyle(
    fontFamily = manropeFamily(),
    fontWeight = FontWeight.Bold,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 32.sp,
)

/**
 * Font size 28.sp
 */

@Composable
fun h2TextStyle() = TextStyle(
    fontFamily = manropeFamily(),
    fontWeight = FontWeight.Bold,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 28.sp,
)

/**
 * Font size 24.sp
 */
@Composable
fun h3TextStyle() = TextStyle(
    fontFamily = manropeFamily(),
    fontWeight = FontWeight.SemiBold,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 24.sp,
)

/**
 * Font size 20.sp
 */

@Composable
fun bodyXXLarge() = TextStyle(
    fontFamily = manropeFamily(),
    fontWeight = FontWeight.Medium,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 20.sp,
)

/**
 * Font size 18.sp
 */
@Composable
fun bodyXLarge() = TextStyle(
    fontFamily = manropeFamily(),
    fontWeight = FontWeight.Medium,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 18.sp,
)

/**
 * Font size 16.sp
 */
@Composable
fun bodyLarge() = TextStyle(
    fontFamily = manropeFamily(),
    fontWeight = FontWeight.Medium,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 16.sp,
)

/**
 * Font size 14.sp
 */
@Composable
fun bodyNormal() = TextStyle(
    fontFamily = manropeFamily(),
    fontWeight = FontWeight.Medium,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 14.sp,
)

/**
 * Font size 12.sp
 */
@Composable
fun bodySmall() = TextStyle(
    fontFamily = manropeFamily(),
    fontWeight = FontWeight.Medium,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 12.sp,
)

/**
 * Font size 10.sp
 */
@Composable
fun bodyXSmall() = TextStyle(
    fontFamily = manropeFamily(),
    fontWeight = FontWeight.Normal,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 10.sp,
)
