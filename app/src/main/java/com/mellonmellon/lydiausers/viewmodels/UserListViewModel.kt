package com.mellonmellon.lydiausers.viewmodels

import androidx.lifecycle.*
import com.mellonmellon.lydiausers.LydiaUserApp
import com.mellonmellon.lydiausers.data.entities.User
import com.mellonmellon.lydiausers.helpers.NetworkHelper
import com.mellonmellon.lydiausers.repositories.UsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserListViewModel constructor(private val savedStateHandle: SavedStateHandle = SavedStateHandle()): ViewModel() {
    private val repository = UsersRepository()

    val page: MutableStateFlow<Int> = MutableStateFlow(
        savedStateHandle.get(USER_LIST_SAVED_STATE_KEY) ?: NO_PAGE
    )

    @ExperimentalCoroutinesApi
    val users: LiveData<List<User>>? = page.flatMapLatest { p ->
        repository.getUserByPage(p)
    }.asLiveData()


    var isInternetAvailable: Boolean = NetworkHelper.isNetworkAvailable(LydiaUserApp.context)

    init {
        viewModelScope.launch {
            page.collect { newPage ->
                savedStateHandle.set(USER_LIST_SAVED_STATE_KEY, newPage)
            }
        }
    }

    companion object {
        private const val NO_PAGE = 1
        private const val USER_LIST_SAVED_STATE_KEY = "USER_LIST_SAVED_STATE_KEY"
    }
}