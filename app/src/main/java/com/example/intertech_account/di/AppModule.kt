package com.example.intertech_account.di

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.intertech_account.view_model.GetAccountViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Job
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)

class AppModule {
    @Provides
    @ViewModelScoped
    fun providesErrorMessage() = MutableLiveData<String>("")

}