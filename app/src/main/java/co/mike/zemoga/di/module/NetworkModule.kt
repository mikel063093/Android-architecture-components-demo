package co.mike.zemoga.di.module

import android.util.Log
import co.mike.zemoga.BuildConfig
import co.mike.zemoga.di.qualifier.BasePath
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
object NetworkModule {

    @Provides
    @Singleton
    @JvmStatic
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }


    @Provides
    @JvmStatic
    fun okhttpTimeout(): OkHttpClient.Builder {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.readTimeout(5, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS)
        return httpClientBuilder
    }


    @Provides
    @Singleton
    @BasePath
    @JvmStatic
    fun basePathClient(
            @BasePath httpClient: OkHttpClient, provideGson: Gson): Retrofit {
        return retrofitBuilder(httpClient, "https://jsonplaceholder.typicode.com/",
                provideGson).build()
    }


    @Provides
    @Singleton
    @BasePath
    @JvmStatic
    fun basePathOkhttp(httpClientBuilder: OkHttpClient.Builder): OkHttpClient {
        httpClientBuilder.addInterceptor(getLoggingInterceptor())
        return httpClientBuilder.build()
    }


    @JvmStatic
    fun retrofitBuilder(httpClient: OkHttpClient, path: String, provideGson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
                .client(httpClient)
                .baseUrl(path)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(provideGson))
    }

    @JvmStatic
    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> Log.e("SERVER", message) }
                .apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }
    }
}