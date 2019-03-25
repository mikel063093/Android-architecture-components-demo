package co.mike.zemoga.di.module

import androidx.lifecycle.ViewModelProvider
import co.mike.zemoga.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class FactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}