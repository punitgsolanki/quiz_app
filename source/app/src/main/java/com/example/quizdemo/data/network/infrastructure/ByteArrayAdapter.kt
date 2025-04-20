package com.example.quizdemo.data.network.infrastructure

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.google.gson.stream.JsonToken.NULL
import java.io.IOException

class ByteArrayAdapter : TypeAdapter<ByteArray>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter?, value: ByteArray?) {
        if (value == null) {
            out?.nullValue()
        } else {
            out?.value(String(value))
        }
    }

    @Throws(IOException::class)
    override fun read(out: JsonReader?): ByteArray? {
        out ?: return null

        return when (out.peek()) {
            NULL -> {
                out.nextNull()
                null
            }

            else -> {
                out.nextString().toByteArray()
            }
        }
    }
}
