package com.mellonmellon.lydiausers.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @Embedded val name: Name,
    @Embedded val location: Location,
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "registered") val registered: Long,
    @ColumnInfo(name = "dob") val dob: Long,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "cell") val cell: String,
    @ColumnInfo(name = "nat") val nat: String,
    @Embedded val picture: Picture,
    @ColumnInfo(name = "page_number") var pageNumber: Int
)