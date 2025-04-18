package com.project.di.module

import com.project.BuildConfig
import com.project.network.AuthInterceptor
import com.project.network.GitApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    // Provide okhttp instance with Http logging
    single {
        OkHttpClient.Builder()
            // for debugging purpose only
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            // This code is intentionally commented. AuthInterceptor is only used for demo purpose
            // and for testing purpose.
            //.addInterceptor(get<AuthInterceptor>())
            .build()
    }

    // Provide moshi instance
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    // Provide retrofit instance
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
            .create(GitApiService::class.java)
    }

    // Provide AuthInterceptor instance
    single {
        AuthInterceptor()
    }


}