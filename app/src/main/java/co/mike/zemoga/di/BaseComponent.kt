package co.mike.zemoga.di

import co.mike.zemoga.base.InjectableApplication
import co.mike.zemoga.di.qualifier.BasePath
import com.google.gson.Gson
import dagger.android.AndroidInjector
import retrofit2.Retrofit

interface
BaseComponent : AndroidInjector<InjectableApplication> {

    fun gson(): Gson

    @BasePath
    fun retrofitBasePath(): Retrofit


}