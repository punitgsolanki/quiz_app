package com.example.quizdemo.data.network.infrastructure

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime

object Serializer {
    @JvmStatic
    val gsonBuilder: GsonBuilder = GsonBuilder()
        .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeAdapter())
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .registerTypeAdapter(ByteArray::class.java, ByteArrayAdapter())

    @JvmStatic
    val gson: Gson by lazy {
        gsonBuilder.create()
    }
}
