package com.death.monkey.util

import androidx.compose.runtime.Composable

@Composable
fun Draw(content: (@Composable () -> Unit)){
    content()
}