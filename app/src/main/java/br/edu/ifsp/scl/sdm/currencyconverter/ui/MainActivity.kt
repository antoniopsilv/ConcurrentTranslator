package br.edu.ifsp.scl.sdm.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import br.edu.ifsp.scl.sdm.currencyconverter.R
import br.edu.ifsp.scl.sdm.currencyconverter.databinding.ActivityMainBinding
import br.edu.ifsp.scl.sdm.currencyconverter.model.api.TranslateRequest
import br.edu.ifsp.scl.sdm.currencyconverter.model.domain.LanguegeList
import br.edu.ifsp.scl.sdm.currencyconverter.model.livedata.TranslateLiveData
import br.edu.ifsp.scl.sdm.currencyconverter.ui.viewModel.TranslateViewModel

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val lvm: TranslateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        setSupportActionBar(amb.mainTb.apply { title = getString(R.string.app_name) })

          lvm.getLanguages()
          var fromLanguage = ""
          var toLanguage = ""
          var languageAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf<String>())
          with(amb) {
              fromLanguageMactv.apply {
                  setAdapter(languageAdapter)
                  setOnItemClickListener { _, _, _, _ ->
                      fromLanguage = text.toString().substringAfter ("-")
                  }
              }
              toLanguageMactv.apply {
                  setAdapter(languageAdapter)
                  setOnItemClickListener { _, _, _, _ ->
                      toLanguage = text.toString().substringAfter ("-")
                  }
              }
              convertBt.setOnClickListener {
                  Log.i("\n\n\n Languagem From",  ">>>> ${fromLanguage}")
                  Log.i("\n\n\n Languagem To",  ">>>> ${toLanguage}")
                  Log.i("\n\n\n Text",  ">>>> ${textTiet.text.toString()}")
                  lvm?.translate(fromLanguage, toLanguage, textTiet.text.toString())
              }
          }

        TranslateLiveData.languageListLiveData.observe(this) { languageList ->
                languageAdapter.clear()
                languageAdapter.add(languageList.forEach { language ->
                    languageAdapter.add(language.language+'-'+language.code)
                }.toString())

                languageAdapter.getItem(0)?.also { language ->
                    amb.fromLanguageMactv.setText(language, false)
                    fromLanguage = language
                }

                languageAdapter.getItem(languageAdapter.count - 1)?.also { language ->
                    amb.toLanguageMactv.setText(language, false)
                    toLanguage = language
                }
        }


        TranslateLiveData.translateResultLiveData.observe(this) { translationResult ->
            with(amb) {
                translationResult.trans.also { result ->
                    Log.i("\n\n\n Texto retornado",  "******* >>>> $result")
                    resultTiet.setText(result)
                }
            }
        }

    }

}