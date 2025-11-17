package cz.quanti.spacex.di

import cz.quanti.spacex.core.data.HttpClientFactory
import cz.quanti.spacex.features.rockets.data.network.KtorRemoteRocketDataSource
import cz.quanti.spacex.features.rockets.data.network.RemoteRocketDataSource
import cz.quanti.spacex.features.rockets.data.repository.DefaultRocketRepository
import cz.quanti.spacex.features.rockets.domain.repository.RocketRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }

    singleOf(::KtorRemoteRocketDataSource).bind<RemoteRocketDataSource>()
    singleOf(::DefaultRocketRepository).bind<RocketRepository>()

}