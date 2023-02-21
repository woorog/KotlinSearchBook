package com.example.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity
data class Review (
    @PrimaryKey val id: Int?,
    @ColumnInfo (name = "review") val review: String?
)