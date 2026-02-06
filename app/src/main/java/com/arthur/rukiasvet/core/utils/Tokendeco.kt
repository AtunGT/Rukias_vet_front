package com.arthur.rukiasvet.core.utils

import android.util.Base64
import org.json.JSONObject

class Tokendeco {

    fun decodificarPayload(token: String): Map<String, String> {
        try {
            val parts = token.split(".")
            if (parts.size < 2) return emptyMap()

            val payload = parts[1]
            val decodedBytes = Base64.decode(payload, Base64.URL_SAFE)
            val decodedString = String(decodedBytes, Charsets.UTF_8)

            val jsonObject = JSONObject(decodedString)
            val map = mutableMapOf<String, String>()

            val iterator = jsonObject.keys()
            while (iterator.hasNext()) {
                val key = iterator.next()
                map[key] = jsonObject.optString(key)
            }
            return map
        } catch (e: Exception) {
            return emptyMap()
        }
    }
}