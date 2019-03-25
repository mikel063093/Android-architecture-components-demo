package co.mike.zemoga

import co.mike.zemoga.base.InjectableApplication
import co.mike.zemoga.di.BaseComponent
import co.mike.zemoga.di.component.DaggerMainComponent


open class MainApplication : InjectableApplication() {

    lateinit var component: BaseComponent

    override fun initializeInjector() {
        component = DaggerMainComponent.builder()
                .application(this)
                .build()
                .apply { inject(this@MainApplication) }
    }

}