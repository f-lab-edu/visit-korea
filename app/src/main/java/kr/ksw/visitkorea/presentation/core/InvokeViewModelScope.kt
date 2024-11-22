package kr.ksw.visitkorea.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

fun ViewModel.viewModelLauncher(
    block: suspend () -> Unit
) {
    viewModelScope.launch {
        block()
    }
}