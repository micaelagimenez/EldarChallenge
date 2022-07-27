package com.project.eldarchallenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.eldarchallenge.data.QrDataSource
import com.project.eldarchallenge.data.QrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.ResponseBody
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    private val qrRepository: QrRepository
) : ViewModel() {

    private val _qrResponse = MutableLiveData<ByteArray>()
    val qrResponse: LiveData<ByteArray> = _qrResponse
    private val _qrErrorResponse = MutableLiveData<String>()
    val qrErrorResponse: LiveData<String> get() = _qrErrorResponse

    fun getQrResponse(content: String) {
        val response = object : QrDataSource.NetworkResponse<ResponseBody> {
            override fun onResponse(value: InputStream) {
                _qrResponse.value = value.readBytes()
            }

            override fun onError(error: String) {
                _qrErrorResponse.value = error
            }
        }
        qrRepository.qrResponse(content, response)
    }
}