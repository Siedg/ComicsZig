package br.com.comicszig.sample

import android.app.Application
import br.com.comicszig.home.di.HomeModule
import br.com.comicszig.network.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ComicsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@ComicsApplication)
            modules(
                HomeModule,
                NetworkModule
            )
        }
    }
}