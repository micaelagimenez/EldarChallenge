package com.project.eldarchallenge.data

import okhttp3.ResponseBody
import javax.inject.Inject

class QrRepository @Inject constructor(
    private val remote: QrDataSource
) {
    fun qrResponse(content: String, networkResponse: QrDataSource.NetworkResponse<ResponseBody>) =
        remote.getQrData(content, networkResponse)
}