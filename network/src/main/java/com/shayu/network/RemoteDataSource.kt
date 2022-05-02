package com.shayu.network

import android.content.Context
import okhttp3.*

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RemoteDataSource @Inject constructor() {

    companion object {
        private const val BASE_URL = "https://dev2.mymedisage.com/mediapi/"
        private lateinit var interceptor: HttpLoggingInterceptor
        private lateinit var okHttpClient: OkHttpClient
    }

    fun <Api> buildApi(
        api: Class<Api>,
        context: Context
    ): Api {
        interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(BasicAuthInterceptor("medimob", "boom_boom"))
            //.addInterceptor(BasicAuthInterceptorVimeo())
            .connectionSpecs(
                Arrays.asList(
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.COMPATIBLE_TLS,
                    ConnectionSpec.CLEARTEXT))
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .cache(null)
            .build()

        /*   val okkHttpclient = OkHttpClient.Builder()
               .addInterceptor(networkConnectionInterceptor)
               .build()*/

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            //.baseUrl("https://analytics-dev.mymedisage.com/api/v1/")
            //.baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
    class BasicAuthInterceptor(username: String, password: String): Interceptor {
        private var credentials: String = Credentials.basic(username, password)

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            request = request.newBuilder().header("Authorization", credentials).build()
            return chain.proceed(request)
        }
    }


    private fun getRetrofitClient(authenticator: Authenticator? = null): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Accept", "application/json")
                }.build())
            }.also { client ->
                authenticator?.let { client.authenticator(it) }
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build()
    }
}