package co.mike.zemoga.base

import androidx.databinding.BaseObservable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import co.mike.zemoga.extencion.disposeWithoutFear
import io.reactivex.disposables.CompositeDisposable

abstract class ViewModel : BaseObservable(), LifecycleObserver {

    protected open val disposable = CompositeDisposable()


    protected open fun subscribeToObservables() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
        subscribeToObservables()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        disposable.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        disposable.disposeWithoutFear()
    }

}