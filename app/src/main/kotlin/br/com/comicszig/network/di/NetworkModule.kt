package br.com.comicszig.network.di

import br.com.comicszig.network.domain.NetworkImpl
import br.com.comicszig.network.domain.Network
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val NetworkModule = module {
    singleOf(::NetworkImpl) { bind<Network>() }
}