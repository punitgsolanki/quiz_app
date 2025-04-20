// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("hiltVersion", "2.48")
        set("glide", "4.12.0")
        set("kotlinVersion", "1.9.0")
        set("retrofitVersion", "2.9.0")
        set("roomVersion", "2.6.0")
    }
}
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.android.library") version "8.1.4" apply false
}