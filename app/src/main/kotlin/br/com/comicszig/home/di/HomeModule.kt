package br.com.comicszig.home.di

import br.com.comicszig.home.domain.HomeRepository
import br.com.comicszig.home.domain.HomeRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import br.com.comicszig.home.presentation.viewmodel.HomeViewModel
import br.com.comicszig.home.service.provideHomeApi
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf


val HomeModule = module {
    single { provideHomeApi(get()) }
    singleOf(::HomeRepositoryImpl) { bind<HomeRepository>() }
    viewModelOf(::HomeViewModel)
}
