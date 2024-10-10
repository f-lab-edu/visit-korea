package kr.ksw.visitkorea.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.usecase.mapper.toCommonCardModel
import kr.ksw.visitkorea.domain.usecase.search.GetListByKeywordUseCase
import kr.ksw.visitkorea.presentation.common.ContentType
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.home.viewmodel.HomeUiEffect
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getListByKeywordUseCase: GetListByKeywordUseCase
): ViewModel() {
    private val _searchState: MutableStateFlow<SearchState> = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState>
        get() = _searchState.asStateFlow()

    private val _searchUiEffect = MutableSharedFlow<SearchUiEffect>(replay = 0)
    val searchUiEffect: SharedFlow<SearchUiEffect>
        get() = _searchUiEffect.asSharedFlow()

    fun onAction(action: SearchActions) {
        when(action) {
            is SearchActions.UpdateSearchKeyword -> {
                _searchState.update {
                    it.copy(
                        searchKeyword = action.newKeyword
                    )
                }
            }
            is SearchActions.SubmitSearchKeyword -> {
                getListByKeyword()
            }
            is SearchActions.ClickCardItem -> {
                startDetailActivity(action.data)
            }
        }
    }

    private fun getListByKeyword() {
        viewModelScope.launch {
            _searchState.update {
                it.copy(
                    isLoadingImages = true
                )
            }
            val searchListFlow = getListByKeywordUseCase(
                searchState.value.searchKeyword
            ).getOrNull()?.map { pagingData ->
                pagingData.filter {
                    contentTypeFilter(it.contentTypeId)
                }.map {
                    it.toCommonCardModel()
                }
            }?.cachedIn(viewModelScope) ?: emptyFlow()

            delay(500)
            _searchState.update {
                it.copy(
                    searchCardModelFlow = searchListFlow,
                    isLoadingImages = false
                )
            }
        }
    }

    private fun startDetailActivity(data: DetailParcel) {
        viewModelScope.launch {
            _searchUiEffect.emit(SearchUiEffect.StartDetailActivity(data))
        }
    }

    private fun contentTypeFilter(contentType: String) = when(contentType) {
        ContentType.CULTURE.contentTypeId,
        ContentType.LEiSURE.contentTypeId,
        ContentType.RESTAURANT.contentTypeId,
        ContentType.TOURIST.contentTypeId -> true
        else -> false
    }
}