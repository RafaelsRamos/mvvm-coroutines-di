package com.example.mvvmcoroutines.ui

import androidx.lifecycle.*
import com.example.mvvmcoroutines.models.Post
import com.example.mvvmcoroutines.network.repos.MainRepository
import com.example.mvvmcoroutines.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Post>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Post>>> get () = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {

            when (mainStateEvent) {

                is MainStateEvent.GetPostEvents -> {
                    mainRepository.getPost()
                        .onEach {
                            dataState -> _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.None -> {
                    // Do Nothing
                }

            }

        }
    }

}

sealed class MainStateEvent {

    object GetPostEvents: MainStateEvent()

    object None: MainStateEvent()

}