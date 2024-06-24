package br.edu.ifsp.scl.sdm.currencyconverter.model.livedata

import androidx.lifecycle.MutableLiveData
import br.edu.ifsp.scl.sdm.currencyconverter.model.domain.LanguegeList
import br.edu.ifsp.scl.sdm.currencyconverter.model.domain.TranslateResult

object TranslateLiveData {
    val languageListLiveData = MutableLiveData<LanguegeList>()
    val translateResultLiveData = MutableLiveData<TranslateResult>()
}