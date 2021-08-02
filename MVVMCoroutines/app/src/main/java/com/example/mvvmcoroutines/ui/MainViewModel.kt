package com.example.mvvmcoroutines.ui

import androidx.lifecycle.*
import com.example.mvvmcoroutines.models.Post
import com.example.mvvmcoroutines.network.repos.MainRepository
import com.example.mvvmcoroutines.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object {
        private const val EntriesPerPage = 10
    }

    private var page: Int = 0

    private var lastFetchedId = 0

    private val _dataState: MutableLiveData<DataState<List<Post>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Post>>> get () = _dataState

    // Function responsible for triggering updates
    @ExperimentalCoroutinesApi
    fun setStateEvent(stateEvent: StateEvent) {
        when (stateEvent) {
            is StateEvent.GetPostsPaginatedEvents -> launchCoroutine { mainRepository.getPostsByPage(++page, EntriesPerPage) }
            is StateEvent.GetCachedPostsEvents -> launchCoroutine { mainRepository.fetchCachedPosts() }
            is StateEvent.GetPostsSlicedEvents -> launchCoroutine { mainRepository.getPostsByRange(lastFetchedId, lastFetchedId + EntriesPerPage) }
            is StateEvent.None -> {
                // Do Nothing
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun launchCoroutine(func: suspend () -> Flow<DataState<List<Post>>>) {
        viewModelScope.launch {
            func().onEach { dataState ->
                when (dataState) {
                    is DataState.Success -> lastFetchedId = dataState.data.getLastFetchedId()
                    else -> {
                        // Do Nothing
                    }
                }
                _dataState.value = dataState
            }.launchIn(viewModelScope)
        }
    }

    sealed class StateEvent {

        object GetPostsPaginatedEvents: StateEvent()

        object GetPostsSlicedEvents: StateEvent()

        object GetCachedPostsEvents: StateEvent()

        object None: StateEvent()

    }
}

private fun List<Post>.getLastFetchedId() = this[lastIndex].id



