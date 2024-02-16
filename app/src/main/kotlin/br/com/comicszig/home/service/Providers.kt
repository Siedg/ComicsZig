package br.com.comicszig.home.service

import br.com.comicszig.home.api.HomeApi
import br.com.comicszig.network.domain.Network

fun provideHomeApi(network: Network) = network.provideApi(HomeApi::class.java)
