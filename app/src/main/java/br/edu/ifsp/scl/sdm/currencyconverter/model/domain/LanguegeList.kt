package br.edu.ifsp.scl.sdm.currencyconverter.model.domain

class LanguegeList : ArrayList<LanguegeList.LanguegeListItem>(){
    data class LanguegeListItem(
        val code: String,
        val language: String
    )
}