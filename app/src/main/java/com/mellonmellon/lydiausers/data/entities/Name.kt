package com.mellonmellon.lydiausers.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Name (
    @ColumnInfo(name = "first") val first: String?,
    @ColumnInfo(name = "last") val last: String?,
    @ColumnInfo(name = "title") val title: String?
)