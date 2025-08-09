package com.dmendano.megaverse.di

import com.dmendano.megaverse.ui.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
}
