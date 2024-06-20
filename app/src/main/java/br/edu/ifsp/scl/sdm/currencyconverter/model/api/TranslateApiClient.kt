package br.edu.ifsp.scl.sdm.currencyconverter.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TranslateApiClient {

    private const val BASE_URL = "x-rapidapi-host: google-translator9.p.rapidapi.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: TranslateApiService = retrofit.create(TranslateApiService::class.java)
}