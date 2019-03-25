package co.mike.zemoga.di

import android.app.Activity
import dagger.android.AndroidInjection

fun Activity.inject() {
    AndroidInjection.inject(this)
}

