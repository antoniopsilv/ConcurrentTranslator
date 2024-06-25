package br.edu.ifsp.scl.sdm.currencyconverter.model.api

import br.edu.ifsp.scl.sdm.currencyconverter.model.domain.LanguegeList
import br.edu.ifsp.scl.sdm.currencyconverter.model.domain.TranslateResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TranslateApiService {
    @GET("support-languages")

    @Headers(
        "x-rapidapi-host: aibit-translator.p.rapidapi.com",
        "x-rapidapi-key: ceca8c7820mshc0919944ac028f2p1451edjsn36a0a403afb5"
    )
    fun getLanguages(): Call<LanguegeList>

    @Headers(
        "x-rapidapi-host: aibit-translator.p.rapidapi.com",
        "x-rapidapi-key: ceca8c7820mshc0919944ac028f2p1451edjsn36a0a403afb5"
    )
    @POST("text")
    fun translate(
        @Query ("from") from: String,
        @Query ("to") to: String,
        @Body text: String
    ): Call<TranslateResult>
}