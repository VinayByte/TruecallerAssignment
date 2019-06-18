package com.vinay.truecallerassignment.network

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url


/**
 * Created by VINAY on 2019-06-18.
 * vinay6kr@gmail.com
 */
const val BASE_URL = "https://truecaller.blog/2018/01/22/life-as-an-android-engineer/"

interface FetchService {
    @GET
    fun grabTruecaller10thCharRequest(@Url url: String = BASE_URL): Deferred<ResponseBody>

    @GET
    fun grabTruecallerEvery10thCharRequest(@Url url: String = BASE_URL): Deferred<ResponseBody>

    @GET
    fun grabTruecallerWordCounterRequest(@Url url: String = BASE_URL): Deferred<ResponseBody>

}