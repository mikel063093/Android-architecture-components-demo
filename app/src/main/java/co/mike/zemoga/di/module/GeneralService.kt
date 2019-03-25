package co.mike.zemoga.di.module

import co.mike.zemoga.di.qualifier.BasePath
import co.mike.zemoga.services.ZemogaService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
object GeneralService {

    @Provides
    @Reusable
    @JvmStatic
    fun api(@BasePath retrofit: Retrofit): ZemogaService {
        return retrofit.create(ZemogaService::class.java)
    }
}