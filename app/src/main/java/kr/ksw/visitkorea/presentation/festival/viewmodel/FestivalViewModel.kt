package kr.ksw.visitkorea.presentation.festival.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.usecase.festival.GetFestivalListUseCase
import kr.ksw.visitkorea.domain.usecase.mapper.toFestival
import javax.inject.Inject

@HiltViewModel
class FestivalViewModel @Inject constructor(
    private val getFestivalListUseCase: GetFestivalListUseCase
) : ViewModel() {
    private val _festivalState = MutableStateFlow(FestivalState())
    val festivalState: StateFlow<FestivalState>
        get() = _festivalState.asStateFlow()

    init {
        getFestivalList()
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
}