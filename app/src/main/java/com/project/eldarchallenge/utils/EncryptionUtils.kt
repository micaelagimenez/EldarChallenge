package com.project.eldarchallenge.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

private const val SECRET_KEY = "aesEncryptionKey"
private const val INIT_VECTOR = "encryptionIntVec"

fun encrypt(value: String): String? {
    try {
        val iv = IvParameterSpec(INIT_VECTOR.toByteArray(charset("UTF-8")))
        val skeySpec = SecretKeySpec(SECRET_KEY.toByteArray(charset("UTF-8")), "AES")
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
        val encrypted: ByteArray = cipher.doFinal(value.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return null
}