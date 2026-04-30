package com.example.ascentlister.di

import com.example.ascentlister.ascent.data.local.AscentDatabase
import com.example.ascentlister.ascent.data.network.KtorRemoteAscentDataSource
import com.example.ascentlister.ascent.data.network.RemoteAscentDataSource
import com.example.ascentlister.ascent.data.repository.DefaultAscentRepository
import com.example.ascentlister.ascent.domain.AscentRepository
import com.example.ascentlister.core.data.HttpClientFactory
import com.example.ascentlister.route.presentation.route_list.RouteListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

var sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteAscentDataSource).bind<RemoteAscentDataSource>()
    singleOf(::DefaultAscentRepository).bind<AscentRepository>()

    single { get<AscentDatabase>().ascentDao() }

    viewModelOf(::RouteListViewModel)
}
