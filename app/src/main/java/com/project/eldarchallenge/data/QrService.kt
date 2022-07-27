package com.project.eldarchallenge.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface  QrService {

    @POST("/qr-code?user-id=mockUser12&api-key=9pmaz9yHPA7ME1XcBgMs8r8v8YW3t8wUCHGyMZ4ejs5C9WNX")
    fun qrResponse(@Query("content") content: String): Call<ResponseBody>
}