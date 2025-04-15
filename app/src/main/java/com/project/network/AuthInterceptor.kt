package com.project.network

import com.project.data.Constants.AUTHORIZATION
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    // For demonstration only: not a good practice to store the token in the client side
    // or in clear text.
    // Also github rejects any tokens present in the commit
    companion object {
        const val TOKEN =
            "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"
        const val BEARER = "Bearer $TOKEN"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authenticationRequest = originalRequest.newBuilder()
            .header(
                AUTHORIZATION,
                BEARER
            )
            .build()
        return chain.proceed(authenticationRequest)
    }

}