package br.edu.ifsp.scl.sdm.currencyconverter.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.sdm.currencyconverter.model.api.TranslateApiClient
import br.edu.ifsp.scl.sdm.currencyconverter.model.livedata.TranslateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class TranslateViewModel: ViewModel() {
    fun getLanguages() =
        viewModelScope.launch(Dispatchers.IO) {
        TranslateApiClient.service.getLanguages().execute().also { response ->
            if (response.code() == HttpURLConnection.HTTP_OK) {
                response.body()?.also { languageList ->
                    TranslateLiveData.languageListLiveData.postValue(languageList)
                }
            }
        }
    }

    fun translate(fromLanguage: String, toLanguage: String, text: String) =
        viewModelScope.launch(Dispatchers.IO) {
            TranslateApiClient.service.translate(fromLanguage, toLanguage, text).execute().also { response ->
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    response.body()?.let { translationResult ->
                        TranslateLiveData.translateResultLiveData.postValue(translationResult)
                    }
                }
            }
    }

}