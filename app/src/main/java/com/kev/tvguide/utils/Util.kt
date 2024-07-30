package com.kev.tvguide.utils

import android.util.Log
import com.kev.tvguide.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber


class TokenManager {
    private var token: String? = null

    fun getToken(): String? = token

    fun setToken(token: String) {
        this.token = token
    }
}


class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response
    {
        val originalRequest
                = chain.request()
        val token = tokenManager.getToken()

        val requestBuilder = originalRequest.newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", BuildConfig.API_KEY)
        }

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}


class RequestHeadersLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val headers = request.headers

        // Log each header
        for (name in headers.names()) {
//            log.d()

            Log.d("RequestHeaders", "$name: ${headers[name]}")

        }

        return chain.proceed(request)
    }
}