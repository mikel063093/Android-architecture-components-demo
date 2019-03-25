package co.mike.zemoga.di

import dagger.android.HasActivityInjector

interface ComponentProvider : HasActivityInjector {
    fun clearComponent()
}
