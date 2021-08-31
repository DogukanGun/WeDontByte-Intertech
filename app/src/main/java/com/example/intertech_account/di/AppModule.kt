package com.example.intertech_account.di

import androidx.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)

class AppModule {
    @Provides
    @ViewModelScoped
    fun providesErrorMessage() = MutableLiveData<String>("")

}