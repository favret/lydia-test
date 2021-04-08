package com.mellonmellon.lydiausers.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Picture (
    @ColumnInfo(name = "large") val large: String?,
    @ColumnInfo(name = "medium") val medium: String?,
    @ColumnInfo(name = "thumbnail") val thumbnail: String?
)