package com.mellonmellon.lydiausers.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Location (
    @ColumnInfo(name = "street") val street: String?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "state") val state: String?,
    @ColumnInfo(name = "postcode") val postcode: String?
)