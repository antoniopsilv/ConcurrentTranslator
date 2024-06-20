package br.edu.ifsp.scl.sdm.currencyconverter.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.sdm.currencyconverter.model.api.CurrencyConverterApiClient
import br.edu.ifsp.scl.sdm.currencyconverter.model.livedata.CurrencyConverterLiveData
import br.edu.ifsp.scl.sdm.currencyconverter.service.ConvertService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class CurrencyConverterViewModel: ViewModel() {
    fun getCurricies() = viewModelScope.launch(Dispatchers.IO) {
        CurrencyConverterApiClient.service.getCurricies().execute().also { response ->
            if ( response.code() == HttpURLConnection.HTTP_OK) {
                response.body()?.also { currencyList ->
                    CurrencyConverterLiveData.currenciesLiveData.postValue(currencyList)
                }
            }
        }
    }

    fun convert(from: String, to: String, amount: String) = viewModelScope.launch(Dispatchers.IO) {
        CurrencyConverterApiClient.service.convert(from , to, amount).execute().also { response ->
            if (response.code() == HttpURLConnection.HTTP_OK) {
                response.body()?.let { conversionResult ->
                    CurrencyConverterLiveData.conversionResultLiveData.postValue(conversionResult)
                }
            }
        }
    }
}