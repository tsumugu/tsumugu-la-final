package xyz.tsumugu2626.app.la23.final2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private const val ONE_DAY_MILLIS = 24 * 60 * 60 * 1000

class MainActivityViewModel : ViewModel() {

    private val _currentTimeMillis: MutableLiveData<Long> =
        MutableLiveData<Long>(System.currentTimeMillis())
    val currentTimeMillis: LiveData<Long> get() = _currentTimeMillis

    private val _fabUrl: MutableLiveData<String> =
        MutableLiveData<String>()
    val fabUrl: LiveData<String> get() = _fabUrl

    init {
        loadConfigFromFirebase()
    }

    fun onNextButtonClicked() {
        _currentTimeMillis.value = _currentTimeMillis.value?.plus(ONE_DAY_MILLIS)
    }

    fun onPrevButtonClicked() {
        _currentTimeMillis.value = _currentTimeMillis.value?.minus(ONE_DAY_MILLIS)
    }

    fun loadConfigFromFirebase() {
        val db = Firebase.firestore

        db.collection("config").document("faburl").addSnapshotListener { event, e ->
            if (e != null) {
                Log.e("error", "Listen failed.", e)
                return@addSnapshotListener
            }
            val faburl =  event?.get("url")
            if (faburl != null) {
                _fabUrl.value = faburl as String?
            }
        }
    }

}