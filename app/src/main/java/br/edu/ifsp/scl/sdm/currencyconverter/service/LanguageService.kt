package br.edu.ifsp.scl.sdm.currencyconverter.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import br.edu.ifsp.scl.sdm.currencyconverter.model.api.CurrencyConverterApiClient
import br.edu.ifsp.scl.sdm.currencyconverter.model.api.TranslateApiClient
import br.edu.ifsp.scl.sdm.currencyconverter.model.api.TranslateApiService
import br.edu.ifsp.scl.sdm.currencyconverter.model.livedata.CurrencyConverterLiveData
import br.edu.ifsp.scl.sdm.currencyconverter.model.livedata.TranslateLiveData
import br.edu.ifsp.scl.sdm.currencyconverter.model.livedata.TranslateLiveData.languageListLiveData
import java.net.HttpURLConnection

class LanguageService: Service() {

    private lateinit var handler: LanguageServiceHandler
    private lateinit var serviceLogTag: String

    private inner class LanguageServiceHandler(looper: Looper): Handler(looper) {
        override fun handleMessage(msg: Message) {
           TranslateApiClient.service.getLanguages().execute().also { response ->
                if ( response.code() == HttpURLConnection.HTTP_OK) {
                    response.body()?.also { languageList ->
                        TranslateLiveData.languageListLiveData.postValue(languageList)
                    }
                }
            }
            stopSelf(msg.arg1)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        Log.v(serviceLogTag, "Service done")
    }
}