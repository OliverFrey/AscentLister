package com.example.ascentlister.di

import com.example.ascentlister.ascent.data.local.AscentDatabase
import com.example.ascentlister.ascent.data.network.KtorRemoteAscentDataSource
import com.example.ascentlister.ascent.data.network.RemoteAscentDataSource
import com.example.ascentlister.ascent.data.repository.DefaultAscentRepository
import com.example.ascentlister.ascent.domain.AscentRepository
import com.example.ascentlister.core.data.AuthRepository
import com.example.ascentlister.core.data.HttpClientFactory
import com.example.ascentlister.ascent.data.local.AscentDao
import com.example.ascentlister.core.domain.SessionStorage
import com.example.ascentlister.route.data.repository.DefaultRouteRepository
import com.example.ascentlister.route.domain.RouteRepository
import com.example.ascentlister.route.presentation.route_detailpage.RouteDetailViewModel
import com.example.ascentlister.route.presentation.route_list.RouteListViewModel
import com.example.ascentlister.ascent.presentation.add_ascent.AddAscentViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

var sharedModule = module {
    // AuthRepository needs a client without the Auth plugin to avoid infinite loops
    single(named("authClient")) { HttpClientFactory.createAuthClient(get()) }
    single { AuthRepository(get(named("authClient"))) }
    
    // Main HttpClient with Auth plugin
    single { HttpClientFactory.create(get(), get(), get()) }
    
    singleOf(::KtorRemoteAscentDataSource).bind<RemoteAscentDataSource>()
    singleOf(::DefaultAscentRepository).bind<AscentRepository>()
    singleOf(::DefaultRouteRepository).bind<RouteRepository>()

    single { get<AscentDatabase>().ascentDao() }

    viewModelOf(::RouteListViewModel)
    viewModelOf(::RouteDetailViewModel)
    viewModelOf(::AddAscentViewModel)
}
