package kr.ksw.visitkorea.presentation.more.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
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
import kr.ksw.visitkorea.presentation.common.latitudeToStringOrDefault
import kr.ksw.visitkorea.presentation.common.longitudeToStringOrDefault
import kr.ksw.visitkorea.presentation.core.BaseViewModel
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class MoreViewModel @Inject constructor(
    private val getMoreListUseCase: GetMoreListUseCase,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
): BaseViewModel<MoreUiEffect>() {
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
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnCompleteListener { task ->
            getMoreListByContentType(
                contentTypeId = contentTypeId,
                forceFetch = forceFetch,
                lat = task.result.latitudeToStringOrDefault(),
                lng = task.result.longitudeToStringOrDefault()
            )
        }
    }

    private fun getMoreListByContentType(
        contentTypeId: String,
        forceFetch: Boolean,
        lat: String,
        lng: String,
    ) {
        viewModelScope.launch {
            val moreListFlow = getMoreListUseCase(
                forceFetch,
                lng,
                lat,
                contentTypeId
            ).getOrNull()?.map { pagingData ->
                pagingData.map {
                    it.toMoreCardModel()
                }
            }?.cachedIn(viewModelScope) ?: emptyFlow()

            delay(500)
            _moreState.update {
                it.copy(
                    moreCardModelFlow = moreListFlow,
                    isLoading = false,
                    isRefreshing = false
                )
            }
        }
    }
}