package com.example.quizdemo.utils.extensions

/**Null value check*/
fun String?.nullSafe(defaultValue: String = ""): String {
    return this ?: defaultValue
}

/**Get class name*/
fun Any.getClassName(): String {
    return this::class.java.simpleName
}

fun Boolean?.nullSafe(defaultValue: Boolean = false): Boolean {
    return this ?: defaultValue
}

fun <T> List<T>?.nullSafe(defaultValue: List<T> = ArrayList()): List<T> {
    return this ?: defaultValue
}