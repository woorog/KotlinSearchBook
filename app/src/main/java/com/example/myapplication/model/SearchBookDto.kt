package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

class SearchBookDto (
    @SerializedName(value = "title") val title: String,
    @SerializedName(value = "item") val books: List<Book>,
    )