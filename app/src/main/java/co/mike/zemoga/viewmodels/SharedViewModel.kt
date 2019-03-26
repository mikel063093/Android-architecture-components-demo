package co.mike.zemoga.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val clearItems = MutableLiveData<Boolean>()

    fun clearItems(clear: Boolean) {
        clearItems.value = clear
    }
    fun clearItems() = clearItems
}