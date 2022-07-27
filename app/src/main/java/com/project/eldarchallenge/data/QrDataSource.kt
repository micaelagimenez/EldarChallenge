package com.project.eldarchallenge.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject


class QrDataSource @Inject constructor(private val service: QrService) {

    fun getQrData(content: String, networkResponse: NetworkResponse<ResponseBody>) {
        val call: Call<ResponseBody> = service.qrResponse(content)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful) {
                    response.body()?.byteStream()?.let { networkResponse.onResponse(it) }
                } else if (response.code() == 400) {
                    networkResponse.onError("Code 400")
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                val errorMessage = when (t) {
                    is IOException -> "No internet connection"
                    is HttpException -> "Something went wrong!"
                    else -> t.localizedMessage
                }
                networkResponse.onError(errorMessage)
            }
        })

    }

    interface NetworkResponse<T> {
        fun onResponse(value: InputStream)
        fun onError(value: String)
    }
}
