package com.example.myapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//직렬화 - parcelize
@Parcelize
data class Book (
    @SerializedName(value = "itemId") val id: Long,
    @SerializedName(value = "title") val title: String,
    @SerializedName(value = "description") val description: String,
    @SerializedName(value = "coverSmallUrl") val coverSmallUrl: String,

    ):Parcelable