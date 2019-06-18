package com.vinay.truecallerassignment.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit


/**
 * Created by VINAY on 2019-06-18.
 * vinay6kr@gmail.com
 */
class ServiceProvider {
    companion object {
    val instance by lazy {
        ServiceProvider()
    }
}

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    val fetchService: FetchService =
        retrofit.create(FetchService::class.java)

}