package com.arslantestig.ktorexample.network

import com.arslantestig.ktorexample.model.RequestModel
import com.arslantestig.ktorexample.model.ResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

interface ApiService {
    suspend fun getProducts():List<ResponseModel>

    suspend fun createProducts(productRequest: RequestModel): ResponseModel?

    companion object{

        fun create(): ApiService=
            ApiServiceImpl(client = HttpClient(Android){

                //Logging
                install(Logging){
                    level=LogLevel.ALL
                }

                //Json
                install(JsonFeature){
                    serializer=KotlinxSerializer(Json {
                        ignoreUnknownKeys=true
                        isLenient=true
                        encodeDefaults=false
                    })
                }

                //Timeout
                install(HttpTimeout){
                    requestTimeoutMillis=15000L
                    connectTimeoutMillis=15000L
                    socketTimeoutMillis=15000L
                }

                //Apply to all requests
                defaultRequest {

                    if (method!= HttpMethod.Get) contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                }
            })
    }

}