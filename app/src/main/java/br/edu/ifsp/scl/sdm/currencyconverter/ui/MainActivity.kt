package br.edu.ifsp.scl.sdm.currencyconverter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import br.edu.ifsp.scl.sdm.currencyconverter.R
import br.edu.ifsp.scl.sdm.currencyconverter.databinding.ActivityMainBinding
import br.edu.ifsp.scl.sdm.currencyconverter.model.livedata.CurrencyConverterLiveData
import br.edu.ifsp.scl.sdm.currencyconverter.service.CurrienciesService

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val concurrencyServiceIntent by lazy {
        Intent(this, CurrienciesService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        setSupportActionBar(amb.mainTb.apply { title = getString(R.string.app_name) })

        var fromQuote = ""
        var toQuote = ""
        val currencyAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf<String>())
        with(amb) {
            fromQuoteMactv.apply {
                setAdapter(currencyAdapter)
                setOnItemClickListener { _, _, _, _ ->
                    fromQuote = text.toString()
                }
            }
            toQuoteMactv.apply {
                setAdapter(currencyAdapter)
                setOnItemClickListener { _, _, _, _ ->
                    toQuote = text.toString()
                }
            }
            convertBt.setOnClickListener{}
        }
        CurrencyConverterLiveData.currenciesLiveData.observe(this) {currencyList ->
            currencyAdapter.clear()
            currencyAdapter.addAll(currencyList.currencies.keys.sorted())
            currencyAdapter.getItem(0)?.also { quote ->
                amb.fromQuoteMactv.setText(quote,false)
                fromQuote = quote
            }

            currencyAdapter.getItem(currencyAdapter.count -1)?.also { quote ->
                amb.toQuoteMactv.setText(quote,false)
                toQuote = quote
            }
        }
        startService(concurrencyServiceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(concurrencyServiceIntent)
    }
}