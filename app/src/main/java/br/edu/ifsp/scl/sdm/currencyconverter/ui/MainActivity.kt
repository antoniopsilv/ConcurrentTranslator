package br.edu.ifsp.scl.sdm.currencyconverter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import br.edu.ifsp.scl.sdm.currencyconverter.R
import br.edu.ifsp.scl.sdm.currencyconverter.databinding.ActivityMainBinding
import br.edu.ifsp.scl.sdm.currencyconverter.model.api.CurrencyConverterApiService
import br.edu.ifsp.scl.sdm.currencyconverter.model.livedata.CurrencyConverterLiveData
import br.edu.ifsp.scl.sdm.currencyconverter.model.livedata.TranslateLiveData
import br.edu.ifsp.scl.sdm.currencyconverter.service.CurrienciesService
import br.edu.ifsp.scl.sdm.currencyconverter.service.LanguageService
import br.edu.ifsp.scl.sdm.currencyconverter.ui.viewModel.CurrencyConverterViewModel
import br.edu.ifsp.scl.sdm.currencyconverter.ui.viewModel.TranslateViewModel

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val languageServiceServiceIntent by lazy {
        Intent(this, LanguageService::class.java)
    }

//    private val curringServiceIntent by lazy {
//        Intent(this, CurrienciesService::class.java)
//    }

//    private val ccvm: CurrencyConverterViewModel by viewModels()

    private val lvm: TranslateViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        setSupportActionBar(amb.mainTb.apply { title = getString(R.string.app_name) })

        var fromLanguage = ""
        var toLanguage = ""
          var languageAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf<String>())
          with(amb) {
              fromLanguageMactv.apply {
                  setAdapter(languageAdapter)
                  setOnItemClickListener { _, _, _, _ ->
                      fromLanguage = text.toString()
                  }
              }
              toLanguageMactv.apply {
                  setAdapter(languageAdapter)
                  setOnItemClickListener { _, _, _, _ ->
                      toLanguage = text.toString()
                  }
              }
              convertBt.setOnClickListener {
//                  ccvm.convert(fromQuote, toQuote, amountTiet.text.toString())
              }
          }

        TranslateLiveData.languageListLiveData.observe(this) { languageList ->
            languageAdapter.clear()
            languageAdapter.addAll(languageList.map { it.language })
            Log.i("\n\n\n Lista que está retornando",  "******* >>>> ${languageList.map { it.language } }")
                languageAdapter.getItem(0)?.also { language ->
                amb.fromLanguageMactv.setText(language, false)
                fromLanguage = language
            }

            languageAdapter.getItem(languageAdapter.count - 1)?.also { language ->
                amb.toLanguageMactv.setText(language, false)
                toLanguage = language
            }
        }
        startService(languageServiceServiceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(languageServiceServiceIntent)
    }

}