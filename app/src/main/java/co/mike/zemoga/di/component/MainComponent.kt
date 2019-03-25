package co.mike.zemoga.di.component

import co.mike.zemoga.base.InjectableApplication
import co.mike.zemoga.di.BaseComponent
import co.mike.zemoga.di.module.ActivityBuilder
import co.mike.zemoga.di.module.AppModule
import co.mike.zemoga.di.module.FactoryModule
import co.mike.zemoga.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ActivityBuilder::class, AppModule::class, NetworkModule::class,  FactoryModule::class, AndroidSupportInjectionModule::class])
interface MainComponent : BaseComponent {

    @Component.Builder
    interface Builder {
        fun build(): BaseComponent

        @BindsInstance
        fun application(application: InjectableApplication): Builder
    }
}
