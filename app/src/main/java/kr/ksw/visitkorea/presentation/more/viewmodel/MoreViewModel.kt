package kr.ksw.visitkorea.presentation.more.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.R
import kr.ksw.visitkorea.domain.usecase.mapper.toMoreCardModel
import kr.ksw.visitkorea.domain.usecase.more.GetMoreListUseCase
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val getMoreListUseCase: GetMoreListUseCase
): ViewModel() {
    private val _moreState = MutableStateFlow(MoreState())
    val moreState: StateFlow<MoreState>
        get() = _moreState.asStateFlow()

    fun getMoreListByContentType(
        contentTypeId: String,
        forceFetch: Boolean = false
    ) {
        viewModelScope.launch {
            if(forceFetch) {
                _moreState.update {
                    it.copy(isRefreshing = true)
                }
                delay(500)
            }

            val moreListFlow = getMoreListUseCase(
                forceFetch,
                "126.9817290217",
                "37.5678958128",
                contentTypeId
            ).getOrNull()
            if(moreListFlow == null) {
                // Toast Effect
                _moreState.update {
                    it.copy(isRefreshing = false)
                }
                return@launch
            }
            val moreCardModelFlow = moreListFlow.map { pagingData ->
                pagingData.map {
                    it.toMoreCardModel()
                }
            }.cachedIn(viewModelScope)
            _moreState.update {
                it.copy(
                    moreCardModelFlow = moreCardModelFlow,
                    isRefreshing = false
                )
            }
        }
    }
}