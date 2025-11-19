package cz.quanti.spacex

import androidx.compose.ui.window.ComposeUIViewController
import cz.quanti.spacex.app.App
import cz.quanti.spacex.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }