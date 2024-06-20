package br.edu.ifsp.scl.sdm.currencyconverter.model.api

import br.edu.ifsp.scl.sdm.currencyconverter.model.domain.CurrencyList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface TranslateApiService {
    @Headers(
        "x-rapidapi-host: google-translator9.p.rapidapi.com",
        "x-rapidapi-key: ceca8c7820mshc0919944ac028f2p1451edjsn36a0a403afb5"
    )
    @GET("list")
    fun getLanguages(): Call<CurrencyList>
}