package kr.ksw.visitkorea.presentation.favorite.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.usecase.favorite.GetAllFavoriteEntityUseCase
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoriteEntityUseCase: GetAllFavoriteEntityUseCase,
): ViewModel() {
    private val _favoriteState = MutableStateFlow(FavoriteState())
    val favoriteState: StateFlow<FavoriteState>
        get() = _favoriteState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllFavoriteEntityUseCase().collect { entities ->
                _favoriteState.update {
                    it.copy(
                        favoriteList = entities
                    )
                }
            }
        }
    }
}