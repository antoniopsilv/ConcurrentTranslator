package br.edu.ifsp.scl.sdm.currencyconverter.model.domain

class LanguageList : ArrayList<LanguageList.LanguageListItem>(){
    data class LanguageListItem(
        val language: String,
        val name: String
    )
}