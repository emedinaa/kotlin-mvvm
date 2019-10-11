package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository
import com.emedinaa.kotlinmvvm.viewmodel.MuseumViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule= module {
    single<MuseumDataSource>{MuseumRepository()}

    viewModel { MuseumViewModel(get()) }
}