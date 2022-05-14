package com.death.monkey.screen

import android.view.View
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.death.monkey.domain.Apps
import com.death.monkey.repositories.ApplicationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val appRepository: ApplicationsRepository): ViewModel() {
    private val state = mutableStateOf(HomeScreenViewState())
    val viewState: State<HomeScreenViewState> = state

    private val internalActions = Channel<ViewAction>(Channel.BUFFERED)
    val actions = internalActions.receiveAsFlow()

    init {
        getLaunchAbleApps()
    }

    private  fun getLaunchAbleApps(){
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val apps = appRepository.getAllApps()
            state.value = state.value.copy(isLoading = false, appList = apps)
        }
    }

    fun processViewEvent(event: ViewEvent){
        when(event){
            ViewEvent.AddApp -> {
                if(state.value.isLoading){
                    internalActions.trySend(ViewAction.ShowSheetWithApps(emptyList()))
                }else{
                    internalActions.trySend(ViewAction.ShowSheetWithApps(state.value.appList, isLoading = false))
                }
            }
        }

    }

}

sealed class ViewEvent{
    object AddApp:ViewEvent()
}

sealed class ViewAction{
    data class ShowSheetWithApps(val apps: List<Apps>, val isLoading: Boolean = true): ViewAction()
}


data class HomeScreenViewState(
    val isLoading:Boolean = false,
    val appList: List<Apps> = emptyList()
)
