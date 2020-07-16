package com.towerowl.kaffio.di

import android.content.Context
import androidx.room.Room
import com.towerowl.kaffio.data.LocalDatabase
import com.towerowl.kaffio.models.AuthenticationViewModel
import com.towerowl.kaffio.repositories.AuthenticationRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [RepositoriesModule::class, ViewModelsModule::class, DatabaseModule::class])
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
class DatabaseModule {
    companion object {
        private const val DB_NAME = "KAFFIO_DB"
    }

    @Provides
    fun provideLocalDatabase(context: Context): LocalDatabase = Room.databaseBuilder(
        context,
        LocalDatabase::class.java,
        DB_NAME
    ).build()
}

@Module(includes = [DatabaseModule::class])
class RepositoriesModule {
    @Provides
    fun provideAuthenticationRepository(localDatabase: LocalDatabase): AuthenticationRepository =
        AuthenticationRepository(localDatabase.userDao())
}

@Module(includes = [RepositoriesModule::class])
class ViewModelsModule {
    @Provides
    fun provideAuthenticationViewModel(authenticationRepository: AuthenticationRepository) =
        AuthenticationViewModel(authenticationRepository)
}