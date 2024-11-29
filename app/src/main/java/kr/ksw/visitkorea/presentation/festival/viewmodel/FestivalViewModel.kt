package kr.ksw.visitkorea.presentation.festival.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kr.ksw.visitkorea.domain.usecase.festival.GetFestivalListUseCase
import kr.ksw.visitkorea.domain.usecase.mapper.toFestival
import kr.ksw.visitkorea.presentation.core.BaseViewModel
import kr.ksw.visitkorea.presentation.core.getResult
import kr.ksw.visitkorea.presentation.core.viewModelLauncher
import javax.inject.Inject

@HiltViewModel
class FestivalViewModel @Inject constructor(
    private val getFestivalListUseCase: GetFestivalListUseCase
) : BaseViewModel<FestivalUiEffect>() {
    private val _festivalState = MutableStateFlow(FestivalState())
    val festivalState: StateFlow<FestivalState>
        get() = _festivalState.asStateFlow()

    init {
        getFestivalList()
    }

    fun onAction(action: FestivalActions) {
        when(action) {
            is FestivalActions.ClickFestivalCardItem -> {
                postUIEffect(FestivalUiEffect.StartDetailActivity(action.data))
            }
        }
    }

    private fun getFestivalList(forceFetch: Boolean = false) {
        viewModelLauncher {
            getFestivalListUseCase(
                forceFetch,
                "20241007",
                "20241007",
                null,
                null
            ).getResult { result ->
                val festivalModelFlow = result.map { pagingData ->
                    pagingData.map {
                        it.toFestival()
                    }
                }.cachedIn(viewModelScope)
                _festivalState.update {
                    it.copy(
                        festivalModelFlow = festivalModelFlow
                    )
                }
            }
        }
    }
}