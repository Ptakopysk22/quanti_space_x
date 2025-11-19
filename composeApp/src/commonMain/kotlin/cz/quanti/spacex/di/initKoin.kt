package cz.quanti.spacex.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("InitKoin", swiftName = "initKoin")
fun initKoin(config: KoinAppDeclaration? = null){
    startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule)
    }
}