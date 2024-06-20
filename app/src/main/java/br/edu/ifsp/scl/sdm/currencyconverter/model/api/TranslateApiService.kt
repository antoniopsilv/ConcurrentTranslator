package br.edu.ifsp.scl.sdm.currencyconverter.model.api

import br.edu.ifsp.scl.sdm.currencyconverter.model.domain.ConversionResult
import br.edu.ifsp.scl.sdm.currencyconverter.model.domain.CurrencyList
import br.edu.ifsp.scl.sdm.currencyconverter.model.domain.LanguageList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TranslateApiService {
    @Headers(
        "x-rapidapi-host: google-translator9.p.rapidapi.com",
        "x-rapidapi-key: ceca8c7820mshc0919944ac028f2p1451edjsn36a0a403afb5"
    )
    @GET("list")
    fun getLanguages(): Call<LanguageList>

    @Headers(
        "x-rapidapi-host: google-translator9.p.rapidapi.com",
        "x-rapidapi-key: ceca8c7820mshc0919944ac028f2p1451edjsn36a0a403afb5"
    )
    @POST("convert")
    fun convert(
        @Query("q") text: String,
        @Query("source") source: String,
        @Query("target") target: String,
        @Query("format") format: String,
    ): Call<ConversionResult>
}