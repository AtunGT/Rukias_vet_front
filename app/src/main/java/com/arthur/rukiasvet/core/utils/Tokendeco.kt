package com.arthur.rukiasvet.core.utils

import android.util.Base64
import org.json.JSONObject
import javax.inject.Inject

class TokenDeco @Inject constructor() {

    fun decodePayload(token: String): Map<String, Any> {
        val parts = token.split(".")

        if (parts.size < 2) return emptyMap()

        val payload = parts[1]
        val decodedBytes = Base64.decode(payload, Base64.URL_SAFE)
        val json = String(decodedBytes)
        val jsonObject = JSONObject(json)

        val map = mutableMapOf<String, Any>()
        jsonObject.keys().forEach { map[it] = jsonObject.get(it) }

        return map
    }
}