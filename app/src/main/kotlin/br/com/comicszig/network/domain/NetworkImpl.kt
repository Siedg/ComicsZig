package br.com.comicszig.network.domain

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://gateway.marvel.com/v1/public/"

class NetworkImpl : Network {
    override fun <T> provideApi(
        clazz: Class<T>,
    ): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(clazz)
    }
}
