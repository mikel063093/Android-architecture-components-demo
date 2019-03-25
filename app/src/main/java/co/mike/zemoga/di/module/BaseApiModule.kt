package co.mike.zemoga.di.module


import co.mike.zemoga.di.qualifier.BasePath
import co.mike.zemoga.services.ZemogaService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object BaseApiModule {

    @Provides
    @Singleton
    @BasePath
    @JvmStatic
    fun informationApi(@BasePath retrofit: Retrofit): ZemogaService {
        return retrofit.create(ZemogaService::class.java)
    }


}