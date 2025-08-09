package com.dmendano.data.di

import com.dmendano.data.apiservice.MegaverseService
import com.dmendano.data.repository.GoalRepositoryImpl
import com.dmendano.domain.repository.GoalRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
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
        ""
    }
    factory<GoalRepository> {
        GoalRepositoryImpl(
            get(named("candidateId")), get()
        )
    }
}
