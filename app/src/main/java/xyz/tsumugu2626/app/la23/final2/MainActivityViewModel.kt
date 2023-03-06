package xyz.tsumugu2626.app.la23.final2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


private const val ONE_DAY_MILLIS = 24 * 60 * 60 * 1000

class MainActivityViewModel : ViewModel() {

    private val _currentTimeMillis: MutableLiveData<Long> =
        MutableLiveData<Long>(System.currentTimeMillis())
    val currentTimeMillis: LiveData<Long> get() = _currentTimeMillis

    fun onNextButtonClicked() {
        _currentTimeMillis.value = _currentTimeMillis.value?.plus(ONE_DAY_MILLIS)
    }

    fun onPrevButtonClicked() {
        _currentTimeMillis.value = _currentTimeMillis.value?.minus(ONE_DAY_MILLIS)
    }

}