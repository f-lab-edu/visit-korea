package kr.ksw.visitkorea.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel<EFFECT> : ViewModel() {
    private val _uiEffect = MutableSharedFlow<EFFECT>(replay = 0)
    val uiEffect: SharedFlow<EFFECT>
        get() = _uiEffect.asSharedFlow()

    protected fun postUIEffect(effect: EFFECT) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}