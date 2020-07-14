package com.towerowl.kaffio.di

import android.content.Context
import com.towerowl.kaffio.data.LocalDatabase
import com.towerowl.kaffio.models.AuthenticationViewModel
import com.towerowl.kaffio.repositories.AuthenticationRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [RepositoriesModule::class, ViewModelsModule::class])
interface AppComponent {
    fun authenticationRepository(): AuthenticationRepository

    @Singleton
    fun authenticationViewModel(): AuthenticationViewModel
}

@Module
class ContextModule(private val context: Context) {
    @Provides
    fun provideContext(): Context = context
}

@Module(includes = [ContextModule::class])
class RepositoriesModule(private val localDatabase: LocalDatabase) {
    @Provides
    fun provideAuthenticationRepository(): AuthenticationRepository =
        AuthenticationRepository(localDatabase.userDao())
}

@Module(includes = [RepositoriesModule::class])
class ViewModelsModule(private val authenticationRepository: AuthenticationRepository) {
    @Provides
    fun provideAuthenticationViewModel() = AuthenticationViewModel(authenticationRepository)
}