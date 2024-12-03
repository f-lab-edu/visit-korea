package kr.ksw.visitkorea.presentation.more.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
import kr.ksw.visitkorea.presentation.core.BaseViewModel
import kr.ksw.visitkorea.presentation.core.viewModelLauncher
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class MoreViewModel @Inject constructor(
    private val getMoreListUseCase: GetMoreListUseCase,
    private val getAllFavoriteEntityUseCase: GetAllFavoriteEntityUseCase,
    private val upsertFavoriteEntityUseCase: UpsertFavoriteEntityUseCase,
    private val deleteFavoriteEntityByContentIdUseCase: DeleteFavoriteEntityByContentIdUseCase,
    fusedLocationProviderClient: FusedLocationProviderClient,
): BaseViewModel<MoreUiEffect>(fusedLocationProviderClient) {
    private val _moreState = MutableStateFlow(MoreState())
    val moreState: StateFlow<MoreState>
        get() = _moreState.asStateFlow()

    private val favoriteIdFlow = MutableStateFlow(setOf<String>())

    fun onAction(action: MoreActions) {
        when(action) {
            is MoreActions.ClickCardItem -> {
                postUIEffect(
                    MoreUiEffect.StartDetailActivity(action.data)
                )
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