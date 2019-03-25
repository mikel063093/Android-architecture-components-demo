package co.mike.zemoga.di.module

import android.app.Application
import android.content.Context
import co.mike.zemoga.base.InjectableApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun providesContext(app: InjectableApplication): Context = app.applicationContext

        @Provides
        @Singleton
        @JvmStatic
        fun providesApplication(app: InjectableApplication): Application = app


    }
}