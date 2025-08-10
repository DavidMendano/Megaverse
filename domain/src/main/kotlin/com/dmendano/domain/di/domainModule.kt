package com.dmendano.domain.di

import com.dmendano.domain.usecase.CreateComethUseCase
import com.dmendano.domain.usecase.CreatePolyanetUseCase
import com.dmendano.domain.usecase.CreateSoloonUseCase
import com.dmendano.domain.usecase.DeleteMegaverseObjectUseCase
import com.dmendano.domain.usecase.GetGoalUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetGoalUseCase)
    factoryOf(::CreatePolyanetUseCase)
    factoryOf(::CreateComethUseCase)
    factoryOf(::CreateSoloonUseCase)
    factoryOf(::DeleteMegaverseObjectUseCase)
}
