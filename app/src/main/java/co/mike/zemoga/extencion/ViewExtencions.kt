package co.mike.zemoga.extencion

import android.view.View
import android.view.ViewGroup

fun View.removeFromParent() {
    this.parent?.let {
        (it as ViewGroup).removeView(this)
    }
}