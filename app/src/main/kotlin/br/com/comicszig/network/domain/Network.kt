package br.com.comicszig.network.domain

interface Network {
    fun <T> provideApi(
        clazz: Class<T>
    ): T
}
