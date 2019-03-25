package co.mike.zemoga.base

import co.mike.zemoga.di.BaseComponent



abstract class BaseApplication : InjectableApplication() {

    private var INSTANCE_: BaseApplication? = null

    var component: BaseComponent? = null

    fun getInstance(): BaseApplication? {
        return INSTANCE_
    }



    override fun onCreate() {
        super.onCreate()
        INSTANCE_ = this
    }
}