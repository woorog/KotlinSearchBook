package com.example.myapplication.api

import com.example.myapplication.model.BestSellerDto
import com.example.myapplication.model.SearchBookDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface BookService {
   @GET(value = "/api/search.api?output=json")
   fun getBooksByName(
      @Query(value = "key") apiKey: String,
      @Query(value = "query") Keyword: String
   ): Call<SearchBookDto> //클래스반환


   @GET(value = "/api/bestSeller.api?output=json&categoryId=100")
   fun getBestSellerBooks(
      @Query(value = "key") apiKey: String
   ): Call<BestSellerDto> //클래스 반환
}