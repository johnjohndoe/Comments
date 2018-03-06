@file:Suppress("MayBeConstant", "unused")

package com.example

object Versions {
    val androidGradle = "3.0.1"
    val androidJob = "1.2.4"
    val assertjCore = "3.9.1"
    val constraintLayout = "1.0.2"
    val gradleVersions = "0.17.0"
    val junit = "4.12"
    val kotlin = "1.2.30"
    val kotlinxCoroutines = "0.22.5"
    val okhttp = "3.10.0"
    val moshi = "1.5.0"
    val retrofit = "2.3.0"
    val room = "1.0.0"
    val rxandroid = "1.2.1"
    val supportLibrary = "27.1.0"
}

object GradlePlugins {
    val android = "com.android.tools.build:gradle:${Versions.androidGradle}"
    val gradleVersions = "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersions}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Libs {
    val androidJob = "com.evernote:android-job:${Versions.androidJob}"
    val appcompat = "com.android.support:appcompat-v7:${Versions.supportLibrary}"
    val assertjCore = "org.assertj:assertj-core:${Versions.assertjCore}"
    val cardview = "com.android.support:cardview-v7:${Versions.supportLibrary}"
    val constraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"
    val junit = "junit:junit:${Versions.junit}"
    val kotlinStdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val kotlinxCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinxCoroutines}"
    val kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
    val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val recyclerview = "com.android.support:recyclerview-v7:${Versions.supportLibrary}"
    val roomCompiler = "android.arch.persistence.room:compiler:${Versions.room}"
    val roomRuntime = "android.arch.persistence.room:runtime:${Versions.room}"
    val rxandroid = "io.reactivex:rxandroid:${Versions.rxandroid}"
}
