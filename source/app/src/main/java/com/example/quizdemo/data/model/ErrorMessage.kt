package com.example.quizdemo.data.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @param message
 */
data class ErrorMessage(
    @SerializedName("message")
    val message: String? = null
)

