package com.mellonmellon.lydiausers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mellonmellon.lydiausers.data.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE page_number = (:pageNumber)")
    fun loadUsersByPage(pageNumber: Int): Flow<List<User>>

    @Query("SELECT count (*) FROM user WHERE page_number = (:pageNumber)")
    fun hasUserByPage(pageNumber: Int): Int

    @Query("SELECT * FROM user WHERE uid = :userId")
    fun findById(userId: Int): Flow<User>

    @Insert
    fun insertAll(vararg user: User)

    @Delete
    fun delete(user: User)
}