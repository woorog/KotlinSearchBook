package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class BestSellerDto (
    @SerializedName(value = "title") val title: String,
    @SerializedName(value = "item") val books: List<Book>,

)