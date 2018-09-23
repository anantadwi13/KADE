package com.anantadwi13.myapplication

import com.anantadwi13.myapplication.model.ResultApi
import com.anantadwi13.myapplication.model.Tenant
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("global")
    fun getTenant(): Observable<Tenant>

    @GET("global")
    fun getResultApi(): Observable<ResultApi>

    companion object {
        private var apiService:ApiService?=null

        fun create(): ApiService{

            if (apiService == null) {
                val builder = Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://devrouf.000webhostapp.com/API/")
                //builder.client(getUnsafeOkHttpClient())
                val retrofit = builder.build()
                return retrofit.create(ApiService::class.java)
            }
            return apiService ?: create()

        }
    }

}