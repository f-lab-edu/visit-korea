package kr.ksw.visitkorea.presentation.festival.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.usecase.festival.GetFestivalListUseCase
import kr.ksw.visitkorea.domain.usecase.mapper.toFestival
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.home.viewmodel.HomeUiEffect
import javax.inject.Inject

@HiltViewModel
class FestivalViewModel @Inject constructor(
    private val getFestivalListUseCase: GetFestivalListUseCase
) : ViewModel() {
    private val _festivalState = MutableStateFlow(FestivalState())
    val festivalState: StateFlow<FestivalState>
        get() = _festivalState.asStateFlow()

    private val _festivalUiEffect = MutableSharedFlow<FestivalUiEffect>(replay = 0)
    val festivalUiEffect: SharedFlow<FestivalUiEffect>
        get() = _festivalUiEffect.asSharedFlow()

    init {
        getFestivalList()
    }

    fun onAction(action: FestivalActions) {
        when(action) {
            is FestivalActions.ClickFestivalCardItem -> {
                startDetailActivity(action.data)
            }
        }
    }

    private fun getFestivalList(forceFetch: Boolean = false) {
        viewModelScope.launch {
            val hotelListFlow = getFestivalListUseCase(
                forceFetch,
                "20241007",
                "20241007",
                null,
                null
            ).getOrNull()
            if(hotelListFlow != null) {
                val festivalModelFlow = hotelListFlow.map { pagingData ->
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

    private fun startDetailActivity(data: DetailParcel) {
        viewModelScope.launch {
            _festivalUiEffect.emit(FestivalUiEffect.StartDetailActivity(data))
        }
    }
}