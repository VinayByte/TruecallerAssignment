package com.vinay.truecallerassignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinay.truecallerassignment.network.BASE_URL
import com.vinay.truecallerassignment.network.FetchService
import com.vinay.truecallerassignment.network.RequestStatus
import com.vinay.truecallerassignment.network.ServiceProvider
import com.vinay.truecallerassignment.utils.Utils
import com.vinay.truecallerassignment.utils.WordsCounter
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


/**
 * Created by VINAY on 2019-06-18.
 * vinay6kr@gmail.com
 */
class MainViewModel : ViewModel(), CoroutineScope {
    val api: FetchService = ServiceProvider.instance.fetchService

    var lastUrlA: String = BASE_URL
    var lastUrlB: String = BASE_URL
    var lastUrlC: String = BASE_URL

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    val resultOne: MutableLiveData<RequestStatus<Char?>> = MutableLiveData()
    val resultTwo: MutableLiveData<RequestStatus<List<Char>>> = MutableLiveData()
    val resultThree: MutableLiveData<RequestStatus<Map<String, Int>>> = MutableLiveData()

    fun runRequests(url: String = BASE_URL) {
        getTruecaller10thCharacter(url)
        getTruecallerEvery10thCharacter(url)
        getTruecallerWordCounter(url)
    }

    private fun getTruecaller10thCharacter(url: String) {
        resultOne.postValue(RequestStatus.Requesting)
        lastUrlA = url
        launch {
            try {
                val response = api.grabTruecaller10thCharRequest(url).await()
                val result = Utils.getTruecaller10thCharacter(response.byteStream())
                resultOne.postValue(RequestStatus.Success(result))
            } catch (e: Exception) {
                resultOne.postValue(RequestStatus.Failure(e))
            }
        }
    }

    private fun getTruecallerEvery10thCharacter(url: String) {
        resultTwo.postValue(RequestStatus.Requesting)
        lastUrlB = url
        launch {
            try {
                val response = api.grabTruecallerEvery10thCharRequest(url).await()
                val result = Utils.getEveryTruecaller10thCharacter(response.byteStream())
                resultTwo.postValue(RequestStatus.Success(result))
            } catch (e: Exception) {
                resultTwo.postValue(RequestStatus.Failure(e))
            }
        }
    }

    private fun getTruecallerWordCounter(url: String) {
        resultThree.postValue(RequestStatus.Requesting)
        lastUrlC = url
        launch {
            try {
                val response = api.grabTruecallerWordCounterRequest(url).await()
                val result = WordsCounter.getWordsOccurrenceCount(response.byteStream())
                resultThree.postValue(RequestStatus.Success(result))
            } catch (e: Exception) {
                resultThree.postValue(RequestStatus.Failure(e))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}