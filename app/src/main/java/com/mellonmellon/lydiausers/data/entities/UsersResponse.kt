package com.mellonmellon.lydiausers.data.entities

import com.google.gson.annotations.SerializedName


data class UsersResponse(
    @field:SerializedName("results") val results: List<User>
)