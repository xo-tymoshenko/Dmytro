package dev.tymoshenko.dmytro.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun absDp(value: Dp): Dp {
    return if (value <= 0.dp) -value else value
}