package com.example.scanyourbill.data

import android.content.Context
import com.mklimek.sslutilsandroid.SslUtils
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class AuthInterceptor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { tokenProvider.getToken() }
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}

class ApiConfig {
    companion object {
        fun getApiService(context: Context, tokenProvider: TokenProvider): ApiService {
            val loggingInterceptor1 =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
            val loggingInterceptor2 =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val authInterceptor = AuthInterceptor(tokenProvider)

            // Interceptor to add Content-Type header conditionally
            val contentTypeInterceptor = Interceptor { chain ->
                val request = chain.request()
                val newRequest =
                    if (request.url.encodedPath.contains("stories") && request.method == "POST") {
                        request.newBuilder()
                            .addHeader("Content-Type", "multipart/form-data")
                            .build()
                    } else {
                        request
                    }
                chain.proceed(newRequest)
            }

            // Load C241PS281.crt from assets
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val certificate: X509Certificate
            context.assets.open("C241PS281.crt").use { certInputStream ->
                certificate = certificateFactory.generateCertificate(certInputStream) as X509Certificate
            }

            // Create a KeyStore containing our trusted CAs
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
                load(null, null)
                setCertificateEntry("C241PS281", certificate)
            }

            // Create a TrustManager that trusts the CAs in our KeyStore
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
                init(keyStore)
            }

            // Create an SSLContext that uses our TrustManager
            val sslContext = SSLContext.getInstance("TLS").apply {
                init(null, trustManagerFactory.trustManagers, null)
            }

            // Get the TrustManager from the TrustManagerFactory
            val trustManagers = trustManagerFactory.trustManagers
            val trustManager = trustManagers[0] as X509TrustManager

            // Custom HostnameVerifier that always returns true
            val hostnameVerifier = HostnameVerifier { _, _ -> true }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor1)
                .addInterceptor(loggingInterceptor2)
                .addInterceptor(authInterceptor)
                .addInterceptor(contentTypeInterceptor)
                .sslSocketFactory(sslContext.socketFactory, trustManager)
                .hostnameVerifier(hostnameVerifier)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://34.101.231.43/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
