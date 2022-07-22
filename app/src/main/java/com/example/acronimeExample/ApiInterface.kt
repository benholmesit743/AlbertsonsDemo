package com.example.acronimeExample

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("software/acromine/dictionary.py")
    suspend fun getShortForms(@Query("sf") shortForm: String): Response<List<AcronimesSF>>
}