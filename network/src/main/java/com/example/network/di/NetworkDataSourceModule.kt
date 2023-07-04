package com.example.network.di

import com.example.network.NetworkDataSource
import com.example.network.retrofit.Network
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
interface NetworkDataSourceModule {

    @Binds
    fun bindNetworkDataSource(
        dataSource: Network
    ): NetworkDataSource
}