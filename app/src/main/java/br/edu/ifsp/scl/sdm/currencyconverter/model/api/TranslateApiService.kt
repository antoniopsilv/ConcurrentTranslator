package br.edu.ifsp.scl.sdm.currencyconverter.model.api

import br.edu.ifsp.scl.sdm.currencyconverter.model.domain.LanguageList
import br.edu.ifsp.scl.sdm.currencyconverter.model.domain.TranslateResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TranslateApiService {
    @Headers(
        "x-rapidapi-host: aibit-translator.p.rapidapi.com",
        "x-rapidapi-key: ceca8c7820mshc0919944ac028f2p1451edjsn36a0a403afb5"
    )
    @GET("support-languages")
    fun getLanguages(): Call<LanguageList>

    @Headers(
        "x-rapidapi-host: google-translator9.p.rapidapi.com",
        "x-rapidapi-key: ceca8c7820mshc0919944ac028f2p1451edjsn36a0a403afb5"
    )
    @POST
    fun convert(
        @Query("q") text: String,
        @Query("source") source: String,
        @Query("target") target: String,
        @Query("format") format: String,
    ): Call<TranslateResult>
}