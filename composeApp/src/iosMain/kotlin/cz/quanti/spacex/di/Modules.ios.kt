package cz.quanti.spacex.di

import cz.quanti.spacex.features.rockets.domain.sensors.AccelerometerSensor
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single { AccelerometerSensor() }
    }