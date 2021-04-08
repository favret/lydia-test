package com.mellonmellon.lydiausers.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mellonmellon.lydiausers.repositories.UsersRepository

class UserDetailViewModel(userId: Int): ViewModel() {
    private val repository = UsersRepository()
    var user = repository.getUserById(userId).asLiveData()
}