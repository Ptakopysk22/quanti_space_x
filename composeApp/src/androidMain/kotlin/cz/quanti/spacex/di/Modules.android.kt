package cz.quanti.spacex.di


import cz.quanti.spacex.features.rockets.domain.sensors.AccelerometerSensor
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single<AccelerometerSensor> { AccelerometerSensor(context = androidContext()) }
}

