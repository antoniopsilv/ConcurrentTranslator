package br.edu.ifsp.scl.sdm.currencyconverter.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.sdm.currencyconverter.model.api.TranslateApiClient
import br.edu.ifsp.scl.sdm.currencyconverter.model.livedata.TranslateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class TranslateViewModel: ViewModel() {
    fun getLanguages() =
        viewModelScope.launch(Dispatchers.Default) {
        TranslateApiClient.service.getLanguages().execute().also { response ->
            Log.v("\n\n\n ####  $response.code() #####"," $response.code() ")
            if (response.code() == HttpURLConnection.HTTP_OK) {
                Log.v("\n\n\n ####  Retornou ok no GETLANGUAGES #####"," Retornou ok no GETLANGUAGES ")
                response.body()?.also { languageList ->
                    TranslateLiveData.languageListLiveData.postValue(languageList)
                }
            }
            Log.v("\n\n\n ####  NÃO Retornou ok no GETLANGUAGES #####"," Retornou ok no GETLANGUAGES ")
        }
    }

    fun translate(fromLanguage: String, toLanguage: String, text: String) =
        viewModelScope.launch(Dispatchers.Default) {
            TranslateApiClient.service.translate(fromLanguage, toLanguage, text).execute().also { response ->
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Log.v("\n\n\n ####  Retornou ok no TRANSLATE #####"," Retornou ok no TRANSLATE ")
                    response.body()?.let { translationResult ->
                        TranslateLiveData.translateResultLiveData.postValue(translationResult)
                    }
                }
                Log.v("\n\n\n ####  NÃO Retornou ok no TRANSLATE #####"," Retornou ok no TRANSLATE ")
            }
    }

}