package com.anantadwi13.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    //Jalan di background
    private val apiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_get_data?.onClick {
            getData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    private fun getData(){
        disposable = apiService.getResultApi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result-> Log.e("Success", result.toString())},
                        {error-> Log.e("Error", error.toString())}
                )
    }
}
