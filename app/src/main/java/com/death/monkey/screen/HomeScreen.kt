package com.death.monkey.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel){
    val viewState = viewModel.viewState.value

    Box(modifier = Modifier.fillMaxSize()) {
        if(viewState.isLoading){
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator()
            }
        }
    }
}