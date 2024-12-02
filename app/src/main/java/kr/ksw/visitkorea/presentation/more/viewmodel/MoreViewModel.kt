package kr.ksw.visitkorea.presentation.more.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.usecase.mapper.toMoreCardModel
import kr.ksw.visitkorea.domain.usecase.more.GetMoreListUseCase
import kr.ksw.visitkorea.presentation.core.BaseViewModel
import kr.ksw.visitkorea.presentation.core.getResult
import kr.ksw.visitkorea.presentation.core.viewModelLauncher
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val getMoreListUseCase: GetMoreListUseCase,
    fusedLocationProviderClient: FusedLocationProviderClient,
): BaseViewModel<MoreUiEffect>(fusedLocationProviderClient) {
    private val _moreState = MutableStateFlow(MoreState())
    val moreState: StateFlow<MoreState>
        get() = _moreState.asStateFlow()

    fun onAction(action: MoreActions) {
        when(action) {
            is MoreActions.ClickCardItem -> {
                postUIEffect(
                    MoreUiEffect.StartDetailActivity(action.data)
                )
            }
        }
    }

    fun getMoreListByContentType(
        contentTypeId: String,
        forceFetch: Boolean = false
    ) {
        if(forceFetch) {
            _moreState.update {
                it.copy(isRefreshing = true)
            }
        }
        getWithLocation { lat, lng ->
            getMoreListByContentType(
                contentTypeId = contentTypeId,
                forceFetch = forceFetch,
                lat = lat,
                lng = lng
            )
        }
    }

    private fun getMoreListByContentType(
        contentTypeId: String,
        forceFetch: Boolean,
        lat: String,
        lng: String,
    ) {
        viewModelLauncher {
            getMoreListUseCase(
                forceFetch,
                lng,
                lat,
                contentTypeId
            ).getResult { result ->
                val moreFlow = result.map { pagingData ->
                    pagingData.map {
                        it.toMoreCardModel()
                    }
                }.cachedIn(viewModelScope)
                _moreState.update { state ->
                    state.copy(
                        moreCardModelFlow = moreFlow,
                        isLoading = false,
                        isRefreshing = false
                    )
                }
            }
        }
    }
}