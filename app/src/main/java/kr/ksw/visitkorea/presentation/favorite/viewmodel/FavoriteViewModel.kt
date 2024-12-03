package kr.ksw.visitkorea.presentation.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.model.Favorite
import kr.ksw.visitkorea.domain.usecase.favorite.DeleteFavoriteEntityUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.GetAllFavoriteEntityUseCase
import kr.ksw.visitkorea.presentation.common.DetailParcel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoriteEntityUseCase: GetAllFavoriteEntityUseCase,
    private val deleteFavoriteEntityUseCase: DeleteFavoriteEntityUseCase
): ViewModel() {
    private val _favoriteState = MutableStateFlow(FavoriteState())
    val favoriteState: StateFlow<FavoriteState>
        get() = _favoriteState.asStateFlow()

    private val _favoriteUiEffect = MutableSharedFlow<FavoriteUiEffect>(replay = 0)
    val favoriteUiEffect: SharedFlow<FavoriteUiEffect>
        get() = _favoriteUiEffect.asSharedFlow()

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

    fun onAction(action: FavoriteActions) {
        when(action) {
            is FavoriteActions.ClickFavoriteIcon -> {
                deleteFavoriteItem(action.favorite)
            }
            is FavoriteActions.ClickCardItem -> {
                startDetailActivity(action.data)
            }
        }
    }

    private fun deleteFavoriteItem(favorite: Favorite) {
        viewModelScope.launch {
            deleteFavoriteEntityUseCase(favorite)
        }
    }

    private fun startDetailActivity(data: DetailParcel) {
        viewModelScope.launch {
            _favoriteUiEffect.emit(FavoriteUiEffect.StartDetailActivity(data))
        }
    }
}