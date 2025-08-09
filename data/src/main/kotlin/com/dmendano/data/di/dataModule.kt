package com.dmendano.data.di

import com.dmendano.data.apiservice.MegaverseService
import com.dmendano.data.repository.GoalRepositoryImpl
import com.dmendano.data.repository.SafeApiCallDelegate
import com.dmendano.data.repository.SafeApiCallDelegateImpl
import com.dmendano.domain.repository.GoalRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    single<MegaverseService> {
        Retrofit.Builder()
            .baseUrl("https://challenge.crossmint.io/api/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MegaverseService::class.java)
    }
    single<String>(named("candidateId")) {
        "1900ebbb-bf8e-4a03-93d6-653cf27ae221"
    }
    singleOf(::SafeApiCallDelegateImpl) bind SafeApiCallDelegate::class
    factory<GoalRepository> {
        GoalRepositoryImpl(
            get(),
            get(named("candidateId")),
            get()
        )
    }
}
