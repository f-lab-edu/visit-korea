package kr.ksw.visitkorea.presentation.more.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import kr.ksw.visitkorea.domain.usecase.favorite.DeleteFavoriteEntityByContentIdUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.GetAllFavoriteEntityUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.UpsertFavoriteEntityUseCase
import kr.ksw.visitkorea.domain.usecase.mapper.toMoreCardModel
import kr.ksw.visitkorea.domain.usecase.more.GetMoreListUseCase
import kr.ksw.visitkorea.presentation.common.DEFAULT_LATITUDE
import kr.ksw.visitkorea.presentation.common.DEFAULT_LONGITUDE
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.common.latitudeToStringOrDefault
import kr.ksw.visitkorea.presentation.common.longitudeToStringOrDefault
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class MoreViewModel @Inject constructor(
    private val getMoreListUseCase: GetMoreListUseCase,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val getAllFavoriteEntityUseCase: GetAllFavoriteEntityUseCase,
    private val upsertFavoriteEntityUseCase: UpsertFavoriteEntityUseCase,
    private val deleteFavoriteEntityByContentIdUseCase: DeleteFavoriteEntityByContentIdUseCase
): ViewModel() {
    private val _moreState = MutableStateFlow(MoreState())
    val moreState: StateFlow<MoreState>
        get() = _moreState.asStateFlow()

    private val _moreUiEffect = MutableSharedFlow<MoreUiEffect>(replay = 0)
    val moreUiEffect: SharedFlow<MoreUiEffect>
        get() = _moreUiEffect.asSharedFlow()

    private val favoriteIdFlow = MutableStateFlow(setOf<String>())

    fun onAction(action: MoreActions) {
        when(action) {
            is MoreActions.ClickCardItem -> {
                startDetailActivity(action.data)
            }
            is MoreActions.ClickFavoriteIconUpsert -> {
                upsertFavorite(action.favorite)
            }
            is MoreActions.ClickFavoriteIconDelete -> {
                deleteFavorite(action.contentId)
            }
        }
    }

    fun getAllFavoriteEntity() {
        viewModelScope.launch {
            getAllFavoriteEntityUseCase().collect { entities ->
                favoriteIdFlow.update {
                    entities.map {
                        it.contentId
                    }.toSet()
                }
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
            val result = try {
                task.result.latitudeToStringOrDefault() to
                        task.result.longitudeToStringOrDefault()
            } catch (e: Exception) {
                DEFAULT_LATITUDE to DEFAULT_LONGITUDE
            }
            getMoreListByContentType(
                contentTypeId = contentTypeId,
                forceFetch = forceFetch,
                lat = result.first,
                lng = result.second
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
                    moreCardModelFlow = combine(
                        moreListFlow,
                        favoriteIdFlow
                    ) { paging, favorites ->
                        paging.map { more ->
                            if(favorites.contains(more.contentId)) {
                                more.copy(isFavorite = true)
                            } else {
                                more.copy(isFavorite = false)
                            }
                        }
                    },
                    isLoading = false,
                    isRefreshing = false
                )
            }
        }
    }

    private fun startDetailActivity(data: DetailParcel) {
        viewModelScope.launch {
            _moreUiEffect.emit(MoreUiEffect.StartDetailActivity(data))
        }
    }

    private fun upsertFavorite(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            upsertFavoriteEntityUseCase(favoriteEntity)
        }
    }

    private fun deleteFavorite(contentId: String) {
        viewModelScope.launch {
            deleteFavoriteEntityByContentIdUseCase(contentId)
        }
    }
}