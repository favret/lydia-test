package com.mellonmellon.lydiausers.api

import com.mellonmellon.lydiausers.data.entities.UsersResponse
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface UserService {
    @GET("api/1.0/")
    suspend fun searchUsers(
        @Query("page") page: Int,
        @Query("results") perPage: Int = 10,
        @Query("seed") seed: String = "lydia"
    ): UsersResponse

    companion object {
        private const val BASE_URL = "https://randomuser.me/"

        fun create(): UserService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService::class.java)
        }
    }


}