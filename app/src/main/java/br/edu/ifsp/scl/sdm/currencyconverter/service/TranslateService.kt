package br.edu.ifsp.scl.sdm.currencyconverter.service

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import br.edu.ifsp.scl.sdm.currencyconverter.model.api.TranslateApiClient
import br.edu.ifsp.scl.sdm.currencyconverter.model.livedata.TranslateLiveData
import java.net.HttpURLConnection

class TranslateService: Service() {

    private val translateServiceBinder = TranslateServiceBinder()

    private lateinit var handler: TranslateServiceHandler

    companion object {
        const val FROM_PARAMETER = "from"
        const val TO_PARAMETER = "to"
        const val TEXT_PARAMETER = "text"
    }

    inner class TranslateServiceBinder : Binder() {
        fun getService(): TranslateService = this@TranslateService
    }

    private inner class TranslateServiceHandler(looper: Looper): Handler(looper) {
        override fun handleMessage(msg: Message) {
            with(msg.data) {
                TranslateApiClient.service.translate(
                    from = getString(FROM_PARAMETER, ""),
                    to = getString(TO_PARAMETER, ""),
                    text = getString(TEXT_PARAMETER, "")
                ).execute().also { response ->
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        response.body()?.let { translationResult ->
                            TranslateLiveData.translateResultLiveData.postValue(translationResult)
                        }
                    }
                }
            }
        }
    }

    fun translate( from: String, to: String, text: String) {
        Log.v(" >>>> Translate Function <<<< ", "**** Called *****")
        HandlerThread(this.javaClass.name).apply {
            start()
            handler = TranslateServiceHandler(looper)
        }
        handler.obtainMessage().apply {

            data.putString(TranslateService.FROM_PARAMETER, from)
            data.putString(TranslateService.TO_PARAMETER, to)
            data.putString(TranslateService.TEXT_PARAMETER, text)
            handler.sendMessage(this)
        }
    }
    override fun onBind(intent: Intent?): IBinder? {
        Log.v("TranslateService", "Service start")
        return translateServiceBinder
    }

    override fun unbindService(conn: ServiceConnection) {
        Log.v("TranslateService", "Service done")
        return super.unbindService(conn)
    }

}