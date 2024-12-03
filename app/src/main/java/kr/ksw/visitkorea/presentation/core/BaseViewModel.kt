package kr.ksw.visitkorea.presentation.core

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.presentation.common.DEFAULT_LATITUDE
import kr.ksw.visitkorea.presentation.common.DEFAULT_LONGITUDE
import kr.ksw.visitkorea.presentation.common.latitudeToStringOrDefault
import kr.ksw.visitkorea.presentation.common.longitudeToStringOrDefault

open class BaseViewModel<EFFECT> (
    private val fusedLocationProviderClient: FusedLocationProviderClient? = null
) : ViewModel() {
    private val _uiEffect = MutableSharedFlow<EFFECT>(replay = 0)
    val uiEffect: SharedFlow<EFFECT>
        get() = _uiEffect.asSharedFlow()

    protected fun postUIEffect(effect: EFFECT) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }

    @SuppressLint("MissingPermission")
    protected fun getWithLocation(block: (lat: String, lng: String) -> Unit) {
        fusedLocationProviderClient?.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        )?.addOnCompleteListener { task ->
            val result = try {
                task.result.latitudeToStringOrDefault() to
                        task.result.longitudeToStringOrDefault()
            } catch (e: Exception) {
                DEFAULT_LATITUDE to DEFAULT_LONGITUDE
            }
            block(result.first, result.second)
        }
    }
}