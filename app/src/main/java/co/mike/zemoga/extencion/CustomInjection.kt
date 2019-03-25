package co.mike.zemoga.extencion

import android.app.Activity
import dagger.android.AndroidInjection

fun Activity.inject() {
    AndroidInjection.inject(this)
}
