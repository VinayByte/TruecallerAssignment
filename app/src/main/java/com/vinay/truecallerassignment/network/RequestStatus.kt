package com.vinay.truecallerassignment.network


/**
 * Created by VINAY on 2019-06-18.
 * vinay6kr@gmail.com
 */
sealed class RequestStatus <out T : Any?> {
    object Cancelled : RequestStatus<Nothing>()
    object Requesting : RequestStatus<Nothing>()
    data class Success<out T : Any?>(val data: T) : RequestStatus<T>()
    data class Failure(val error: Throwable) : RequestStatus<Nothing>()

}