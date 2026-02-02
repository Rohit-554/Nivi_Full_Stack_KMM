package io.jadu.nivi.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp


object Spacing {
    val s1 = 1.dp
    val s2 = 2.dp
    val s3 = 3.dp
    val s4 = 4.dp
    val s5 = 5.dp
    val s6 = 6.dp
    val s7 = 7.dp
    val s8 = 8.dp
    val s9 = 9.dp
    val s10 = 10.dp
    val s11 = 11.dp
    val s12 = 12.dp
    val s13 = 13.dp
    val s14 = 14.dp
    val s15 = 15.dp
    val s16 = 16.dp
    val s17 = 17.dp
    val s18 = 18.dp
    val s19 = 19.dp
    val s20 = 20.dp
    val s21 = 21.dp
    val s22 = 22.dp
    val s23 = 23.dp
    val s24 = 24.dp
    val s25 = 25.dp
    val s26 = 26.dp
    val s27 = 27.dp
    val s28 = 28.dp
    val s29 = 29.dp
    val s30 = 30.dp
    val s31 = 31.dp
    val s32 = 32.dp
    val s33 = 33.dp
    val s34 = 34.dp
    val s35 = 35.dp
    val s36 = 36.dp
    val s37 = 37.dp
    val s38 = 38.dp
    val s39 = 39.dp
    val s40 = 40.dp
    val s41 = 41.dp
    val s42 = 42.dp
    val s43 = 43.dp
    val s44 = 44.dp
    val s45 = 45.dp
    val s46 = 46.dp
    val s47 = 47.dp
    val s48 = 48.dp
    val s49 = 49.dp
    val s50 = 50.dp
    val s51 = 51.dp
    val s52 = 52.dp
    val s53 = 53.dp
    val s54 = 54.dp
    val s55 = 55.dp
    val s56 = 56.dp
    val s57 = 57.dp
    val s58 = 58.dp
    val s59 = 59.dp
    val s60 = 60.dp
    val s61 = 61.dp
    val s62 = 62.dp
    val s63 = 63.dp
    val s64 = 64.dp
}

val LocalSpacingProvider = compositionLocalOf { Spacing }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacingProvider.current