package com.mellonmellon.lydiausers.repositories

import com.mellonmellon.lydiausers.LydiaUserApp
import com.mellonmellon.lydiausers.api.UserService
import com.mellonmellon.lydiausers.data.AppDatabase
import com.mellonmellon.lydiausers.data.dao.UserDao
import kotlinx.coroutines.flow.Flow
import com.mellonmellon.lydiausers.data.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class UsersRepository {
    private val service: UserService = UserService.create()
    private val userDao: UserDao = AppDatabase.getInstance(LydiaUserApp.context).userDao()

    fun getUserByPage(pageNumber: Int): Flow<List<User>>  {
        synchronized(refreshUser(pageNumber)) {
            return userDao.loadUsersByPage(pageNumber)
        }
    }

    fun getUserById(userId: Int): Flow<User>  {
        return userDao.findById(userId)
    }

    private fun refreshUser(pageNumber: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val userExists = userDao.hasUserByPage(pageNumber)
            if (userExists < 1) {
                fetchApi(pageNumber)
            }
        }
    }

    private suspend fun fetchApi(pageNumber: Int) {
        try {
            service.searchUsers(pageNumber).let { response ->
                for (user: User in response.results) {
                    user.pageNumber = pageNumber
                    userDao.insertAll(user)
                }
            }
        } catch (e: Exception) {
            print(e.message)
        }
    }
}